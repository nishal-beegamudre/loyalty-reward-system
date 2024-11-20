package com.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
public class EmailserviceApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(EmailserviceApplication.class, args);
	}

}
