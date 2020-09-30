package cn.paohe.components.filter;

import cn.paohe.framework.utils.rest.AjaxResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/30 9:27
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class FilterErrorUtil {

    /**
     * TODO 封装异常信息返回
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/9/30 9:51
     * @throws:
     */
    public static Mono<Void> errorInfo(ServerWebExchange exchange, AjaxResult ajaxResult) {
        return Mono.defer(() -> {
            byte[] bytes;
            try {
                bytes = new ObjectMapper().writeValueAsBytes(ajaxResult);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("信息序列化异常");
            } catch (Exception e) {
                throw new RuntimeException("异常" + e.getMessage());
            }
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Flux.just(buffer));
        });
    }

}
