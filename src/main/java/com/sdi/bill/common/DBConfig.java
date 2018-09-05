package com.sdi.bill.common;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class DBConfig {

	
    @Bean(name = "mDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.m")
    public DataSource mDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mJdbcTemplate")
    public JdbcTemplate mJdbcTemplate(@Qualifier("mDataSource") DataSource dsmy) {
        return new JdbcTemplate(dsmy);
    }
	
}
