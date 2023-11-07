package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.fullstack.libapp.service.BookService;
import org.fullstack.libapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

  private MessageService messageService;

  @Autowired
  public MessagesController(MessageService messageService){
    this.messageService = messageService;
  }

  @PostMapping("/secure/add/message")
  public void addMessage (@RequestBody Message messageReq) throws Exception{
    String userEmail = "test@test.com";
    messageService.postMessage(userEmail, messageReq);
  }

  @PostMapping("/secure/respond/message")
  public void responseMessage (@RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception{
    String userEmail = "admin@test.com";

    // Admin check post authorization impl

    messageService.respondMessage(userEmail, adminQuestionRequest);
  }

}
