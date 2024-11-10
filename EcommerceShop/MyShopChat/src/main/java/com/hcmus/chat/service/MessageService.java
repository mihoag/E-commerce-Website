package com.hcmus.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.chat.model.message;
import com.hcmus.chat.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepo;
	
	public List<message> findByCustomerId(Integer customerId) {
		return messageRepo.findByCustomerId(customerId);
	}
	
	public message save(message mess) {
		return messageRepo.save(mess);
	}
	
}
