package cn.les.kghj;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.jfree.ui.RefineryUtilities;
import cn.thu.utility.ConnectMySql;
import cn.thu.utility.ConnectOracle;

/**
 * @author 14206
 * 
 */
public class TrackCalculate {
	
	//1.读取数据
	//2.解析、统计相关参数  
	//3.显示单一航线:根据时间戳、航班号
	//剖面图：水平轨迹、速度、高度  
    static String title ="";    
	static ResultSet rSet; 
	public static void main(String[] args) {
		TrackCalculate tCalculate = new TrackCalculate();
		Map<Integer, Track> record =  tCalculate.connectDB("kghj","kghj");
		System.out.println("飞行记录点数:"+record.size());
		
		tCalculate.writeHistory(record, "d:/data/kghj_flight/all.txt");

		tCalculate.plotSH(record);
		tCalculate.plotLL(record);
	}
	
	// CXA8229  20170605
	// CSH9437  20170603
	public Map<Integer, Track> connectDB(String usrname,String passwd){
		ConnectOracle  cl = new ConnectOracle();
		cl.connect(usrname,passwd);
		
		//固定航班号、固定时间段   对应的航迹记录点		
		String flt_acid = "CXA8229"; 
		String flt_time = "20170605"; 
		//String sqlString = "select * from radar where acid='"+flt_acid+"' and timestamped like '"+flt_time+"%' order by timestamped ";
		//String sqlString = "select * from radar where acid like 'CCA1858%' or acid like 'CCA1590%' or acid like 'CCA1832%' order by timestamped ";
		String sqlString = "select * from radar where acid like 'AAL137%' or acid like 'AAL186%' or acid like 'AAL187%'    "
				+ " or acid like 'CCA1590%' or acid like 'CCA1832%'  order by timestamped ";
		rSet =  cl.queryResult(sqlString);
		Map<Integer, Track> map = new HashMap<Integer, Track>();
		int tmp=0;
		try {
			while (rSet.next()) {
				tmp++;
				Track track = new Track();
				track.setTrackNo(Integer.valueOf(rSet.getString("trackno")));
				track.setX(Double.valueOf(rSet.getString("x")) );
				track.setY(Double.valueOf(rSet.getString("y")) );
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		//赋值图表标题变量
		StringBuffer sBuffer = new StringBuffer();
		sBuffer = sBuffer.append(map.get(1).getACID());
		sBuffer = sBuffer.append(" ");
		sBuffer = sBuffer.append(map.get(1).getTimestamp().substring(0,8));
		title=sBuffer.toString();
	
		return map;
	}
	
	/**
	 * 飞行速度-高度剖面图
	 * @param map
	 */
	public void plotSH(Map<Integer, Track> map){
	  PlotTraSH chart = new PlotTraSH("中国民航飞行记录表-SH", title,map);
      chart.pack( );          
      RefineryUtilities.centerFrameOnScreen(chart );          
      chart.setVisible( true ); 
	}

	/**
	 * 飞行经度-纬度剖面图（水平轨迹）
	 * @param map
	 */
	public void plotLL(Map<Integer, Track> map){
		  PlotTraLL chart = new PlotTraLL("中国民航飞行记录表-LL", title,map);
		  chart.pack( );          
		  RefineryUtilities.centerFrameOnScreen( chart );          
		  chart.setVisible( true ); 
	}
	
	/**
	 * 将结果集以文本形式写出
	 * 参考： http://www.cnblogs.com/bayes/p/5478862.html
	 * @param map
	 * @param outPath
	 */
	public void writeHistory(Map<Integer, Track> map, String outPath){
		String spt = System.getProperty("line.separator");
		System.out.println("writing.......");
		try {
			FileWriter fWriter = new FileWriter(outPath);
			Iterator<Integer> iterator = map.keySet().iterator();
			while (iterator.hasNext()) { 
					Integer tmp = iterator.next();
					fWriter.write( map.get(tmp).getTrackNo()+" "); 
					fWriter.write( map.get(tmp).getX()+" "); 
					fWriter.write( map.get(tmp).getY()+" "); 
					fWriter.write( map.get(tmp).getMcl()+" "); 
					fWriter.write( map.get(tmp).getSpeed()+" "); 
					fWriter.write( map.get(tmp).getDir()+" "); 
					fWriter.write( map.get(tmp).getSSR()+" "); 
					fWriter.write( map.get(tmp).getMsg_length()+" "); 
					fWriter.write( map.get(tmp).getTimestamp()+" "); 
					fWriter.write( map.get(tmp).getUlStatus()+" "); 
					fWriter.write( map.get(tmp).getWarnMark()+" "); 
					fWriter.write( map.get(tmp).getPlanno()+" "); 
					fWriter.write( map.get(tmp).getTimeout()+" "); 
					fWriter.write( map.get(tmp).getLonitude()+" "); 
					fWriter.write( map.get(tmp).getLatitude()+" "); 
					fWriter.write( map.get(tmp).getACID()); 
					fWriter.write( spt); 
			}
			fWriter.close();
			System.out.println("write finished! ");
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
}
