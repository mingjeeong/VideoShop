package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class RentModel {

	Connection con;
	DBCon db;

	public RentModel() throws Exception {

		// 1. 드라이버 로딩
		// 2. Connection 객체 얻어오기
		con = db.getConnection();
	}
	
	public void rentVideo(String tel, String videoNum) throws SQLException{
		String sql ="INSERT INTO rental(RCODE,OUTDATE,TEL,VCODE) VALUES "
				+ "(RENTAL_RCODE_SEQ.nextval,sysdate,'"+tel+"','"+videoNum+"')";
		
		Statement st = con.createStatement();
		st.executeUpdate(sql);
		
		st.close();

	}

	public int returnVideo(String videoNum) throws SQLException {
		String sql = "UPDATE rental SET INDATE = sysdate WHERE VCODE='" + videoNum + "' and " + "INDATE IS NULL";

		Statement st = con.createStatement();
		int result = st.executeUpdate(sql);
		
		st.close();
		return result;
		
	}
	
	public ArrayList<ArrayList> searchIsReturnList()throws SQLException {
		String sql ="SELECT vi.VICODE viCode, vi.TITLE viTitle,m.NAME mName, "
				+ "m.TEL mTel,(r.OUTDATE+3) willReturn,'미납' isReturned "
				+ "FROM rental r, member m, video v, vinfo vi "
				+ "WHERE r.tel = m.tel and r.VCODE = v.VCODE and v.VICODE=vi.VICODE "
				+ "and r.indate is null";
		
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(sql);
		ArrayList<ArrayList> list = new ArrayList<>();
		while(rs.next()){
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(rs.getString("viCode"));
			temp.add(rs.getString("viTitle"));
			temp.add(rs.getString("mName"));
			temp.add(rs.getString("mTel"));
			temp.add(rs.getString("willReturn"));
			temp.add(rs.getString("isReturned"));
			
			list.add(temp);
		}
		rs.close();
		st.close();
		return list;
	}

}
