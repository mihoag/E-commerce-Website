package com.hcmus.common.entity;

import com.hcmus.common.entity.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "cart_items")
public class CartItem extends IdBasedEntity {

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private int quantity;

	@Transient
	private float shippingCost;

	public CartItem() {
		super();
	}

	public CartItem(Customer customer, Product product, int quantity) {
		super();
		this.customer = customer;
		this.product = product;
		this.quantity = quantity;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}

	public float getSubTotal() {
		return quantity * product.getDiscountPrice();
	}

	@Override
	public String toString() {
		return "CartItem [customer=" + customer + ", product=" + product + ", quantity=" + quantity + ", shippingCost="
				+ shippingCost + "]";
	}
}
