package com.hcmus.admin.brand;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Brand;
import com.hcmus.common.exception.BrandNotFoundException;

@Service
public class BrandService {
	public static final int BRANDS_PER_PAGE = 6;
	
	@Autowired
	private BrandRepository repo;
	
	public List<Brand> listAll() {
		return (List<Brand>) repo.findAll();
	}
	
	public Page<Brand> listByPage(Integer pageNum, String sortField, String sortDir, String keyword) {
		//
		Sort sort = Sort.by(sortField);
		if(sortDir.equals("asc"))
		{
			sort = sort.ascending();
		} 
		else
		{
			sort = sort.descending();
		}
		Pageable pageable = PageRequest.of(pageNum-1, BRANDS_PER_PAGE, sort);
		return repo.findAll(keyword, pageable);
	}
	
	public Brand save(Brand brand) {
		return repo.save(brand);
	}
	
	public Brand get(Integer id) throws BrandNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new BrandNotFoundException("Could not find any brand with ID " + id);
		}
	}
	
	public void delete(Integer id) throws BrandNotFoundException {
	    try {
	    	repo.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BrandNotFoundException("Brand not found with id " + id);
		}
	}
	
	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Brand brandByName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (brandByName != null) return "Duplicate";
		} else {
			if (brandByName != null && brandByName.getId() != id) {
				return "Duplicate";
			}
		}
		return "OK";
	}
}
