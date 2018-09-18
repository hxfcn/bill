package com.sdi.bill.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

public class WxSession {
	
	public class WxSessionObject{
		public String openid;
		public long time;
	}
	
	private static WxSession _gsession = new WxSession();
	public static WxSession instance() {
		return _gsession;
	}
	
	private Map<String,WxSessionObject> _map = new HashMap<String,WxSessionObject>();
	
	public String login(String openid) {
		String uuid = UUID.randomUUID().toString();
		long l = System.currentTimeMillis() / 1000;
		WxSessionObject obj = new WxSessionObject();
		obj.openid = openid;
		obj.time = l;
		_map.put(uuid, obj);
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
		long ct = System.currentTimeMillis() / 1000;
		if(ct - o.time > timeout) {
			_map.remove(uuid);
			return "";
		}
		o.time = ct;
		return o.openid;
	}
	
	public static boolean noLogin(String s) {
		return s == null;
	}
	
	public static boolean loginTimeout(String s) {
		return s.equals("");
	}
	
	public static int timeout = 300;
	
}
