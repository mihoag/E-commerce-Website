package com.hcmus.site.review;

import java.util.Date;

public class ReviewDTO {
	
	private Integer id;
	private String headline;
	private String comment;
	private int rating;	
	private Date reviewTime;
	
	public ReviewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ReviewDTO(Integer id, String headline, String comment, int rating, Date reviewTime) {
		super();
		this.id = id;
		this.headline = headline;
		this.comment = comment;
		this.rating = rating;
		this.reviewTime = reviewTime;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Date getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
}
