package com.example.gateway.filter;

import com.example.gateway.dto.CustomDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomGlobalFilter extends AbstractGatewayFilterFactory<CustomDto> { // 이 dto를 가지고 filter를 만들거다.
    @Value("${jwt.secret}")
    private String secret;
    public CustomGlobalFilter() {
        //얘가 생성될 때 이 클래스가 생성됨. 이 클래스(CustomDto.class) 넣어서 만들어줘.
        super(CustomDto.class);
    }

    @Override
    public GatewayFilter apply(CustomDto config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            //요청 들어올 때마다 secret을 찍어보자.
            System.out.println("=================" + secret);

            if (config.getLogging()) {
                System.out.println("req: " + request.getId() +
                        ", " + request.getMethod() + " : " +
                        request.getPath() +
                        ", " + request.getRemoteAddress()
                );
            }

            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> { //Mono는 getId처럼 하나, Float 는 다수 이다.
                        if (config.getLogging()) {
                            System.out.println("res : " + request.getId() +
                                    ", " + response.getStatusCode()
                            );
                        }
                    })
            );
        };
    }


}
