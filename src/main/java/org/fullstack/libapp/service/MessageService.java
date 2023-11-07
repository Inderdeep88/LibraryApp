package org.fullstack.libapp.service;

import java.util.Optional;
import org.fullstack.libapp.dao.MessageRepository;
import org.fullstack.libapp.entity.Message;
import org.fullstack.libapp.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {

  private MessageRepository messageRepository;

  @Autowired
  public MessageService(MessageRepository messageRepository){
    this.messageRepository = messageRepository;
  }

  public void postMessage(String userEmail, Message messageReq){
    Message message = new Message(userEmail,
        messageReq.getTitle(),
        messageReq.getQuestion(),
        null,
        null,
        false);
    messageRepository.save(message);
  }

  public void respondMessage(String adminEmail, AdminQuestionRequest adminQuestionRequest)
      throws Exception {
    Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());

    if (!message.isPresent()) {
      throw new Exception("Message id <"+adminQuestionRequest.getId()+"> not found !");
    }
    message.get().setAdminEmail(adminEmail);
    message.get().setResponse(adminQuestionRequest.getResponse());
    message.get().setClosed(true);
    messageRepository.save(message.get());
  }

}
