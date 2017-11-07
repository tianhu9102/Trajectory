package cn.thu.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
 * function: connect MySql
 * done!
 *
 */
public class ConnectMySql {

	public static void main(String[] args) {
		ConnectMySql cSql = new ConnectMySql();
		cSql.connect();
	}
	
	public void connect(){
		String url = "jdbc:mysql://127.0.0.1:3306/dy_test";
		String user ="root";
		String password = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user, password);
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery("select * from student");
			
			while(rSet.next()){
				String sno = rSet.getString("sno");
				String sname = rSet.getString("sname");
				String sex = rSet.getString("sex");
				String bdate = rSet.getString("bdate");
				String height = rSet.getString("height");
				System.out.println(sno+" "+" "+sname+" "+sex+" "+bdate+" "+height);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
