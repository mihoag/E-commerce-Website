package com.hcmus.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.hcmus.site.security.oauth.CustomerOAuth2UserService;
import com.hcmus.site.security.oauth.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomerOAuth2UserService oAuth2UserService;
	@Autowired
	private OAuth2LoginSuccessHandler oauth2LoginHandler;
	@Autowired
	private DatabaseLoginSuccessHandler databaseLoginHandler;
	@Autowired
	private RecaptchaFilter recaptchaFilter;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailService() {
		return new CustomerUserDetailsService();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailService());
		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		http.authorizeHttpRequests(auth ->

		auth.requestMatchers("/account_details", "/update_account_details", "/orders/**", "/cart", "/address_book/**",
				"/checkout", "/place_order", "/reviews/**", "/process_paypal_order", "/write_review/**", "/post_review",
				"/chat").authenticated().anyRequest().permitAll())
				.addFilterBefore(recaptchaFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin(login -> login.loginPage("/login").usernameParameter("email")
						.successHandler(databaseLoginHandler).permitAll())
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/login") // Custom login page
						.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
						.successHandler(oauth2LoginHandler))
				.logout(logout -> logout.permitAll())
				.rememberMe(remember -> remember.userDetailsService(userDetailService()).tokenValiditySeconds(86400)) // 1
																														// day)
				.csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/common/**", "/css/**");
	}
}
