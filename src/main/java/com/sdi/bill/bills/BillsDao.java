package com.sdi.bill.bills;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.BaseDao;

@Service
public class BillsDao extends BaseDao {

	public JSONArray getBills(String oid,String t1,String t2) {
		try {
			String sql = "SELECT * FROM bills WHERE openid = '%s' AND billdate > '%s' AND billdate < '%s';";
			String qq = String.format(sql,oid, t1,t2);
	    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
	    	JSONArray arr = new JSONArray();
	    	Iterator it = rows.iterator();
	    	while(it.hasNext()) {
	    		JSONObject o = new JSONObject();
	    		Map<String, Object> r = (Map<String, Object>)it.next();
	    		long id = (long)r.get("id");
	    		Timestamp ts = (Timestamp)r.get("billdate");
	    		Date dt = new Date(ts.getTime());
	    		String time = _format.format(dt);
	    		String type = (String)r.get("billtype");
	    		float money = (float)r.get("money");
	    		String desc = (String)r.get("mark");
	    		String city = (String)r.get("city");
	    		o.put("id", id);
	    		o.put("time", time);
	    		o.put("type", type);
	    		o.put("money", money);
	    		o.put("desc", desc);
	    		o.put("city", city);
	    		arr.add(o);
	    	}
	    	return arr;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject getMoney(String oid,String t1,String t2) {
		
		try {
			String sql = 
				"SELECT SUM(money) AS m FROM bills WHERE openid = '%s' AND TIME > '%s' AND TIME < '%s' AND money < 0;";
				String qq = String.format(sql,oid, t1,t2);
		    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
		    	JSONArray arr = new JSONArray();
		    	Iterator it = rows.iterator();
		    	if(it.hasNext()) {
		    		JSONObject o = new JSONObject();
		    		Map<String, Object> r = (Map<String, Object>)it.next();
		    		float exp = (float)r.get("m");
		    		o.put("expense", exp);
		    		return o;
		    	}
		    	return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	java.text.SimpleDateFormat _format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public int addBill(Bill b) {
		try {
			String time = _format.format(new Date());
			String sql = "INSERT INTO bills (openid,billdate,billtype,money,mark,city) VALUES ('%s','%s','%s',%f,%s,%s);";
			String qq = String.format(sql,b.openid, time,b.type,b.money,_s(b.desc),_s(b.city));
			int res = mJdbcTemplate.update(qq);
			return res;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int update(Bill b) {
		try {
			String sql = "UPDATE bills SET billtype = '%s',money=%f,mark=%s,city=%s WHERE id = %d";
			String qq = String.format(sql,b.type,b.money,_s(b.desc),_s(b.city),b.id);
			int res = mJdbcTemplate.update(qq);
			return res;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int del(long id) {
		try {
			String sql = "delete from bills WHERE id = %d";
			String qq = String.format(sql,id);
			int res = mJdbcTemplate.update(qq);
			return res;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
