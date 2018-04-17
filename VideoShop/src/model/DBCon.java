package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon {

	static Connection con;

	//싱글톤패턴
	private DBCon() throws Exception{//다른곳에서 생성 못하게함 new 못하게
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@70.12.115.68:1521:orcl";
		String user = "scott";
		String pass = "tiger";
		con = DriverManager.getConnection(url, user, pass);
	}
	
	public static Connection getConnection() throws Exception{
		if(con == null){
			new DBCon();
		}
		return con;
	}
	
	
}
