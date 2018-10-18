package com.sdi.bill.mysub;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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
			{
				List<String> ls = _dao.getTopTypes(s);
				if(ls != null && ls.size() > 0) {
					String o = ls.get(0);
					for(int j = 1; j < ls.size();j++) {
						o = o + "," + ls.get(j);
					}
					this._dao.saveTypes(s, o);
				}
			}

			{
				List<String> ls = _dao.getTopCitys(s);
				if(ls != null && ls.size() > 0) {
					JSONArray cs = new JSONArray();
					for(int j = 0; j < ls.size();j++) {
						String o = ls.get(j);
						float lon = 0;
						float lat = 0;
						float[] ll = _dao.getLonLat(o);
						if(ll != null) {
							lon = ll[0];
							lat = ll[1];
						}
						JSONObject ob = new JSONObject();
						ob.put("index", j);
						ob.put("city", o);
						ob.put("lon", lon);
						ob.put("lat", lat);
						cs.add(ob);
					}
					this._dao.saveCitys(s, cs.toJSONString());
				}

			}
		}
		
		return RET.SUCCESS;
	}
	
	public String getMyTypes(String oid) {
		JSONArray arr = _dao.getMyTypes(oid);
		if(arr == null) {
			return RET.error(12, "");
		}
		return arr.toJSONString();
	}
	
	public String getSub(String oid) {
		JSONObject res = new JSONObject();
		
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
		{
			JSONArray llarr = JSON.parseArray(jo.getString("citys"));
			if(llarr == null) {
				llarr = new JSONArray();
			}
			res.put("citys", llarr);
		}
		
		res.put("days", ddd / (1000*60*60*24)+1);
		res.put("money", jo.getFloat("money"));
		res.put("amount", jo.getFloat("amount"));
		return RET.data(res);
	}
}
