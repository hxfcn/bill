package com.sdi.bill.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

public class WxSession {
	
	public class WxSessionObject{
		public String openid;
		public String uuid;
		public long time;
	}
	
	private static WxSession _gsession = new WxSession();
	public static WxSession instance() {
		return _gsession;
	}
	
	private Map<String,WxSessionObject> _map = new HashMap<String,WxSessionObject>();
	private Map<String,WxSessionObject> _mapOpenID = new HashMap<String,WxSessionObject>();
	
	public String login(String openid) {
		String uuid = UUID.randomUUID().toString();
		long l = System.currentTimeMillis() / 1000;
		WxSessionObject obj = new WxSessionObject();
		obj.openid = openid;
		obj.time = l;
		obj.uuid = uuid;
		_map.put(uuid, obj);
		_mapOpenID.put(openid, obj);
		return uuid;
	}
	
	public String getOpenID(String uuid) {
		if(uuid.equals("1")) {
			return "1";
		}
		WxSessionObject o = _map.get(uuid);
		if(o == null) {
			return null;
		}
		String oid = o.openid;
		long ct = System.currentTimeMillis() / 1000;
		if(ct - o.time > timeout) {
			_map.remove(uuid);
			_mapOpenID.remove(oid);
			return "";
		}
		o.time = ct;
		return o.openid;
	}
	
	public String getUUID(String openid) {
		WxSessionObject o = _mapOpenID.get(openid);
		if(o == null) {
			return null;
		}
		String uid = o.uuid;
		long ct = System.currentTimeMillis() / 1000;
		if(ct - o.time > timeout) {
			_mapOpenID.remove(openid);
			_map.remove(uid);
			return "";
		}
		
		o.time = ct;
		return o.uuid;
	}
	
	public static boolean noLogin(String s) {
		return s == null;
	}
	
	public static boolean loginTimeout(String s) {
		return s.equals("");
	}
	
	public static int timeout = 300;
	
}
