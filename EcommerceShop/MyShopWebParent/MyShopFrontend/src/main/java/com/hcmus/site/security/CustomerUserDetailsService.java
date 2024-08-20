package com.hcmus.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hcmus.common.entity.Customer;
import com.hcmus.site.customer.CustomerRepository;

public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	private CustomerRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	    Customer customer = repo.findByEmail(username);
	    if(customer == null)
	    {
	    	throw new UsernameNotFoundException("User not found with email " + username);
	    }
	    return new CustomerUserDetails(customer);
	}

}
