package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.entity.User;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.fullstack.libapp.service.BookService;
import org.fullstack.libapp.service.MessageService;
import org.fullstack.libapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

  private MessageService messageService;
  private UserService userService;

  @Autowired
  public MessagesController(MessageService messageService, UserService userService){
    this.userService = userService;
    this.messageService = messageService;
  }

  @PostMapping("/secure/add/message")
  public void addMessage (@RequestBody Message messageReq,
                          @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    String userEmail = userService.getDetail(headers.get("username")).getEmail();
    messageService.postMessage(userEmail, messageReq);
  }

  // write TC for this
  @PostMapping("/secure/respond/message")
  public void responseMessage (@RequestBody AdminQuestionRequest adminQuestionRequest,
                               @RequestHeader Map<String, String> headers) throws Exception{
    userService.authenticateUser(headers);
    userService.validateAdminUser(headers);
    User user = userService.getDetail(headers.get("username"));
    String userEmail = user.getEmail();
    messageService.respondMessage(userEmail, adminQuestionRequest);
  }

}
