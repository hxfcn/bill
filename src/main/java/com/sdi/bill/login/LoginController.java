package com.sdi.bill.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import com.sdi.bill.common.BaseController;
import com.sdi.bill.common.HttpUtil;
import com.sdi.bill.common.RET;

@RestController 
public class LoginController extends BaseController {


	@Autowired
	LoginService _service;
	
	
	@RequestMapping(value="/login",produces="application/json;charset=UTF-8")  
	public String login(HttpServletRequest request) {
		JSONObject params = this.getJSONParam(request);
		if(params == null) {
			return RET.BAD_REQUEST;
		}
		
		String code = params.getString("code");
		String encryptedData = params.getString("encryptedData");
		String iv = params.getString("iv");
		if(code == null || encryptedData == null || iv== null ) {
			return RET.BAD_REQUEST;
		}
		return _service.login(encryptedData, iv, code);
	}
}
