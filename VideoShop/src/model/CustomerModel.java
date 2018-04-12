package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.vo.Customer;

public class CustomerModel {

	Connection con;
	String pkTel = null;

	public CustomerModel() throws Exception {

		// 1. 드라이버로딩
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@70.12.115.68:1521:orcl";
		String user = "scott";
		String pass = "tiger";
		// 2. Connection 연결객체 얻어오기
		Class.forName(driver);
		con = DriverManager.getConnection(url, user, pass);

	}

	public void insertCustomer(Customer vo) throws Exception {
		// 3. sql 문장 만들기
		String sql ="INSERT INTO member(tel,name,tel2,addr,email) VALUES (?,?,?,?,?)";
		
		// 4. sql 전송객체 (PreparedStatement)
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vo.getCustTel1() );
		ps.setString(2, vo.getCustName());
		ps.setString(3, vo.getCustTel2());
		ps.setString(4, vo.getCustAddr());
		ps.setString(5, vo.getCustEmail());
		
		// 5. sql 전송
		ps.executeUpdate();
		// 6. 닫기 (PreparedStatement 만 닫기)
		ps.close();

	}
	
	public ArrayList<String> selectByName(String name) throws SQLException{
		String sql = "SELECT tel FROM member "
				+ "WHERE name ='"+name+"'";
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ArrayList<String> list = new ArrayList<String>();
		
		
		while(rs.next()){
			list.add(rs.getString("TEL"));
		}
		return list;
		
		
	}

	public Customer selectByTel(String tel) throws Exception {
		Customer vo = new Customer();
		String sql = "SELECT * FROM member WHERE tel ='"+tel+"'";

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()){
			vo.setCustAddr(rs.getString("ADDR"));
			vo.setCustEmail(rs.getString("EMAIL"));
			vo.setCustName(rs.getString("NAME"));
			vo.setCustTel1(rs.getString("TEL"));
			vo.setCustTel2(rs.getString("TEL2"));
			pkTel = rs.getString("TEL");
		}
		return vo;
	}

	public int updateCustomer(Customer vo) throws Exception {

		String sql ="UPDATE member SET name=?,tel2=?,addr=?,email=?,tel=? WHERE tel='"+pkTel+"'";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, vo.getCustName());
		ps.setString(2, vo.getCustTel2());
		ps.setString(3, vo.getCustAddr());
		ps.setString(4, vo.getCustEmail());
		ps.setString(5, vo.getCustTel1());
		
		int result = ps.executeUpdate();
		

		return result;
	}
}
