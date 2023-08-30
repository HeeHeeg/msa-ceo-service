package com.example.auth.config;

import com.example.auth.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;  // 시크릿 키 넣는것.

    //토큰을 만들건데, 뭘 기반으로 만들거냐? 뭘 기반으로할지를 파라미터로 받으면 된다. 우린 유저를 받을거다.
    public String makeToken(User user) { //만들기
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("name", user.getName());
        claims.put("number", user.getNumber());
        claims.put("role", user.getRole().name());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) //한달동안 키 유지
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
        return token;
    }

    //토큰 까는 로직 / 파싱하기
    public TokenInfo parseToken(String token) {
        Claims body = (Claims)Jwts.parserBuilder() // 바디를 Claims로 까줄거임.
                .setSigningKey(secret.getBytes()) //이걸로 까고
                .build()
                .parse(token)
                .getBody();//바디에 모든 데이터가 들어가있으니까 바디를 뽑아주는데,
        return TokenInfo.builder()
                .id(UUID.fromString(body.get("id", String.class)))
                .number(body.get("number", String.class))
                .name(body.get("name", String.class))
                .role(body.get("role", String.class))
                .build();
    }
}
