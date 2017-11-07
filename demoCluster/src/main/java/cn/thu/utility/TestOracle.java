package cn.thu.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * ���ܣ��������ݿⲢ��ѯ������
 * ���Գɹ�
 *
 */
public class TestOracle {

	public static void main(String[] args) throws SQLException {
		
		ConnectOracle  cl = new ConnectOracle();
		cl.connect("kghj","kghj");
		
		String queryString = "select * from radar where acid='CCA1832' ";
		ResultSet rSet =  cl.queryResult(queryString);
		
		int temp =0;
		while(rSet.next()){
			temp++;
			System.out.println(
					temp+": "+
					"  ACID:"+rSet.getString("ACID")+
					"  TRACKNO:"+rSet.getString("TRACKNO")+
					"  X:"+rSet.getString("X")+
					"  Y:"+rSet.getString("Y")+
					"  MCL:"+rSet.getString("MCL")+
					"  SPEED:"+rSet.getString("SPEED")+
					"  DIR:"+rSet.getString("DIR")+
					"  SSR:"+rSet.getString("SSR")+
					"  TIMESTAMPED:"+rSet.getString("TIMESTAMPED")+
					"  ULSTATUS:"+rSet.getString("ULSTATUS")					
					);
			
			/*System.out.println(
			track.getTrackNo()+" "+
			track.getX()+" "+
			track.getY()+" "+
			track.getMcl()+" "+
			track.getSpeed()+" "+
			track.getDir()+" "+
			track.getSSR()+" "+
			track.getMsg_length()+" "+
			track.getTimestamp()+" "+
			track.getUlStatus()+" "+
			track.getWarnMark()+" "+
			track.getPlanno()+" "+
			track.getTimeout()+" "+
			track.getLonitude()+" "+
			track.getLatitude()+" "+
			track.getACID());*/
		}//
		
	}

	
}
