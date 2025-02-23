package com.hcmus.admin.setting.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.admin.setting.country.CountryRepository;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.State;
import com.hcmus.common.entity.StateDTO;
import com.hcmus.common.exception.CountryNotFoundException;

@RestController
@RequestMapping("/api/states")
public class StateRestController {
    @Autowired
    private StateRepository repo;
    
    @Autowired CountryRepository countryRepo;
    
    @GetMapping("/list_by_country/{id}")
    public List<StateDTO> getListStatesByCountry(@PathVariable("id") Integer idCountry) throws CountryNotFoundException
    {
    	Optional<Country> optionalCountry = countryRepo.findById(idCountry);
    	//System.out.println(optionalCountry.get());
    	if(!optionalCountry.isPresent())
    	{
    		throw new CountryNotFoundException("Country not found with id " + idCountry);
    	}
    	List<StateDTO> listDTO = new ArrayList<>();
  
    	List<State> states = repo.findByCountryOrderByName(optionalCountry.get());
    	//states.forEach(System.out::println);
    	for(State state : states)
    	{
    		listDTO.add(new StateDTO(state.getId(), state.getName()));
    	}
    	return listDTO;
    }
    
    @PostMapping("/save")
    public String saveState(@RequestBody State state)
    {
    	State savedState = repo.save(state);
    	return savedState.getId() + "";
    }
    
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteState(@PathVariable("id") Integer id)
    {
    	repo.deleteById(id);
    	return ResponseEntity.ok().build();
    }
}
