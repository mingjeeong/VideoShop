package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.vo.Video;

public class VideoModel {

	Connection con;
	DBCon db;

	public VideoModel() throws Exception {

		/*String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@70.12.115.68:1521:orcl";
		String user = "scott";
		String pass = "tiger";
		Class.forName(driver);
		con = DriverManager.getConnection(url, user, pass);*/
		con = db.getConnection();

	}

	public void insertVideo(Video vo, int count) throws Exception {
		con.setAutoCommit(false);
		for(int i=0; i<count; i++){
		// 3. sql 문장 만들기
		//비디오 정보 테이블
		String sql1 ="INSERT INTO vinfo(vicode, title, genre, director, actor, detail) "
				+ "VALUES(vinfo_vicode_seq.nextval,?,?,?,?,?)";
		// 4. sql 전송객체(PreparedStatement)
		PreparedStatement ps = con.prepareStatement(sql1);
		ps.setString(1, vo.getVideoName());
		ps.setString(2, vo.getGenre()) ;
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getExp());
		
		// 5. sql 전송
		int result1 = ps.executeUpdate();
		
		//비디오 테이블
		String sql2 = "INSERT INTO video(vcode, vicode) "
				+ "VALUES (video_vcode_seq.nextval,vinfo_vicode_seq.CURRVAL)";
		PreparedStatement ps2 = con.prepareStatement(sql2);
		int result2 = ps2.executeUpdate();
		System.out.println("1"+result1+"2"+result2);
		if(result1 != 1 || result2 != 1){
			con.rollback();
		}
		con.commit();
		// 6. 닫기(PreparedStatement만 닫기)
		ps.close();
		ps2.close();
		}
		con.setAutoCommit(true);

	}
	
	public ArrayList searchVideo(int idx , String str) throws Exception{
		String[] key = {"TITLE","DIRECTOR"};
		String sql = "SELECT VICODE,TITLE,GENRE,DIRECTOR,ACTOR FROM VINFO WHERE "+key[idx]+" LIKE '%"+str+"%'";//?왜 못쓸까
		
		System.out.println(sql);
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList data = new ArrayList();
		while(rs.next()){
			ArrayList temp = new ArrayList();
			temp.add(rs.getString("VICODE"));
			temp.add(rs.getString("TITLE"));
			temp.add(rs.getString("GENRE"));
			temp.add(rs.getString("DIRECTOR"));
			temp.add(rs.getString("ACTOR"));
			data.add(temp);
			
			
		}
		rs.close();
		ps.close();
		return data;
		
	}
	
	public Video selectByPk(int no) throws SQLException{
		Video v = new Video();
		String sql ="SELECT * FROM VINFO WHERE VICODE ="+no;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()){
			v.setActor(rs.getString("ACTOR"));
			v.setDirector(rs.getString("DIRECTOR"));
			v.setExp(rs.getString("DETAIL"));
			v.setGenre(rs.getString("GENRE"));
			v.setVideoName(rs.getString("TITLE")); 
			v.setVideoNo(rs.getInt("VICODE"));
			}
		return v;
	}
	
	public int updateVideo(Video v) throws SQLException{
		String sql ="UPDATE VINFO SET TITLE=?, GENRE=?, DETAIL=?, "
				+ "DIRECTOR=?, ACTOR=? WHERE VICODE=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, v.getVideoName());
		ps.setString(2, v.getGenre());
		ps.setString(3, v.getExp());
		ps.setString(4, v.getDirector());
		ps.setString(5, v.getActor());
		ps.setInt(6, v.getVideoNo());
		
		int result = ps.executeUpdate();
		ps.close();
		return result;
		
	}
	
	public void deleteVideo(int no) throws SQLException{
		con.setAutoCommit(false);
		String sql2 = "DELETE FROM VIDEO WHERE VCODE=(SELECT VCODE FROM VIDEO WHERE VICODE="+no+")";
		Statement st2 = con.createStatement();
		int result2 = st2.executeUpdate(sql2);
		
		String sql1 = "DELETE FROM VINFO WHERE VICODE="+no;
		System.out.println(sql1);
		Statement st1 = con.createStatement();
		int result1 = st1.executeUpdate(sql1);
		
		
		if(result1 !=1 || result2 !=1){
			con.rollback();
		}
		
		con.commit();
		st1.close();
		st2.close();
		
		con.setAutoCommit(true);

	
	}

}
