package cn.les.kghj;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jfree.ui.RefineryUtilities;
import cn.thu.utility.ConnectOracle;

/**
 * 
 * @author DUYUE 
 * Date:2017-09-29
 *
 */
public class TrackBasicInfo {

	static ConnectOracle cOracle;
	static String outDir = "D:/data/kghj_flight/history11/"; //数据写出目录

	public static void main(String[] args) {
		// 连接数据库
		cOracle = new ConnectOracle();
		cOracle.connect("scott", "tiger");

		// 统计航迹基本信息
		TrackBasicInfo tCluster = new TrackBasicInfo();
		Map<Integer, String> acids = tCluster.getAcids();
		List<String> all_acids = tCluster.getAcidnum(acids);
		System.out.println("数据中所有航班号数：" + all_acids.size());
		// 鏍规嵁杈撳叆鐨勮埅鐝彿锛岃嚜鍔ㄨ绠楀嚭鏃堕棿璺ㄥ害
		/*
		 * String flt_acid="CCA1590"; Map< Integer,String> range_time=
		 * tCluster.getFlightDate(flt_acid); for(int
		 * k=0;k<range_time.size();k++){ Map<Integer, Track> map =
		 * tCluster.getSingleTra(flt_acid, range_time.get(k));
		 * //tCluster.plotSHLL(map); }
		 */

		//所有北京-上海航班号
		List<String> query_acids = new ArrayList<String>();
		query_acids.add("CCA1858");
		query_acids.add("CCA1590");
		query_acids.add("CCA1832");
		query_acids.add("CCA1502");
		query_acids.add("CCA1532");
		query_acids.add("CCA1558");
		query_acids.add("CCA1884");
		query_acids.add("CCA1518");
		query_acids.add("CCA1522");
		query_acids.add("CCA1516");
		query_acids.add("CCA1836");
		query_acids.add("CCA156");
		query_acids.add("CCA1550");
		query_acids.add("CCA1856");
		query_acids.add("CCA3201");
		query_acids.add("CCA1886");
		query_acids.add("YZR7980");

		// 装载所有BJ-SH的Trajectory
		List<Map<Integer, Track>> Trajectory = new ArrayList<Map<Integer, Track>>();
		for (int n = 0; n < query_acids.size(); n++) {
			Map<Integer, String> range_tim = tCluster.getFlightDate(query_acids.get(n));
			for (int m = 0; m < range_tim.size(); m++) {
				Map<Integer, Track> map = tCluster.getSingleTra(query_acids.get(n), range_tim.get(m));
				Trajectory.add(map);
			}
		}
		System.out.println("BJ-SH航班号数：" + query_acids.size() + " 航迹数：" + Trajectory.size());

		// 选取其中一条航迹
		Map<Integer, Track> TRi = Trajectory.get(1);
		TraClus tClus = new TraClus();
		tClus.getCP(TRi);

		// 画出四维剖面图
		tCluster.plotSHLL(TRi);
		tCluster.plotSHLL(tClus.getCP(TRi));

		// 写出航迹数据
		for (int i = 0; i < Trajectory.size(); i++) {
			// tCluster.writeDataSet(Trajectory.get(i)); //写出原始航迹

			TraClus tClus1 = new TraClus();
			Map<Integer, Track> CPi = tClus1.getCP(Trajectory.get(i));//获取对应的特征航迹

			// tCluster.writeDataSet(CPi); //写出特征点航迹
			System.out.println(i + " 原始航迹点个数：" + Trajectory.get(i).size() + " 特征点数：" + CPi.size());
		}
		System.out.println("=======================================");
		
		
		//写出其中一条航迹对应的28条模拟数据
		TraTSclus ts = new TraTSclus();
		List<Map<Integer, Track>> history = ts.makeTrajects(TRi);
		for(int i=0;i<history.size();i++){
			//tCluster.writeDataSet(history.get(i));//写出模拟航迹
		}
		//聚类并写出
		Map<Integer, Track> centerLine = ts.getCenterLine(history);
		//tCluster.writeDataSet(centerLine);	
	}

	/**
	 * 缁熻鎵�鏈夌殑鑸彮缂栧彿鍙婂叾瀵瑰簲鐨勯琛岀偣鏁�
	 * 
	 * @return
	 */
	public Map<Integer, String> getAcids() {
		String sql = "select distinct acid, count(*) from radar group by acid,acid order by acid";
		ResultSet rSet = cOracle.queryResult(sql);
		Map<Integer, String> acidMap = new HashMap<Integer, String>();
		int tmp = 0;
		try {
			while (rSet.next()) {
				tmp++;
				acidMap.put(tmp, rSet.getString("acid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acidMap;
	}

	/**
	 * 鏌ヨ鑸彮缂栧彿
	 * 
	 * @param map
	 */
	public List<String> getAcidnum(Map<Integer, String> map) {
		List<String> all_acids = new ArrayList<String>();
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Integer kk = iterator.next();
			String vv = map.get(kk);
			all_acids.add(vv);
			// System.out.println(kk+" "+vv);
		}
		return all_acids;
	}

	/**
	 * 根据输入的航班号，自动计算出飞行日期跨度
	 * 
	 * @param acid
	 */
	public Map<Integer, String> getFlightDate(String acid) {
		String sql = "select timestamped from radar where acid='" + acid + "' order by timestamped ";
		ResultSet rSet = cOracle.queryResult(sql);
		String sfulltime;
		String sdifftime;
		Map<Integer, String> map = new HashMap<Integer, String>();
		int tmp = 0;
		try {
			while (rSet.next()) {
				sfulltime = rSet.getString("timestamped");
				sdifftime = sfulltime.substring(0, 8);
				if (!map.containsValue(sdifftime)) {
					map.put(tmp, sdifftime);
					tmp++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.print("flight acid:" + acid + " date:" + map.size() + " ");
		if (map.size() == 0) {
			System.out.println();
		} else {
			for (int i = 0; i < map.size(); i++) {
				if (i == map.size() - 1) {
					System.out.print(map.get(i) + " \n");
				} else {
					System.out.print(map.get(i) + " ");
				}
			}
		}
		return map;
	}

	/**
	 * 根据输入的航班号、飞行日期获得飞行轨迹
	 * 
	 * @param acid
	 * @param date
	 */
	public Map<Integer, Track> getSingleTra(String acid, String date) {
		String sql = " select * from radar where acid like '" + acid + "%' and timestamped like '" + date
				+ "%' order by timestamped ";
		ResultSet rSet = cOracle.queryResult(sql);
		Map<Integer, Track> map = new HashMap<Integer, Track>();
		int tmp = 0;
		try {
			while (rSet.next()) {

				Track track = new Track();
				track.setTrackNo(Integer.valueOf(rSet.getString("trackno")));
				track.setX(Double.valueOf(rSet.getString("x")));
				track.setY(Double.valueOf(rSet.getString("y")));
				track.setMcl(Integer.valueOf(rSet.getString("mcl")));
				track.setSpeed(Double.valueOf(rSet.getString("speed")));
				track.setDir(Double.valueOf(rSet.getString("dir")));
				track.setSSR(rSet.getString("ssr"));
				track.setMsg_length(Integer.valueOf(rSet.getString("msg_length")));
				track.setTimestamp(rSet.getString("timestamped"));
				track.setUlStatus(Long.parseLong(rSet.getString("ulstatus")));
				track.setWarnMark(Integer.valueOf(rSet.getString("warnmark")));
				track.setPlanno(Integer.valueOf(rSet.getString("planno")));
				track.setTimeout(Integer.valueOf(rSet.getString("timeout")));
				track.setLonitude(Double.valueOf(rSet.getString("longitude")));
				track.setLatitude(Double.valueOf(rSet.getString("latitude")));
				track.setACID(rSet.getString("acid"));
				map.put(tmp, track);
				tmp++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 */
	public void getMultipleTra() {

	}

	/**
	 * 绘制4维剖面图
	 * 
	 * @param map
	 */
	public void plotSHLL(Map<Integer, Track> map) {
		String acid = map.get(2).getACID();
		String time = map.get(2).getTimestamp().substring(0, 8);
		StringBuffer sBuffer = new StringBuffer();
		String tittle = sBuffer.append(acid).append(" ").append(time).toString();

		PlotTraSH chart = new PlotTraSH("民航飞行记录表  SH", tittle, map);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);

		PlotTraLL chart1 = new PlotTraLL("民航飞行记录表  LL", tittle, map);
		chart1.pack();
		RefineryUtilities.centerFrameOnScreen(chart1);
		chart1.setVisible(true);
	}

	/**
	 * 将单条飞行轨迹写出
	 * 
	 * @param map
	 */
	public void writeDataSet(Map<Integer, Track> map) {
		if (map.size() > 0) {

			String spt = System.getProperty("line.separator");
			try {
				String acid = map.get(0).getACID();
				String date = map.get(0).getTimestamp().substring(0, 8);
				StringBuffer filename = new StringBuffer();
				filename = filename.append(outDir).append(acid).append("_").append(date).append(".txt");
				FileWriter fWriter = new FileWriter(filename.toString());

				Iterator<Integer> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					Integer tmp = iterator.next();
					fWriter.write(map.get(tmp).getTrackNo() + " ");
					fWriter.write(map.get(tmp).getX() + " ");
					fWriter.write(map.get(tmp).getY() + " ");
					fWriter.write(map.get(tmp).getMcl() + " ");

					fWriter.write(map.get(tmp).getSpeed() + " ");
					fWriter.write(map.get(tmp).getDir() + " ");
					fWriter.write(map.get(tmp).getSSR() + " ");
					fWriter.write(map.get(tmp).getMsg_length() + " ");

					fWriter.write(map.get(tmp).getTimestamp() + " ");
					fWriter.write(map.get(tmp).getUlStatus() + " ");
					fWriter.write(map.get(tmp).getWarnMark() + " ");
					fWriter.write(map.get(tmp).getPlanno() + " ");

					fWriter.write(map.get(tmp).getTimeout() + " ");
					fWriter.write(map.get(tmp).getLonitude() + " ");
					fWriter.write(map.get(tmp).getLatitude() + " ");
					fWriter.write(map.get(tmp).getACID() + " ");

					fWriter.write(spt);
				}
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
