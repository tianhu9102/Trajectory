package cn.thu.utility;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * function: connect Oracle database
 */

public class ConnectOracle {
	
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
			
			ResultSet rt = this.queryResult("select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual");
			while (rt.next()) {
				System.out.println("username:"+dbUser+" time:"+rt.getString(1)+"\n");	
			}	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}// �����쳣	
	}
	
	
	/**
	 * 
	 * @param queryString
	 * @return
	 */
	public ResultSet queryResult(String queryString) {
		ResultSet rSet = null; 
		PreparedStatement pStatement = null;
		try {
			pStatement = connection.prepareStatement(queryString);
			rSet = pStatement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rSet;
	}
	
}
