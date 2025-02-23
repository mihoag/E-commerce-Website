package com.hcmus.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Role;
import com.hcmus.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
   @Autowired
   private UserRepository repo;
   
   @Autowired
   private TestEntityManager entityManager;
   
   @Test
   public void testCreateUserWithOneRole()
   {
	   Role role = entityManager.find(Role.class, 1);
	   User user = new User("minhhoang12345le@gmail.com", "123456", "Hoang", "Le Minh");
	   user.addRole(role);
	   
	   User savedUser = repo.save(user);
	   assertThat(savedUser.getId()).isGreaterThan(0);
   }
   
   @Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		userRavi.setEnabled(true);
		
		Role roleEditor = entityManager.find(Role.class, 8);
		Role roleAssistant = entityManager.find(Role.class, 10);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User savedUser = repo.save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
   
   @Test
   public void testListAllUsers()
   {
	  List<User> listUsers = repo.findAll();
	  listUsers.forEach( u -> { System.out.println(u); });
   }
   
   @Test
   public void testGetUserById()
   {
	   Integer id = 1;
	   User user = repo.findById(id).get();
	   assertThat(user.getId()).isEqualTo(id);
   }
   
   @Test
   public void testUpdateUserDetails()
   {
	   Integer id = 1;
	   User user = repo.findById(id).get();
	   String updatedEmail = "leminhhoang123456le@gmail.com";
	   String lastname = "Le Minh";
	   user.setEmail(updatedEmail);
	   user.setLastName(lastname);
	   
	   User updatedUser = repo.save(user);
	   assertThat(updatedUser.getEmail()).isEqualTo(updatedEmail);
	   assertThat(updatedUser.getLastName()).isEqualTo(lastname);
   }
   
   @Test
   public void testUpdateUserRoles()
   {
	    User userRavi = repo.findById(3).get();
		Role roleEditor = new Role(8);
		Role roleSalesperson = new Role(7);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		
		repo.save(userRavi);
   }
   
   @Test
   public void testDeleteUser()
   {
	   Integer id = 5;
	   repo.deleteById(id);
       assertThrows(NoSuchElementException.class, new Executable() {
		
		@Override
		public void execute() throws Throwable {
			// TODO Auto-generated method stub
			repo.findById(id).get();
		}
	}); 
	   
   }
   
   @Test
	public void testGetUserByEmail() {
		String email = "leminhhoang123456le@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user.getEmail()).isEqualTo(email);
	}   
	
	public void testCountById() {
		
	}
	
	@Test
	public void testDisableUser() {
	    repo.updateEnabledStatus(1, false);
	}
	
	@Test
	public void testEnableUser() {
		 repo.updateEnabledStatus(1, true);
	}
	
	@Test
	public void testListFirstPage()
	{
		Integer pageNum = 0;
		Integer pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
	    Page<User> pageUser = repo.findAll(pageable);
	    
	    List<User> listUser = pageUser.getContent();
	    listUser.forEach(user -> {
	         System.out.println(user);
	    });
	    assertThat(pageUser.getSize()).isEqualTo(pageSize);
	}
   
	@Test
	public void testFilterUserByPage()
	{
		Integer pageNum = 0;
		Integer pageSize = 4;
		String keyword = "bruce";
		
		Pageable pageable = PageRequest.of(pageNum, pageSize);
	    Page<User> pageUser = repo.findAll(keyword, pageable);
	    
	    List<User> listUser = pageUser.getContent();
	    listUser.forEach(user -> {
	         System.out.println(user);
	    });
	}
	
	
	
  
   
}
