package com.hcmus.site.customer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.setting.Setting;
import com.hcmus.site.Utility;
import com.hcmus.site.setting.CountryRepository;
import com.hcmus.site.setting.GeneralSettingBag;
import com.hcmus.site.setting.MailSettingBag;
import com.hcmus.site.setting.SettingService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private SettingService settingService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model)
	{
		List<Country> listCountries = countryRepo.findAll();
		
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("title", "Customer Registration");
		model.addAttribute("customer", new Customer());
		
		return "register/register_form";
	}
	
	// Create new customer
	@PostMapping("/create_customer")
	public String createCustomer(Customer customer, Model model,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		customerService.registerCustomer(customer);
		sendVerificationEmail(request, customer);
		return "register/register_success";
	}
	
	
	// 
	@GetMapping("/verify")
	public String verifyCustomer(@RequestParam("code") String code)
	{
		boolean checkVerify = customerService.checkVerification(code);
		
		if(!checkVerify)
		{ 
			return "register/verify_fail";
		}
		return "register/verify_success";
	}
	
	private void sendVerificationEmail(HttpServletRequest request, Customer customer) 
			throws UnsupportedEncodingException, MessagingException {
		
		MailSettingBag emailSettings = settingService.getMailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);
		
		String toAddress = customer.getEmail();
		String subject = emailSettings.getCustomerVerifySubject();
		String content = emailSettings.getCustomerVerifyContent();
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);
		
		content = content.replace("[[CustomerName]]", customer.getFullName());
		
		System.out.println(Utility.getSiteURL(request));
		String verifyURL = Utility.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();
		
		content = content.replace("[[VerificationLink]]", verifyURL);
		content = content.replace("[[CompanyName]]", emailSettings.getSenderName());
		content = content.replace("[[SupportEmail]]", emailSettings.getFromAddress());
		
		helper.setText(content, true);
		
		mailSender.send(message);
		
		System.out.println("to Address: " + toAddress);
		System.out.println("Verify URL: " + verifyURL);
	}	
	
	@GetMapping("/account_details")
	public String viewAccountDetails(Model model, HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		List<Country> listCountries = customerService.listAllCountries();
		
		model.addAttribute("customer", customer);
		model.addAttribute("listCountries", listCountries);
		
		return "customer/account_form";
	}
	
	@PostMapping("/update_account_details")
	public String updateAccount(Model model, Customer customer, RedirectAttributes ra,
			HttpServletRequest request)
	{
	    customerService.updateCustomer(customer);
	    ra.addFlashAttribute("message", "Update successfully");
		return "redirect:/account_details";
	}
}
