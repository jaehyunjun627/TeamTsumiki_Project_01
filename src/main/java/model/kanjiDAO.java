package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class kanjiDAO {

	// 오라클 접속
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "member";
	String pass = "12345";

	Connection con; // 접속 설정
	PreparedStatement pstmt; // String -> Sql 로 형변환
	ResultSet rs; // 데이터 즉 결과값 리턴 받는 객체

	// ---------------------------------------------------

	public void getCon() {
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, pass);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// getCon

	// ---------------------------1.insert(C)---------------------------
	// 입력
	public void insertMember(MemberDTO mDTO) {

		try {
			// 연동
			getCon();
			// ---------------------------
			String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?)";
			// String -> sql
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, mDTO.getId()); // MemberDTO
			pstmt.setString(2, mDTO.getPass1());
			pstmt.setString(3, mDTO.getPass2());
			pstmt.setString(4, mDTO.getEmail());
			pstmt.setString(5, mDTO.getGender());
			pstmt.setString(6, mDTO.getAddress());
			pstmt.setString(7, mDTO.getPhone());
			pstmt.setString(8, mDTO.getHobby());
			pstmt.setString(9, mDTO.getJob());
			pstmt.setString(10, mDTO.getAge());
			pstmt.setString(11, mDTO.getInfo());

			pstmt.executeUpdate(); // 오라클에 데이터 전달. 오라클에서 ctrl+enter 하는거랑 같음
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------2.select(R)-----------------------
	public Vector<MemberDTO> allSelectMember() {

		Vector<MemberDTO> v = new Vector<MemberDTO>();

		try {
			getCon();

			String sql = "select * from member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString(1));
				dto.setPass1(rs.getString(2));
				dto.setPass2(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setGender(rs.getString(5));
				dto.setAddress(rs.getString(6));
				dto.setPhone(rs.getString(7));
				dto.setHobby(rs.getString(8));
				dto.setJob(rs.getString(9));
				dto.setAge(rs.getString(10));
				dto.setInfo(rs.getString(11));

				v.add(dto);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return v;

	}// allSelectMember

}
