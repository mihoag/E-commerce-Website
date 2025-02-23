package com.hcmus.admin.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.User;

import java.util.List;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> , PagingAndSortingRepository<Brand, Integer>{

	public Brand findByName(String name);
 	
	 @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
	 public Page<Brand> findAll(String keyword, Pageable pageable);
	 

    @Query("SELECT new Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
    public List<Brand> findAll();
}
