package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bson.BSONObject;

import models.GroupSettingsModel;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class Helper {
	

	public BasicDBList removeDuplicate(GroupSettingsModel groupSettingsModel){
		BasicDBList groupUser = new BasicDBList();
		Map<String, String> map = new HashMap<String,String>();
		
		
		String[] user1 = groupSettingsModel.getUser1().split(",");
		String[] user2 = groupSettingsModel.getUser2().split(",");
		String[] user3 = groupSettingsModel.getUser3().split(",");
		String[] user4 = groupSettingsModel.getUser4().split(",");
		
		if (user1.length != 0){
			groupUser.add(user1[user1.length-1]);
		}
		if (user2.length != 0){
			groupUser.add(user2[user2.length-1]);
		}
		if (user3.length != 0){
			groupUser.add(user3[user3.length-1]);
		}
		if (user4.length != 0){
			groupUser.add(user4[user4.length-1]);
		}
		
		System.out.println("helper group list "+groupUser.toString());
		for (Object item : groupUser.toArray()){
			if (!map.containsKey(item.toString()) && !item.toString().equals("None") && !item.equals("")){
				map.put(item.toString(), "");
			}else{
				System.out.println("group user helper remove "+item.toString());
				groupUser.remove(item);
			}
		}
		
		return groupUser;
		
	}
	
	
	
	public BasicDBList removeItem(BasicDBList list, String uuid){
		BasicDBList returnList = new BasicDBList();
		for (Object item : list.toArray()){
			if (!item.toString().equals(uuid)){
				System.out.println("helper add to return list "+item.toString());
				System.out.println("helper uuid "+uuid);
				returnList.add(item);
			}
		}
		System.out.println("helper return remove list "+returnList.toString());
		
		return returnList;
	}
	
	
	public BasicDBList keepUnique(BasicDBList list, String uuid){
		BasicDBList returnList = new BasicDBList();
		int count = 0;
		for (Object item : list.toArray()){
			if (item.toString().equals(uuid)){
				count++;
			}
			returnList.add(item);
		}
		if (count == 0){
			returnList.add(uuid);
		}
		System.out.println("helper count "+count);
		System.out.println("helper liker list "+returnList);
		return returnList;
	}
	
	
	public BasicDBList deleteItemInbox(BasicDBList inbox, String uuid){
		BasicDBList returnList = new BasicDBList();
		for (Object item : inbox.toArray()){
			if (!item.toString().equals(uuid)){
				returnList.add(item.toString());
			}
		}
		return returnList;
	}
	
	
	public BasicDBList getYesFriend(BSONObject friendList){
		BasicDBList returnList = new BasicDBList();
		for (String key : friendList.keySet()){
			if (friendList.get(key).toString().equals("yes")){
				returnList.add(key);
			}
		}
		return returnList;
	}
	
}
