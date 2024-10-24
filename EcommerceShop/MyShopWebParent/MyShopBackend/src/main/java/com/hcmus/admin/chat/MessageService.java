package com.hcmus.admin.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.admin.customer.CustomerRepository;
import com.hcmus.admin.user.UserRepository;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.chat.message;

@Service
public class MessageService {
	@Autowired
	private MessageRepository repo;
	
	@Autowired
	private CustomerRepository cusRepo; 
	
	public List<message> getMessageByIdUser(Integer idCustomer)
	{
		Customer customer = cusRepo.findById(idCustomer).get();
		return repo.findByCustomer(customer);	
	}

	public message save(message mess) {
		// TODO Auto-generated method stub
		return repo.save(mess);
	}
}
