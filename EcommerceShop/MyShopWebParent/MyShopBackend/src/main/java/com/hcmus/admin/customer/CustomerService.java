package com.hcmus.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Customer;
import com.hcmus.common.exception.CustomerNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
	
   @Autowired
   private CustomerRepository repo;
   
   public static final int CUSTOMERS_PER_PAGE = 10;
	
   public Page<Customer> listByPage(int pageNum, String sortField, String sortDir, String keyword)
   {
	   Sort sort = Sort.by(sortField);
	   sort =  sortDir.equals("asc") ? sort.ascending() : sort.descending();
	   Pageable pageable = PageRequest.of(pageNum-1, CUSTOMERS_PER_PAGE, sort);
	   
	   return repo.findAll(keyword, pageable);
   }
   
   public void updateCustomerEnable(Integer id, boolean enabled)
   {
	   repo.updateEnabledStatus(id, enabled);
   }
   
   public void deleteCustomer(Integer id) throws CustomerNotFoundException
   {
	   try {
		  repo.deleteById(id);
	} catch (Exception e) {
		// TODO: handle exception
		throw new CustomerNotFoundException("Customer not found with id " + id);
	}   
   }
   
   public Customer getCustomerById(Integer id) throws CustomerNotFoundException
   {
	  try {
		return repo.findById(id).get();
	} catch (Exception e) {
		// TODO: handle exception
		throw new CustomerNotFoundException("Customer not found with id " + id);
	}
   }
   
   public Customer saveCustomer(Customer customer)
   {
	    if(customer.getPassword().isEmpty())
	    {
	    	 Customer customerDb = repo.findById(customer.getId()).get();
	    	 customer.setPassword(customerDb.getPassword());
	    }
	    else
	    {
	    	encodePassword(customer);
	    }
	    
	    return repo.save(customer);
   }
   
   public void encodePassword(Customer customer)
   {
	   PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	   customer.setPassword(passwordEncoder.encode(customer.getPassword()));
   }
   
   public List<Customer> getAllCustomer(String keyword)
   {
	   return repo.findByKeyword(keyword);
   }
   
   public int updateMessageCount(int id) throws CustomerNotFoundException
	{
		Customer customer = repo.findById(id).get();
		if(customer == null)
		{
			throw new CustomerNotFoundException("Customer not found");
		}
		customer.setUnseenMessageCount(customer.getUnseenMessageCount()+1);
		Customer savedCustomer =  repo.save(customer); 
		return savedCustomer.getUnseenMessageCount();
	}
	
	public int resetMessageCount(int id) throws CustomerNotFoundException
	{
		Customer customer = repo.findById(id).get();
		if(customer == null)
		{
			throw new CustomerNotFoundException("Customer not found");
		}
		customer.setUnseenMessageCount(0);
		Customer savedCustomer =  repo.save(customer); 
		return savedCustomer.getUnseenMessageCount();
	}
}
