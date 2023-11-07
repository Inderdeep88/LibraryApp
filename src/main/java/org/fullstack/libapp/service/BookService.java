package org.fullstack.libapp.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import org.fullstack.libapp.dao.BookRepository;
import org.fullstack.libapp.dao.CheckoutRepository;
import org.fullstack.libapp.dao.HistoryRepository;
import org.fullstack.libapp.dao.ReviewRepository;
import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Checkout;
import org.fullstack.libapp.entity.History;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

  private BookRepository bookRepository;
  private CheckoutRepository checkoutRepository;
  private ReviewRepository reviewRepository;
  private HistoryRepository historyRepository;

  public BookService(BookRepository bookRepository,
      CheckoutRepository checkoutRepository,
      HistoryRepository historyRepository,
      ReviewRepository reviewRepository){
    this.bookRepository = bookRepository;
    this.checkoutRepository = checkoutRepository;
    this.historyRepository = historyRepository;
    this.reviewRepository = reviewRepository;
  }

  public Book checkoutBook (String userEmail, Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);

    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

    if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0){
      throw new Exception("Book does not exist or already checked out !");
    }

    book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1 );
    bookRepository.save(book.get());

    Checkout checkout = new Checkout(
        userEmail,
        LocalDate.now().toString(),
        LocalDate.now().plusDays(7).toString(),
        bookId);

    checkoutRepository.save(checkout);

    return book.get();
  }

  public void returnBook (String userEmail, Long bookId) throws Exception {
    Optional<Book> book = bookRepository.findById(bookId);
    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

    if (!book.isPresent() || validateCheckout == null){
      throw new Exception("Book does not exist or not checked out by user !");
    }

    book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1 );

    bookRepository.save(book.get());
    checkoutRepository.deleteById(validateCheckout.getId());

    History history = new History(userEmail,
        validateCheckout.getCheckoutDate(),
        LocalDate.now().toString(),
        book.get().getTitle(),
        book.get().getDescription(),
        book.get().getAuthor(),
        book.get().getImg());

    historyRepository.save(history);
  }

  public void renewLoan(String userEmail, Long bookId) throws Exception{
    Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
    Optional<Book> book = bookRepository.findById(bookId);

    if (!book.isPresent() || validateCheckout == null){
      throw new Exception("Book does not exist or not checked out by user !");
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Date d1 = simpleDateFormat.parse(validateCheckout.getReturnDate());
    Date d2 = simpleDateFormat.parse(LocalDate.now().toString());

    if(d1.before(d2)){
      validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
      checkoutRepository.save(validateCheckout);
    }
  }

  public Boolean checkoutBookByUser (String userEmail, Long bookId){
    Checkout checkout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
    return (checkout != null);
  }

  public int currentLoansCount(String userEmail){
    return checkoutRepository.findCheckoutsByUserEmail(userEmail).size();
  }


}
