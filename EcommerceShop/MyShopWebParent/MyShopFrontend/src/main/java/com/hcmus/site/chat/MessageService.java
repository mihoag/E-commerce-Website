package com.hcmus.site.chat;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.chat.message;

@Service
public class MessageService {
	@Autowired
	private MessageRepository repo;

	@Autowired
	private com.hcmus.site.customer.CustomerRepository cusRepo;

	public List<message> getMessageByIdUser(Integer idCustomer) {
		Customer customer = cusRepo.findById(idCustomer).get();
		return repo.findByCustomer(customer);
	}

	public message save(message mess) {
		return repo.save(mess);
	}
}
