package com.example.cseventapi.service;

import com.example.cseventapi.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User create(User user);

    User getByEmail(String email);

    UserDetailsService userDetailsService();

    User getCurrentUser();
}
