package com.hcmus.site.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	public String checkEmail(String email)
	{
		Customer customer = repo.findByEmail(email);
		if(customer == null)
		{
			return "OK";
		}
		return "Duplicated";
	}
}
