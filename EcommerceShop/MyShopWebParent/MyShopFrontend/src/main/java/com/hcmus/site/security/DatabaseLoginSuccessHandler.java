package com.hcmus.site.security;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hcmus.common.entity.AuthenticationType;
import com.hcmus.common.entity.Customer;
import com.hcmus.site.customer.CustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private CustomerService service;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
		Customer customer = userDetails.getCustomer();

		service.updateAuthenticationType(customer, AuthenticationType.DATABASE);

		super.onAuthenticationSuccess(request, response, authentication);
	}
}
