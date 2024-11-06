package com.hcmus.site.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hcmus.common.entity.Constant;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.exception.CustomerNotFoundException;
import com.hcmus.site.Utility;
import com.hcmus.site.customer.CustomerService;
import com.hcmus.site.security.CustomerUserDetails;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/chat")
public class MessageController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String homeChat(HttpServletRequest request, Model model) throws CustomerNotFoundException {
		Customer customer = getCustomerByAuthenticatedRequest(request);
		model.addAttribute("sideBarFieldName", "chat");
		model.addAttribute("customerId", customer.getId());
		model.addAttribute("customerName", customer.getFullName());
		model.addAttribute("avatar", customer.getAvatarPath());
		model.addAttribute("CLIENT_SOCKET_CONNECTION_URI", Constant.CLIENT_SOCKET_CONNECTION_URI);
		return "chat/chat";
	}

	public Customer getCustomerByAuthenticatedRequest(HttpServletRequest request) throws CustomerNotFoundException {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found");
		}
		return customer;
	}

}
