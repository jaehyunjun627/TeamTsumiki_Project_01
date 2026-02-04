package model.dto;

/**
 * AccountDTO.java - 회원 정보 담는 클래스
 *
 * DB의 account 테이블과 매핑
 * - accID      : 회원 고유번호 (PK, 자동생성)
 * - userID     : 로그인 ID (UNIQUE)
 * - userPW1    : 비밀번호
 * - userPW2    : 비밀번호 확인
 * - email      : 이메일
 * - phone      : 전화번호
 * - attendance : 출석 정보 (현재 미사용, learning_log 기반)
 * - nickname   : 닉네임
 */
public class AccountDTO {

    private int accID;          // 회원 고유번호 (PK)
    private String userID;      // 로그인 ID
    private String userPW1;     // 비밀번호
    private String userPW2;     // 비밀번호 확인
    private String email;       // 이메일
    private String phone;       // 전화번호
    private String attendance;  // 출석 정보 (미사용)
    private String nickname;    // 닉네임

    // ========== 생성자 ==========
    
    public AccountDTO() {}
    
    // 회원가입용 (accID 제외)
    public AccountDTO(String userID, String userPW1, String userPW2, 
                     String email, String phone, String nickname) {
        this.userID = userID;
        this.userPW1 = userPW1;
        this.userPW2 = userPW2;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
    }
    
    // 전체 필드 (DB SELECT용)
    public AccountDTO(int accID, String userID, String userPW1, String userPW2,
                     String email, String phone, String attendance, String nickname) {
        this.accID = accID;
        this.userID = userID;
        this.userPW1 = userPW1;
        this.userPW2 = userPW2;
        this.email = email;
        this.phone = phone;
        this.attendance = attendance;
        this.nickname = nickname;
    }

    // ========== Getter / Setter ==========

    public int getAccID() { return accID; }
    public void setAccID(int accID) { this.accID = accID; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUserPW1() { return userPW1; }
    public void setUserPW1(String userPW1) { this.userPW1 = userPW1; }

    public String getUserPW2() { return userPW2; }
    public void setUserPW2(String userPW2) { this.userPW2 = userPW2; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAttendance() { return attendance; }
    public void setAttendance(String attendance) { this.attendance = attendance; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}