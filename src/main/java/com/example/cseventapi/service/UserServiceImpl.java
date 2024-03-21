package com.example.cseventapi.service;

import com.example.cseventapi.entity.User;
import com.example.cseventapi.exception.UserWithSuchEmailAlreadyExists;
import com.example.cseventapi.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User create(User user) {
        if (userDao.existsByEmail(user.getEmail())) {
            throw new UserWithSuchEmailAlreadyExists();
        }

        return save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    @Override
    public User getCurrentUser() {
        return getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private User save(User user) {
        return userDao.save(user);
    }
}
