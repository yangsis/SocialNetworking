package models;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class ProfileSettingModel {
	private String profileSetting_firstName;
	private String profileSetting_lastName;
	private String profileSetting_email;
	private String profileSetting_password;
    private String profileSetting_birthday;
    private String profileSetting_status;
    private MultipartFile profileSetting_picture;
	public String getProfileSetting_firstName() {
		return profileSetting_firstName;
	}
	public void setProfileSetting_firstName(String profileSetting_firstName) {
		this.profileSetting_firstName = profileSetting_firstName;
	}
	public String getProfileSetting_lastName() {
		return profileSetting_lastName;
	}
	public void setProfileSetting_lastName(String profileSetting_lastName) {
		this.profileSetting_lastName = profileSetting_lastName;
	}
	public String getProfileSetting_email() {
		return profileSetting_email;
	}
	public void setProfileSetting_email(String profileSetting_email) {
		this.profileSetting_email = profileSetting_email;
	}
	public String getProfileSetting_password() {
		return profileSetting_password;
	}
	public void setProfileSetting_password(String profileSetting_password) {
		this.profileSetting_password = profileSetting_password;
	}
	public String getProfileSetting_birthday() {
		return profileSetting_birthday;
	}
	public void setProfileSetting_birthday(String profileSetting_birthday) {
		this.profileSetting_birthday = profileSetting_birthday;
	}
	public String getProfileSetting_status() {
		return profileSetting_status;
	}
	public void setProfileSetting_status(String profileSetting_status) {
		this.profileSetting_status = profileSetting_status;
	}
	public MultipartFile getProfileSetting_picture() {
		return profileSetting_picture;
	}
	public void setProfileSetting_picture(MultipartFile profileSetting_picture) {
		this.profileSetting_picture = profileSetting_picture;
	}

	

}
