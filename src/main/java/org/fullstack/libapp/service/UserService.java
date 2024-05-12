package org.fullstack.libapp.service;

import org.fullstack.libapp.dao.UserRepository;
import org.fullstack.libapp.entity.Book;
import org.fullstack.libapp.entity.Checkout;
import org.fullstack.libapp.entity.User;
import org.fullstack.libapp.requestmodels.AddBookRequest;
import org.fullstack.libapp.requestmodels.AddUserRequest;
import org.fullstack.libapp.responsemodels.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public User register(AddUserRequest addUserRequest){
    User user = new User();

    user.setUsername(addUserRequest.getUsername());
    user.setPassword(addUserRequest.getPassword());
    user.setEmail(addUserRequest.getEmail());
    user.setFirstName(addUserRequest.getFirst_name());
    user.setLastName(addUserRequest.getLast_name());
    user.setIsAdminUser(addUserRequest.getIs_admin_user());

    return userRepository.save(user);
  }

  public LoginResponse generateToken(String username, String password) throws Exception {

    LoginResponse loginResponse = new LoginResponse();
    User user = userRepository.findByUsername(username);

    if (user == null){
      throw new Exception("User does not exist !");
    }

    if(user.getUsername().equals(username) && user.getPassword().equals(password)){
      String token = createToken(username, password);
      loginResponse.setStatus("success");
      loginResponse.setToken(token);
    } else {
      loginResponse.setStatus("failed");
    }
    return loginResponse;
  }

  public Boolean authenticate (String actualUserName, String actualToken) throws Exception {

    if(actualUserName == null){
      throw new Exception("User name missing in request headers!");
    }

    if(actualToken == null){
      throw new Exception("Token missing in request headers!");
    }

    User userFromDB = userRepository.findByUsername(actualUserName);
    if (userFromDB == null){
      throw new Exception("User does not exist !");
    }

    String expectedToken = createToken(actualUserName, userFromDB.getPassword());
    return expectedToken.equals(actualToken);
  }

  public String createToken(String username, String password){
    return Base64.getEncoder().encodeToString(username.concat(password).concat(Integer.toString(LocalDateTime.now().getHour())).getBytes());
  }

  public User getDetail (String username) throws Exception {
    User user = userRepository.findByUsername(username);
    if (user == null){
      throw new Exception("User does not exist !");
    }
    return user;
  }

  public void authenticateUser (Map<String, String> headers) throws Exception {
    if(!authenticate(headers.get("username"), headers.get("token"))){
      throw new Exception("Authentication failure!");
    }
  }

  public void validateAdminUser (Map<String, String> headers) throws Exception {
    User user = userRepository.findByUsername(headers.get("username"));
    if(!user.getIsAdminUser()){
      throw new Exception("Not an Admin User!");
    }
  }



}
