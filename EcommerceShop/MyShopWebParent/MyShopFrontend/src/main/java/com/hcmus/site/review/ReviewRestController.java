package com.hcmus.site.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.common.entity.Review;

@RestController
@RequestMapping("/api/review")
public class ReviewRestController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping
	public String addReview(Integer orderDetailId, String headline, String comment, int rating) {
		Review review = reviewService.addReview(orderDetailId, headline, comment, rating);
		if (review == null) {
			return "You have writen review for this product";
		} else {
			return "Write review successfully";
		}
	}

	@GetMapping("/{id}")
	public List<ReviewDTO> listReviews(@PathVariable("id") Integer id) {
		List<ReviewDTO> listReviewDto = new ArrayList<>();

		List<Review> reviews = reviewService.getReviewByProductId(id);
		for (Review review : reviews) {
			listReviewDto.add(new ReviewDTO(review.getId(), review.getHeadline(), review.getComment(),
					review.getRating(), review.getReviewTime()));
		}

		return listReviewDto;
	}
}
