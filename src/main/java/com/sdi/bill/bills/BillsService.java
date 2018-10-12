package com.sdi.bill.bills;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.RET;

@Service
public class BillsService {

	@Autowired
	private BillsDao _dao;

	private static long day7ms = 1000 * 60 *60 *24*7;
	java.util.Calendar _cal = java.util.Calendar.getInstance();
	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
	
	
	public String getBills(String oid,Date t1,Date t2) {
		long ms = t2.getTime() - t1.getTime();
//		if(ms < 0 || ms > day7ms) {
//			return RET.error(11, "时间不合理。");
//		}
		_cal.setTime(t2);
		_cal.add(java.util.Calendar.DATE, 1);
		
		String st1 = format.format(t1);
		String st2 = format.format(_cal.getTime());
		JSONArray res = _dao.getBills(oid, st1, st2);
		if(res == null) {
			return RET.error(12, "");
		}
		return RET.data(res);
	}
	
	public String getMoney(String oid) {
		JSONObject res = _dao.getMoney(oid);
		if(res == null) {
			return RET.error(12, "");
		}
		return RET.data(res);
	}

	public String getTip(String oid) {
		JSONObject res = _dao.getTip(oid);
		if(res == null) {
			return RET.error(12, "");
		}
		return RET.data(res);
	}
	

	
	public String addBill(Bill b) {
		int res = _dao.addBill(b);
		if(res == 1) {
			return RET.SUCCESS;
		}else {
			return RET.error(12, "");
		}
	}
	
	public String update(Bill b) {
		
		int res = _dao.update(b);
		if(res == 1) {
			return RET.SUCCESS;
		}else {
			return RET.error(12, "");
		}
	}
	
	public String del(long id) {
		
		int res = _dao.del(id);
		if(res == 1) {
			return RET.SUCCESS;
		}else {
			return RET.error(12, "");
		}
	}
	
	
}
