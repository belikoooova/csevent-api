package com.example.cseventapi.controller;

import com.example.cseventapi.dto.ShortOrganizationResponse;
import com.example.cseventapi.dto.UpdateUserDataRequest;
import com.example.cseventapi.entity.User;
import com.example.cseventapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PutMapping
    public User editCurrentUser(@RequestBody @Valid UpdateUserDataRequest request) {
        return userService.updateUserData(request);
    }

    @GetMapping("/organizations")
    public List<ShortOrganizationResponse> getUsersOrganizations() {
        return userService.getListOfOrganizations();
    }
}
