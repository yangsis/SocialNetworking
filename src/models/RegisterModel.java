package models;

import com.mongodb.gridfs.GridFS;

public class RegisterModel {

	private String firstName;
	private String lastName;
	private String register_email;
	private String register_password;
    private String birthday;
    private String status;
    private String securityQ;
    private String securityA;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSecurityQ() {
		return securityQ;
	}
	public void setSecurityQ(String securityQ) {
		this.securityQ = securityQ;
	}
	public String getSecurityA() {
		return securityA;
	}
	public void setSecurityA(String securityA) {
		this.securityA = securityA;
	}
	public String getRegister_email() {
		return register_email;
	}
	public void setRegister_email(String register_email) {
		this.register_email = register_email;
	}
	public String getRegister_password() {
		return register_password;
	}
	public void setRegister_password(String register_password) {
		this.register_password = register_password;
	}
    

   
}
