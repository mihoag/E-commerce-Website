package com.hcmus.site.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.order.OrderDetail;
import com.hcmus.common.entity.product.Product;
import com.hcmus.site.category.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private CategoryRepository cateRepo;
	
	@Test
	public void findByAlias()
	{
		Product product = repo.findByAlias("pelican-1200-case");
		System.out.println(product);
	}
	
	@Test
	public void findByCate()
	{
		Integer id = 8;
		String idMatcher = "-8-";
		
		Page<Product> products = repo.listByCategory(id, idMatcher, "",PageRequest.of(0, 10));
		List<Product> listProducts = products.getContent();
		listProducts.forEach(System.out::println);
	}
	
	@Test
	public void searchProduct()
	{
		String keyword = "phone";
		Page<Product> products = repo.search(keyword, PageRequest.of(0, 4));
		products.forEach(System.out::println);
		assertThat(products.getSize()).isEqualTo(4);
	}
	
	@Test
	public void testFindByCategory()
	{
		Category category = cateRepo.findById(6).get();
		List<Product> products = repo.findByCategory(category);
		
		products.forEach(System.out::println);
	}
	
	
}
