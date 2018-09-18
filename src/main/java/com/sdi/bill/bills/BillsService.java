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
	
	public String getSub(String oid) {
		JSONObject res = new JSONObject();
		{
			JSONArray arr = new JSONArray();
			{
				JSONObject o1 = new JSONObject();
				o1.put("city", "北京");
				o1.put("lon", 116.4);
				o1.put("lat", 39.9);
				arr.add(o1);
			}
			{
				JSONObject o1 = new JSONObject();
				o1.put("city", "上海");
				o1.put("lon", 121.4);
				o1.put("lat", 31.1);
				arr.add(o1);
			}
			{
				JSONObject o1 = new JSONObject();
				o1.put("city", "广州");
				o1.put("lon", 113);
				o1.put("lat", 23);
				arr.add(o1);
			}
			res.put("citys", arr);
		}
		{
			JSONArray arr = new JSONArray();
			arr.add("娱乐");
			arr.add("旅游");
			arr.add("健康");
			res.put("types", arr);
		}

		res.put("days", 200);
		res.put("money", 12340);
		res.put("amount", 325);
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
