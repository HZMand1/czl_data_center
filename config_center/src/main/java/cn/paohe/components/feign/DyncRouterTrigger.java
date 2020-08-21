package cn.paohe.components.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dync-gateway", url = "http://127.0.0.1:12001")
public interface DyncRouterTrigger {

    @GetMapping("/dync/getRouterReflush")
    void getRouterReflush();
}
