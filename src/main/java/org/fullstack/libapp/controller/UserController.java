package org.fullstack.libapp.controller;

import org.fullstack.libapp.entity.User;
import org.fullstack.libapp.requestmodels.AddBookRequest;
import org.fullstack.libapp.requestmodels.AddUserRequest;
import org.fullstack.libapp.responsemodels.LoginResponse;
import org.fullstack.libapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService){
    this.userService = userService;
  }

  @PostMapping("/secure/register")
  public User register (@RequestBody AddUserRequest addUserRequest) throws Exception{
    return userService.register(addUserRequest);
  }
  @PostMapping("/secure/generateToken")
  public LoginResponse generateToken (@RequestParam String username,
                              @RequestParam String password) throws Exception{
    return userService.generateToken(username, password);
  }

}
