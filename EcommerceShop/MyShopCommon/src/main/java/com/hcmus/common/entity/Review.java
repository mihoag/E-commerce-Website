package com.hcmus.common.entity;

import java.util.Date;

import com.hcmus.common.entity.order.Order;
import com.hcmus.common.entity.order.OrderDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Review")
public class Review extends IdBasedEntity {

	@Column(length = 128, nullable = false)
	private String headline;

	@Column(length = 300, nullable = false)
	private String comment;

	private int rating;

	@Column(nullable = false)
	private Date reviewTime;

	@OneToOne
	@JoinColumn(name = "order_id")
	private OrderDetail orderDetail;

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Review(String headline, String comment, int rating, Date reviewTime) {
		super();
		this.headline = headline;
		this.comment = comment;
		this.rating = rating;
		this.reviewTime = reviewTime;
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

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	@Override
	public String toString() {
		return "Review [headline=" + headline + ", comment=" + comment + ", rating=" + rating + ", reviewTime="
				+ reviewTime + ", orderDetail=" + orderDetail + "]";
	}

}
