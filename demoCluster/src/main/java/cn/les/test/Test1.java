package cn.les.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Test1 {

	public static void main(String[] args) {
		
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("connecting ...");
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";  //192.168.24.27
		try {
			connection = DriverManager.getConnection(url, "scott", "tiger");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rSet = null; 
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement("select acid,trackno,mint,maxt,cnt from RADAR_AT");
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList am = new ArrayList();
		am.add( rSet);
		am.add( pStatement );
		System.out.println("hello: "+ am.get(1));
	}

}
