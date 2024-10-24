package com.hcmus.site.review;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Review;
import com.hcmus.common.entity.order.Order;
import com.hcmus.common.entity.order.OrderDetail;
import com.hcmus.common.entity.product.Product;
import com.hcmus.site.order.OrderDetailRepository;
import com.hcmus.site.order.OrderRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository repoReview;

	@Autowired
	private OrderDetailRepository repoOrderDetail;

	public Review addReview(Integer orderDetailId, String headline, String comment, int rating) {
		if (isReviewedDetaiOrder(orderDetailId)) {
			return null;
		}

		OrderDetail orderDetail = repoOrderDetail.findById(orderDetailId).get();

		// update review product
		Product product = orderDetail.getProduct();
		int count = product.getReviewCount();
		product.setReviewCount(count + 1);
		float newRating = avgRating(product.getAverageRating(), count, rating);
		product.setAverageRating(newRating);

		orderDetail.setProduct(product);

		Review review = new Review(headline, comment, rating, new Date());
		orderDetail.setReview(true);
		review.setOrderDetail(orderDetail);

		return repoReview.save(review);
	}

	public float avgRating(float currentRating, int currentCount, int newRating) {
		return (currentRating * currentCount + newRating) / (currentCount + 1);
	}

	public List<Review> getReviewByProductId(Integer productId) {
		List<Review> reviews = repoReview.listReviewByProduct(productId);
		return reviews;
	}

	public boolean isReviewedDetaiOrder(Integer detailOrderId) {
		try {
			OrderDetail orderDetail = repoOrderDetail.findById(detailOrderId).get();
			Review review = repoReview.findByOrderDetail(orderDetail);
			if (review != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
