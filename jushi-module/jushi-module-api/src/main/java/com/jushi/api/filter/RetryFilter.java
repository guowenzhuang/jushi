package com.jushi.api.filter;

import com.alibaba.fastjson.JSON;
import com.jushi.api.pojo.Result;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RetryFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().toString();
        if (url.indexOf("SSE") >= 0) {
            HttpHeaders headers = request.getHeaders();
            List<String> lastEventIds = headers.get("Last-Event-ID");
            if (lastEventIds != null && lastEventIds.size() > 0) {
                response.setStatusCode(HttpStatus.OK);
                //设置返回类型
                response.getHeaders().setContentType(MediaType.TEXT_EVENT_STREAM);
                Result result = Result.error("第二次请求");
                String resultStr = "data:" + JSON.toJSONString(result) + "\n\n";
                DataBuffer wrap = response.bufferFactory().wrap(resultStr.getBytes());
                return response.writeWith(Mono.just(wrap));
            }
        }
        return chain.filter(exchange);
    }
}
