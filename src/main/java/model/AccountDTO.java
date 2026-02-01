package model;

public class AccountDTO {
	
	private String userID;
	private String userPW1;
	private String userPW2;
	private String email;
	private String phone;
	
	
	
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
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	private String attendance;
	
	

}
