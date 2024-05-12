package org.fullstack.libapp.requestmodels;

import lombok.Data;

@Data
public class AddUserRequest {

  private String username;

  private String password;

  private String email;

  private String first_name;

  private String last_name;

  private Boolean is_admin_user;
}
