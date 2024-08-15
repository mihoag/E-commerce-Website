package com.hcmus.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
      
	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory() {
       Category category = new Category("Electronics");
       Category savedCategory =  repo.save(category);
       
       assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(1);
		Category subCategory = new Category("iPhone", parent);
		Category savedCategory = repo.save(subCategory);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}

	@Test
	public void testGetCategory() {
		Category category = repo.findById(1).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		
		for (Category subCategory : children) {
			System.out.println(subCategory.getName());	
		}
		
		assertThat(children.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintHierarchicalCategories() {
        Iterable<Category> categories = repo.findAll();
		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());
				
				Set<Category> children = category.getChildren();
				
				for (Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}
	
	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for (Category subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {				
				System.out.print("--");
			}
			
			System.out.println(subCategory.getName());
			
			printChildren(subCategory, newSubLevel);
		}		
	}


	@Test
	public void testListRootCategories() {
		 List<Category> rootCategories = repo.findRootCategories(Sort.by("name").ascending());
	     rootCategories.forEach(root -> System.out.println(root));
	}
	
	@Test
	public void testFindByName() {
		Category category = repo.findByAlias("iPhone");
		assertThat(category).isNotNull();
	}
	
	@Test
	public void testFindByAlias() {
		Category category = repo.findByName("iPhone");
		assertThat(category).isNotNull();
	}
	
	@Test
	public void testAddListCatory()
	{
		//Category cate1 = new Category(1,'Electronics','electronics','electronics.png',1,NULL)
	}
	
	
	@Test
	public void updateCategory()
	{
		List<Category> categories = repo.findAll();
		for(Category cate : categories)
		{
			repo.save(cate);
		}
	}
}
