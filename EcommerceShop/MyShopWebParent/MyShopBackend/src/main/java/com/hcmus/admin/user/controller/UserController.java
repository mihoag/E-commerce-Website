package com.hcmus.admin.user.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.user.UserRepository;
import com.hcmus.admin.user.UserService;
import com.hcmus.admin.user.export.UserCsvExporter;
import com.hcmus.admin.user.export.UserExcelExporter;
import com.hcmus.admin.user.export.UserPdfExporter;
import com.hcmus.admin.util.AmazonS3Util;
import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.Role;
import com.hcmus.common.entity.User;
import com.hcmus.common.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/users")
public class UserController {
   
	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	private String defaultRedirectURL = "redirect:/users/page/%d?sortField=%s&sortDir=%s&keyword=%s";
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/new")
	public String newUser(Model model)
	{
		List<Role> listRoles = userService.listRole();
		User user  = new User();
		user.setEnabled(true);
		model.addAttribute("sideBarFieldName", "users");
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("title", "New User");
		return "users/user_form";
	}
	
    private String formatDirectUrl(Integer pageNum, String sortField, String sortDir, String keyword)
    {
    	return String.format(defaultRedirectURL, pageNum, sortField, sortDir, keyword);
    }
	
	@PostMapping("/save")
	public String saveUser(User user, RedirectAttributes redirect, @RequestParam("image") MultipartFile multipartFile)
	{
		try {
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
			redirect.addFlashAttribute("message", "Save user successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			redirect.addFlashAttribute("message", "Create new user fail");
			LOGGER.error(e.getMessage());
		}
		return formatDirectUrl(1, "firstName" , "asc", user.getEmail());
	}

	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) 
	{
		try {
			User user = userService.getUserById(id);
			List<Role> listRoles = userService.listRole();
			
			model.addAttribute("sideBarFieldName", "users");
			model.addAttribute("user", user);
			model.addAttribute("listRoles", listRoles);
			model.addAttribute("title", String.format("Update user ( id : %d )", id));
		
			
			return "users/user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return formatDirectUrl(1, "firstName" , "asc", "");
			// TODO: handle exception
		}
	}

	@GetMapping("/user/{id}/enabled/{status}")
	public String updateUserEnable(@PathVariable("id") int id, @PathVariable("status") boolean status, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword, @Param("page") int page) throws UserNotFoundException
	{
	    userService.updateUserEnable(id, status);
		return formatDirectUrl(page, sortField, sortDir, keyword);
	}
	
	@GetMapping("/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserCsvExporter exporter = new UserCsvExporter();
		exporter.export(listUsers, response);
	}

	@GetMapping("/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserPdfExporter exporter = new UserPdfExporter();
		exporter.export(listUsers, response);
	}
	
	@GetMapping("/export/excel")
	public void exportToEXCEL(HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll();
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(listUsers, response);
	}
	
	
	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword,  Model model)
	{
		if(keyword == null)
	    {
	    	keyword = "";
	    }
	    Page<User> pageUser = userService.listUserByPage(pageNum, sortField, sortDir, keyword);
	    List<User> listUsers = pageUser.getContent();
	    long totalElement = pageUser.getTotalElements();
	    
	    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
	       
	    model.addAttribute("listUsers", listUsers);
		model.addAttribute("sideBarFieldName", "users");
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageUser.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
	    model.addAttribute("keyword", keyword);
		model.addAttribute("totalElement", totalElement);
	    
		return "users/user";
	}
	
	@GetMapping("/**")
	public String home(Model model)
	{
		return listByPage(1, "firstName", "asc", "", model);
	}
}
