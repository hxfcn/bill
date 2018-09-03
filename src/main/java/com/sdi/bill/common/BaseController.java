package com.sdi.bill.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.sdi.bill.session.WxSession;

public class BaseController {
	
	public class ParamObj
	{
		public JSONObject params = null;
		public String openid = null;
		public String error = null;
	}
	
	public ParamObj checkRequest(HttpServletRequest request) {
		ParamObj po = new ParamObj();
		JSONObject params = this.getJSONParam(request);
		if(params == null) {
			po.error = Ret.BAD_REQUEST;
			return po;
		}
		String sid = this.getSession(params);
		if(StringUtils.isNullOrEmpty(sid)) {
			po.error = Ret.NO_SESSIONKEY;
			return po;
		}
		String oid = WxSession.instance().getOpenID(sid);
		if(WxSession.noLogin(oid)) {
			po.error =  Ret.NO_LOGIN;
			return po;
		}else if(WxSession.loginTimeout(oid)) {
			po.error =  Ret.LOGIN_TIMEOUT;
			return po;
		}
		
		po.params = params;
		po.openid = oid;
		po.error = null;
		return po;
	}
	
	public String getSession(JSONObject request) {
		String str = request.getString("sid");
		return str;
	}
	
	public JSONObject getJSONParam(HttpServletRequest request) {
	    JSONObject jsonParam = null;
	    try {
	        // 获取输入流
	        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

	        // 写入数据到Stringbuilder
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = streamReader.readLine()) != null) {
	            sb.append(line);
	        }
	        jsonParam = JSONObject.parseObject(sb.toString());
	        // 直接将json信息打印出来
	        //System.out.println(jsonParam.toJSONString());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return jsonParam;
	}
}
