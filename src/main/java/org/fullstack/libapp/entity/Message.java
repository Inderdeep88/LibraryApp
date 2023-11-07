package org.fullstack.libapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Messages")
@Data
public class Message {

  Message() {}

  public Message(String userEmail, String title, String question,
      String adminEmail, String response, Boolean closed) {
    this.userEmail = userEmail;
    this.title = title;
    this.question = question;
    this.adminEmail = adminEmail;
    this.response = response;
    this.closed = closed;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_email")
  private String userEmail;

  @Column(name = "title")
  private String title;

  @Column(name = "question")
  private String question;

  @Column(name = "admin_email")
  private String adminEmail;

  @Column(name = "response")
  private String response;

  @Column(name = "closed")
  private Boolean closed;
}
