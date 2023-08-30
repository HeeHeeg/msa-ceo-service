package com.example.auth.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "users")
@Entity @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String number;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING) //ORDINAL로 하면 인덱스를 가져와서 코드를 보지 않으면 내가 뭐가 몇번째인지 모르니까 왠만해선 스트링으로 넣자.
    private Role role;
}
