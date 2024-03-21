package com.example.cseventapi.service.auth;

import com.example.cseventapi.dto.JwtAuthenticationResponse;
import com.example.cseventapi.dto.SignInRequest;
import com.example.cseventapi.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
