package org.fullstack.libapp.dao;


import org.fullstack.libapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(@RequestParam("email") String email);
  User findByUsername(@RequestParam("username") String username);
}
