package com.sdi.bill.type;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.sdi.bill.common.BaseDao;

public class BillTypeDao extends BaseDao {

	public boolean addType(String oid,String name) {
		String sql = 
		"INSERT INTO type_user VALUES ('%s','%s');";
		String qq = String.format(sql, oid,name);
		int res = mJdbcTemplate.update(qq);
		return res == 1;
	}
	
	public boolean existType(String oid,String name) {
		String sql = 
		"SELECT * FROM type_user WHERE openid = '%s' AND NAME = '%s';";
		String qq = String.format(sql, oid,name);
    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(qq);
    	return rows.size() > 0;
	}
	
	public JSONArray getTypes(String oid) {
		String sql = 
		"SELECT name FROM type_user WHERE openid = '%s';";
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
