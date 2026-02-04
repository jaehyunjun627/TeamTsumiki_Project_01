package model;

public class KanjiMemberDTO {

	private String userID;        // 회원 아이디
    private String userPW1;        // 비밀번호
    private String userPW2;        // 비밀번호 확인
    private String email;          // 이메일
    private String phone;          // 전화번호
    private int attendance;        // 출석일
    
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPW1() {
		return userPW1;
	}
	public void setUserPW1(String userPW1) {
		this.userPW1 = userPW1;
	}
	public String getUserPW2() {
		return userPW2;
	}
	public void setUserPW2(String userPW2) {
		this.userPW2 = userPW2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAttendance() {
		return attendance;
	}
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
    
}
