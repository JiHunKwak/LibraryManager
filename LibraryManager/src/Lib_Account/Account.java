package Lib_Account;

public class Account {

	private String name;
	private String id;
	private String password;
	private String phoneNum;
	private String email;
	
	public Account(){}
	
	public Account(String name, String id, String password, String phoneNum, String email){
		this.name = name;
		this.id = id;
		this.password = password;
		this.phoneNum = phoneNum;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return "Account [name=" + name + ", id=" + id + ", phoneNum=" + phoneNum + ", email="
				+ email + "]";
	}
	
	
	
	
}
