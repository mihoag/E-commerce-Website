package com.hcmus.site.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.common.entity.Address;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.exception.CustomerNotFoundException;
import com.hcmus.site.Utility;
import com.hcmus.site.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/address_book")
public class AddressController {

	@Autowired
	private AddressService addService;
	@Autowired
	private CustomerService customerService;

	@GetMapping("/new")
	public String addNew(Model model) {
		List<Country> listCountries = customerService.listAllCountries();
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("address", new Address());
		return "address/address_form";
	}

	@PostMapping("/save")
	public String saveAddress(Address address, HttpServletRequest request, RedirectAttributes ra)
			throws CustomerNotFoundException {

		Customer customer = getCustomerByAuthenticatedRequest(request);

		address.setCustomer(customer);
		addService.save(address);

		ra.addFlashAttribute("message", "The address has been saved successfully.");

		return "redirect:/address_book";
	}

	@GetMapping("/default/{id}")
	public String setDefaultAddress(@PathVariable("id") Integer addressId, HttpServletRequest request)
			throws CustomerNotFoundException {
		Customer customer = getCustomerByAuthenticatedRequest(request);
		// System.out.println(customer);
		// System.out.println(addressId);
		addService.setDefaultAddress(addressId, customer.getId());
		String redirectOption = request.getParameter("redirect");
		String redirectURL = "redirect:/address_book";

		if ("cart".equals(redirectOption)) {
			redirectURL = "redirect:/cart";
		} else if ("checkout".equals(redirectOption)) {
			redirectURL = "redirect:/checkout";
		}

		return redirectURL;
	}

	@GetMapping("/edit/{id}")
	public String editAddress(@PathVariable("id") Integer addressId, Model model, HttpServletRequest request)
			throws CustomerNotFoundException {
		Customer customer = getCustomerByAuthenticatedRequest(request);
		List<Country> listCountries = customerService.listAllCountries();

		Address address = addService.get(addressId, customer.getId());

		model.addAttribute("address", address);
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Edit Address (ID: " + addressId + ")");

		return "address/address_form";
	}

	@GetMapping("/delete/{id}")
	public String deleteAddress(@PathVariable("id") Integer addressId, RedirectAttributes ra,
			HttpServletRequest request) throws CustomerNotFoundException {
		Customer customer = getCustomerByAuthenticatedRequest(request);
		addService.delete(addressId, customer.getId());

		ra.addFlashAttribute("message", "The address ID " + addressId + " has been deleted.");

		return "redirect:/address_book";
	}

	@GetMapping("/**")
	public String home(Model model, HttpServletRequest request) throws CustomerNotFoundException {

		Customer customer = getCustomerByAuthenticatedRequest(request);

		List<Address> listAdress = addService.listAddressBook(customer);

		boolean usePrimaryAddressAsDefault = true;
		for (Address address : listAdress) {
			if (address.isDefaultForShipping()) {
				usePrimaryAddressAsDefault = false;
				break;
			}
		}

		model.addAttribute("redirect", listAdress);
		model.addAttribute("listAddresses", listAdress);
		model.addAttribute("customer", customer);
		model.addAttribute("totalItems", listAdress.size());
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);

		return "address/address";
	}

	public Customer getCustomerByAuthenticatedRequest(HttpServletRequest request) throws CustomerNotFoundException {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		if (customer == null) {
			throw new CustomerNotFoundException("You must login to add this product to cart.");
		}
		return customer;
	}
}
