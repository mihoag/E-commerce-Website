package com.hcmus.site.help;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hcmus.common.entity.Constant;

@Controller
@RequestMapping("/help")
public class HelpController {

	@GetMapping
	public String helpHome(Model model) {
		model.addAttribute("AI_SERVER_URI", Constant.AI_SERVER_URI);
		return "help/help";
	}
}
