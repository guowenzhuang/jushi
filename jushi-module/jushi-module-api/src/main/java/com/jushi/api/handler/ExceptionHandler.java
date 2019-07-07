package com.jushi.api.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jushi.api.exception.CheckException;
import com.jushi.api.exception.FieldNameException;
import com.jushi.api.pojo.Result;
import com.jushi.api.pojo.StatusCode;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 异常处理
 * @Order(-1) 异常等级调高
 * @author 80795
 */
@Component
@Order(-1)
public class ExceptionHandler implements WebExceptionHandler {

    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        //设置响应头 400
        //FIXME 应该设置错误响应码
        response.setStatusCode(HttpStatus.OK);
        //设置返回类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        String errorMsg = toStr(ex);
        DataBuffer wrap = response.bufferFactory().wrap(errorMsg.getBytes());
        return response.writeWith(Mono.just(wrap));
    }

    private String toStr(Throwable ex) {
        Result result = new Result();
        result.setCode(StatusCode.ERROR);
        result.setFlag(false);
        //result.setData(ex);
        //已知异常
        if (ex instanceof CheckException) {
            CheckException e = (CheckException) ex;
            result.setMessage("字段:"+e.getFieldName() +" 值:"+ e.getFieldValue() + " 描述: " + e.getDescription());
        }
        else if(ex instanceof FieldNameException){
            FieldNameException e = (FieldNameException) ex;
            result.setMessage(StrUtil.format("列名错误 应为列名:{},现在列名:{}",e.getFieldName(),e.getNowFieldName()));
        }
        //未知异常 需要打印堆栈,方便定位问题
        else {
            ex.printStackTrace();
            result.setMessage(ex.toString());
        }
        return JSON.toJSONString(result);
    }
}
