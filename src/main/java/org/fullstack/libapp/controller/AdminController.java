package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.requestmodels.AddBookRequest;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.fullstack.libapp.service.AdminService;
import org.fullstack.libapp.service.MessageService;
import org.fullstack.libapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private AdminService adminService;
  private UserService userService;

  @Autowired
  public AdminController(AdminService adminService, UserService userService){
    this.adminService = adminService;
    this.userService = userService;
  }

  @PostMapping("/secure/add/book")
  public Book addBook (@RequestBody AddBookRequest addBookRequest,
                       @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    userService.validateAdminUser(headers);
    return adminService.postBook(addBookRequest);
  }

  @PutMapping("/secure/remove/book")
  public void removeBook (@RequestParam Long bookId,
                          @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    userService.validateAdminUser(headers);
    adminService.removeBook(bookId);
  }

  @PutMapping("/secure/increase/book/qty")
  public void increaseBookQty (@RequestParam Long bookId,
                               @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    userService.validateAdminUser(headers);
    adminService.increaseBookQty(bookId);
  }

  @PutMapping("/secure/decrease/book/qty")
  public void decreaseBookQty (@RequestParam Long bookId,
                               @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    userService.validateAdminUser(headers);
    adminService.decreaseBookQty(bookId);
  }

}
