package com.project.MyDuo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
	/**
	 * Database 설정
	 * url 자신의 port 번호와 DataBase를 설정해주시면
	 * username : root(보통 만드시면 root로 됩니다.)
	 * password : 자신이 처음에 설정한 password 입력
	 * **/
	@Bean
	public DataSource mySqlDataSource()
	{
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
		dataSourceBuilder.url("jdbc:mariadb://localhost:3307/MyDUO");
		dataSourceBuilder.username("root");
		dataSourceBuilder.password("dkwktm45");
		return dataSourceBuilder.build();
	}
}