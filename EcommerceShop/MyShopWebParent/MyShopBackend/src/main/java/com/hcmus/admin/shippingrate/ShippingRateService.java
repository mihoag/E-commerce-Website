package com.hcmus.admin.shippingrate;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hcmus.admin.product.ProductRepository;
import com.hcmus.admin.setting.country.CountryRepository;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.ShippingRate;
import com.hcmus.common.entity.product.Product;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShippingRateService {
	public static final int RATES_PER_PAGE = 10;
	private static final int DIM_DIVISOR = 139;	
	@Autowired private ShippingRateRepository shipRepo;
	@Autowired private CountryRepository countryRepo;
	@Autowired private ProductRepository productRepo;
	
	public Page<ShippingRate> listByPage(Integer pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		
		if(sortDir.equals("asc"))
		{
			sort = sort.ascending();
		} 
		else
		{
			sort = sort.descending();
		}
		Pageable pageable = PageRequest.of(pageNum-1, RATES_PER_PAGE, sort);
		return shipRepo.findAll(keyword, pageable);
	}
	
	public List<Country> listAllCountries() {
		return countryRepo.listAllAsc();
	}	
	
	public ShippingRate get(Integer id) throws ShippingRateNotFoundException {
		ShippingRate sr = shipRepo.findById(id).get();
		if(sr == null)
		{
			throw new ShippingRateNotFoundException("Shipping rate not found with ID " + id);
		}
	    return sr;
	}
	
	public void delete(Integer id) throws  ShippingRateNotFoundException {
	   try {
		   shipRepo.deleteById(id);
	   } catch (Exception e) {
		// TODO: handle exception
		throw new ShippingRateNotFoundException("Shipping rate not found with ID " + id);
	   }
	}
	
	
	public void updateCOD(int id, boolean enable) throws ShippingRateNotFoundException
	{
		ShippingRate sr = shipRepo.findById(id).get();
		if (sr == null) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
		}
		shipRepo.updateCODSupport(id, enable);
	}
	
	public List<ShippingRate> listAll()
	{
		return shipRepo.findAll();
	}

	public void save(ShippingRate rateInForm) throws ShippingRateAlreadyExistsException {
		ShippingRate rateInDB = shipRepo.findByCountryAndState(
				rateInForm.getCountry().getId(), rateInForm.getState());
		boolean foundExistingRateInNewMode = rateInForm.getId() == null && rateInDB != null;
		boolean foundDifferentExistingRateInEditMode = rateInForm.getId() != null && rateInDB != null && !rateInDB.equals(rateInForm);
		
		if (foundExistingRateInNewMode || foundDifferentExistingRateInEditMode) {
			throw new ShippingRateAlreadyExistsException("There's already a rate for the destination "
						+ rateInForm.getCountry().getName() + ", " + rateInForm.getState()); 					
		}
		shipRepo.save(rateInForm);
	}
	
	public float calculateShippingCost(Integer productId, Integer countryId, String state) 
			throws ShippingRateNotFoundException {
		ShippingRate shippingRate = shipRepo.findByCountryAndState(countryId, state);
		
		if (shippingRate == null) {
			throw new ShippingRateNotFoundException("No shipping rate found for the given "
					+ "destination. You have to enter shipping cost manually.");
		}
		
		Product product = productRepo.findById(productId).get();
		
		float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
		float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;
				
		return finalWeight * shippingRate.getRate();
	}
}
