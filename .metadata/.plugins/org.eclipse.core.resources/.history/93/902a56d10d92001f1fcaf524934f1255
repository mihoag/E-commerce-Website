package com.hcmus.site.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RecaptchaFilter extends OncePerRequestFilter {
    @Autowired
    private CaptchaService captchaService;

    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Apply filter only to specific endpoints (like login)
    	System.out.println(request.getMethod());
        return !("/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod()));
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		   String recaptchaToken = request.getParameter("recaptchaToken");
	        
	        if (recaptchaToken == null || !captchaService.validateCaptcha(recaptchaToken)) {
	            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Captcha validation failed");
	            return;
	        }
	        
	        filterChain.doFilter(request, response);
	}
}
