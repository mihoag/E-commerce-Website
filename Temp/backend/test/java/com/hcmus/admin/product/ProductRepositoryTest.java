package com.hcmus.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.order.Order;
import com.hcmus.common.entity.product.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 37);
		Category category = entityManager.find(Category.class, 5);
		
		for(int i = 6 ; i < 20; i++)
		{
			Product product = new Product();
			
			
			product.setName("Acer Aspire Desktop" + i);
			product.setAlias("acer_aspire_desktop" + i);
			product.setShortDescription("Short description for Acer Aspire");
			product.setFullDescription("Full description for Acer Aspire");
			
			product.setBrand(brand);
			product.setCategory(category);
			
			product.setPrice(678);
			product.setCost(600);
			product.setEnabled(true);
			product.setInStock(true);
			product.setMainImage("defaul.png");
			
			product.setCreatedTime(new Date());
			product.setUpdatedTime(new Date());
			
			Product savedProduct = repo.save(product);
			
			assertThat(savedProduct).isNotNull();
			assertThat(savedProduct.getId()).isGreaterThan(0);
		}
	
	}
	
	@Test
	public void testListAllProducts()
	{
	    Iterable<Product> iterableProducts = repo.findAll();
		iterableProducts.forEach(System.out::println);
	}
	
	@Test
	public void testGetProduct()
	{
		Integer id = 2;
		Product product = repo.findById(id).get();
		System.out.println(product);
		
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testUpdateProduct()
	{
		Integer id = 2;
		Product product = repo.findById(id).get();
		product.setPrice(499);
		
		repo.save(product);
		
		Product updatedProduct = entityManager.find(Product.class, id);
		
		assertThat(updatedProduct.getPrice()).isEqualTo(499);
	}
	
	@Test
	public void testDeleteProduct()
	{
		Integer id = 2;
		repo.deleteById(id);
		
		Optional<Product> result = repo.findById(id);
		
		assertThat(!result.isPresent());
	}
	
	@Test
	public void deleteAllProduct()
	{
		repo.deleteAll();
	}
	
	@Test
	public void testSaveProductWithImages()
	{
		Integer productId = 3;
		Product product = repo.findById(productId).get();
		
		product.setMainImage("main image.jpg");
		product.addExtraImage("extra image 1.png");
		product.addExtraImage("extra_image_2.png");
		product.addExtraImage("extra-image3.png");
		
		Product savedProduct = repo.save(product);
		
		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}
	
	@Test
	public void testSaveProductWithDetails()
	{
		Integer productId = 3;
		Product product = repo.findById(productId).get();
		
		product.addDetail("Device Memory", "128 GB");
		product.addDetail("CPU Model", "MediaTek");
		product.addDetail("OS", "Android 10");
		
		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();		
	}
	
	@Test
	public void listProductNotInOrder()
	{
		Integer orderId = 3;
		List<Product> products = repo.listProductNotIntOrder(orderId);
		products.forEach(System.out::println);
	}
}
