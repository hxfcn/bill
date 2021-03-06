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
	
	public List<String> getTopCitys(String oid){
		String sql = "SELECT city,COUNT(id) AS c FROM bills WHERE openid = '%s' AND city IS NOT NULL GROUP BY city ORDER BY c DESC LIMIT 3 ";
    	String qq = String.format(sql, oid);
		List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	List<String> arr = new ArrayList<String>();
    	Iterator it = rows.iterator();
    	while(it.hasNext()) {
    		Map<String, Object> r = (Map<String, Object>)it.next();
    		arr.add((String)r.get("city"));
    	}
		return arr;
	}
	
	public boolean saveTypes(String oid,String ts) {
		String sql = "UPDATE mysub SET types='%s' WHERE openid = '%s'";
    	String qq = String.format(sql, ts,oid);
		int res = mJdbcTemplate.update(qq);
		return res == 1;
	}
	
	public boolean saveCitys(String oid,String ts) {
		Timestamp tts = new Timestamp(System.currentTimeMillis());  
		String ttts = tts.toString();
		String sql = "UPDATE mysub SET citys='%s',updatetime='%s' WHERE openid = '%s'";
    	String qq = String.format(sql, ts,ttts,oid);
		int res = mJdbcTemplate.update(qq);
		return res == 1;
	}
	
	public float[] getLonLat(String city) {
		String sql = "SELECT lon,lat FROM xzqh WHERE LOCATE(shi,'%s') > 0 AND TYPE = 2";
    	String qq = String.format(sql, city);
		List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	Iterator it = rows.iterator();
    	if(it.hasNext()) {
    		Map<String, Object> r = (Map<String, Object>)it.next();
    		float[] vs = new float[2];
    		vs[0] = (float)r.get("lon");
    		vs[1] = (float)r.get("lat");
    		return vs;
    	}
		return null;
	}
	
	public JSONArray getMyTypes(String oid) {
		try {
			
			{
				float count  = 0;
				List<String> strs = new ArrayList<String>();
				List<Float> vals = new ArrayList<Float>();
				String sql = "SELECT billtype,COUNT(id) AS c FROM bills WHERE openid = '%s' GROUP BY billtype ORDER BY c DESC";
				String qq = String.format(sql,oid);
		    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
		    	
		    	Iterator it = rows.iterator();
		    	while(it.hasNext()) {
		    		Map<String, Object> r = (Map<String, Object>)it.next();
		    		String b = (String)r.get("billtype");
		    		long iv = (Long)r.get("c");
		    		strs.add(b);
		    		vals.add((float)iv);
		    		count += (float)iv;
		    	}
		    	
		    	JSONArray arr = new JSONArray();
		    	for(int i=0;i<strs.size();i++) {
		    		JSONObject o = new JSONObject();
		    		o.put("name", strs.get(i));
		    		float n = vals.get(i) / (float)count * 100.0f;
		    		int in = (int)n;
		    		o.put("data", in);
		    		arr.add(o);
		    	}
		    	return arr;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject getMySub(String oid) {
		try {
			JSONObject o = new JSONObject();
			{
				String sql = "SELECT * FROM mysub WHERE openid = '%s'";
				String qq = String.format(sql,oid);
		    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
		    	
		    	Iterator it = rows.iterator();
		    	if(it.hasNext()) {
		    		
		    		
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
		    	}
			}
			{
				String sql = "SELECT SUM(money) AS m,COUNT(id) AS c FROM bills WHERE openid = '%s'";
				String qq = String.format(sql,oid);
		    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
		    	
		    	Iterator it = rows.iterator();
		    	if(it.hasNext()) {
		    		Map<String, Object> r = (Map<String, Object>)it.next();
		    		double mo = 0;
		    		{
		    			Object oo = r.get("m");
		    			if(oo != null) {
		    				mo = (double)(oo);
		    			}
		    		}
		    		long mnt = (long)r.get("c");	
		    		o.put("money", mo);
		    		o.put("amount", mnt);
		    	}
			}
			return o;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
