package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.requestmodels.AddBookRequest;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.fullstack.libapp.service.AdminService;
import org.fullstack.libapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService){
    this.adminService = adminService;
  }

  @PostMapping("/secure/add/book")
  public Book addBook (@RequestBody AddBookRequest addBookRequest) throws Exception{
    return adminService.postBook(addBookRequest);
  }

  @PutMapping("/secure/remove/book")
  public void removeBook (@RequestParam Long bookId) throws Exception{
    adminService.removeBook(bookId);
  }

  @PutMapping("/secure/increase/book/qty")
  public void increaseBookQty (@RequestParam Long bookId) throws Exception{
    adminService.increaseBookQty(bookId);
  }

  @PutMapping("/secure/decrease/book/qty")
  public void decreaseBookQty (@RequestParam Long bookId) throws Exception{
    adminService.decreaseBookQty(bookId);
  }

}
