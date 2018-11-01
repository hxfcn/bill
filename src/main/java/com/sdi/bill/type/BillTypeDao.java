package com.sdi.bill.type;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.sdi.bill.common.BaseDao;

@Service
public class BillTypeDao extends BaseDao {

	public boolean addType(BType bt) {
		try {
			String sql = 
					"INSERT INTO type_user VALUES ('%s','%s',%d)";
			String qq = String.format(sql, bt.openid,bt.name,bt.type);
			int res = mJdbcTemplate.update(qq);
			return res == 1;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean existType(BType bt) {
		String sql = 
		"SELECT * FROM type_user WHERE openid = '%s' AND NAME = '%s' AND type = %d";
		String qq = String.format(sql, bt.openid,bt.name,bt.type);
    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	return rows.size() > 0;
	}
	
	public JSONArray getTypes(String oid) {
		String sql = 
		"SELECT name FROM type_user WHERE openid = '%s' AND type = 0";
		String qq = String.format(sql, oid);
    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	JSONArray arr = new JSONArray();
    	Iterator it = rows.iterator();
    	while(it.hasNext()) {
    		Map<String, Object> r = (Map<String, Object>)it.next();
    		arr.add((String)r.get("name"));
    	}
    	return arr;
	}
	
	public JSONArray getIncomeTypes(String oid) {
		String sql = 
		"SELECT name FROM type_user WHERE openid = '%s' AND type = 1";
		String qq = String.format(sql, oid);
    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	JSONArray arr = new JSONArray();
    	Iterator it = rows.iterator();
    	while(it.hasNext()) {
    		Map<String, Object> r = (Map<String, Object>)it.next();
    		arr.add((String)r.get("name"));
    	}
    	return arr;
	}
	
}
