package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.requestmodels.AddReviewRequest;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.fullstack.libapp.service.MessageService;
import org.fullstack.libapp.service.ReviewService;
import org.fullstack.libapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  private ReviewService reviewService;
  private UserService userService;

  @Autowired
  public ReviewController(ReviewService reviewService, UserService userService){
    this.userService =  userService;
    this.reviewService = reviewService;
  }

  @PostMapping("/secure/add/review")
  public void addReview (@RequestBody AddReviewRequest addReviewRequest,
                         @RequestHeader Map<String, String> headers) throws Exception{

    userService.authenticateUser(headers);
    String userEmail = userService.getDetail(headers.get("username")).getEmail();
    reviewService.postReview(userEmail, addReviewRequest);
  }

}
