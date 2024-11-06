package com.hcmus.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.security.MyShopUserDetails;
import com.hcmus.admin.user.UserService;
import com.hcmus.admin.util.AmazonS3Util;
import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.User;
import com.hcmus.common.exception.UserNotFoundException;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/account")
public class AccountController {
           
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String viewDetails(@AuthenticationPrincipal MyShopUserDetails loggedUser,
			Model model) {
		String email = loggedUser.getUsername();
		User user = userService.getUserByEmail(email);
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", userService.listRole());
		model.addAttribute("title", "Detail Account");
		return "users/account_form";
	}
	
	@PostMapping("/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal MyShopUserDetails loggedUser,
			@RequestParam("image") MultipartFile multipartFile) throws UserNotFoundException, IOException
	{
		System.out.println(user.getId() + " " + user.getPassword());
		if (!multipartFile.isEmpty()) {
			
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.saveUser(user);
			
			String uploadDir = "user-photos/" + savedUser.getId();
			AmazonS3Util.removeFolder(uploadDir);
			AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
	
		} else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			
			userService.saveUser(user);
		}
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated.");
		return "redirect:/account";
	}
}
