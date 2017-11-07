package cn.les.kghj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author DUYUE
 * 功能：基于时间-空间的航迹聚类
 * 参考：http://www.docin.com/p-736487429.html?docfrom=rrela
 *     http://www.cnblogs.com/xinfang520/p/7684578.html
 *
 */
public class TraTSclus {
	
	/**
	 * 功能：生成模拟的历史航迹数据，根据输入的一条真实航迹生成对应的28条模拟航迹，包括真实数据一共29条数据
	 * 说明：在后期拿到真正的历史航迹数据以后，可删除此函数
	 * @param TRi
	 * @return
	 */
	public List<Map<Integer, Track>>  makeTrajects(Map<Integer,Track> TRi){
		List<Map<Integer, Track>> historyTras = new ArrayList<Map<Integer, Track>>();
		
		//三个随机偏移量
		double dx=0.0;
		double dy=0.0;
		double dmcl=0.0;
		int ddmcl=0;
		int kk=0;
		String tpt="";
		for(int i=0;i<56;i++){
			if(i<10){
				tpt="170";
			}else {
				tpt="17";
			}
			
			Map<Integer, Track> singleTra = new HashMap<Integer, Track>();
			
			for(int index=0;index<TRi.size();index++){
				dx = Math.random() * 330 -20; //[-20,310]   k* (m-n) +n
				dy = Math.random() * 56 -30; //[-30,26]
				dmcl = Math.random() * 100 + 15; //[15,115]
				ddmcl =  (int) dmcl;
				
				Track track = new Track();
				track.setTrackNo(TRi.get(index).getTrackNo());
				
				track.setX(TRi.get(index).getX()+  dx * Math.pow(1,index+1 ) );//分散于原始数据两侧 
				track.setY(TRi.get(index).getY()+  dy * Math.pow(1,index+1 ));
				kk = (int) (TRi.get(index).getMcl()+ ddmcl* Math.pow(1,index+1 ));
				track.setMcl( kk ); 
				
				track.setSpeed(TRi.get(index).getSpeed());
				track.setDir(TRi.get(index).getDir());
				track.setSSR(TRi.get(index).getSSR());
				track.setMsg_length(TRi.get(index).getMsg_length());
				//修改时间戳 ，避免写出文件时名称重复
				track.setTimestamp(TRi.get(index).getTimestamp().replace(TRi.get(index).getTimestamp().substring(0, 4), tpt+ String.valueOf(i) ));

				track.setUlStatus(TRi.get(index).getUlStatus());
				track.setWarnMark(TRi.get(index).getWarnMark());
				track.setPlanno(TRi.get(index).getPlanno());
				track.setTimeout(TRi.get(index).getTimeout());
				track.setLonitude(TRi.get(index).getLonitude());
				track.setLatitude(TRi.get(index).getLatitude());
				track.setACID(TRi.get(index).getACID());
				
				singleTra.put(index, track);
				
//				System.out.println(i+":"+index+" ["+
//				       track.getX()+" "+TRi.get(index).getX()+" "+dx+"; "+
//					   track.getY()+" "+TRi.get(index).getY() +" "+dy+"; "+
//				       track.getMcl()+" "+TRi.get(index).getMcl()+" "+ddmcl +" ]");
			}
			historyTras.add(singleTra);//模拟数据
		}
		
		historyTras.add(TRi);//添加一条输入的真实数据
		return historyTras;
	}
	

	/**
	 * 功能： 根据输入的历史数据，获得一条聚类航迹
	 * @param historyTras
	 * @return
	 */
	public Map<Integer, Track> getCenterLine(List<Map<Integer, Track>> historyTras){
		Map<Integer, Track> centerLine = new HashMap<Integer, Track>();
	
		double x=0.0;
		double y=0.0;
		int mcl=0;
			
		for(int i=0;i<historyTras.get(0).size();i++){
			x=0.0;
			y=0.0;
			mcl=0;
			
			Track track = new Track();
			for(int j=0;j<historyTras.size();j++){
				x +=historyTras.get(j).get(i).getX();
				y +=historyTras.get(j).get(i).getY();
				mcl +=historyTras.get(j).get(i).getMcl();	
			}
			
			track.setTrackNo(historyTras.get(0).get(i).getTrackNo());
			track.setX(x/historyTras.size());
			track.setY(y/historyTras.size());
			track.setMcl(mcl/historyTras.size());
			track.setSpeed(historyTras.get(0).get(i).getSpeed());
			track.setDir(historyTras.get(0).get(i).getDir());
			track.setSSR(historyTras.get(0).get(i).getSSR());
			track.setMsg_length(historyTras.get(0).get(i).getMsg_length());
			track.setTimestamp(historyTras.get(0).get(i).getTimestamp().replace(historyTras.get(0).get(i).getTimestamp().substring(0, 4), "AAAA" ));
			track.setUlStatus(historyTras.get(0).get(i).getUlStatus());
			track.setWarnMark(historyTras.get(0).get(i).getWarnMark());
			track.setPlanno(historyTras.get(0).get(i).getPlanno());
			track.setTimeout(historyTras.get(0).get(i).getTimeout());
			track.setLonitude(historyTras.get(0).get(i).getLonitude());
			track.setLatitude(historyTras.get(0).get(i).getLatitude());
			track.setACID(historyTras.get(0).get(i).getACID());
			
			centerLine.put(i, track);
		}
			
		System.out.println(historyTras.size()+" "+historyTras.get(0).size());
		return centerLine;
	}
		
}
