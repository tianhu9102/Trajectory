package cn.les.jl;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jfree.ui.RefineryUtilities;

import cn.les.kghj.PlotTraLL;
import cn.les.kghj.PlotTraSH;
import cn.les.kghj.Track;
import cn.thu.utility.ConnectOracle;

/**
 * 
 * @author 14206
 * 
 */
public class ReadData {

	//统计基本信息 sql1语句或者sql2
	static String sql1="select acid,trackno,min(timestamped),max(timestamped),count(1) from RADAR t group by acid,trackno order by acid,trackno;";
	static String sql2="select acid,trackno,mint,maxt,cnt from RADAR_AT";
	//根据acid和trackno唯一确定一次飞行计划
	String sql3="select * from RADAR where acid='AAL137' and trackno=75 order by timestamped";
	
	static ConnectOracle cOracle;
	static String outDir = "D:/deve/QtProject/Trajectory/kghj_flight/realData/"; //数据写出目录
	
	static {
		cOracle = new ConnectOracle();
		cOracle.connect("scott", "tiger");
	}
	
	public HashMap<Integer,String> getAcidTrackno() {
		ArrayList list = cOracle.queryResultList(sql2);
		PreparedStatement pStatement = (PreparedStatement) list.get(0);
		ResultSet rSet = (ResultSet) list.get(1);
		
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		int tmp = 0;
		try {
			while (rSet.next()) {
				tmp++;
				map.put(tmp, rSet.getString("acid")+"_"+rSet.getString("trackno"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		try {
			rSet.close();
			pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;		
	}  
	
	public void doPrint(Map<Integer,String> map){
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Integer kk = iterator.next();
			String vv = map.get(kk);
		    System.out.println(kk+" "+vv);
		}
	}
	
	/**
	 * 功能：根据输入的acid_trackno获取一次飞行计划
	 * @param str
	 * @return
	 * @throws SQLException 
	 */
	public Map<Integer, Track> getSingleTra(String acid_trackno)  {
		String[]  am = acid_trackno.split("_");
		String acid = am[0];
		String trackno = am[1];
		//select * from RADAR where acid='AAL137' and trackno=75 order by timestamped
		String sql = " select * from radar where acid = '" + acid + "' and trackno= '"+ trackno
				+ "' order by timestamped ";
		
		ArrayList list = cOracle.queryResultList(sql);
		PreparedStatement pStatement = (PreparedStatement) list.get(0);
		ResultSet rSet = (ResultSet) list.get(1);
		
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
				tmp++;
				map.put(tmp, track);
				//System.out.println(tmp+": "+track.getACID()+" "+track.getTrackNo()+" "+track.getTimestamp());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rSet.close();
			pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public void writeDataSet(Map<Integer, Track> map) {
		if (map.size() > 0) {
			String spt = System.getProperty("line.separator");
			try {
				String acid = map.get(1).getACID();
				String trackno =  String.valueOf(map.get(1).getTrackNo());
				StringBuffer filename = new StringBuffer();
				filename = filename.append(outDir).append(acid).append("_").append(trackno).append(".txt");
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

	public void plotSHLL(Map<Integer, Track> map) {
		String acid = map.get(2).getACID();
		String trackno =  String.valueOf(map.get(2).getTrackNo());
		StringBuffer sBuffer = new StringBuffer();
		String tittle = sBuffer.append(acid).append("_").append(trackno).toString();

		PlotTraSH chart = new PlotTraSH("民航飞行记录表  SH", tittle, map);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);

		PlotTraLL chart1 = new PlotTraLL("民航飞行记录表  LL", tittle, map);
		chart1.pack();
		RefineryUtilities.centerFrameOnScreen(chart1);
		chart1.setVisible(true);
	}
	
}
