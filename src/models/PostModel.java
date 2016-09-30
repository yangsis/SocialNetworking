package models;

import org.springframework.web.multipart.MultipartFile;

public class PostModel {
	private String post_email;
	private String post_password;
	private String post_id;
	private String newliker;
	private String text;
	private String group_id;
	public String getPost_email() {
		return post_email;
	}
	public void setPost_email(String post_email) {
		this.post_email = post_email;
	}
	public String getPost_password() {
		return post_password;
	}
	public void setPost_password(String post_password) {
		this.post_password = post_password;
	}
	public String getNewliker() {
		return newliker;
	}
	public void setNewliker(String newliker) {
		this.newliker = newliker;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPost_id() {
		return post_id;
	}
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
}
