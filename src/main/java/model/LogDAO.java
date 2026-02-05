package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "system";
    String pass = "12345";

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    
    public void getCon() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== 출석 체크 (학습 완료 시 호출) ==========
    // 오늘 날짜로 출석 INSERT
    // MERGE 사용: 이미 오늘 출석했으면 중복 INSERT 안 됨
    // 
    // [사용법] 한자 학습 완료 시:
    // AttendanceDAO dao = new AttendanceDAO();
    // dao.checkAttendance(userID);
    public void checkAttendance(String userID) {
        getCon();
        try {
            String sql = "MERGE INTO attendance a " +
                         "USING (SELECT ? AS userID, TRUNC(SYSDATE) AS attend_date FROM dual) b " +
                         "ON (a.userID = b.userID AND a.attend_date = b.attend_date) " +
                         "WHEN NOT MATCHED THEN INSERT (userID, attend_date) VALUES (b.userID, b.attend_date)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== 해당 월 출석 날짜 목록 가져오기 ==========
    // 캘린더 표시할 때 사용
    // 반환값: 출석한 날짜(일) 목록 (예: [1, 3, 5, 7] → 1일, 3일, 5일, 7일 출석)
    //
    // [사용법] main.jsp에서:
    // AttendanceDAO dao = new AttendanceDAO();
    // List<Integer> days = dao.getMonthAttendance(userID, 2026, 2);
    public List<Integer> getMonthAttendance(String userID, int year, int month) {
        getCon();
        List<Integer> days = new ArrayList<>();
        try {
            String sql = "SELECT EXTRACT(DAY FROM attend_date) AS day " +
                         "FROM attendance " +
                         "WHERE userID = ? " +
                         "AND EXTRACT(YEAR FROM attend_date) = ? " +
                         "AND EXTRACT(MONTH FROM attend_date) = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                days.add(rs.getInt("day"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    // ========== 오늘 출석했는지 확인 ==========
    // 반환값: true = 오늘 학습 완료, false = 아직 학습 안 함
    //
    // [사용법]
    // AttendanceDAO dao = new AttendanceDAO();
    // boolean attended = dao.isTodayAttended(userID);
    public boolean isTodayAttended(String userID) {
        getCon();
        boolean attended = false;
        try {
            String sql = "SELECT 1 FROM attendance WHERE userID = ? AND attend_date = TRUNC(SYSDATE)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            attended = rs.next();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attended;
    }
    
}
