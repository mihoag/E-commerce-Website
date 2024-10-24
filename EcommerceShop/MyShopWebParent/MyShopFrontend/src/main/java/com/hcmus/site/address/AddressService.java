package com.hcmus.site.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Address;
import com.hcmus.common.entity.Customer;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService {

	@Autowired
	private AddressRepository addressRepo;

	public List<Address> listAddressBook(Customer customer) {
		return addressRepo.findByCustomer(customer);
	}

	public void save(Address address) {
		addressRepo.save(address);
	}

	public Address get(Integer addressId, Integer customerId) {
		return addressRepo.findByIdAndCustomer(addressId, customerId);
	}

	public void delete(Integer addressId, Integer customerId) {
		addressRepo.deleteByIdAndCustomer(addressId, customerId);
	}

	public void setDefaultAddress(Integer defaultAddressId, Integer customerId) {
		System.out.println(defaultAddressId + " " + customerId);
		if (defaultAddressId > 0) {
			addressRepo.setDefaultAddress(defaultAddressId);
		}

		addressRepo.setNotDefaultForOthers(defaultAddressId, customerId);
	}

	public Address getDefaultAddress(Customer customer) {
		return addressRepo.findDefaultByCustomer(customer.getId());
	}
}
