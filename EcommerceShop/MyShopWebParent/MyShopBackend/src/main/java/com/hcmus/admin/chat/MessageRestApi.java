package com.hcmus.admin.chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.customer.CustomerService;
import com.hcmus.chat.model.RoleChat;
import com.hcmus.chat.model.message;
import com.hcmus.chat.service.MessageService;
import com.hcmus.common.exception.CustomerNotFoundException;

@RestController
@RequestMapping("/api/chat")
public class MessageRestApi {
     
	@Autowired
	private MessageService service;

	
	@GetMapping("/{id}")
	public List<message> getByCustomerId(@PathVariable("id") Integer id)
	{
		List<message> listMessage = service.findByCustomerId(id);
		return listMessage;
	}
	

	@PostMapping
	public String addMessage(@RequestBody message message) throws ParseException, CustomerNotFoundException
	{
		message.setRole_chat(RoleChat.ADMIN);
		System.out.println(message);
		message mess = service.save(message);
		return mess == null ? "fail" : "ok";
	}

}
