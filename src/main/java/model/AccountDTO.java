package model;

/**
 * AccountDTO.java - 회원 정보 담는 클래스
 * 
 * DB의 account 테이블과 매핑됨
 * - userID    : 아이디 (이메일 형식)
 * - userPW1   : 비밀번호
 * - userPW2   : 비밀번호 확인
 * - email     : 이메일
 * - phone     : 전화번호
 * - nickname  : 닉네임
 * - regDate   : 가입일
 */
public class AccountDTO {

    private String userID;      // 아이디 (이메일)
    private String userPW1;     // 비밀번호
    private String userPW2;     // 비밀번호 확인
    private String email;       // 이메일
    private String phone;       // 전화번호
    private String nickname;    // 닉네임
    private String regDate;     // 가입일

    // ========== Getter / Setter ==========

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

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRegDate() { return regDate; }
    public void setRegDate(String regDate) { this.regDate = regDate; }
}