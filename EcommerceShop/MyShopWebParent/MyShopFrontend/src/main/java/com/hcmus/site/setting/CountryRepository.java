package com.hcmus.site.setting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{

	@Query("select c from Country c order by c.name asc")
	public List<Country> listAllAsc();
}
