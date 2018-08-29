package com.sdi.bill.common;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {

    @Resource(name = "mJdbcTemplate")
    protected JdbcTemplate mJdbcTemplate;
    
    
}
