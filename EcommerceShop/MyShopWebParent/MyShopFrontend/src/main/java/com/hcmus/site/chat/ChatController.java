package com.hcmus.site.chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.chat.MessageDTO;
import com.hcmus.common.entity.chat.RoleChat;
import com.hcmus.common.entity.chat.message;
import com.hcmus.site.customer.CustomerService;

@Controller
public class ChatController {

	@Autowired
	private CustomerService cusService;

	@Autowired
	private MessageService messageService;

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public MessageDTO sendMessage(@Payload MessageDTO chatMessage) {
		System.out.println(dtoToEntity(chatMessage));
		message mess = messageService.save(dtoToEntity(chatMessage));

		System.out.println(mess);
		return new MessageDTO();
	}

	public message dtoToEntity(MessageDTO dto) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateSent = null;
		try {
			dateSent = formatter.parse(dto.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Customer customer = cusService.getCustomerById(dto.getCustomerId());
		return new message(dto.getContent(), dto.getRole_chat(), customer, null, dateSent);
	}
}
