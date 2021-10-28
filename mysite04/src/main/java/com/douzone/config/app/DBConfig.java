
package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/douzone/mysite/config/app/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));// 최초로 데이터베이스와 연결되는 커넥션 수
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));// 동시에 사용할 수 있는 최대 커넥션 개수
		
		return dataSource;
	}
}
