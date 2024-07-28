package com.hcmus.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
   
   @Test	
   public void testBcryptEncoder()
   {
	   String rawPassword = "123456";
	   org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	   String encodedPassword = passwordEncoder.encode(rawPassword);
	   System.out.println(encodedPassword);
	   Boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
	   assertThat(matches).isTrue();
	   
   }
}
