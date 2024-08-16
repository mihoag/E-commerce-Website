package com.hcmus.admin.setting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer>{
	
	@Query(value = "select * from currencies order by name asc", nativeQuery = true)
	public List<Currency> findAllByOrderByNameAsc();
}
