package server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import models.ChatSettingsModel;
import models.FriendListModel;
import models.FriendSearchModel;
import models.GroupListModel;
import models.GroupSettingsModel;
import models.HomeModel;
import models.InboxModel;
import models.LoginModel;
import models.MessageModel;
import models.PostModel;
import models.ProfileModel;
import models.ProfileSettingModel;
import models.RegisterModel;

import org.bson.BSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import db.Helper;
import db.MongoDB;


@Controller
public class WebController{
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	   public String index() {
		   return "loginPage";
	   }
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.POST)
	public String deleteaccount(ModelMap modelMap,
			@ModelAttribute("profileSettingModel") ProfileSettingModel profileSettingModel,
			@ModelAttribute("registerModel") RegisterModel registerModel,
			 @ModelAttribute("homeModel") HomeModel homeModel){	
		if(homeModel.getHome_password() != null){
		System.out.println("testsignout");		
		return "loginPage";
		}
		else if(profileSettingModel.getProfileSetting_password()!=null){
			MongoDB mongo = new MongoDB("user", "userProfile");
	    	DBObject profile = (DBObject) mongo.queryUser(profileSettingModel.getProfileSetting_email()); 
	    	
	    	mongo = new MongoDB("user", "userProfile");
	    	mongo.deleteRecord(profile);


			return "loginPage";
		}
		else {
			 MongoDB mongo = new MongoDB("user", "userProfile");
		    	BSONObject profile = mongo.queryUser(registerModel.getRegister_email());
		    	System.out.println("sq"+profile.get("securityQ"));
		    	System.out.println("sa"+profile.get("securityA"));
				if(
				 (!registerModel.getSecurityQ().equals(profile.get("securityQ")))
				 ||(!registerModel.getSecurityA().equals(profile.get("securityA"))))
			{
			    modelMap.put("errorOffinding", "not correct information");
				System.out.println("here");
				return "loginPage";
			}
				else{
					modelMap.put("register_email", registerModel.getRegister_email());
					modelMap.put("firstName",  profile.get("firstName"));
			    	modelMap.put("lastName", profile.get("lastName"));
			    	modelMap.put("birthday", profile.get("birthday"));
			    	modelMap.put("status", profile.get("status"));
					
					
					return  "changepassword";
				}
			
			
		}

		
	}
	
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String resetpassword(ModelMap modelMap, @ModelAttribute("profileModel") ProfileModel profileModel,
			@ModelAttribute("registerModel") RegisterModel registerModel	) throws IOException{
		if(profileModel.getProfile_email()!=null){
		modelMap.put("register_email", profileModel.getProfile_email());
    	modelMap.put("register_password", profileModel.getProfile_password());
    	MongoDB mongo = new MongoDB("user", "userProfile");// database name , table name
    	BSONObject profile = mongo.queryUser(profileModel.getProfile_email());
    	System.out.println(profile.get("securityQ")+"QUESTION");
    	System.out.println(profile.get("securityA")+"ANSWER");
    	modelMap.put("securityQ", profile.get("securityQ"));
    	modelMap.put("securityA", profile.get("securityA"));
		return "resetpassword";
		}
		else{
			modelMap.put("securityA", registerModel.getSecurityA());
			MongoDB mongo = new MongoDB("user", "userProfile");
	    	BSONObject profile = mongo.queryUser(registerModel.getRegister_email());
	    	if(!registerModel.getSecurityA().equals(profile.get("securityA"))){
	    		modelMap.put("errorOflogin", "not a correct answer");
	    		modelMap.put("register_email", registerModel.getRegister_email());
	        	modelMap.put("register_password", registerModel.getRegister_password());
	        	modelMap.put("securityQ", profile.get("securityQ"));
	        	modelMap.put("securityA", profile.get("securityA"));
	    		return "resetpassword";	
	    	}
	    	else{
	    		System.out.println("correct answer");
	    		modelMap.put("register_email", registerModel.getRegister_email());
	        	//modelMap.put("register_password", registerModel.getRegister_password());
	        	modelMap.put("securityQ", profile.get("securityQ"));
	        	modelMap.put("securityA", profile.get("securityA"));
	        	modelMap.put("firstName", profile.get("firstName"));
	        	modelMap.put("lastName", profile.get("lastName"));
	        	modelMap.put("birthday", profile.get("birthday"));
	        	modelMap.put("status", profile.get("status"));
	        	mongo = new MongoDB("picture");
		    	GridFSDBFile picture = mongo.getImage(profile.get("picture"));
		    	if (picture != null){
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	picture.writeTo(baos);
			    	System.out.println("pictureid"+baos.size());
			    	System.out.println(baos.size());
			    	baos.close();
			    	String encodedString = new String(Base64.encode(baos.toByteArray()));
			    	modelMap.put("picture", "data:image/png;base64,"+encodedString);
		    	}
	        	
	        	
	    		return "changepassword";
	    		
	    	}			
			
		}
		
	}
	
	   
	
	
	@RequestMapping(value = "/homePage", method = RequestMethod.POST)
	public String homePage(ModelMap modelMap, @ModelAttribute("loginModel") LoginModel loginModel, 
			@ModelAttribute("registerModel") RegisterModel registerModel,
			@ModelAttribute("profileModel") ProfileModel profileModel,
			@ModelAttribute("profileSettingModel") ProfileSettingModel profileSettingModel,
			@ModelAttribute("friendSearchModel") FriendSearchModel friendSearchModel,
			@ModelAttribute("friendListModel") FriendListModel friendListModel,
			@ModelAttribute("groupListModel") GroupListModel groupListModel,
			@ModelAttribute("inboxModel") InboxModel inboxModel) {
		MongoDB mongo = new MongoDB("user","userProfile");
		System.out.println(Thread.currentThread().getId());
		if (loginModel.getLogin_email() != null){	
	    	System.out.println("2"+loginModel.getLogin_password());
		    if (mongo.valid(loginModel.getLogin_email(), loginModel.getLogin_password())){
		    	modelMap.put("home_email", loginModel.getLogin_email());
		    	modelMap.put("home_password", loginModel.getLogin_password());
		    	return "homePage";
		    } else {
			    modelMap.put("errorOflogin", "Invalid UserName / Password");
				return "loginPage";
		    }
	    }else if (registerModel.getRegister_password() != null){
	    	if (!registerModel.getRegister_email().equals("") && !registerModel.getRegister_password().equals("")){
		    	System.out.println("3");		    	
		    	mongo.register(registerModel);
		    	modelMap.put("home_email", registerModel.getRegister_email());
		    	modelMap.put("home_password", registerModel.getRegister_password());
		    	return "homePage";
		    }
		    else {
		    	modelMap.put("errorOfregister", "NULL Email / Password");
		    	return "loginPage";
		    }
	    }else if (profileModel.getProfile_password() != null){
	    	modelMap.put("home_email", profileModel.getProfile_email());
	    	modelMap.put("home_password", profileModel.getProfile_password());
	    	return "homePage";
	    }else if (profileSettingModel.getProfileSetting_email() != null){
	    	modelMap.put("home_email", profileSettingModel.getProfileSetting_email());
	    	modelMap.put("home_password", profileSettingModel.getProfileSetting_password());
	    	System.out.println("check email"+profileSettingModel.getProfileSetting_email());
	    	System.out.println("check password"+profileSettingModel.getProfileSetting_password());
	    	return "homePage";
	    } else if (friendSearchModel.getFriendSearchModel_password() != null){
	    	modelMap.put("home_email", friendSearchModel.getFriendSearchModel_email());
	    	modelMap.put("home_password", friendSearchModel.getFriendSearchModel_password());
	    	System.out.println("check email friendSearchModel"+friendSearchModel.getFriendSearchModel_email());
	    	System.out.println("check password friendSearchModel"+friendSearchModel.getFriendSearchModel_password());
	    	return "homePage";
	    }else if (friendListModel.getFriendList_email() != null){
	    	modelMap.put("home_email", friendListModel.getFriendList_email());
	    	modelMap.put("home_password", friendListModel.getFriendList_password());
	    	System.out.println("check email friendListModel "+friendListModel.getFriendList_email());
	    	System.out.println("check password friendListModel"+friendListModel.getFriendList_password());
	    	return "homePage";
	    }else if (groupListModel.getGroupList_passwod() != null){
	    	modelMap.put("home_email", groupListModel.getGroupList_email());
	    	modelMap.put("home_password", groupListModel.getGroupList_passwod());
	    	System.out.println("check email friendListModel "+groupListModel.getGroupList_email());
	    	System.out.println("check password friendListModel"+groupListModel.getGroupList_passwod());
	    	return "homePage";
	    }else{
	    	modelMap.put("home_email", inboxModel.getInbox_email());
	    	modelMap.put("home_password", inboxModel.getInbox_password());
	    	System.out.println("check email friendListModel "+inboxModel.getInbox_email());
	    	System.out.println("check password friendListModel"+inboxModel.getInbox_password());
	    	return "homePage";
	    }

	}
	

	
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String profilePage(ModelMap modelMap, @ModelAttribute("homeModel") HomeModel homeModel, 
			 @ModelAttribute("registerModel") RegisterModel registerModel,
			@ModelAttribute("profileSettingModel") ProfileSettingModel profileSettingModel) throws IOException{
		if (homeModel.getHome_password() != null){	
			modelMap.put("profile_email", homeModel.getHome_email());
	    	modelMap.put("profile_password", homeModel.getHome_password());
	    	MongoDB mongo = new MongoDB("user", "userProfile");
	    	BSONObject profile = mongo.queryUser(homeModel.getHome_email());
	    	modelMap.put("profile_email", profile.get("_id"));
	    	modelMap.put("firstName", profile.get("firstName"));
	    	modelMap.put("lastName", profile.get("lastName"));
	    	modelMap.put("birthday", profile.get("birthday"));
	    	modelMap.put("status", profile.get("status"));
	    	mongo = new MongoDB("picture");
	    	GridFSDBFile picture = mongo.getImage(profile.get("picture"));
	    	if (picture != null){
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	picture.writeTo(baos);
		    	System.out.println("pictureid"+baos.size());
		    	System.out.println(baos.size());
		    	baos.close();
		    	String encodedString = new String(Base64.encode(baos.toByteArray()));
		    	modelMap.put("picture", "data:image/png;base64,"+encodedString);
	    	}
			return "profile";
		}
		else if(registerModel.getRegister_email()!=null){
			MongoDB mongo = new MongoDB("user", "userProfile");
	    	BSONObject profile = mongo.queryUser(registerModel.getRegister_email());
	    	System.out.println(registerModel.getRegister_password()+ "ps");
	    	System.out.println(profile.get("password")+ "pss");
	    	
			if(!registerModel.getRegister_password().equals(profile.get("password"))&& !registerModel.getRegister_password().equals("")){
				mongo = new MongoDB("user", "userProfile");
				mongo.update(registerModel, registerModel.getRegister_email());	
				modelMap.put("profile_email", registerModel.getRegister_email());
		    	modelMap.put("profile_password", registerModel.getRegister_password());	
		     	modelMap.put("firstName", registerModel.getFirstName());
		    	modelMap.put("lastName", registerModel.getLastName());
		    	modelMap.put("birthday", registerModel.getBirthday());
		    	modelMap.put("status", registerModel.getStatus());
		    	mongo = new MongoDB("picture");
		    	GridFSDBFile picture = mongo.getImage(profile.get("picture"));
		    	if (picture != null){
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	picture.writeTo(baos);
			    	System.out.println("pictureid"+baos.size());
			    	System.out.println(baos.size());
			    	baos.close();
			    	String encodedString = new String(Base64.encode(baos.toByteArray()));
			    	modelMap.put("picture", "data:image/png;base64,"+encodedString);
		    	}
		     	
		    	
				System.out.println("end here");
				System.out.println();
				return "profile";
			}
			else if(registerModel.getRegister_password().equals("")){
				modelMap.put("register_email", profile.get("_id"));
		    	//modelMap.put("profile_password", registerModel.getRegister_password());
		    	modelMap.put("firstName", registerModel.getFirstName());
		    	modelMap.put("lastName", registerModel.getLastName());
		    	modelMap.put("birthday", registerModel.getBirthday());
		    	modelMap.put("status", registerModel.getStatus());
		    	 modelMap.put("errorOfinsert2", "not an empty password");
		    	
		    	mongo = new MongoDB("picture");
		    	GridFSDBFile picture = mongo.getImage(profile.get("picture"));
		    	if (picture != null){
			    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    	picture.writeTo(baos);
			    	System.out.println("pictureid"+baos.size());
			    	System.out.println(baos.size());
			    	baos.close();
			    	String encodedString = new String(Base64.encode(baos.toByteArray()));
			    	modelMap.put("picture", "data:image/png;base64,"+encodedString);
		    	}
		    	System.out.println("end thenother");
		    	
				return "changepassword";
				
			}
			else{
				
			modelMap.put("register_email", registerModel.getRegister_email());
	    	//modelMap.put("profile_password", registerModel.getRegister_password());
	    	modelMap.put("firstName", registerModel.getFirstName());
	    	modelMap.put("lastName", registerModel.getLastName());
	    	modelMap.put("birthday", registerModel.getBirthday());
	    	modelMap.put("status", registerModel.getStatus());
	    	 modelMap.put("errorOfinsert", "not the old password");
	    	
	    	mongo = new MongoDB("picture");
	    	GridFSDBFile picture = mongo.getImage(profile.get("picture"));
	    	if (picture != null){
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	picture.writeTo(baos);
		    	System.out.println("pictureid"+baos.size());
		    	System.out.println(baos.size());
		    	baos.close();
		    	String encodedString = new String(Base64.encode(baos.toByteArray()));
		    	modelMap.put("picture", "data:image/png;base64,"+encodedString);
	    	}
	    	System.out.println("end another");
	    	
			return "changepassword";
			}
		}
		
		else {
			MongoDB mongo = new MongoDB("picture");
			if (profileSettingModel.getProfileSetting_picture() != null){
				Object pictureID = mongo.uploadImage(profileSettingModel.getProfileSetting_picture());
				mongo = new MongoDB("user","userProfile");
		    	mongo.update(profileSettingModel, profileSettingModel.getProfileSetting_email(), pictureID);
			}
			mongo = new MongoDB("user", "userProfile");
	    	BSONObject profile = mongo.queryUser(profileSettingModel.getProfileSetting_email());    	
	    	modelMap.put("profile_email", profileSettingModel.getProfileSetting_email());
	    	modelMap.put("profile_password", profileSettingModel.getProfileSetting_password());
	    	modelMap.put("firstName", profileSettingModel.getProfileSetting_firstName());
	    	modelMap.put("lastName", profileSettingModel.getProfileSetting_lastName());
	    	modelMap.put("birthday", profileSettingModel.getProfileSetting_birthday());
	    	modelMap.put("status", profileSettingModel.getProfileSetting_status());
	    	mongo = new MongoDB("picture");
	    	GridFSDBFile picture = mongo.getImage(profile.get("picture"));
	    	if (picture != null){
		    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    	picture.writeTo(baos);
		    	System.out.println("pictureid"+baos.size());
		    	System.out.println(baos.size());
		    	baos.close();
		    	String encodedString = new String(Base64.encode(baos.toByteArray()));
		    	modelMap.put("picture", "data:image/png;base64,"+encodedString);
	    	}
	    	mongo.close();
			return "profile";
		}
	
	}
	
	@RequestMapping(value = "/profileSetting", method = RequestMethod.POST)
	public String profilePageSetting(ModelMap modelMap,
			@ModelAttribute("profileModel") ProfileModel profileModel){		
		modelMap.put("profileSetting_email", profileModel.getProfile_email());
		modelMap.put("profileSetting_password", profileModel.getProfile_password());
		MongoDB mongo = new MongoDB("user", "userProfile");
		BSONObject profile = mongo.queryUser(profileModel.getProfile_email());
		System.out.println(profile.toString());
		modelMap.put("profileSetting_email", profile.get("_id"));
		modelMap.put("profileSetting_firstName", profile.get("firstName"));
		modelMap.put("profileSetting_lastName", profile.get("lastName"));
		modelMap.put("profileSetting_password", profile.get("password"));
		modelMap.put("profileSetting_birthday", profile.get("birthday"));
		modelMap.put("profileSetting_status", profile.get("status"));
		System.out.println(profileModel.getProfile_email());
		return "profileSetting";
		
	}
	
	
	@RequestMapping(value = "/friendSearch", method = RequestMethod.POST)
	public String friendSearch(ModelMap modelMap,
			@ModelAttribute("homeModel") HomeModel homeModel,
			@ModelAttribute("friendSearchModel") FriendSearchModel friendSearchModel) throws IOException{	
		if (homeModel.getHome_email() != null){
			System.out.println("friendSearch home "+ homeModel.getHome_email());
			modelMap.put("friendSearchModel_email", homeModel.getHome_email());
			modelMap.put("friendSearchModel_password", homeModel.getHome_password());
			modelMap.put("friendSearchModel_search_yes", "no");
			modelMap.put("friendSearchModel_search_email", null);
			System.out.println("friendSearchModel_email from home Page" + homeModel.getHome_email());
			return "friendSearch";
		} else {
			System.out.println("friendSearchModel_email "+ friendSearchModel.getFriendSearchModel_email());
			System.out.println("friendSearchModel_search_yes "+ friendSearchModel.getFriendSearchModel_search_yes());
			modelMap.put("friendSearchModel_email", friendSearchModel.getFriendSearchModel_email());
			modelMap.put("friendSearchModel_password", friendSearchModel.getFriendSearchModel_password());
			if (friendSearchModel.getFriendSearchModel_search_yes().equals("no")){
				if (!friendSearchModel.getFriendSearchModel_email().equals(friendSearchModel.getFriendSearchModel_search_email())){
					MongoDB mongo = new MongoDB("user", "userProfile");
					BSONObject user = mongo.queryUser(friendSearchModel.getFriendSearchModel_email());
					mongo = new MongoDB("user", "userProfile");
					BSONObject friend = mongo.queryUser(friendSearchModel.getFriendSearchModel_search_email());
					BSONObject user_friendList = (BSONObject) user.get("friendList");
					if (friend == null){
						modelMap.put("errorOfSearch", "No such user");
						modelMap.put("friendSearchModel_search_email", null);
				    	modelMap.put("friendSearchModel_search_yes", "no");
					}else if (user_friendList == null){
						modelMap.put("friendSearch_firstName", friend.get("firstName"));
						modelMap.put("friendSearch_lastName", friend.get("lastName"));
						modelMap.put("friendSearch_birthday", friend.get("birthday"));
						modelMap.put("friendSearch_status", friend.get("status"));
						mongo = new MongoDB("picture");
				    	GridFSDBFile picture = mongo.getImage(friend.get("picture"));
				    	if (picture != null){
					    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
					    	picture.writeTo(baos);
					    	baos.close();
					    	String encodedString = new String(Base64.encode(baos.toByteArray()));
					    	modelMap.put("friendSearch_picture", "data:image/png;base64,"+encodedString);
				    	}
				    	System.out.println("friendEmail "+friend.get("_id"));
				    	modelMap.put("friendSearchModel_search_email", friend.get("_id"));
				    	modelMap.put("friendSearchModel_search_yes", "yes");
					}else if (!user_friendList.containsKey(friend.get("_id").toString())){
						modelMap.put("friendSearch_firstName", friend.get("firstName"));
						modelMap.put("friendSearch_lastName", friend.get("lastName"));
						modelMap.put("friendSearch_birthday", friend.get("birthday"));
						modelMap.put("friendSearch_status", friend.get("status"));
						mongo = new MongoDB("picture");
				    	GridFSDBFile picture = mongo.getImage(friend.get("picture"));
				    	if (picture != null){
					    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
					    	picture.writeTo(baos);
					    	baos.close();
					    	String encodedString = new String(Base64.encode(baos.toByteArray()));
					    	modelMap.put("friendSearch_picture", "data:image/png;base64,"+encodedString);
				    	}
				    	System.out.println("friendEmail "+friend.get("_id"));
				    	modelMap.put("friendSearchModel_search_email", friend.get("_id"));
				    	modelMap.put("friendSearchModel_search_yes", "yes");
					}else {
						modelMap.put("errorOfSearch", "S/HE is your friend Already");
						modelMap.put("friendSearchModel_search_email", null);
				    	modelMap.put("friendSearchModel_search_yes", "no");
					}
					mongo.close();
					return "friendSearch";
				}else{
					modelMap.put("errorOfSearch", "This is your account");
					modelMap.put("friendSearchModel_search_email", null);
			    	modelMap.put("friendSearchModel_search_yes", "no");
					return "friendSearch";
				}
			} else { 
				
				System.out.println("final update request");
				MongoDB mongo = new MongoDB("user","userProfile");
				BSONObject oldFriendList = (BSONObject) mongo.queryUser(friendSearchModel.getFriendSearchModel_search_email()).get("friendList");
				mongo = new MongoDB("user","userProfile");
				mongo.updateRequest(friendSearchModel.getFriendSearchModel_search_email(), friendSearchModel.getFriendSearchModel_email(), oldFriendList);
				modelMap.put("friendSearchModel_search_yes", "no");
				modelMap.put("friendSearchModel_search_email", null);
				modelMap.put("errorOfSearch", "Successfully send the request");
				return "friendSearch";}
		}
		
	}
	
	@RequestMapping(value = "/friendList", method = RequestMethod.POST)
	public String friendList(ModelMap modelMap,
			@ModelAttribute("homeModel") HomeModel homeModel,
			@ModelAttribute("friendListModel") FriendListModel friendListModel) throws IOException {	
		if (homeModel.getHome_password() != null){
			System.out.println("friendSearch home "+ homeModel.getHome_email());
			modelMap.put("friendList_email", homeModel.getHome_email());
			modelMap.put("friendList_password", homeModel.getHome_password());
			MongoDB mongo = new MongoDB("user", "userProfile");
			BSONObject list = (BSONObject) mongo.queryUser(homeModel.getHome_email()).get("friendList");
			Map<String, Map<String, Map<String, List<Object>>>> friendList = new HashMap<String, Map<String, Map<String, List<Object>>>>();
			if (list != null){
				System.out.println("friendList "+list.toString());
				
				if (list != null){				
					for (String key : list.keySet()){
						mongo = new MongoDB("user","userProfile");
						List person_information = new ArrayList<String>();
						System.out.println("friendSearch friend email "+key);
						BSONObject person = mongo.queryUser(key);
						person_information.add(person.get("firstName"));
						person_information.add(person.get("lastName"));
						person_information.add(person.get("birthday"));
						person_information.add(person.get("status"));
						mongo = new MongoDB("picture");
						String encodedString = null;
						System.out.println("lsit add "+key);
						GridFSDBFile picture = mongo.getImage(person.get("picture"));
				    	if (picture != null){
					    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
					    	picture.writeTo(baos);
					    	baos.close();
					    	encodedString = new String(Base64.encode(baos.toByteArray()));
					    	System.out.println(encodedString.length());
				    	}
				    	
				    	Map<String, List<Object>> deepList = new HashMap<String, List<Object>>();
				    	deepList.put("data:image/png;base64,"+encodedString, person_information);
				    	Map<String, Map<String, List<Object>>> insideList = new HashMap<String, Map<String, List<Object>>>();
				    	insideList.put(list.get(key).toString(), deepList);
						friendList.put(key, insideList);
					}
				}
			}
			System.out.println("freindList size "+friendList.size());
			modelMap.put("friendList", friendList);
			modelMap.put("friendList_process_email", null);
			modelMap.put("friendList_process", null);
			mongo.close();
			return "friendList";
		}else{
			System.out.println("accept test "+friendListModel.getFriendList_process_email());
			modelMap.put("friendList_email", friendListModel.getFriendList_email());
			modelMap.put("friendList_password", friendListModel.getFriendList_password());
			MongoDB mongo = new MongoDB("user", "userProfile");
			BSONObject userFriendList = (BSONObject) mongo.queryUser(friendListModel.getFriendList_email()).get("friendList");
			mongo = new MongoDB("user", "userProfile");
			BSONObject friendEmailList = (BSONObject) mongo.queryUser(friendListModel.getFriendList_process_email()).get("friendList");
			mongo = new MongoDB("user", "userProfile");
			if (friendListModel.getFriendList_process().equals("add")){
				mongo.acceptRequest( friendListModel.getFriendList_email(), friendListModel.getFriendList_process_email(),
						userFriendList, friendEmailList);
			}else{
				mongo.deleteRequest(friendListModel.getFriendList_email(), friendListModel.getFriendList_process_email(),
						userFriendList, friendEmailList);
			}
			mongo = new MongoDB("user", "userProfile");
			BSONObject list = (BSONObject) mongo.queryUser(friendListModel.getFriendList_email()).get("friendList");
			System.out.println("accept lsit check "+ list.toString());
			Map<String, Map<String, Map<String, List<Object>>>> friendList = new HashMap<String, Map<String, Map<String, List<Object>>>>();
			if (list != null){				
				for (String key : list.keySet()){
					mongo = new MongoDB("user","userProfile");
					List person_information = new ArrayList<String>();
					BSONObject person = mongo.queryUser(key);
					person_information.add(person.get("firstName"));
					person_information.add(person.get("lastName"));
					person_information.add(person.get("birthday"));
					person_information.add(person.get("status"));
					mongo = new MongoDB("picture");
					String encodedString = null;
					System.out.println("lsit add "+key);
					GridFSDBFile picture = mongo.getImage(person.get("picture"));
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
			    	}
			    	
			    	Map<String, List<Object>> deepList = new HashMap<String, List<Object>>();
			    	deepList.put("data:image/png;base64,"+encodedString, person_information);
			    	Map<String, Map<String, List<Object>>> insideList = new HashMap<String, Map<String, List<Object>>>();
			    	insideList.put(list.get(key).toString(), deepList);
					friendList.put(key, insideList);
				}
			}
			System.out.println("freindList size "+friendList.size());
			modelMap.put("friendList", friendList);
			modelMap.put("friendList_process_email", null);
			modelMap.put("friendList_process", null);
			mongo.close();
			return "friendList";
		}
		
		
	}
	
	
	
	@RequestMapping(value = "/groupList", method = RequestMethod.POST)
	public String groupList(ModelMap modelMap,
			@ModelAttribute("homeModel") HomeModel homeModel,
			@ModelAttribute("groupSettingsModel") GroupSettingsModel groupSettingsModel,
			@ModelAttribute("groupListModel") GroupListModel groupListModel,
			@ModelAttribute("postModel") PostModel postModel) throws IOException {
		System.out.println("luoyulouyluyou");
		if (homeModel.getHome_password() != null){
			modelMap.put("groupList_email", homeModel.getHome_email());
			modelMap.put("groupList_passwod", homeModel.getHome_password());
			modelMap.put("process_group", null);
			System.out.println("groupList_email "+homeModel.getHome_email());
			MongoDB mongo = new MongoDB("user","userProfile");
			BSONObject person = mongo.queryUser(homeModel.getHome_email());
			System.out.println("groupList person "+ person.toString());
			BasicDBList group = (BasicDBList) person.get("groupList");
			Map<String, Map<String, Map<String,String>>> groupList = new HashMap<String, Map<String, Map<String,String>>>();
			
			if (group != null){
				System.out.println("123123 "+group.toString());
				for (int i = 0 ; i < group.size(); i++){
					mongo = new MongoDB("group", "groupProfile");
					BSONObject item = mongo.queryUser(group.get(i).toString());
					mongo = new MongoDB("picture");
					String encodedString = null;
					
					GridFSDBFile picture = mongo.getImage(item.get("groupPicture"));
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
			    	}
			    	Map<String, String> image = new HashMap<String, String>();
			    	
					System.out.println("group item "+ item.toString());
					String process = "no";
					if (homeModel.getHome_email().equals(item.get("groupOwner"))){
						process = "yes";
					}
					image.put(process, "data:image/png;base64,"+encodedString);
					Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
					tem.put(item.get("groupName").toString(), image);
					groupList.put(group.get(i).toString(), tem);
					mongo.close();
				}
			}
			if (new Helper().getYesFriend((BSONObject) person.get("friendList")) == null){
				modelMap.put("errorOfGroupList", "You don't have friends to create a group");
				modelMap.put("createInvalid", "yes");
			}
			modelMap.put("groupList", groupList);
			
			return "groupList";
		}else if (groupSettingsModel.getGroupSetting_email() != null){
			System.out.println("groupseting return "+groupSettingsModel.getGroupSetting_email());
			modelMap.put("groupList_email", groupSettingsModel.getGroupSetting_email());
			modelMap.put("groupList_passwod", groupSettingsModel.getGroupSetting_password());
			MongoDB mongo = new MongoDB("picture");
			Object pictureID = null;
			if (groupSettingsModel.getGroupPicture() != null){
				pictureID = mongo.uploadImage(groupSettingsModel.getGroupPicture());
				
			}
			if (groupSettingsModel.getGroupID().equals("") || groupSettingsModel.getGroupID() == null){
				UUID id = UUID.randomUUID();
				mongo = new MongoDB("group","groupProfile");
				mongo.registerGroup(groupSettingsModel, id, pictureID);
				multiGroupUpdate(groupSettingsModel, id.toString());
				
			}else{
				mongo = new MongoDB("group","groupProfile");
				System.out.println("I am updating a gorup" + groupSettingsModel.getGroupID() + " "
						+groupSettingsModel.getGroupName());
				mongo.updateGroup(groupSettingsModel, groupSettingsModel.getGroupID(), pictureID);
				multiGroupUpdate(groupSettingsModel, groupSettingsModel.getGroupID());
			}
			mongo = new MongoDB("user","userProfile");
			BSONObject person = mongo.queryUser(groupSettingsModel.getGroupSetting_email());
			System.out.println("groupList person "+ person.toString());
			BasicDBList group = (BasicDBList) person.get("groupList");
			Map<String, Map<String, Map<String,String>>> groupList = new HashMap<String, Map<String, Map<String,String>>>();
			
			if (group != null){
				for (int i = 0 ; i < group.size(); i++){
					mongo = new MongoDB("group", "groupProfile");
					BSONObject item = mongo.queryUser(group.get(i).toString());
					System.out.println("group item "+ group.get(i).toString());
					mongo = new MongoDB("picture");
					String encodedString = null;
					
					GridFSDBFile picture = mongo.getImage(item.get("groupPicture"));
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
			    	}
			    	
					String process = "no";
					if (groupSettingsModel.getGroupSetting_email().equals(item.get("groupOwner"))){
						process = "yes";
					}
					Map<String, String> image = new HashMap<String, String>();
					image.put(process, "data:image/png;base64,"+encodedString);
					Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
					tem.put(item.get("groupName").toString(), image);
					groupList.put(group.get(i).toString(), tem);
					mongo.close();
				}
			}
			modelMap.put("groupList", groupList);
			mongo.close();
			return "groupList";
		}else if (groupListModel.getGroupList_email() != null){
			modelMap.put("groupList_email", groupListModel.getGroupList_email());
			modelMap.put("groupList_passwod", groupListModel.getGroupList_passwod());
			modelMap.put("process_group", null);
			multiGroupDelete(groupListModel.getProcess_group());
			System.out.println("groupList_email "+groupListModel.getGroupList_email());
			MongoDB mongo = new MongoDB("user","userProfile");
			BSONObject person = mongo.queryUser(groupListModel.getGroupList_email());
			System.out.println("groupList person "+ person.toString());
			BasicDBList group = (BasicDBList) person.get("groupList");
			Map<String, Map<String, Map<String,String>>> groupList = new HashMap<String, Map<String, Map<String,String>>>();
			
			if (group != null){
				System.out.println("123123 "+group.toString());
				for (int i = 0 ; i < group.size(); i++){
					mongo = new MongoDB("group", "groupProfile");
					BSONObject item = mongo.queryUser(group.get(i).toString());
					mongo = new MongoDB("picture");
					String encodedString = null;
					
					GridFSDBFile picture = mongo.getImage(item.get("groupPicture"));
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
				    	
			    	}
			    	Map<String, String> image = new HashMap<String, String>();
			    	
					System.out.println("group item "+ item.toString());
					String process = "no";
					if (groupListModel.getGroupList_email().equals(item.get("groupOwner"))){
						process = "yes";
					}
					image.put(process, "data:image/png;base64,"+encodedString);
					Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
					tem.put(item.get("groupName").toString(), image);
					groupList.put(group.get(i).toString(), tem);
					mongo.close();
				}
			}
			if (new Helper().getYesFriend((BSONObject) person.get("friendList")) == null){
				modelMap.put("errorOfGroupList", "You don't have friends to create a group");
				modelMap.put("createInvalid", "yes");
			}
			modelMap.put("groupList", groupList);
			mongo.close();
			return "groupList";
		}else{
			modelMap.put("groupList_email", postModel.getPost_email());
			modelMap.put("groupList_passwod", postModel.getPost_password());
			modelMap.put("process_group", null);
			System.out.println("groupList_email "+postModel.getPost_email());
			MongoDB mongo = new MongoDB("user","userProfile");
			BSONObject person = mongo.queryUser(postModel.getPost_email());
			System.out.println("groupList person "+ person.toString());
			BasicDBList group = (BasicDBList) person.get("groupList");
			Map<String, Map<String, Map<String,String>>> groupList = new HashMap<String, Map<String, Map<String,String>>>();
			
			if (group != null){
				System.out.println("123123 "+group.toString());
				for (int i = 0 ; i < group.size(); i++){
					mongo = new MongoDB("group", "groupProfile");
					BSONObject item = mongo.queryUser(group.get(i).toString());
					mongo = new MongoDB("picture");
					String encodedString = null;
					
					GridFSDBFile picture = mongo.getImage(item.get("groupPicture"));
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
			    	}
			    	Map<String, String> image = new HashMap<String, String>();
			    	
					System.out.println("group item "+ item.toString());
					String process = "no";
					if (postModel.getPost_email().equals(item.get("groupOwner"))){
						process = "yes";
					}
					image.put(process, "data:image/png;base64,"+encodedString);
					Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
					tem.put(item.get("groupName").toString(), image);
					groupList.put(group.get(i).toString(), tem);
					mongo.close();
				}
			}
			if (new Helper().getYesFriend((BSONObject) person.get("friendList")) == null){
				modelMap.put("errorOfGroupList", "You don't have friends to create a group");
				modelMap.put("createInvalid", "yes");
			}
			modelMap.put("groupList", groupList);
			
			return "groupList";
		}
	}


	@RequestMapping(value = "/groupSetting", method = RequestMethod.POST)
	public String groupSetting(ModelMap modelMap,
			@ModelAttribute("groupList") GroupListModel groupListModel) throws IOException {
		modelMap.put("groupSetting_email", groupListModel.getGroupList_email());
		modelMap.put("groupSetting_passwod", groupListModel.getGroupList_passwod());
		MongoDB mongo = new MongoDB("group","groupProfile");
		BSONObject group = mongo.queryUser(groupListModel.getProcess_group());
		System.out.println("groupSetting process group" + groupListModel.getProcess_group());
		mongo = new MongoDB("user", "userProfile");
		BSONObject person = mongo.queryUser(groupListModel.getGroupList_email());
	
		if (!groupListModel.getProcess_group().equals("")){
			modelMap.put("groupName", group.get("groupName"));
			modelMap.put("description", group.get("description"));
			modelMap.put("groupOwner", groupListModel.getGroupList_email());
			BasicDBList groupUser = new BasicDBList();
			groupUser = (BasicDBList) group.get("groupUser");
			String oldGroupUser = "";
			int count = 1;
			for(Object key : groupUser.toArray()){
				oldGroupUser += key.toString();
				oldGroupUser += ",";
				String user = "user"+Integer.toString(count);
				modelMap.put(user, key.toString());
				count++;
			}
			System.out.println("send the old group User "+oldGroupUser);
			modelMap.put("oldGroupUser", oldGroupUser);
			
		}else{
			modelMap.put("groupOwner", groupListModel.getGroupList_email());
			modelMap.put("oldGroupUser", null);			
			modelMap.put("user1", null);
			modelMap.put("user2", null);
			modelMap.put("user3", null);
			modelMap.put("user4", null);
		}
		ArrayList<String> group_temp = new ArrayList<String>();
		for (Object item : new Helper().getYesFriend((BSONObject) person.get("friendList"))){
			group_temp.add(item.toString());
		}
		group_temp.add("None");
		modelMap.put("group", group_temp);
		modelMap.put("groupID", groupListModel.getProcess_group());
		modelMap.put("postList", null);
		return "groupSetting";
	}
	
	
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(ModelMap modelMap,
			@ModelAttribute("groupList") GroupListModel groupListModel,
			@ModelAttribute("postModel") PostModel postModel) throws IOException {
		if (groupListModel.getGroupList_passwod() != null){
			modelMap.put("post_email", groupListModel.getGroupList_email());
			modelMap.put("post_password", groupListModel.getGroupList_passwod());
			modelMap.put("group_id", groupListModel.getProcess_group());
			Map<String, List<String>> main = new LinkedHashMap<String, List<String>>();
			Map<String, String> picture = new HashMap<String, String>();
			Map<String, List<String>> liker = new HashMap<String, List<String>>();
				
			MongoDB mongo = new MongoDB("group","groupProfile");
			BSONObject group = mongo.queryUser(groupListModel.getProcess_group());
			BasicDBList postList = (BasicDBList) group.get("postList");
			if (postList != null){			
				for (Object item : postList){
					mongo = new MongoDB("post","postProfile");
					BSONObject post = mongo.queryUser(item.toString());
					List<String> content = new ArrayList<String>();
					if (post.get("owner_id") != null && !post.get("owner_id").equals("")){
						content.add(post.get("owner_id").toString());
						mongo = new MongoDB("user", "userProfile");
						BSONObject person = mongo.queryUser(post.get("owner_id").toString());
						System.out.println("psot post user "+person);
						mongo = new MongoDB("picture");
						System.out.println("post user picture "+person.get("picture"));
						String encodedString = null;
						GridFSDBFile pi = mongo.getImage(person.get("picture"));
				    	if (pi != null){
					    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
					    	pi.writeTo(baos);
					    	baos.close();
					    	encodedString = new String(Base64.encode(baos.toByteArray()));
					    	picture.put(item.toString(), "data:image/png;base64,"+encodedString);
				    	}
						
					}
					if (post.get("time") != null && !post.get("time").equals("")){
						content.add(post.get("time").toString());
					}
					if (post.get("text") != null && !post.get("text").equals("")){
						content.add(post.get("text").toString());
					}
					main.put(item.toString(), content);
					
					
					
					BasicDBList likeList = (BasicDBList) post.get("liker");
					List<String> temp = new ArrayList<String>();
					System.out.println("post liker list "+likeList);
					if (likeList != null){
						for (Object like : likeList.toArray()){
							temp.add(like.toString());
						}
					}
					liker.put(item.toString(), temp);	
					mongo.close();
				}
			}
			modelMap.put("main", main);
			modelMap.put("picture", picture);
			modelMap.put("liker", liker);	
			return "post";
		}else {
			if (postModel.getText() != null || postModel.getNewliker() != null){
				if (postModel.getNewliker() != null && !postModel.getNewliker().equals("")){
					MongoDB mongo = new MongoDB("post", "postProfile");
					BSONObject post = mongo.queryUser(postModel.getPost_id());
					System.out.println("Post add new liker "+postModel.getNewliker());
					System.out.println("post the entire post "+ post);
					BasicDBList likerList = (BasicDBList) post.get("liker");
					
					if (likerList != null){
						mongo = new MongoDB("post","postProfile");
						mongo.update(postModel.getPost_id(), "liker", (new Helper()).keepUnique(likerList, postModel.getNewliker()));
					}else{
						likerList = new BasicDBList();
						likerList.add(postModel.getNewliker());
						mongo = new MongoDB("post","postProfile");
						mongo.update(postModel.getPost_id(), "liker", likerList);
					}
					
					
				}else{
					UUID id = UUID.randomUUID();
					MongoDB mongo = new MongoDB("post","postProfile");
					mongo.registerPost(postModel, id.toString());
					mongo = new MongoDB("group","groupProfile");
					BSONObject group = mongo.queryUser(postModel.getGroup_id());
					BasicDBList postList = (BasicDBList) group.get("postList");
					if (postList == null){
						postList = new BasicDBList();
						postList.add(id.toString());
					}else{
						postList.add(id.toString());
					}
					mongo = new MongoDB("group","groupProfile");
					mongo.update(postModel.getGroup_id(), "postList", postList);
				}
			}
			
			modelMap.put("post_email", postModel.getPost_email());
			modelMap.put("post_password", postModel.getPost_password());
			modelMap.put("group_id", postModel.getGroup_id());	
			Map<String, List<String>> main = new LinkedHashMap<String, List<String>>();
			Map<String, String> picture = new HashMap<String, String>();
			Map<String, List<String>> liker = new HashMap<String, List<String>>();
				
			MongoDB mongo = new MongoDB("group","groupProfile");
			BSONObject group = mongo.queryUser(postModel.getGroup_id());
			BasicDBList postList = (BasicDBList) group.get("postList");
			if (postList != null){			
				for (Object item : postList){
					mongo = new MongoDB("post","postProfile");
					BSONObject post = mongo.queryUser(item.toString());
					List<String> content = new ArrayList<String>();
					if (post.get("owner_id") != null && !post.get("owner_id").equals("")){
						content.add(post.get("owner_id").toString());
						mongo = new MongoDB("user", "userProfile");
						BSONObject person = mongo.queryUser(post.get("owner_id").toString());
						mongo = new MongoDB("picture");
						String encodedString = null;
						
						GridFSDBFile pi = mongo.getImage(person.get("picture"));
				    	if (pi != null){
					    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
					    	pi.writeTo(baos);
					    	baos.close();
					    	encodedString = new String(Base64.encode(baos.toByteArray()));
					    	picture.put(item.toString(), "data:image/png;base64,"+encodedString);
				    	}
				    	mongo.close();
						
					}
					if (post.get("time") != null && !post.get("time").equals("")){
						content.add(post.get("time").toString());
					}
					if (post.get("text") != null && !post.get("text").equals("")){
						content.add(post.get("text").toString());
					}
					main.put(item.toString(), content);
					
					BasicDBList likeList = (BasicDBList) post.get("liker");
					List<String> temp = new ArrayList<String>();
					if (likeList != null){
						for (Object like : likeList.toArray()){
							temp.add(like.toString());
						}
					}
					liker.put(item.toString(), temp);
					mongo.close();
				}
			}
			modelMap.put("main", main);
			modelMap.put("picture", picture);
			modelMap.put("liker", liker);	
			return "post";
		}
	}
	
	

	@RequestMapping(value = "/inbox", method = RequestMethod.POST)
	public String inbox(ModelMap modelMap,
			@ModelAttribute("homeModel") HomeModel homeModel,
			@ModelAttribute("inboxModel") InboxModel inboxModel,
			@ModelAttribute("chatSettingsModel") ChatSettingsModel chatSettingsModel) throws IOException {
		if (homeModel.getHome_password() != null){
			modelMap.put("inbox_email", homeModel.getHome_email());
			modelMap.put("inbox_password", homeModel.getHome_password());
			modelMap.put("process_chat", null);
			System.out.println("inbox_email "+homeModel.getHome_email());
			MongoDB mongo = new MongoDB("user","userProfile");
			BSONObject person = mongo.queryUser(homeModel.getHome_email());
			System.out.println("inboxList person "+ person.toString());
			BasicDBList inbox = (BasicDBList) person.get("inbox");
			Map<String, Map<String, Map<String,String>>> inboxList = new HashMap<String, Map<String, Map<String,String>>>();
			
			if (inbox != null){
				System.out.println("123123 "+inbox.toString());
				for (int i = 0 ; i < inbox.size(); i++){
					mongo = new MongoDB("chat", "chatProfile");
					BSONObject chat = mongo.queryUser(inbox.get(i).toString());
					mongo = new MongoDB("user","userProfile");
					Object pictureID = "";
					String name = "";
					if (homeModel.getHome_email().equals(chat.get("owner").toString())){
						pictureID = mongo.queryUser(chat.get("partner").toString()).get("picture");
						name = chat.get("partner").toString();
					} else{
						pictureID = mongo.queryUser(chat.get("owner").toString()).get("picture");
						name = chat.get("owner").toString();
					}
					
					mongo = new MongoDB("picture");
					String encodedString = null;
					
					GridFSDBFile picture = mongo.getImage(pictureID);
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
			    	}
			    	Map<String, String> image = new HashMap<String, String>();
			    	
					System.out.println("chat item "+ chat.toString());
					String process = "no";
					if (homeModel.getHome_email().equals(chat.get("owner"))){
						process = "yes";
					}
					image.put(process, "data:image/png;base64,"+encodedString);
					Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
					tem.put(name, image);
					inboxList.put(inbox.get(i).toString(), tem);
					System.out.println("inbox pass the list "+inboxList.toString());
					mongo.close();
				}
			}
			if (person.get("friendList") == null){
				modelMap.put("errorOfInboxList", "You don't have friends to create a chat");
				modelMap.put("createInvalid", "yes");
			}
			modelMap.put("inboxList", inboxList);		
			return "inbox";
		}else if (inboxModel.getInbox_password() != null){		
			System.out.println("inbox delete a chat "+inboxModel.getProcess_chat());
			if (inboxModel.getProcess_chat() != null && !inboxModel.getProcess_chat().equals("")){
				MongoDB mongo = new MongoDB("chat","chatProfile");
				BSONObject chat = mongo.queryUser(inboxModel.getProcess_chat());
				String owner = chat.get("owner").toString();
				String partner = chat.get("partner").toString();
				multiInboxUpdate(owner, partner, inboxModel.getProcess_chat(), "yes");				
			}
						
			modelMap.put("inbox_email", inboxModel.getInbox_email());
			modelMap.put("inbox_password", inboxModel.getInbox_password());
			modelMap.put("process_chat", null);
			System.out.println("inbox_email "+inboxModel.getInbox_email());
			MongoDB mongo = new MongoDB("user","userProfile");
			BSONObject person = mongo.queryUser(inboxModel.getInbox_email());
			System.out.println("inboxList person "+ person.toString());
			BasicDBList inbox = (BasicDBList) person.get("inbox");
			Map<String, Map<String, Map<String,String>>> inboxList = new HashMap<String, Map<String, Map<String,String>>>();
			
			if (inbox != null){
				System.out.println("123123 "+inbox.toString());
				for (int i = 0 ; i < inbox.size(); i++){
					mongo = new MongoDB("chat", "chatProfile");
					BSONObject chat = mongo.queryUser(inbox.get(i).toString());
					mongo = new MongoDB("user","userProfile");
					Object pictureID = "";
					String name = "";
					if (inboxModel.getInbox_email().equals(chat.get("owner").toString())){
						pictureID = mongo.queryUser(chat.get("partner").toString()).get("picture");
						name = chat.get("partner").toString();
					} else{
						pictureID = mongo.queryUser(chat.get("owner").toString()).get("picture");
						name = chat.get("owner").toString();
					}
					
					mongo = new MongoDB("picture");
					String encodedString = null;
					
					GridFSDBFile picture = mongo.getImage(pictureID);
			    	if (picture != null){
				    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	picture.writeTo(baos);
				    	baos.close();
				    	encodedString = new String(Base64.encode(baos.toByteArray()));
				    	System.out.println(encodedString.length());
			    	}
			    	Map<String, String> image = new HashMap<String, String>();
			    	
					System.out.println("chat item "+ chat.toString());
					String process = "no";
					if (inboxModel.getInbox_email().equals(chat.get("owner"))){
						process = "yes";
					}
					image.put(process, "data:image/png;base64,"+encodedString);
					Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
					tem.put(name, image);
					inboxList.put(inbox.get(i).toString(), tem);
					mongo.close();
				}
			}
			if (person.get("friendList") == null){
				modelMap.put("errorOfInboxList", "You don't have friends to create a chat");
				modelMap.put("createInvalid", "yes");
			}
			modelMap.put("inboxList", inboxList);		
			return "inbox";
		}else if (chatSettingsModel.getChat_password() != null){
			if (chatSettingsModel.getPartner() != null && !chatSettingsModel.getPartner().equals("")){
				UUID uuid = UUID.randomUUID();
				MongoDB mongo = new MongoDB("chat","chatProfile");
				mongo.registerChat(chatSettingsModel, uuid.toString());
				System.out.println("chat list from settings (check partner) "+ chatSettingsModel.getPartner());
				multiInboxUpdate(chatSettingsModel.getChat_email(), chatSettingsModel.getPartner(), uuid.toString(), "no");
				
				modelMap.put("inbox_email", chatSettingsModel.getChat_email());
				modelMap.put("inbox_password", chatSettingsModel.getChat_password());
				modelMap.put("process_chat", null);
				System.out.println("inbox_email "+chatSettingsModel.getChat_email());
				mongo = new MongoDB("user","userProfile");
				BSONObject person = mongo.queryUser(chatSettingsModel.getChat_email());
				System.out.println("inboxList person "+ person.toString());
				BasicDBList inbox = (BasicDBList) person.get("inbox");
				Map<String, Map<String, Map<String,String>>> inboxList = new HashMap<String, Map<String, Map<String,String>>>();
				
				if (inbox != null){
					System.out.println("inbox content "+inbox.toString());
					for (int i = 0 ; i < inbox.size(); i++){
						mongo = new MongoDB("chat", "chatProfile");
						BSONObject chat = mongo.queryUser(inbox.get(i).toString());
						mongo = new MongoDB("user","userProfile");
						Object pictureID = "";
						String name = "";
						if (chatSettingsModel.getChat_email().equals(chat.get("owner").toString())){
							pictureID = mongo.queryUser(chat.get("partner").toString()).get("picture");
							name = chat.get("partner").toString();
						} else{
							pictureID = mongo.queryUser(chat.get("owner").toString()).get("picture");
							name = chat.get("owner").toString();
						}
						
						mongo = new MongoDB("picture");
						String encodedString = null;
						
						GridFSDBFile picture = mongo.getImage(pictureID);
				    	if (picture != null){
					    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
					    	picture.writeTo(baos);
					    	baos.close();
					    	encodedString = new String(Base64.encode(baos.toByteArray()));
					    	System.out.println(encodedString.length());
				    	}
				    	Map<String, String> image = new HashMap<String, String>();
				    	
						System.out.println("chat item "+ chat.toString());
						String process = "no";
						if (chatSettingsModel.getChat_email().equals(chat.get("owner"))){
							process = "yes";
						}
						image.put(process, "data:image/png;base64,"+encodedString);
						Map<String, Map<String,String>> tem = new HashMap<String, Map<String,String>>();
						tem.put(name, image);
						inboxList.put(inbox.get(i).toString(), tem);
						mongo.close();
					}
				}
				modelMap.put("inboxList", inboxList);
				return "inbox";
			}else{
				modelMap.put("chat_email", chatSettingsModel.getChat_email());
				modelMap.put("chat_passwod", chatSettingsModel.getChat_password());
				MongoDB mongo = new MongoDB("user","userProfile");
				BSONObject person = mongo.queryUser(chatSettingsModel.getChat_email());
				BSONObject friendList = (BSONObject) person.get("friendList");
				BasicDBList friend = new Helper().getYesFriend(friendList);
				modelMap.put("friend", friend);
				modelMap.put("errorOfcreateChat", "please choose one friend");
				return "createChat";
			}
		}else{
			return "inbox";
		}
	
	}
	
	
	@RequestMapping(value = "/createChat", method = RequestMethod.POST)
	public String inbox(ModelMap modelMap,
			@ModelAttribute("inboxModel") InboxModel inboxModel)throws IOException {
		modelMap.put("chat_email", inboxModel.getInbox_email());
		modelMap.put("chat_passwod", inboxModel.getInbox_password());
		MongoDB mongo = new MongoDB("user","userProfile");
		BSONObject person = mongo.queryUser(inboxModel.getInbox_email());
		BSONObject friendList = (BSONObject) person.get("friendList");
		BasicDBList friend = new Helper().getYesFriend(friendList);
		modelMap.put("friend", friend);
		return "createChat";
	}
	
	
	@RequestMapping(value = "/chatPage", method = RequestMethod.POST)
	public String chatPage(ModelMap modelMap,
			@ModelAttribute("inboxModel") InboxModel inboxModel,
			@ModelAttribute("messageModel") MessageModel messageModel)throws IOException {
		
		System.out.println("chat Page is working ");
		if (messageModel.getMessage_password() != null){
			UUID uuid = UUID.randomUUID();
			MongoDB mongo = new MongoDB("chat","chatProfile");
			BasicDBList messageList = (BasicDBList) mongo.queryUser(messageModel.getChat_id()).get("messageList");
			if (messageList == null){
				messageList = new BasicDBList();
			}
			messageList.add(uuid.toString());
			System.out.println(messageList);
			if (messageModel.getMessage() == null || messageModel.getMessage().equals("")){
				modelMap.put("messageOfError", "Please enter some message");
			}else{
				mongo = new MongoDB("chat","chatProfile");
				mongo.update(messageModel.getChat_id(), "messageList", messageList);
				mongo = new MongoDB("message","messageProfile");
				mongo.registerMessage(messageModel, uuid.toString());
			}
			modelMap.put("message_email", messageModel.getMessage_email());
			modelMap.put("message_passwod", messageModel.getMessage_password());
			modelMap.put("chat_id", messageModel.getChat_id());
			System.out.println("chat page send message "+messageModel.getMessage());
		}else{
			modelMap.put("message_email", inboxModel.getInbox_email());
			modelMap.put("message_passwod", inboxModel.getInbox_password());
			modelMap.put("chat_id", inboxModel.getProcess_chat());
		}
		
		return "chatPage";
	}
	
	@RequestMapping(value = "/chatPage/{chat_id}", method = RequestMethod.GET)
	public String showHistory(ModelMap modelMap, @PathVariable(value = "chat_id") String chat_id){
		System.out.println("history is work "+chat_id);
		MongoDB mongo = new MongoDB("chat","chatProfile");
		BasicDBList messageList = (BasicDBList) mongo.queryUser(chat_id).get("messageList");
		BasicDBList list = new BasicDBList();
		if (messageList != null){			
			for (Object item : messageList){
				mongo = new MongoDB("message","messageProfile");
				BSONObject message = mongo.queryUser(item.toString());
				String chat = "";
				chat = chat + message.get("sender").toString() + " ( " + message.get("time").toString() +
						" ) : "+message.get("text");
				list.add(chat);
			}
		}
		modelMap.put("list", list);
		System.out.println("list "+ list);
		return "history";
	}
	
	
	
	public void multiGroupUpdate(GroupSettingsModel groupSettingsModel, String uuid){
		MongoDB mongo = new MongoDB("user","userProfile");
		BasicDBList person = null;
		BasicDBList groupList = new BasicDBList();
		String oldList = groupSettingsModel.getOldGroupUser();
		BasicDBList oldGroupList = new BasicDBList(); 
		String[] list = null;
		System.out.println("old user I get from setting  "+groupSettingsModel.getOldGroupUser());
		if (oldList != null){
			list = oldList.split(",");
			for (int i = 0 ; i < list.length;i++){
				System.out.println("wojiushi yao lai "+ list[i]);
				oldGroupList.add(list[i]);
			}
		}
		
		System.out.println("fffffff old list "+ oldList);
		groupList = (new Helper()).removeDuplicate(groupSettingsModel);
		if (!uuid.equals("")){
			groupList.add(groupSettingsModel.getGroupSetting_email());
		}
		for (Object item : groupList.toArray()){
			if (oldGroupList.contains(item.toString())){
				oldGroupList.remove(item);
				groupList.remove(item);
			}
		}
		
		System.out.println("old list "+ oldGroupList.toString());
		System.out.println("new list "+groupList.toString());
		for (Object item : groupList.toArray()){			
			if (!item.equals("") && item != null){
				mongo = new MongoDB("user", "userProfile");
				System.out.println("luoyu item "+item );
				person = (BasicDBList) mongo.queryUser(item.toString()).get("groupList");
				if (person == null){
					person = new BasicDBList();
				}
				System.out.println("person mulitu "+person.toString());
				person.add(uuid);
				mongo = new MongoDB("user","userProfile");
				mongo.update(item.toString(), "groupList", person);
			}
			
		}
		
		for (Object item : oldGroupList.toArray()){
			if (!item.equals("") && item != null){
				mongo = new MongoDB("user", "userProfile");
				person = (new Helper()).removeItem((BasicDBList) mongo.queryUser(item.toString()).get("groupList"), uuid);				
				mongo = new MongoDB("user","userProfile");
				mongo.update(item.toString(), "groupList", person);
			}
		}
		mongo.close();
	
	}
	
	public void multiGroupDelete(String groupID){
		MongoDB mongo = new MongoDB("group", "groupProfile");
		BSONObject group = mongo.queryUser(groupID);
		BasicDBList groupUser = (BasicDBList) group.get("groupUser");
		System.out.println("wo de groupUser " + groupUser.toString());
		for (Object key : groupUser.toArray()){
			if (!key.toString().equals(",")){
				mongo = new MongoDB("user","userProfile");
				System.out.println("wode keu a "+key);
				BSONObject person = mongo.queryUser(key.toString().replaceAll(",", ""));
				BasicDBList groupList = new Helper().removeItem((BasicDBList) person.get("groupList"), groupID);
				mongo = new MongoDB("user","userProfile");
				mongo.update(key.toString().replaceAll(",", ""), "groupList", groupList);
			}
			
		}
		mongo = new MongoDB("user","userProfile");
		BSONObject person = mongo.queryUser(group.get("groupOwner").toString());
		BasicDBList groupList = new Helper().removeItem((BasicDBList) person.get("groupList"),groupID);
		
		mongo = new MongoDB("user","userProfile");
		mongo.update(group.get("groupOwner").toString(), "groupList", groupList);
		
		mongo = new MongoDB("group", "groupProfile");
		mongo.deleteRecord((DBObject) group);		
	}
	
	
	public void multiInboxUpdate(String owner, String partner, String uuid, String delete){
		System.out.println("mul inbox update owner "+owner);
		System.out.println("mul inbox update partner "+partner);
		MongoDB mongo = new MongoDB("user","userProfile");
		BSONObject person_owner = mongo.queryUser(owner);
		BasicDBList owner_inbox = (BasicDBList) person_owner.get("inbox");	
		if (owner_inbox == null){
			owner_inbox = new BasicDBList();
		}
		mongo = new MongoDB("user","userProfile");
		BSONObject person_partner = mongo.queryUser(partner);
		BasicDBList partner_inbox = (BasicDBList) person_partner.get("inbox");	
		if (partner_inbox == null){
			partner_inbox = new BasicDBList();
		}
		if (delete.equals("yes")){
			System.out.println("multiInboxUpdate delete old ow "+owner_inbox.toString());
			System.out.println("multiInboxUpdate delete old pa "+partner_inbox.toString());
			BasicDBList new_owner_inbox = new Helper().deleteItemInbox(owner_inbox, uuid);
			BasicDBList new_partner_inbox = new Helper().deleteItemInbox(partner_inbox, uuid);
			System.out.println("multiInboxUpdate delete ow "+new_owner_inbox.toString());
			System.out.println("multiInboxUpdate delete pa "+new_partner_inbox.toString());
			mongo = new MongoDB("user", "userProfile");
			mongo.update(owner, "inbox", new_owner_inbox);
			mongo = new MongoDB("user", "userProfile");
			mongo.update(partner, "inbox", new_partner_inbox);
		}else{
			owner_inbox.add(uuid);
			partner_inbox.add(uuid);
			mongo = new MongoDB("user","userProfile");
			mongo.update(owner, "inbox", owner_inbox);
			mongo = new MongoDB("user", "userProfile");
			mongo.update(partner, "inbox", partner_inbox);
		}
	}
	
	
}
