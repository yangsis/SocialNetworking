package models;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class GroupSettingsModel {
	private String groupSetting_email;
	private String groupSetting_password;
	private String groupID;
	private String groupName;
	private String description;
	private MultipartFile groupPicture;
	private String groupOwner;
	private String user1;
	private String user2;
	private String user3;
	private String user4;
	private String postList;
	private String oldGroupUser;

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getGroupPicture() {
		return groupPicture;
	}
	public void setGroupPicture(MultipartFile groupPicture) {
		this.groupPicture = groupPicture;
	}
	public String getPostList() {
		return postList;
	}
	public void setPostList(String postList) {
		this.postList = postList;
	}
	public String getGroupOwner() {
		return groupOwner;
	}
	public void setGroupOwner(String groupOwner) {
		this.groupOwner = groupOwner;
	}
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public String getUser3() {
		return user3;
	}
	public void setUser3(String user3) {
		this.user3 = user3;
	}
	public String getUser4() {
		return user4;
	}
	public void setUser4(String user4) {
		this.user4 = user4;
	}
	public String getGroupSetting_email() {
		return groupSetting_email;
	}
	public void setGroupSetting_email(String groupSetting_email) {
		this.groupSetting_email = groupSetting_email;
	}
	public String getGroupSetting_password() {
		return groupSetting_password;
	}
	public void setGroupSetting_password(String groupSetting_password) {
		this.groupSetting_password = groupSetting_password;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getOldGroupUser() {
		return oldGroupUser;
	}
	public void setOldGroupUser(String oldGroupUser) {
		this.oldGroupUser = oldGroupUser;
	}


}
