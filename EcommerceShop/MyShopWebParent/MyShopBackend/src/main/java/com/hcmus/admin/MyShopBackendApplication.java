package com.hcmus.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.hcmus.chat", "com.hcmus.admin"})
@EntityScan({"com.hcmus.common.entity"})
public class MyShopBackendApplication {
	public static void main(String[] args) {
		
		SpringApplication.run(MyShopBackendApplication.class, args);
	}
}
