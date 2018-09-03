package com.sdi.bill.type;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.sdi.bill.common.BaseService;
import com.sdi.bill.common.Ret;

public class BillTypeService extends BaseService {

	@Autowired
	private BillTypeDao _dao;
	
	public String addType(String oid,String typename) {
		if(typename.length() > 16) {
			return Ret.error(10, "字符长度超过限制");
		}
		if(_dao.existType(oid, typename)) {
			return Ret.error(12, "类型已存在");
		}
		
		boolean res = _dao.addType(oid, typename);
		if(res) {
			return Ret.SUCCESS;
		}else {
			return Ret.error(14, "添加失败");
		}
	}
	
	public String getTypes(String oid) {
		JSONArray arr = _dao.getTypes(oid);
		return Ret.data(arr);
	}
	
}
