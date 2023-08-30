package com.example.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    //요청들어올 때 필터를 하나 걸겠다는 것.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        //헤더에 토큰 있는지 없는지 검증
        String authHeader = request.getHeader("Authorization"); //여기서 토큰넣어 가져오는데
        if (authHeader == null || !authHeader.startsWith("Bearer ")){ //없거나 Bearer가 붙어있지 않으면
            filterChain.doFilter(request, response); //아무것도 하지 않고 그냥 진행시켜
            return;
        }
        String token = authHeader.replace("Bearer ", ""); // 있으면 없애줘. 있다는건 검증이 됐다는거니까.
        //토큰 까줘. 까서 검증.
        TokenInfo tokenInfo = jwtService.parseToken(token);
        //토큰인포가 널이 아니어야 하고.
        if (tokenInfo != null
                //!tokenInfo.getId().toString().isEmpty() - id가 비지 않아야한다는 것.
                && !tokenInfo.getId().toString().isEmpty()
                //내가 이미 검증을 끝낸 유저냐 아니냐. 등록이 되지 않았어야 함.
                && SecurityContextHolder.getContext().getAuthentication() == null
        ){
            //여기서 등록해서 편하게 사용할 수 있도록 하는것.
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            tokenInfo, // 이거 가져다 쓰라고..?
                            null,
                            tokenInfo.getAuthorities() //권한 넣어줌
                    );
            SecurityContextHolder.getContext().setAuthentication(authToken); // 여기 등록하려고 authToken를 만든것.
        }
        filterChain.doFilter(request, response);
    }
}
