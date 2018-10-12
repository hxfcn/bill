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
			String sql = "SELECT * FROM bills WHERE openid = '%s' AND billdate > '%s' AND billdate < '%s'";
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
	    		o.put("type", _so(type));
	    		o.put("money", money);
	    		o.put("desc", _so(desc));
	    		o.put("city", _so(city));
	    		arr.add(o);
	    	}
	    	return arr;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject getMoney(String oid) {
		
		try {
			String sql = 
				"SELECT SUM(money) AS m FROM bills WHERE openid = '%s' AND money < 0";
				String qq = String.format(sql,oid);
		    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
		    	JSONArray arr = new JSONArray();
		    	Iterator it = rows.iterator();
		    	if(it.hasNext()) {
		    		JSONObject o = new JSONObject();
		    		Map<String, Object> r = (Map<String, Object>)it.next();
		    		Object oo = r.get("m");
		    		float exp = 0;
		    		if(oo != null && oo instanceof Float ) {
		    			exp = (float)oo;
		    		}
		    		o.put("expense", exp);
		    		o.put("income", 0);
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
			this.addBillMysub(b);
			return res;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public void addBillMysub(Bill b) {
		try {
			String sql = 
		    "UPDATE mysub SET money = money + %d,amount = amount+1 where openid = '%s'";
			String qq = String.format(sql,Math.abs(b.money), b.openid);
			mJdbcTemplate.update(qq);
		}
		catch(Exception e) {
			e.printStackTrace();
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
	
	public JSONObject getTip(String oid) {
		try {
			String sql = 
				"SELECT SUM(money) AS m,COUNT(id) AS c FROM bills WHERE openid = '%s' AND money < 0";
				String qq = String.format(sql,oid);
		    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
		    	JSONArray arr = new JSONArray();
		    	Iterator it = rows.iterator();
		    	if(it.hasNext()) {
		    		JSONObject o = new JSONObject();
		    		Map<String, Object> r = (Map<String, Object>)it.next();
		    		Object oo = r.get("m");
		    		float exp = 0;
		    		if(oo != null && oo instanceof Float ) {
		    			exp = (float)oo;
		    		}
		    		o.put("expense", exp);
		    		
		    		Object oc = r.get("c");
		    		int ioc = 0;
		    		if(oc != null && oo instanceof Integer ) {
		    			ioc = (int)oc;
		    		}
		    		o.put("bills", ioc);
		    		o.put("income", 0);
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
