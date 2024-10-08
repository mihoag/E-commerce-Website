package com.hcmus.admin.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.hcmus.admin.user.UserRepository;
import com.hcmus.common.entity.User;

public class MyShopUserDetailsService implements UserDetailsService {

	@Autowired UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		// TODO Auto-generated method stub
	     User u = userRepo.getUserByEmail(email);
	     System.out.println(u);
	     if(u == null)
	     {
	    	 throw new UsernameNotFoundException("User not found!");
	     }
	     return new MyShopUserDetails(u);
	}

}

