package com.hcmus.admin.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class MessageController {

	@GetMapping
	public String homeChat(Model model)
	{
		model.addAttribute("sideBarFieldName", "chat");
		return "chat/chat";
	}

}
