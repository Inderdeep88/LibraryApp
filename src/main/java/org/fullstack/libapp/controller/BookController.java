package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService){
    this.bookService = bookService;
  }

  @PutMapping("/secure/checkout")
  public Book checkout (@RequestParam Long bookId) throws Exception{
    String userEmail = "test@test.com";
    return bookService.checkoutBook(userEmail, bookId);
  }

  @GetMapping("/secure/ischeckedout/byuser")
  public Boolean checkoutBookByUser(@RequestParam Long bookId){
    String userEmail = "test@test.com";
    return bookService.checkoutBookByUser(userEmail, bookId);
  }

  @GetMapping("/secure/currentloans/count")
  public int currentLoansCount(){
    String userEmail = "test@test.com";
    return bookService.currentLoansCount(userEmail);
  }

}
