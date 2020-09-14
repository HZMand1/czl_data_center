package cn.paohe.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.paohe.components.listener.DyncConfigListener;

@RestController
@RequestMapping("/dync")
public class RestDyncReflush {

    @Autowired
    private DyncConfigListener dyncConfigListener;

    @GetMapping("/getRouterReflush")
    public void getRouterReflush() {
        dyncConfigListener.reflushRouterConfig();
    }
}
