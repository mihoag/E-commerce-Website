package com.hcmus.site.help;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help")
public class HelpController {

	@GetMapping
	public String helpHome() {
		return "help/help";
	}
}
