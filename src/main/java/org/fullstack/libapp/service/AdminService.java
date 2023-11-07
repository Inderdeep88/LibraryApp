package org.fullstack.libapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.fullstack.libapp.dao.BookRepository;
import org.fullstack.libapp.dao.CheckoutRepository;
import org.fullstack.libapp.dao.ReviewRepository;
import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Checkout;
import org.fullstack.libapp.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

  private BookRepository bookRepository;
  private CheckoutRepository checkoutRepository;
  private ReviewRepository reviewRepository;

  @Autowired
  public AdminService(BookRepository bookRepository,
      CheckoutRepository checkoutRepository,
      ReviewRepository reviewRepository){
    this.bookRepository = bookRepository;
    this.checkoutRepository = checkoutRepository;
    this.reviewRepository = reviewRepository;
  }

  public void postBook(AddBookRequest adminBookRequest){
    Book book = new Book();

    book.setTitle(adminBookRequest.getTitle());
    book.setAuthor(adminBookRequest.getAuthor());
    book.setDescription(adminBookRequest.getDescription());
    book.setCopies(adminBookRequest.getCopies());
    book.setCopiesAvailable(adminBookRequest.getCopies());
    book.setCategory(adminBookRequest.getCategory());
    book.setImg(adminBookRequest.getImg());

    bookRepository.save(book);
  }

  public void increaseBookQty(Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);

    if(book.isEmpty()){
      throw new Exception("Book does not exist !");
    }

    book.get().setCopies(book.get().getCopies() + 1);
    book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

    bookRepository.save(book.get());
  }

  public void decreaseBookQty(Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);

    if(book.isEmpty() || book.get().getCopies()==0 || book.get().getCopiesAvailable()==0){
      throw new Exception("Book does not exist or Qty locked!");
    }

    book.get().setCopies(book.get().getCopies() - 1);
    book.get().setCopiesAvailable(book.get().getCopies() - 1);

    bookRepository.save(book.get());
  }
  public void removeBook(Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);
    List<Checkout> checkoutList = checkoutRepository.findByBookId(bookId);

    if(book.isEmpty()){
      throw new Exception("Book id<"+bookId+"> not found !");
    }

    checkoutRepository.deleteAllByBookId(bookId);
    reviewRepository.deleteAllByBookId(bookId);
    bookRepository.delete(book.get());
  }

}
