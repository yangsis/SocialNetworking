package models;

public class MessageModel {
	private String message_email;
	private String message_password;
	private String chat_id;
	private String message;
	public String getMessage_email() {
		return message_email;
	}
	public void setMessage_email(String message_email) {
		this.message_email = message_email;
	}
	public String getMessage_password() {
		return message_password;
	}
	public void setMessage_password(String message_password) {
		this.message_password = message_password;
	}
	public String getChat_id() {
		return chat_id;
	}
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}