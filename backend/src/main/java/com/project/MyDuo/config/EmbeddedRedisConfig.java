package com.project.MyDuo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class EmbeddedRedisConfig {

	@Value("${spring.redis.port}")
	private int redisPort;

	private RedisServer redisServer;

	@PostConstruct
	public void redisServer() {
		redisServer =  RedisServer.builder()
				.port(redisPort)
				.setting("maxmemory 128M")
				.build();
		try{
			redisServer.start();
		}catch (Exception e){
			log.info("exception : " + e);
		}
	}

	@PreDestroy
	public void stopRedis() {
		if (redisServer != null) {
			redisServer.stop();
		}
	}
}