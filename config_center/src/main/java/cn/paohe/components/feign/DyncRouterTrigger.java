package cn.paohe.components.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "dync-gateway")
public interface DyncRouterTrigger {

    @GetMapping("/dync/getRouterReflush")
    void getRouterReflush();
}
