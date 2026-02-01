package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class AccountDAO {

	// 오라클 접속
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "system";
	String pass = "12345";

	Connection con; // 접속 설정
	PreparedStatement pstmt; // String -> Sql 로 형변환
	ResultSet rs; // 데이터 즉 결과값 리턴 받는 객체

//-------------------------
	public void getCon() {
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, pass);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// getCon

	public void insertMember(AccountDTO mDTO) {

		try {
			// 연동이 되었는가?
			getCon();
			// -----------------------------
			String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?)";
			// string -> sql문법으로 바꿔야함
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, mDTO.getUserID());// memberDTO
			pstmt.setString(2, mDTO.getUserPW1());
			pstmt.setString(3, mDTO.getUserPW2());
			pstmt.setString(4, mDTO.getEmail());
			pstmt.setString(5, mDTO.getPhone());
			pstmt.setString(6, mDTO.getAttendance());
		
			pstmt.executeUpdate();// 오라클에 데이터 넣는 녀석
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

}
