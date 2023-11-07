package org.fullstack.libapp.dao;

import java.util.List;
import org.fullstack.libapp.entity.Checkout;
import org.fullstack.libapp.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

  List<Checkout> findByBookId(Long bookId);

  List<Checkout> findCheckoutsByUserEmail(String userEmail);

  Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

  void deleteAllByBookId(Long bookId);
}
