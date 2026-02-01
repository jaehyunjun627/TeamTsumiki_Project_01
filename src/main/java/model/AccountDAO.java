package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    // 회원가입
    public void insertMember(AccountDTO mDTO) {
        try {
            // 연동이 되었는가?
            getCon();
            // -----------------------------
            String sql = "INSERT INTO member VALUES (?, ?, ?, ?, ?, ?, ?)";
            // string -> sql문법으로 바꿔야함
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, mDTO.getUserID());// memberDTO
            pstmt.setString(2, mDTO.getUserPW1());
            pstmt.setString(3, mDTO.getUserPW2());
            pstmt.setString(4, mDTO.getEmail());
            pstmt.setString(5, mDTO.getPhone());
            pstmt.setString(6, mDTO.getAttendance());
            pstmt.setString(7, mDTO.getNickname());
            pstmt.executeUpdate();// 오라클에 데이터 넣는 녀석
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// insertMember

    // 로그인 체크: 1=성공, 0=비번틀림, -1=아이디없음
    public int loginCheck(String userID, String userPW) {
        int result = -1;
        try {
            // 연동이 되었는가?
            getCon();
            // -----------------------------
            String sql = "SELECT userPW1 FROM member WHERE userID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();// 오라클에서 데이터 가져오는 녀석

            if (rs.next()) {
                // 아이디 존재
                String dbPw = rs.getString("userPW1");
                if (dbPw.equals(userPW)) {
                    result = 1; // 로그인 성공
                } else {
                    result = 0; // 비밀번호 틀림
                }
            } else {
                result = -1; // 아이디 없음
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }// loginCheck

    // 회원 정보 가져오기
    public AccountDTO getMember(String userID) {
        AccountDTO dto = null;
        try {
            // 연동이 되었는가?
            getCon();
            // -----------------------------
            String sql = "SELECT * FROM member WHERE userID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new AccountDTO();
                dto.setUserID(rs.getString("userID"));
                dto.setUserPW1(rs.getString("userPW1"));
                dto.setUserPW2(rs.getString("userPW2"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setAttendance(rs.getString("attendance"));
                dto.setNickname(rs.getString("nickname"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }// getMember

    // 아이디 중복 체크: true=이미 존재, false=사용 가능
    public boolean idCheck(String userID) {
        boolean result = false;
        try {
            // 연동이 되었는가?
            getCon();
            // -----------------------------
            String sql = "SELECT userID FROM member WHERE userID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true; // 이미 존재하는 아이디
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }// idCheck

    // 닉네임 중복 체크: true=이미 존재, false=사용 가능
    public boolean nicknameCheck(String nickname) {
        boolean result = false;
        try {
            // 연동이 되었는가?
            getCon();
            // -----------------------------
            String sql = "SELECT nickname FROM member WHERE nickname = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true; // 이미 존재하는 닉네임
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }// nicknameCheck
}