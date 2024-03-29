package com.example.cseventapi.service.auth;

import com.example.cseventapi.dto.JwtAuthenticationResponse;
import com.example.cseventapi.dto.SignInRequest;
import com.example.cseventapi.dto.SignUpRequest;
import com.example.cseventapi.entity.User;
import com.example.cseventapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .color("fillBlue")
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.create(user);

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());

        return new JwtAuthenticationResponse(jwtService.generateToken(userDetails));
    }
}
