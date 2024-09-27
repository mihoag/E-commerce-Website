package com.hcmus.admin.customer;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Customer;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {
    @Autowired
    private CustomerRepository repo;
    
    @Test
    public void testFindAll()
    {
       String keyword = "le";
       Page<Customer> pageCustomer = repo.findAll(keyword, PageRequest.of(0, 4));
       List<Customer> customers = pageCustomer.getContent();
       customers.forEach(System.out::println);
    }
    
    @Test
    public void updateCustomerEnable()
    {
    	repo.updateEnabledStatus(3, true);
    }
    
    @Test
    public void getCustomerByEmail()
    {
    	String email = "tknhatgpt10@gmail.com";
    	Customer customer = repo.findByEmail(email);
    	System.out.println(customer);
    }
}
