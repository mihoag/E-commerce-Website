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
import com.hcmus.common.entity.chat.MessageDTO;
import com.hcmus.common.entity.chat.message;
import com.hcmus.common.exception.CustomerNotFoundException;

@RestController
@RequestMapping("/api/chat")
public class MessageRestApi {
     
	@Autowired
	private MessageService service;
	
	@Autowired
	private CustomerService cusService;
	
	@GetMapping("/{id}")
	public List<MessageDTO> getByCustomerId(@PathVariable("id") Integer id)
	{
		List<message> listMessage = service.getMessageByIdUser(id);
		return convertToDTO(listMessage);
	}
	
	public List<MessageDTO> convertToDTO(List<message> listMess)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MessageDTO> dtos = new ArrayList<>();
		for(message mess : listMess)
		{
			dtos.add(new MessageDTO(mess.getId(), mess.getContent(), mess.getCustomer().getId(), mess.getCustomer().getFullName(),  null, formatter.format(mess.getTime()), mess.getRole_chat()));
		}
		System.out.println(dtos);
		return dtos;
	}
	
	@PostMapping
	public String addMessage(@RequestBody MessageDTO dto) throws ParseException, CustomerNotFoundException
	{
		message mess = service.save(dtoToEntity(dto));
		return mess == null ? "fail" : "ok";
	}
	
	public message dtoToEntity(MessageDTO dto) throws ParseException, CustomerNotFoundException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new message(dto.getContent(), dto.getRole_chat(), cusService.getCustomerById(dto.getCustomerId()), null, formatter.parse(dto.getTime()));
	}
}
