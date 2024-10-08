package com.hcmus.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.admin.user.UserRepository;
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandReposiotoryTests {

	   @Autowired
	   private BrandRepository repo;
	   
	
	   @Test
	   public void testCreateBrand()
	   {
		   Brand brand = new Brand("Default brand");
		   
		   Category cate1 = new Category(1);
		   Set<Category> categories = new HashSet<>();
		   categories.add(cate1);
		   brand.setCategories(categories);
		   
		   Brand savedBrand = repo.save(brand);
		   assertThat(savedBrand.getId()).isGreaterThan(0); 
	   }
	  
	   
	   @Test
	   public void testListAllBrands()
	   {
		  List<Brand> brands = repo.findAll();
		  brands.forEach(brand -> System.out.println(brand));
	   }
	   
	   @Test
	   public void testGetBrandById()
	   {
		   Brand brand = repo.findById(1).get();
		   assertThat(brand).isNotNull();
	   }
	  
	   @Test
	   public void testUpdateBrand()
	   {
		   Brand brand = repo.findById(1).get();
		   brand.setName("Update name");
	   }
	     
	   @Test
	   public void testDeleteBrand()
	   {
		   int id = 1;
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
	   public void testGetCategories()
	   {
		  int id = 1;
		  Brand brand = repo.findById(id).get();
		  Set<Category> categoriess = brand.getCategories();
		  
		  for(Category cate : categoriess)
		  {
			  System.out.println(cate);
		  }
	   }
	   
	   @Test
	   public void testFindAllAsc()
	   {
		   List<Brand> listBrands = repo.findAll();
		   listBrands.forEach(System.out::println);
	   }
}
