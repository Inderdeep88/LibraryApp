package org.fullstack.libapp.service;

import java.time.LocalDate;
import java.util.Optional;
import org.fullstack.libapp.dao.BookRepository;
import org.fullstack.libapp.dao.CheckoutRepository;
import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

  private BookRepository bookRepository;
  private CheckoutRepository checkoutRepository;

  public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository){
    this.bookRepository = bookRepository;
    this.checkoutRepository = checkoutRepository;
  }

  public Book checkoutBook (String userEmail, Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);

    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

    if (!book.isPresent() || validateCheckout != null || book.get().getCopies() <= 0){
      throw new Exception("Book does not exist or already checked out !");
    }

    book.get().setCopies(book.get().getCopies() - 1 );
    bookRepository.save(book.get());

    Checkout checkout = new Checkout(
        userEmail,
        LocalDate.now().toString(),
        LocalDate.now().plusDays(7).toString(),
        bookId);

    checkoutRepository.save(checkout);

    return book.get();
  }

  public Boolean checkoutBookByUser (String userEmail, Long bookId){
    Checkout checkout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
    return (checkout != null);
  }

  public int currentLoansCount(String userEmail){
    return checkoutRepository.findCheckoutsByUserEmail(userEmail).size();
  }
}
