package com.hcmus.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.hcmus.chat", "com.hcmus.admin"})
@EnableMongoRepositories(basePackages = "com.hcmus.chat.repository")
@EntityScan({"com.hcmus.common.entity"})
public class MyShopBackendApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(MyShopBackendApplication.class, args);
	}
}
