package cn.paohe.components.router;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import cn.paohe.components.constants.CommonConstant;
import cn.paohe.framework.utils.ref.ReflectUtil;
import cn.paohe.vo.RouterConfigVO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @Copyright (c) by paohe information technology Co., Ltd.
 * @All right reserved.
 * @Project: dync_gateway
 * @File: RedisRouteDefinitionRepository.java
 * @Description: redis动态路由定义储存器
 *
 * @Author: yuanzhenhui
 * @Date: 2020/07/03
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final Logger logger = LoggerFactory.getLogger(RedisRouteDefinitionRepository.class);

    /**
     * 在redis中提取路由配置
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        logger.debug(" --------- request has been coming --------- ");
        List<RouteDefinition> gatewayRouteEntityList = Lists.newArrayList();
        if (null != CommonConstant.configMap) {
            CommonConstant.configMap.forEach((k, v) -> {
                RouterConfigVO result = ReflectUtil.map2Bean(v, RouterConfigVO.class);
                // 先判断当前网关是否禁用
                if (result.getStatus().equals("1")) {
                    logger.debug(" --------- rourter can be use name : " + result.getInterfaceName() + " --------- ");
                    RouteDefinition rd = new RouteDefinition();
                    // 定义动态路由的id名称
                    rd.setId(result.getInterfaceName());
                    // 定义映射的路径
                    URI uri = UriComponentsBuilder.fromHttpUrl(result.getMappingPath()).build().toUri();
                    rd.setUri(uri);

                    // 设置断言
                    List<PredicateDefinition> pdList = new ArrayList<>();
                    // 设置Path断言，拦截访问路径为设定值的请求
                    PredicateDefinition predicatePath = new PredicateDefinition();
                    predicatePath.setName("Path");
                    Map<String, String> predicateParamsPath = new HashMap<>(1);
                    predicateParamsPath.put("pattern", result.getContextName());
                    predicatePath.setArgs(predicateParamsPath);
                    pdList.add(predicatePath);
                    rd.setPredicates(pdList);
                    // 将路由发布到网关
                    gatewayRouteEntityList.add(rd);
                }
            });
        }
        return Flux.fromIterable(gatewayRouteEntityList);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
