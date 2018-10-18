package com.sdi.bill.mysub;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.bill.common.BaseController;
import com.sdi.bill.common.BaseController.ParamObj;
import com.sdi.bill.type.BillTypeService;

@RestController
public class MySubController extends BaseController {

	@Autowired
	MySubService _service;
	
	@RequestMapping(value="/my/sub",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  	
	public String mysub(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		return _service.getSub(po.openid);
	}
	
	@RequestMapping(value="/my/types",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  	
	public String mytypes(HttpServletRequest request) {
		ParamObj po = this.checkRequest(request);
		if(po.error != null) {
			return po.error;
		}
		return _service.getMyTypes(po.openid);
	}
	
	@RequestMapping(value="/my/updateall",method= {RequestMethod.POST},produces="application/json;charset=UTF-8")  	
	public String updateall(HttpServletRequest request) {
		return _service.updateAll();
	}
}
