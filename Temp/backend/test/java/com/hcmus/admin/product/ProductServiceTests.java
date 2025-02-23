package com.hcmus.admin.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hcmus.common.entity.product.Product;
import com.hcmus.common.entity.product.ProductImage;

@SpringBootTest
public class ProductServiceTests {
	
	@Autowired
	ProductRepository repo;
	
	@Test
    public void saveExtraImage()
    {
		List<Product> products = repo.findAll();
		
		
		for(Product p : products)
		{
			String dir = "product-images/" + p.getId() + "/extras";;
	        Path dirPath = Paths.get(dir);
			Set<ProductImage> setProductImage = new HashSet<>();
	        
			try {
				Files.list(dirPath).forEach(file -> {
					String filePath = file.toAbsolutePath().toString();
					int lastIndexOfSlash = filePath.lastIndexOf("\\");
					String fileName = filePath.substring(lastIndexOfSlash + 1, filePath.length()); 
					
					setProductImage.add(new ProductImage(fileName, p));
					
				});
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			p.setImages(setProductImage);
			repo.save(p);
		}
	
    }
}
