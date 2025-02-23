package com.hcmus.admin.setting;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.admin.setting.country.CountryRepository;
import com.hcmus.admin.setting.state.StateRepository;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.State;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Test
    public void testListByCountry()
    {
		Country country = countryRepo.findById(242).get();
    	List<State> states = stateRepo.findByCountryOrderByName(country);
    	states.forEach(System.out::println);
    }
}
