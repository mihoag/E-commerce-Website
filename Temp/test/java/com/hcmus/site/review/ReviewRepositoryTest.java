package com.hcmus.site.review;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Review;
import com.hcmus.common.entity.order.Order;
import com.hcmus.common.entity.order.OrderDetail;
import com.hcmus.site.order.OrderDetailRepository;
import com.hcmus.site.order.OrderRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewRepositoryTest {
	@Autowired
	private OrderDetailRepository repoOrderDetail;
	
	@Autowired
	private ReviewRepository repoReview;
	
	@Test
	public void addReviewTest()
	{
		Integer orderDetailId = 239;
		String headline = "So good product";
		String comment = "This is modern product with high relative";
		Integer rating = 5;
		OrderDetail orderDetail = repoOrderDetail.findById(orderDetailId).get();
		Review review = new Review(headline, comment, rating, new Date());
		review.setOrderDetail(orderDetail);
		Review savedReview = repoReview.save(review);
		System.out.println(savedReview);
	}
	
	@Test
	public void getReviewByProduct()
	{
		Integer productId= 93;
		List<Review> reviews = repoReview.listReviewByProduct(productId);
		reviews.forEach(System.out::println);
	}
	
	@Test
	public void findByOrderDetailTests()
	{
		OrderDetail orderDetail = repoOrderDetail.findById(29).get();
	    System.out.println(repoReview.findByOrderDetail(orderDetail));	
	}
	
	@Test
	public void isReviewedDetaiOrderTest()
	{
		try {
			OrderDetail orderDetail = repoOrderDetail.findById(29).get();
			Review review = repoReview.findByOrderDetail(orderDetail);
			if(review !=null)
			{
				System.out.println(true);
			}
			else {
				System.out.println(false);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(false);
		}
	}
}
