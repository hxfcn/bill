package com.sdi.bill.bills;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.sdi.bill.common.BaseController;
import com.sdi.bill.common.BaseController.ParamObj;
import com.sdi.bill.common.RET;

@RestController
public class BillsController extends BaseController {

	@Autowired
	BillsService _service;
	
	java.text.SimpleDateFormat _format = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value="/bills/detail",produces="application/json;charset=UTF-8")  
	public String gedetail(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		String st1 = po.params.getString("date0");
		String st2 = po.params.getString("date1");
		try {
			Date t1 = _format.parse(st1);
			Date t2 = _format.parse(st2);
			return _service.getBills(po.openid, t1, t2);
		}
		catch(ParseException pe) {
			return RET.PARAMS_ERROR;
		}
	}
	
	@RequestMapping(value="/bills/money",produces="application/json;charset=UTF-8")  
	public String getmoney(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		String st1 = po.params.getString("date0");
		String st2 = po.params.getString("date1");
		try {
			Date t1 = _format.parse(st1);
			Date t2 = _format.parse(st2);
			return _service.getBills(po.openid, t1, t2);
		}
		catch(ParseException pe) {
			return RET.PARAMS_ERROR;
		}
	}
	
	@RequestMapping(value="/bills/add",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  	
	public String add(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		Bill obj = po.params.toJavaObject(Bill.class);
		if(StringUtils.isEmptyOrWhitespaceOnly(obj.type)) {
			return RET.PARAMS_ERROR;
		}
		obj.openid = po.openid;
		return _service.addBill(obj);
	}
	
	@RequestMapping(value="/bills/update",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  	
	public String update(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		
		Bill obj = po.params.toJavaObject(Bill.class);
		if(StringUtils.isEmptyOrWhitespaceOnly(obj.type) || obj.id <= 0) {
			return RET.PARAMS_ERROR;
		}
		obj.openid = po.openid;
		return _service.update(obj);
	}
	
	@RequestMapping(value="/bills/del",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  	
	public String del(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		long id = 0;
		try {
			id = po.params.getLong("id");
		}
		catch(Exception e) {
			return RET.PARAMS_ERROR; 
		}
		if(id <= 0) {
			return RET.PARAMS_ERROR;
		}
		return _service.del(id);
	}
}
