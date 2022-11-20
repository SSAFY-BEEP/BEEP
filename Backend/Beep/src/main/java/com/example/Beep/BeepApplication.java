package com.example.Beep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
public class BeepApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeepApplication.class, args);
	}

}
