package com.hcmus.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class AppConfig {

	 @Bean
	 public ObjectMapper objectMapper() {
	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
	        return objectMapper;
	 }
}
