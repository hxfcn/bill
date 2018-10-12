package com.sdi.bill.mysub;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.BaseDao;

@Service
public class MySubDao extends BaseDao {

	public List<String> getids() {
		String sql = 
		"SELECT openid FROM mysub;";
    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(sql);
    	List<String> arr = new ArrayList<String>();
    	Iterator it = rows.iterator();
    	while(it.hasNext()) {
    		Map<String, Object> r = (Map<String, Object>)it.next();
    		arr.add((String)r.get("openid"));
    	}
    	return arr;
	}
	
	public List<String> getTopTypes(String oid){
		String sql = "SELECT billtype,COUNT(id) AS c FROM bills WHERE openid = '%s' GROUP BY billtype ORDER BY c DESC LIMIT 3 ";
    	String qq = String.format(sql, oid);
		List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	List<String> arr = new ArrayList<String>();
    	Iterator it = rows.iterator();
    	while(it.hasNext()) {
    		Map<String, Object> r = (Map<String, Object>)it.next();
    		arr.add((String)r.get("billtype"));
    	}
		return arr;
	}
	
	public boolean saveTypes(String oid,String ts) {
		String sql = "UPDATE mysub SET types='%s' WHERE openid = '%s'";
    	String qq = String.format(sql, ts,oid);
		int res = mJdbcTemplate.update(qq);
		return res == 1;
	}
	
	public JSONObject getMySub(String oid) {
		try {
			String sql = 
					"SELECT * FROM mysub WHERE openid = '%s'";
					String qq = String.format(sql,oid);
			    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
			    	
			    	Iterator it = rows.iterator();
			    	if(it.hasNext()) {
			    		
			    		JSONObject o = new JSONObject();
			    		Map<String, Object> r = (Map<String, Object>)it.next();
			    		Timestamp ts = (Timestamp)r.get("regdate");
			    		Date dt = new Date(ts.getTime());
			    		o.put("regdate", dt);
			    		
			    		float mo = (float)r.get("money");
			    		float mnt = (float)r.get("amount");
			    		String citys = (String)r.get("citys");
			    		String types = (String)r.get("types");
			    		
			    		o.put("money", mo);
			    		o.put("amount", mnt);
			    		o.put("citys", citys);
			    		o.put("types", types);
			    		return o;
			    	}
			    	return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
