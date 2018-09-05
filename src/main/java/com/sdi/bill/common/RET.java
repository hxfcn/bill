package com.sdi.bill.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RET {
	
	
	
	public static String BAD_REQUEST = "{code:100,error:\"BAD REQUEST\"}";
	
	public static String NO_SESSIONKEY = "{code:131,error:\"NO SESSIONKEY\"}";
	
	public static String NO_LOGIN = "{code:132,error:\"NO LOGIN\"}";
	
	public static String LOGIN_TIMEOUT = "{code:133,error:\"LOGIN TIMEOUT\"}";
	
	public static String PARAMS_ERROR = "{code:101,error:\"PARAMS ERROR\"}";
	
	public static String SUCCESS = "{code:0}";
	
	public static String error(int code,String msg) {
		return String.format("{code:%d,error:\"%s\"}", code,msg);
	}
	
	public static String data(JSONObject json) {
		JSONObject o = new JSONObject();
		o.put("code", 0);
		o.put("data", json);
		return o.toJSONString();
	}
	public static String data(JSONArray json) {
		JSONObject o = new JSONObject();
		o.put("code", 0);
		o.put("data", json);
		return o.toJSONString();
	}
}
