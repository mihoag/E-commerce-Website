package com.hcmus.site;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {

	public String userHome()
	{
		return "index";
	}
}
