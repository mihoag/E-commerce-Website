package com.hcmus.admin.setting.country;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.CountryDTO;
import com.hcmus.common.entity.product.Product;

@RestController
@RequestMapping("/api/country")
public class CountryController {

	
	@Autowired
	private CountryRepository repo;
	
	@GetMapping
	public List<CountryDTO> listAll()
	{
		List<Country> countries = repo.findAll();
		List<CountryDTO> listDto = new ArrayList<>();
		for(Country country : countries)
		{
			CountryDTO dto = new CountryDTO(country.getId(), country.getName(),country.getCode());
			listDto.add(dto);
		}
		return listDto;
	}
	
	
	
	@PostMapping("/save")
	public String save(@RequestBody Country country) {
		Country savedCountry = repo.save(country);
		return String.valueOf(savedCountry.getId());
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable("id") Integer id)
	{
		try {
			repo.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
