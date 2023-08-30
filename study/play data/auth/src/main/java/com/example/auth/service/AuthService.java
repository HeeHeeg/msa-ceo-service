package com.example.auth.service;

import com.example.auth.domain.entity.Role;
import com.example.auth.domain.entity.User;
import com.example.auth.domain.request.SignupRequest;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public void signUp(SignupRequest request) { // request로는 유저의 정보면되겠다.
        User build = User.builder()
                .name(request.getName())
                .number(request.getNumber())
                .email(request.getEmail())
                .role(Role.valueOf(request.getRole()))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(build);

    }
}

