package com.example.gateway.filter;

import com.example.gateway.dto.CustomDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class CacheFilter extends AbstractGatewayFilterFactory<CustomDto> {
    private final Map<String, CacheData> map = new HashMap<>();

    public CacheFilter() {
        super(CustomDto.class);
    }

    @Override
    public GatewayFilter apply(CustomDto config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            request.getMethod();
            request.getPath();
            map.put(request.getPath().toString()
                    , map.getOrDefault(request.getPath().toString(),
                            new CacheData(request.getPath().toString(),
                                    request.getMethod())));

            ServerHttpResponse response = exchange.getResponse();
            HttpStatusCode statusCode = response.getStatusCode();
            System.out.println(statusCode);
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        System.out.println(map.get(request.getPath().toString()));
                        System.out.println((response.setComplete()));
                    })
            );
        });
    }



    class CacheData {
        private Integer count;
        private final Date date;
        private final String api;
        private final HttpMethod method;

        public CacheData(String api, HttpMethod method) {
            this.count = 1; //생서이 될 때 한번 불렸으니 1로 시작.
            this.date = new Date();
            this.api = api;
            this.method = method;
        }

        public void plus() {
            this.count += 1;
        }

        @Override
        public String toString() {
            return "count : " + count;
        }
    }
}

