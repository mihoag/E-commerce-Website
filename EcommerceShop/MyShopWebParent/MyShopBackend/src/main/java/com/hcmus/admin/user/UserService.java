package com.hcmus.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcmus.admin.category.controller.CategoryPageInfo;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.Role;
import com.hcmus.common.entity.User;
import com.hcmus.common.exception.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	
	private static final Integer PAGE_SIZE = 6;
	
    @Autowired
    private UserRepository userRepo;
     
    @Autowired
    private RoleRepository roleRepo; 
    
    public List<User> listAll()
    {
    	return userRepo.findAll();
    }
    
    public List<Role> listRole()
    {
    	return roleRepo.findAll();
    }
    
    public User saveUser(User user) throws UserNotFoundException
    {
    	boolean isUpdatingMode = (user.getId() != null);
    	user.setPassword(user.getPassword().trim());
    	
    	if(isUpdatingMode)
    	{
    		if(user.getPassword().equals(""))
    		{
    			Integer id = user.getId();
        		User userInDb = getUserById(id);
        		user.setPassword(userInDb.getPassword());
    		}
    		else
    		{  		   
    		   encodePassword(user);	
    		}
    	}
    	else
    	{
    		encodePassword(user);
    	}
    	return userRepo.save(user);
    }
    
    public void encodePassword(User user)
    {
    	String rawPassword = user.getPassword();
    	PasswordEncoder bcrypt = new BCryptPasswordEncoder();
    	String encodedPassword = bcrypt.encode(rawPassword);
    	user.setPassword(encodedPassword);
    }
    
    public User getUserById(int id) throws UserNotFoundException
    {
    	User user;
    	 try {
		    user = userRepo.findById(id).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw new UserNotFoundException("User not found with id " + id);
		}
         return user;
    }
    
    public void deleteUserById(int id) throws UserNotFoundException
    {
    	try {
    		userRepo.deleteById(id);
		} catch (Exception e) {
		   throw new UserNotFoundException("User not found with id " + id);
		}
    }
    
    public boolean checkUniqueEmail(Integer id, String email)
    {
    	User user = userRepo.getUserByEmail(email);
    	if(user == null)
    	{
    		return true;
    	}
    	
    	boolean isCreatingMode = (id == null);
    	
    	if(isCreatingMode)
    	{
    		return false;
    	}
    	else
    	{
    		if(user.getId() != id)
    		{
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    public void updateUserEnable(Integer id, boolean enable)
    {	   	
    	try {
			userRepo.updateEnabledStatus(id, enable);
		} catch (Exception e) {
			// TODO: handle exception
		    e.printStackTrace();
		}
    }
    
    public Page<User> listUserByPage(Integer pageNum, String sortField, String sortDir, String keyword)
    {
    	Pageable pageable = PageRequest.of(pageNum-1, UserService.PAGE_SIZE, sortDir.equals("asc") ? Sort.by(sortField).ascending()
				  : Sort.by(sortField).descending());
    	Page<User> pageUser = userRepo.findAll(keyword,pageable);
    	return pageUser;
    }
    
    public User getUserByEmail(String email)
    {
    	return userRepo.getUserByEmail(email);
    }
}
