package com.hcmus.admin.category;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.hcmus.admin.category.controller.CategoryPageInfo;
import com.hcmus.common.entity.Category;
import com.hcmus.common.exception.CategoryNotFoundException;

@SpringBootTest
public class CategoryServiceTests {
	
	  @Autowired
	  private CategoryService service;
	  
	  @Test
      public void listCategoryPerPage()
      {
		  CategoryPageInfo pageInfo = new CategoryPageInfo();
		  List<Category> categories = service.listCategoryByPage(pageInfo ,1,"name", "asc", "");
   
    	  categories.forEach(cate -> System.out.println(cate));
      }
	  
	  @Test
	  public void listCategoriesUsedInForm()
	  {
		  List<Category> categories =  service.listCategoriesUsedInForm();
		  categories.forEach(cate -> System.out.println(cate));
	  }
	  
	  @Test
	  public void updateAllCategories() throws CategoryNotFoundException
	  {
		 for( int i = 12 ;i <= 31 ; i++)
		 {
			 Category cate = service.getCateById(i);
			 service.save(cate);
		 }
		
	  }
	  
	  @Test
	  public void updateNameCategory() throws CategoryNotFoundException
	  {
		  
		  for(int i = 1 ; i <=31 ; i++)
		  {
			  Category cate = service.getCateById(i);
			  cate.setName(cate.getName().replaceAll("--", ""));
			  service.save(cate);
		  }	  
	  }
}
