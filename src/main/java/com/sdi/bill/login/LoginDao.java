package com.sdi.bill.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.BaseDao;

@Service
public class LoginDao extends BaseDao {
	
	boolean hasInfo(String openid) {
		String sql = "Select openid from user_infor where openid = '" + openid + "'";
    	List<Map<String, Object>> rows = mJdbcTemplate.queryForList(sql);
    	return rows.size() > 0;
	}
	
	int register(JSONObject userInfoJSON) {

		String openid = userInfoJSON.getString("openId");
		String nickName = userInfoJSON.getString("nickName");
		String gender = userInfoJSON.getString("gender");
		String city = userInfoJSON.getString("city");
		String province = userInfoJSON.getString("province");
		String country = userInfoJSON.getString("country");
		String avatarUrl = userInfoJSON.getString("avatarUrl");
		
		Date ct=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String dtstr = df.format(ct);
		String sql = 
		"INSERT INTO user_infor (openid,nickName,gender,country,province,city,regtime) VALUES ('%s','%s','%s','%s','%s','%s','%s')";
		String qq = String.format(sql, openid,nickName,gender,country,province,city,dtstr);
		int res = mJdbcTemplate.update(qq);
		return res;
	}
}
