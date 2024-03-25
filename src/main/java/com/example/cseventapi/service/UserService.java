package com.example.cseventapi.service;

import com.example.cseventapi.dto.ShortOrganizationResponse;
import com.example.cseventapi.dto.UpdateUserDataRequest;
import com.example.cseventapi.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User create(User user);

    User getByEmail(String email);

    UserDetailsService userDetailsService();

    User getCurrentUser();

    User updateUserData(UpdateUserDataRequest request);

    List<ShortOrganizationResponse> getListOfOrganizations();
}
