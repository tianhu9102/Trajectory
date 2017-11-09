package cn.thu.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasicConnectOracle {
	
	Connection connection = null;
	
	/**
	 * 
	 * @param dbUser
	 * @param dbPwd
	 */
	public void connect(String dbUser, String dbPwd) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("connecting ...");
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";  //192.168.24.27
			connection = DriverManager.getConnection(url, dbUser, dbPwd);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param queryString
	 * @return
	 */
	public ArrayList queryResult(String queryString) {
		ArrayList list = new ArrayList();
		ResultSet rSet = null; 
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(queryString);
			rSet = pStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		list.add(pStatement);
		list.add(rSet);
			
		return list;
	}
	
}
