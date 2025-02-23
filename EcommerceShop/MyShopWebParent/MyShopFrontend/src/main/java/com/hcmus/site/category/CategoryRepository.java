package com.hcmus.site.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hcmus.common.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.enabled = true ORDER BY c.name ASC")
	public List<Category> findAllEnabled();

	@Query("SELECT c FROM Category c WHERE c.enabled = true AND c.alias = ?1")
	public Category findByAliasEnabled(String alias);

	@Query("select c from Category c WHERE c.name like %?1%")
	public List<Category> findByKeyWord(String keyword);
}
