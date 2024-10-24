package com.hcmus.site.shopping_cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hcmus.common.entity.Address;
import com.hcmus.common.entity.CartItem;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.ShippingRate;
import com.hcmus.common.exception.CustomerNotFoundException;
import com.hcmus.site.Utility;
import com.hcmus.site.address.AddressService;
import com.hcmus.site.customer.CustomerService;
import com.hcmus.site.shipping.ShippingRateService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ShippingRateService shipService;

	@GetMapping("/**")
	public String getCartItems(Model model, HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);

		List<CartItem> listCartItems = cartService.listCartItems(customer);
		float totalPrice = 0;
		for (CartItem item : listCartItems) {
			totalPrice += item.getSubTotal();
		}

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		boolean usePrimaryAddressAsDefault = false;

		if (defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			usePrimaryAddressAsDefault = true;
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		model.addAttribute("shippingSupported", shippingRate != null);
		model.addAttribute("listCartItems", listCartItems);
		model.addAttribute("totalItems", listCartItems.size());
		model.addAttribute("totalPrice", totalPrice);

		return "cart/shopping_cart";
	}
}
