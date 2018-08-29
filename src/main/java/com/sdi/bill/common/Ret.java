package com.sdi.bill.common;

import com.alibaba.fastjson.JSONObject;

public class Ret {
	
	
	
	public static String BAD_REQUEST = "{code:100,error:\"BAD REQUEST\"}";
	
	public static String error(int code,String msg) {
		return String.format("{code:%d,error:\"%s\"}", code,msg);
	}
	
	public static String data(JSONObject json) {
		JSONObject o = new JSONObject();
		o.put("code", 0);
		o.put("data", json);
		return o.toJSONString();
	}
}
