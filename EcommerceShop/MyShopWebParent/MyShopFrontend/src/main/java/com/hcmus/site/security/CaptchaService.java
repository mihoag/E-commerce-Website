package com.hcmus.site.security;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hcmus.common.entity.Constant;

@Service
public class CaptchaService {

	private final Double THRESHOLD = 0.5;

	public boolean validateCaptcha(String captchaToken) {
		RestTemplate restTemplate = new RestTemplate();

		// Set up headers and request body
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("secret", Constant.CAPTCHA_SECRET_KEY);
		requestBody.add("response", captchaToken);

		// Make the POST request
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
		ResponseEntity<Map> response = restTemplate.exchange(Constant.RECAPTCHA_VERIFY_URL, HttpMethod.POST, request,
				Map.class);

		// Process the response
		Map<String, Object> responseBody = response.getBody();
		if (responseBody != null && (Boolean) responseBody.get("success")) {
			Double score = (Double) responseBody.get("score");
			return score >= THRESHOLD; // Adjust the threshold based on your needs
		}
		return false;
	}
}
