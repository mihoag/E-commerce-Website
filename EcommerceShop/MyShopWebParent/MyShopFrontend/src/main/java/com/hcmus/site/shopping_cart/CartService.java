package com.hcmus.site.shopping_cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.CartItem;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.product.Product;
import com.hcmus.site.product.ProductRepository;

import ch.qos.logback.core.joran.spi.NewRuleProvider;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository cartRepo;

	public CartItem getCartItem(Integer id) {
		return cartRepo.findById(id).get();
	}

	public List<CartItem> listCartItems(Customer customer) {
		return cartRepo.findByCustomer(customer);
	}

	public void updateQuantity(Integer productId, Integer quantity, Customer customer) {
		cartRepo.updateQuantity(quantity, customer.getId(), productId);
	}

	public void deleteByCustomer(Integer customerId) throws CartItemNotFoundException {
		try {
			cartRepo.deleteByCustomer(customerId);
		} catch (Exception e) {
			throw new CartItemNotFoundException("Customer not found with id " + customerId);
		}
	}

	public float deleteByCustomerAndProduct(Integer customerId, Integer productId) {
		cartRepo.deleteByCustomerAndProduct(customerId, productId);

		List<CartItem> cartItems = cartRepo.findByCustomer(new Customer(customerId));
		float total = 0;
		for (CartItem item : cartItems) {
			total += item.getSubTotal();
		}
		return total;
	}

	public Integer addToCart(Customer customer, Integer productId, Integer quantity) throws ShoppingCartException {
		Integer updatedQuantity = quantity;

		Product product = new Product(productId);
		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);

		if (cartItem != null) {
			Integer quantityDB = cartItem.getQuantity();
			updatedQuantity += quantityDB;
			if (updatedQuantity > 5) {
				throw new ShoppingCartException("Could not add more " + quantity + " item(s)"
						+ " because there's already " + cartItem.getQuantity() + " item(s) "
						+ "in your shopping cart. Maximum allowed quantity is 5.");
			}
		} else {
			cartItem = new CartItem();
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}

		cartItem.setQuantity(updatedQuantity);
		cartRepo.save(cartItem);
		return updatedQuantity;
	}

	public float updateQuantity(Customer customer, Integer productId, Integer quantity) {
		Product product = new Product(productId);
		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		cartItem.setQuantity(quantity);

		cartRepo.save(cartItem);

		List<CartItem> cartItems = cartRepo.findByCustomer(customer);
		float total = 0;
		for (CartItem item : cartItems) {
			total += item.getSubTotal();
		}
		return total;
	}

}
