package com.hcmus.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.user.UserService;
import com.hcmus.admin.util.AmazonS3Util;
import com.hcmus.common.entity.User;
import com.hcmus.common.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
      @Autowired
      private UserService userService;
      
      
      @GetMapping("/{id}")
      public User getUserById(@PathVariable("id") int id) throws UserNotFoundException
      {
    	  return userService.getUserById(id);
      }
      
      @DeleteMapping("/{id}")
      public ResponseEntity<?> deleteUserById(@PathVariable("id") int id)
      {
    	  try {
    		userService.deleteUserById(id);
    		String userDir = "user-photos/" + id;
			AmazonS3Util.removeFolder(userDir);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.notFound().build();
		}
      }
      
      @PostMapping("/check_email")
  	  public String checkDuplicateEmail(Integer id ,  String email) {
        System.out.println(id + " " + email);
  		return userService.checkUniqueEmail(id, email) ? "OK" : "Duplicated";
      }
}
