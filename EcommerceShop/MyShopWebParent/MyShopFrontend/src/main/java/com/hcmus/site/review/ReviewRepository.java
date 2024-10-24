package com.hcmus.site.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.Review;
import com.hcmus.common.entity.order.OrderDetail;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("select rv from Review rv where rv.orderDetail.product.id = ?1")
	public List<Review> listReviewByProduct(Integer productId);

	Review findByOrderDetail(OrderDetail orderDetail);

}
