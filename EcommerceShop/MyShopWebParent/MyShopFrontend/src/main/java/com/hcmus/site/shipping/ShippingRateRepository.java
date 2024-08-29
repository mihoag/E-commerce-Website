package com.hcmus.site.shipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.ShippingRate;

public interface ShippingRateRepository extends JpaRepository<ShippingRate, Integer>, PagingAndSortingRepository<ShippingRate, Integer>{
	public ShippingRate findByCountryAndState(Country country, String state);
}
