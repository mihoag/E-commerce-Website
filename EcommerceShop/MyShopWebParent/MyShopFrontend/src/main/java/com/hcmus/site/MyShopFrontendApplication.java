package com.hcmus.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.hcmus.chat", "com.hcmus.site"})
@EntityScan({"com.hcmus.common.entity"})
public class MyShopFrontendApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyShopFrontendApplication.class, args);
	}
}
