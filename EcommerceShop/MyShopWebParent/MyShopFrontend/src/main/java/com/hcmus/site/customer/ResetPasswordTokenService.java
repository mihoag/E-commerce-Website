package com.hcmus.site.customer;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService {
	private final StringRedisTemplate redisTemplate;
	private static final long OTP_EXPIRATION_MINUTES = 1;

	public ResetPasswordTokenService(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void storeResetPasswordToken(String email, String token) {
		redisTemplate.opsForValue().set("RESET_PASSWORD_TOKEN_" + email, token, OTP_EXPIRATION_MINUTES,
				TimeUnit.MINUTES);
		// Store email associated with token
		redisTemplate.opsForValue().set("TOKEN_EMAIL_" + token, email, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);
	}

	public boolean verifyResetPasswordToken(String email, String token) {
		String storedToken = redisTemplate.opsForValue().get("RESET_PASSWORD_TOKEN_" + email);
		return token.equals(storedToken);
	}

	public String getEmailFromToken(String token) {
		return redisTemplate.opsForValue().get("TOKEN_EMAIL_" + token);
	}
}
