package com.hcmus.common.entity.product;

import com.hcmus.common.entity.Constant;
import com.hcmus.common.entity.IdBasedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "product_images")
public class ProductImage extends IdBasedEntity {

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public ProductImage() {
	}

	public ProductImage(Integer id, String name, Product product) {
		this.id = id;
		this.name = name;
		this.product = product;
	}

	public ProductImage(String name, Product product) {
		this.name = name;
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Transient
	public String getImagePath() {
		return Constant.S3_BASE_URI + "/product-images/" + product.getId() + "/extras/" + this.name;
	}

	@Override
	public String toString() {
		return "ProductImage [name=" + name + ", product=" + product + "]";
	}

}
