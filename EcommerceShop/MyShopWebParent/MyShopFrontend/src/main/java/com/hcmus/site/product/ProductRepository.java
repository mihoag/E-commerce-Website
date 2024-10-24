package com.hcmus.site.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.product.Product;
import com.hcmus.common.entity.Category;

@Repository
public interface ProductRepository
		extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)" + " AND p.name like %?3%"
			+ " ORDER BY p.name ASC")
	public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, String keyword, Pageable pageable);

	public Product findByAlias(String alias);

	List<Product> findByCategory(Category category);

	@Query(value = "SELECT * FROM products WHERE enabled = true AND "
			+ "MATCH(name, short_description, full_description) AGAINST (?1)", nativeQuery = true)
	public Page<Product> search(String keyword, Pageable pageable);
}
