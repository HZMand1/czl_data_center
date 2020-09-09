package cn.paohe.components.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import cn.paohe.components.router.RedisRouteDefinitionRepository;

/**
 * 
 * @Copyright (c) by paohe information technology Co., Ltd.
 * @All right reserved.
 * @Project: dync_gateway
 * @File: DyncConfigListener.java
 * @Description: 动态数据配置监听器
 *
 * @Author: yuanzhenhui
 * @Date: 2020/07/06
 */
@Component
public class DyncConfigListener implements ApplicationEventPublisherAware {

    public static final Logger logger = LoggerFactory.getLogger(DyncConfigListener.class);

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;

    }

    /**
     * 提供对外触发刷新路由接口
     */
    public void reflushRouterConfig() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

}
