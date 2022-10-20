package com.springsecurity.bootsecurity.service;




import com.springsecurity.bootsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    void update(User user);

    List<User> listUsers();

    User findById(int id);

    void delete(int id);

    @Transactional
    User findByUsername(String username);
    UserDetails loadUserByUsername(String username);
}