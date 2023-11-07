package org.fullstack.libapp.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import org.fullstack.libapp.dao.BookRepository;
import org.fullstack.libapp.dao.MessageRepository;
import org.fullstack.libapp.dao.ReviewRepository;
import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.entity.Review;
import org.fullstack.libapp.requestmodels.AddReviewRequest;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewService {

  private BookRepository bookRepository;
  private ReviewRepository reviewRepository;

  @Autowired
  public ReviewService(ReviewRepository reviewRepository,
      BookRepository bookRepository){
    this.reviewRepository = reviewRepository;
    this.bookRepository = bookRepository;
  }

  public void postReview(String userEmail, AddReviewRequest addReviewRequest) throws Exception {
    Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, addReviewRequest.getBookId());
    if(validateReview!=null){
      throw new Exception("Review already exist for this book !");
    }
    Review review = new Review();

    review.setUserEmail(userEmail);
    review.setDate(Date.valueOf(LocalDate.now()));
    review.setRating(addReviewRequest.getRating());
    review.setBookId(addReviewRequest.getBookId());
    if(addReviewRequest.getReviewDescription().isPresent()) {
      review.setReviewDescription(addReviewRequest.getReviewDescription().get());
    }

    reviewRepository.save(review);
  }
}
