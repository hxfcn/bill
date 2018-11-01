package com.sdi.bill.type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.sdi.bill.common.BaseController;
import com.sdi.bill.common.RET;
import com.sdi.bill.session.WxSession;

@RestController
public class BillTypeController extends BaseController{
	
	@Autowired
	BillTypeService _service;

	@RequestMapping(value="/type/add",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  
	public String addtype(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		String name = po.params.getString("name");
		if(StringUtils.isEmptyOrWhitespaceOnly(name)){
			return RET.PARAMS_ERROR;
		}
		
		BType bt = new BType();
		bt.openid = po.openid;
		bt.name = name;
		bt.type = 0;
		{
			Integer type = po.params.getInteger("type");
			if(type != null){
				bt.type = type;
			}
		}
		return _service.addType(bt);
	}
	
	@RequestMapping(value="/type/get",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  
	public String getypes(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		Integer type = po.params.getInteger("type");
		if(type == null || type == 0){
			return _service.getTypes(po.openid);
		}
		if(type == 1){
			return _service.getIncomeTypes(po.openid);
		}
		return RET.error(12, "UNKNOW TYPE");
	}
}
