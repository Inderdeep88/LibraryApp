package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.requestmodels.AddReviewRequest;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.fullstack.libapp.service.MessageService;
import org.fullstack.libapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  private ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService){
    this.reviewService = reviewService;
  }

  @PostMapping("/secure/add/review")
  public void addReview (@RequestBody AddReviewRequest addReviewRequest) throws Exception{
    String userEmail = "test@test.com";
    reviewService.postReview(userEmail, addReviewRequest);
  }

}
