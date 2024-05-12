package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.User;
import org.fullstack.libapp.service.BookService;
import org.fullstack.libapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private BookService bookService;
  private UserService userService;

  @Autowired
  public BookController(BookService bookService,
                        UserService userService){
    this.bookService = bookService;
    this.userService = userService;
  }

  @PutMapping("/secure/checkout")
  public Book checkout (@RequestParam Long bookId,
                        @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    User user = userService.getDetail(headers.get("username"));
    return bookService.checkoutBook(user.getEmail(), bookId);
  }

  @PutMapping("/secure/return")
  public void returnBook (@RequestParam Long bookId,
                          @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    User user = userService.getDetail(headers.get("username"));
    bookService.returnBook(user.getEmail(), bookId);
  }

  @PutMapping("/secure/renew/loan")
  public void renewLoan (@RequestParam Long bookId,
                         @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    User user = userService.getDetail(headers.get("username"));
    bookService.renewLoan(user.getEmail(), bookId);
  }

  @GetMapping("/secure/ischeckedout/byuser")
  public Boolean checkoutBookByUser(@RequestParam Long bookId,
                                    @RequestHeader Map<String, String> headers) throws Exception {
    userService.authenticateUser(headers);
    User user = userService.getDetail(headers.get("username"));
    return bookService.checkoutBookByUser(user.getEmail(), bookId);
  }

  @GetMapping("/secure/currentloans/count")
  public int currentLoansCount(@RequestHeader Map<String, String> headers) throws Exception {
    userService.authenticateUser(headers);
    User user = userService.getDetail(headers.get("username"));
    return bookService.currentLoansCount(user.getEmail());
  }

}
