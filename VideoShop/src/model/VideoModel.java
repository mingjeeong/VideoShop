package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.vo.Video;

public class VideoModel {

	Connection con;

	public VideoModel() throws Exception {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@70.12.115.68:1521:orcl";
		String user = "scott";
		String pass = "tiger";
		Class.forName(driver);
		con = DriverManager.getConnection(url, user, pass);

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
		System.out.println(">>");
		
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

}
