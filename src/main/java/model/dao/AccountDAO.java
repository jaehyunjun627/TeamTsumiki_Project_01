package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.dto.AccountDTO;

/**
 * AccountDAO.java - 회원 DB 접근 클래스
 * 
 * account 테이블에 대한 CRUD 처리
 * - insertMember()   : 회원가입 (INSERT)
 * - loginCheck()     : 로그인 체크 (SELECT)
 * - getMember()      : 회원 정보 가져오기 (SELECT)
 * - idCheck()        : 아이디 중복 체크 (SELECT)
 * - nicknameCheck()  : 닉네임 중복 체크 (SELECT)
 */
public class AccountDAO {

    // ========== Oracle 접속 정보 ==========
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "system";
    String pass = "12345";

    Connection con;          // DB 연결 객체
    PreparedStatement pstmt; // SQL 실행 객체
    ResultSet rs;            // 결과 반환 객체

    // ========== DB 연결 ==========
    public void getCon() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== 회원가입 ==========
    public void insertMember(AccountDTO mDTO) {
        try {
            getCon();
            String sql = "INSERT INTO account (userID, userPW1, userPW2, email, phone, nickname) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, mDTO.getUserID());
            pstmt.setString(2, mDTO.getUserPW1());
            pstmt.setString(3, mDTO.getUserPW2());
            pstmt.setString(4, mDTO.getEmail());
            pstmt.setString(5, mDTO.getPhone());
            pstmt.setString(6, mDTO.getNickname());
            pstmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== 로그인 체크 ==========
    public int loginCheck(String userID, String userPW) {
        int result = -1;
        try {
            getCon();
            String sql = "SELECT userPW1 FROM account WHERE userID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String dbPw = rs.getString("userPW1");
                if (dbPw.equals(userPW)) {
                    result = 1;
                } else {
                    result = 0;
                }
            } else {
                result = -1;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ========== 회원 정보 가져오기 ==========
    public AccountDTO getMember(String userID) {
        AccountDTO dto = null;
        try {
            getCon();
            String sql = "SELECT * FROM account WHERE userID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new AccountDTO();
                dto.setAccID(rs.getInt("accID"));
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
    }

    // ========== 아이디 중복 체크 ==========
    public boolean idCheck(String userID) {
        boolean result = false;
        try {
            getCon();
            String sql = "SELECT userID FROM account WHERE userID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ========== 닉네임 중복 체크 ==========
    public boolean nicknameCheck(String nickname) {
        boolean result = false;
        try {
            getCon();
            String sql = "SELECT nickname FROM account WHERE nickname = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nickname);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}