package com.hcmus.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
   @Autowired
   private RoleRepository repo;
   
   @Test
   public void testCreateFirstRole()
   {
	   Role role = new Role("Admin", "Manage everything");
	   Role savedRole = repo.save(role);
	   assertThat(savedRole).isNotNull();
	   assertThat(savedRole.getName()).isEqualTo("Admin");
   }
   
   @Test
   public void testCreateRoles()
   {
	   Role roleSalesperson = new Role("Salesperson", "manage product price, "
				+ "customers, shipping, orders and sales report");
		
		Role roleEditor = new Role("Editor", "manage categories, brands, "
				+ "products, articles and menus");
		
		Role roleShipper = new Role("Shipper", "view products, view orders "
				+ "and update order status");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");
		
		List<Role> lsRs =  repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
		assertThat(lsRs.size()).isEqualTo(4);
   }
}
