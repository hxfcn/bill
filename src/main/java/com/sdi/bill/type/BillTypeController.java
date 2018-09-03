package com.sdi.bill.type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.sdi.bill.common.BaseController;
import com.sdi.bill.common.Ret;
import com.sdi.bill.session.WxSession;

public class BillTypeController extends BaseController{
	
	@Autowired
	BillTypeService _service;

	@RequestMapping(value="/add",produces="application/json;charset=UTF-8")  
	public String addtype(HttpServletRequest request) {
//		JSONObject params = this.getJSONParam(request);
//		if(params == null) {
//			return Ret.BAD_REQUEST;
//		}
//		String sid = this.getSession(params);
//		if(StringUtils.isNullOrEmpty(sid)) {
//			return Ret.NO_SESSIONKEY;
//		}
//		String oid = WxSession.instance().getOpenID(sid);
//		if(WxSession.noLogin(oid)) {
//			return Ret.NO_LOGIN;
//		}else if(WxSession.loginTimeout(oid)) {
//			return Ret.LOGIN_TIMEOUT;
//		}
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		String name = po.params.getString("name");
		if(StringUtils.isEmptyOrWhitespaceOnly(name)){
			return Ret.PARAMS_ERROR;
		}

		return _service.addType(po.openid, name);
	}
	
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")  
	public String getypes(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		return _service.getTypes(po.openid);
	}
}
