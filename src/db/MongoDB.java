package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.bson.BSONObject;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import models.ChatSettingsModel;
import models.GroupSettingsModel;
import models.MessageModel;
import models.PostModel;
import models.ProfileModel;
import models.ProfileSettingModel;
import models.RegisterModel;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


public class MongoDB {
	private DBCollection collection;
	private Mongo mongo;
	private DB database;
	private String filePath;
	
	public MongoDB(String db, String coll){
		mongo = new Mongo("localhost", 27017);
		database = mongo.getDB(db);
		collection = database.getCollection(coll);
		
	}
	
	public MongoDB(String db){
		mongo = new Mongo("localhost", 27017);
		database = mongo.getDB(db);
	}

	
	public void register(RegisterModel rm){
		System.out.println("BasicDBObject example...");
		BasicDBObject document = new BasicDBObject();
		document.put("_id", rm.getRegister_email());
		document.put("firstName", rm.getFirstName());
		document.put("lastName", rm.getLastName());
		document.put("password", rm.getRegister_password());
		document.put("birthday", rm.getBirthday());
		document.put("status", rm.getStatus());
		document.put("picture", null);
		document.put("securityQ", rm.getSecurityQ());
		document.put("securityA", rm.getSecurityA());
		document.put("friendList", null);
		document.put("groupList", null);
		document.put("inbox", null);
		collection.insert(document);
		mongo.close();


	}
	
	
	public Boolean valid(String email, String password){
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", email);
		DBCursor cursor = collection.find(whereQuery);
		while(cursor.hasNext()) {
		    if (cursor.next().get("password").equals(password)){
		    	mongo.close();
		    	return true;
		    }
		}
		mongo.close();
		return false;
	}
	
	
	public BSONObject queryUser(String email){
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", email);
		DBCursor cursor = collection.find(whereQuery);
		while(cursor.hasNext()) {
			mongo.close();
		    return cursor.next();
		}
		mongo.close();
		return null;
	}
	
	
	public void update(ProfileSettingModel pm, String email, Object pictureID){
		System.out.println("BasicDBObject example...");
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();		
		document.put("firstName", pm.getProfileSetting_firstName());
		document.put("lastName", pm.getProfileSetting_lastName());
		document.put("birthday", pm.getProfileSetting_birthday());
		document.put("status", pm.getProfileSetting_status());
		document.put("picture", pictureID);
		BasicDBObject oldDocument = new BasicDBObject();
		newDocument.append("$set", document);
		oldDocument.append("_id", email);	
		collection.update(oldDocument, newDocument);
		mongo.close();

	}
	
	public void update(String email, String part, BasicDBList list){
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();
		document.put(part, list);
		newDocument.append("$set", document);
		BasicDBObject oldDocument = new BasicDBObject();
		oldDocument.append("_id", email);
		collection.update(oldDocument, newDocument);
		mongo.close();
	}
	
	public void update(RegisterModel rm, String email){
		System.out.println("BasicDBObject example...");
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();		
		document.put("password", rm.getRegister_password());
		BasicDBObject oldDocument = new BasicDBObject();
		newDocument.append("$set", document);
		oldDocument.append("_id", email);	
		collection.update(oldDocument, newDocument);
		mongo.close();
		
		
	}
	
	
	public void updateRequest(String userEmail, String friendEmail, BSONObject oldFriendList){
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();
		BasicDBObject oldDocument = new BasicDBObject();
		oldDocument.append("_id", userEmail);	
		if (oldFriendList != null){
			for (String key : oldFriendList.keySet()){
				document.put(key, oldFriendList.get(key));
			}
		}
		document.put(friendEmail, "no");
		BasicDBObject temp = new BasicDBObject();
		newDocument.append("$set", temp.append("friendList", document));
		collection.update(oldDocument, newDocument);
		mongo.close();
	}
	
	
	
	public Object uploadImage(MultipartFile file){
		try {
	        //Create GridFS object
	        GridFS fs = new GridFS(database);
	        //Save image into database	        
	        GridFSInputFile in = fs.createFile(file.getBytes());
	        in.save();
	        mongo.close();
	        return in.getId();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mongo.close();
			return null;
		}
	}
	
	
	public GridFSDBFile getImage(Object id){
		GridFS fs = new GridFS(database);
        GridFSDBFile out = fs.findOne( new BasicDBObject("_id",id));    
        return out;
	}
	
	
	public void acceptRequest(String userEmail, String friendEmail, BSONObject userFriendList, BSONObject friendList){
		//set friendList of user
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();
		BasicDBObject oldDocument = new BasicDBObject();
		oldDocument.append("_id", userEmail);	
		if (userFriendList != null){
			for (String key : userFriendList.keySet()){
				if (!key.equals(friendEmail) && !key.equals(userEmail)){
					document.put(key, userFriendList.get(key));
				}
			}
		}
		document.put(friendEmail, "yes");
		BasicDBObject temp = new BasicDBObject();
		newDocument.append("$set", temp.append("friendList", document));
		collection.update(oldDocument, newDocument);
		//set friendList of Friend
		BasicDBObject document1 = new BasicDBObject();
		BasicDBObject newDocument1 = new BasicDBObject();
		BasicDBObject oldDocument1 = new BasicDBObject();
		oldDocument1.append("_id", friendEmail);	
		if (friendList != null){
			for (String key : friendList.keySet()){
				if (!key.equals(userFriendList) && !key.equals(friendEmail)){
					document1.put(key, friendList.get(key));
				}
			}
		}
		document1.put(userEmail, "yes");
		BasicDBObject temp1 = new BasicDBObject();
		newDocument1.append("$set", temp1.append("friendList", document1));
		collection.update(oldDocument1, newDocument1);
		mongo.close();
	}
	
	
	public void deleteRequest(String userEmail, String friendEmail, BSONObject userFriendList, BSONObject friendList){
		//set friendList of user
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();
		BasicDBObject oldDocument = new BasicDBObject();
		oldDocument.append("_id", userEmail);	
		if (userFriendList != null){
			for (String key : userFriendList.keySet()){
				if (!key.equals(friendEmail) && !key.equals(userEmail)){
					document.put(key, userFriendList.get(key));
				}
			}
		}
		BasicDBObject temp = new BasicDBObject();
		newDocument.append("$set", temp.append("friendList", document));
		collection.update(oldDocument, newDocument);
		

		BasicDBObject document1 = new BasicDBObject();
		BasicDBObject newDocument1 = new BasicDBObject();
		BasicDBObject oldDocument1 = new BasicDBObject();
		oldDocument1.append("_id", friendEmail);	
		if (friendList != null){
			for (String key : friendList.keySet()){
				if (!key.equals(userEmail) && !key.equals(friendEmail)){
					document1.put(key, friendList.get(key));
				}
			}
		}
		BasicDBObject temp1 = new BasicDBObject();
		newDocument1.append("$set", temp1.append("friendList", document1));
		collection.update(oldDocument1, newDocument1);
		mongo.close();
	}
	
	
	public void close(){
		mongo.close();
	}
	
	public void registerGroup(GroupSettingsModel groupSettingsModel, UUID uuid, Object pictureID){
		
			BasicDBObject document = new BasicDBObject();
			document.put("_id", uuid.toString());
			document.put("groupName", groupSettingsModel.getGroupName());
			document.put("description", groupSettingsModel.getDescription());
			BasicDBList groupUser = new BasicDBList();
			groupUser = (new Helper()).removeDuplicate(groupSettingsModel);
			document.put("groupUser", groupUser);
			document.put("groupPicture", pictureID);
			document.put("postList", null);
			document.put("groupOwner", groupSettingsModel.getGroupSetting_email());
			collection.insert(document);
			mongo.close();
		
	}
	
	
	public void updateGroup(GroupSettingsModel groupSettingsModel, String id, Object pictureID){
		BasicDBObject document = new BasicDBObject();
		BasicDBObject newDocument = new BasicDBObject();
		BasicDBObject oldDocument = new BasicDBObject();
		oldDocument.append("_id", id);
		document.put("groupName", groupSettingsModel.getGroupName());
		document.put("description", groupSettingsModel.getDescription());
		BasicDBList groupUser = new BasicDBList();
		groupUser = (new Helper()).removeDuplicate(groupSettingsModel);
		System.out.println("fu "+groupUser.toString());
		document.put("groupPicture", pictureID);
		document.put("groupUser", groupUser);
		newDocument.append("$set", document);
		collection.update(oldDocument, newDocument);
		mongo.close();
	}
	
	
	public void deleteRecord(DBObject object){
		collection.remove(object);
		mongo.close();	
	}
	
	public void registerPost(PostModel postModel, String uuid){
		BasicDBObject document = new BasicDBObject();
		document.put("_id", uuid);
		document.put("owner_id", postModel.getPost_email());
		String months[] = {
			      "Jan", "Feb", "Mar", "Apr",
			      "May", "Jun", "Jul", "Aug",
			      "Sep", "Oct", "Nov", "Dec"};
		GregorianCalendar gcalendar = new GregorianCalendar();
		document.put("time", months[gcalendar.get(Calendar.MONTH)]+" " + gcalendar.get(Calendar.DATE) + " "+gcalendar.get(Calendar.YEAR)+
				" " + gcalendar.get(Calendar.HOUR) + ":"+gcalendar.get(Calendar.MINUTE) + ":"+gcalendar.get(Calendar.SECOND));
		document.put("liker", null);
		document.put("text", postModel.getText());
		collection.insert(document);
		mongo.close();
	}
	
	
	
	public void registerChat(ChatSettingsModel chatSettingsModel, String uuid){
		BasicDBObject document = new BasicDBObject();
		document.put("_id", uuid);
		document.put("owner", chatSettingsModel.getChat_email());
		document.put("partner", chatSettingsModel.getPartner());
		document.put("messageList", null);
		collection.insert(document);
		mongo.close();
	}
	
	
	public void registerMessage(MessageModel messageModel, String uuid){
		BasicDBObject document = new BasicDBObject();
		document.put("_id", uuid);
		document.put("sender", messageModel.getMessage_email());
		String months[] = {
			      "Jan", "Feb", "Mar", "Apr",
			      "May", "Jun", "Jul", "Aug",
			      "Sep", "Oct", "Nov", "Dec"};
		GregorianCalendar gcalendar = new GregorianCalendar();
		document.put("time", months[gcalendar.get(Calendar.MONTH)]+" " + gcalendar.get(Calendar.DATE) + " "+gcalendar.get(Calendar.YEAR)+
				" " + gcalendar.get(Calendar.HOUR) + ":"+gcalendar.get(Calendar.MINUTE) + ":"+gcalendar.get(Calendar.SECOND));
		document.put("text", messageModel.getMessage());
		collection.insert(document);
		mongo.close();
	}


}
