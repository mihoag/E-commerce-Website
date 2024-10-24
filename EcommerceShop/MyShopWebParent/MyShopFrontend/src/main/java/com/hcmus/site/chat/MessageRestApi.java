package com.hcmus.site.chat;

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
import com.hcmus.common.entity.chat.MessageDTO;
import com.hcmus.common.entity.chat.message;
import com.hcmus.site.customer.CustomerService;

@RestController
@RequestMapping("/api/chat")
public class MessageRestApi {

	@Autowired
	private MessageService service;

	@Autowired
	private CustomerService cusService;

	@GetMapping("/{id}")
	public List<MessageDTO> getByCustomerId(@PathVariable("id") Integer id) {
		List<message> listMessage = service.getMessageByIdUser(id);
		return convertToDTO(listMessage);
	}

	@PostMapping
	public String addMessage(@RequestBody MessageDTO chatMessage) throws ParseException {
		message mess = service.save(dtoToEntity(chatMessage));

		return mess == null ? "fail" : "ok";
	}

	public List<MessageDTO> convertToDTO(List<message> listMess) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MessageDTO> dtos = new ArrayList<>();
		for (message mess : listMess) {
			dtos.add(new MessageDTO(mess.getId(), mess.getContent(), mess.getCustomer().getId(),
					mess.getCustomer().getFullName(), null, formatter.format(mess.getTime()), mess.getRole_chat()));
		}
		return dtos;
	}

	public message dtoToEntity(MessageDTO dto) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new message(dto.getContent(), dto.getRole_chat(), cusService.getCustomerById(dto.getCustomerId()), null,
				formatter.parse(dto.getTime()));
	}
}
