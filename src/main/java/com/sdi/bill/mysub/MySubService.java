package com.sdi.bill.mysub;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.BaseService;
import com.sdi.bill.common.RET;
import com.sdi.bill.type.BillTypeDao;

@Service
public class MySubService  extends BaseService{

	@Autowired
	private MySubDao _dao;
	
	
	public String updateAll() {
		
		List<String> oids = _dao.getids();
		if(oids == null || oids.size() <= 0) {
			return RET.error(11, "No Data");
		}
		
		for(int i = 0; i < oids.size();i++) {
			String s = oids.get(i);
			List<String> ls = _dao.getTopTypes(s);
			if(ls == null || ls.size() <= 0) {
				continue;
			}
			String o = ls.get(0);
			for(int j = 1; j < ls.size();j++) {
				o = o + "," + ls.get(j);
			}
			this._dao.saveTypes(s, o);
		}
		
		return RET.SUCCESS;
	}
	
	public String getSub(String oid) {
		JSONObject res = new JSONObject();
		{
			JSONArray arr = new JSONArray();
			{
				JSONObject o1 = new JSONObject();
				o1.put("city", "测试1北京");
				o1.put("lon", 116.4);
				o1.put("lat", 39.9);
				arr.add(o1);
			}
			{
				JSONObject o1 = new JSONObject();
				o1.put("city", "测试2上海");
				o1.put("lon", 121.4);
				o1.put("lat", 31.1);
				arr.add(o1);
			}
			{
				JSONObject o1 = new JSONObject();
				o1.put("city", "测试3广州");
				o1.put("lon", 113);
				o1.put("lat", 23);
				arr.add(o1);
			}
			res.put("citys", arr);
		}

		
		JSONObject jo = _dao.getMySub(oid);
		if(jo == null) {
			return RET.error(12, "");
		}
		
		Date dt = jo.getDate("regdate");
		Date now = new Date();
		long ddd = now.getTime() - dt.getTime();
		
		{
			String tts = jo.getString("types");
			String[] sss = tts.split(",");
			JSONArray arr = new JSONArray();
			for(int i=0;i<sss.length;i++) {
				arr.add(sss[i]);
			}
			res.put("types", arr);
		}
		
		res.put("days", ddd / (1000*60*60*24)+1);
		res.put("money", jo.getFloat("money"));
		res.put("amount", jo.getFloat("amount"));
		return RET.data(res);
	}
}
