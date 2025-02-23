package com.hcmus.admin.product;

import java.util.Date;

import jakarta.persistence.Column;

public class FullProductDTO {
	
	private int id;
	private String name;
	private String alias;
	private String shortDescription;
	private String fullDescription;
	private String link;
	private String mainImage;
	private boolean enabled;
	private boolean inStock;
	private float cost;
	private float price;
	private float discountPercent;
	private float length;
	private float width;
	private float height;
	private float weight;
	private String detail;
	private String category;
	private String brand;
	private int reviewCount;
	private float avgRating;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getMainImage() {
		return mainImage;
	}
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	public FullProductDTO() {
	}
	/**
	 * @param id
	 * @param name
	 * @param alias
	 * @param shortDescription
	 * @param fullDescription
	 * @param enabled
	 * @param inStock
	 * @param cost
	 * @param price
	 * @param discountPercent
	 * @param length
	 * @param width
	 * @param height
	 * @param weight
	 * @param detail
	 * @param category
	 * @param brand
	 * @param reviewCount
	 * @param avgRating
	 */
	public FullProductDTO(int id, String name, String alias, String shortDescription, String fullDescription, String link, String mainImage,
			boolean enabled, boolean inStock, float cost, float price, float discountPercent, float length, float width,
			float height, float weight, String detail, String category, String brand, int reviewCount,
			float avgRating) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.link = link;
		this.mainImage = mainImage;
		this.enabled = enabled;
		this.inStock = inStock;
		this.cost = cost;
		this.price = price;
		this.discountPercent = discountPercent;
		this.length = length;
		this.width = width;
		this.height = height;
		this.weight = weight;
		this.detail = detail;
		this.category = category;
		this.brand = brand;
		this.reviewCount = reviewCount;
		this.avgRating = avgRating;
	}
}
