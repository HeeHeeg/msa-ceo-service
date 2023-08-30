package com.example.auth.domain.request;

import com.example.auth.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String name;
    private String number;
    private String email;
    private String password;
    private String role;  //enum을 받아올 순 없으니 string으로
}
