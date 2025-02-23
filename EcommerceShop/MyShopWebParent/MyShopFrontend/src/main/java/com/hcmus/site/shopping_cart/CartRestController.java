package com.hcmus.site.shopping_cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.common.entity.Customer;
import com.hcmus.common.exception.CustomerNotFoundException;
import com.hcmus.site.Utility;
import com.hcmus.site.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("/add/{productId}/{quantity}")
	public String addToCart(@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity,
			HttpServletRequest request) {
		try {
			Customer customer = getCustomerByAuthenticatedRequest(request);

			Integer updatedQuantity = cartService.addToCart(customer, productId, quantity);
			return updatedQuantity + " item(s) of this product were added to your shopping cart.";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}

	@GetMapping("/update/{productId}/{quantity}")
	public float updateQuantity(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) throws CustomerNotFoundException {
		Customer customer = getCustomerByAuthenticatedRequest(request);
		return cartService.updateQuantity(customer, productId, quantity);
	}

	public Customer getCustomerByAuthenticatedRequest(HttpServletRequest request) throws CustomerNotFoundException {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);
		if (customer == null) {
			throw new CustomerNotFoundException("You must login to add this product to cart.");
		}
		return customer;
	}

	@GetMapping("/delete/{productId}")
	public float deleteCart(@PathVariable("productId") Integer id, HttpServletRequest request)
			throws CustomerNotFoundException {

		Customer customer = getCustomerByAuthenticatedRequest(request);

		System.out.println(customer.getId() + " " + id);

		float totalPrice = cartService.deleteByCustomerAndProduct(customer.getId(), id);
		System.out.println(totalPrice);
		return totalPrice;

	}
}
