package com.hcmus.admin.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.hcmus.admin.AbstractExporter;
import com.hcmus.common.entity.User;
import com.hcmus.common.entity.product.Product;

import jakarta.servlet.http.HttpServletResponse;

public class ProductCsvExpoter extends AbstractExporter{
	
	public void export(List<Product> listProducts, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "products_");
		
		List<FullProductDTO> listProductDto = new ArrayList<>();
		
		
		for(Product p : listProducts)
		{
			listProductDto.add(new FullProductDTO(p.getId(), p.getName(), p.getAlias(),Jsoup.parse(p.getShortDescription()).text() ,Jsoup.parse(p.getFullDescription()).text(), p.getURI(), p.getMainImagePath(),
					p.isEnabled(), p.isInStock(), p.getCost(), p.getPrice(), p.getDiscountPercent(), p.getLength(), p.getWidth(), p.getHeight(),
					p.getWeight(), p.concatDetailProduct(), p.getCategory().getName(), p.getBrand().getName(), p.getReviewCount(),p.getAverageRating()));
		}
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"ID", "Name", "Alias", "Short Description", "Full Description", "URI","Main Image", "Is Enabled", 
				"Is in Stock", "Cost", "Price", "Discount Percent", "Length", "Width","Height", "Weight", 
				"Detail Product", "Category", "Brand", "Review count", "Avarage rating"};
	
		String[] fieldMapping = {"id", "name", "alias", "shortDescription", "fullDescription", "link", "mainImage" ,"enabled", "inStock",
          "cost", "price", "discountPercent" , "length" , "width", "height", "weight", "detail", "category" , "brand", "reviewCount", "avgRating"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (FullProductDTO dto : listProductDto) {
			csvWriter.write(dto, fieldMapping);
		}
		
		csvWriter.close();
	}
}
