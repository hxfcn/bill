package com.sdi.bill.common;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {

    @Resource(name = "mJdbcTemplate")
    protected JdbcTemplate mJdbcTemplate;
    
    protected String _s(String v) {
    	return v==null ? "NULL" : "'" + v + "'";
    }
    
    protected String _so(String v) {
    	return v==null ? "" : v;
    }
}
