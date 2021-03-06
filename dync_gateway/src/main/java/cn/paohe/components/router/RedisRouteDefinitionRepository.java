package cn.paohe.components.router;

import cn.paohe.components.constants.CommonConstant;
import cn.paohe.framework.utils.RedisClient;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.ref.ReflectUtil;
import cn.paohe.vo.RouterConfigVO;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Copyright (c) by paohe information technology Co., Ltd.
 * @All right reserved.
 * @Project: dync_gateway
 * @File: RedisRouteDefinitionRepository.java
 * @Description: redis动态路由定义储存器
 * @Author: yuanzhenhui
 * @Date: 2020/07/03
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final Logger logger = LoggerFactory.getLogger(RedisRouteDefinitionRepository.class);

    private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^(?i)(http|https):(//(([^@\\[/?#]*)@)?(\\[[\\p{XDigit}\\:\\.]*[%\\p{Alnum}]*\\]|[^\\[/?#:]*)(:(\\d*(?:\\{[^/]+?\\})?))?)?([^?#]*)(\\?(.*))?");

    @Autowired
    private RedisClient redisClient;

    /**
     * 在redis中提取路由配置
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {

        Map<String, HashMap<String, Object>> map = redisClient.hgetAllHash(CommonConstant.ROUTER_KEY);
        Map<String, HashMap<String, Object>> configMap = new HashMap<>();
        if (!ObjectUtils.isNullObj(map)) {
            map.forEach((k, v) -> {
                HashMap<String, Object> valMap = new HashMap<>();
                for (Map.Entry<String, Object> entry : v.entrySet()) {
                    valMap.put(StringUtil.underline2Camel(entry.getKey(), true), entry.getValue());
                }
                configMap.put(k, valMap);
            });
        }

        logger.debug(" --------- request has been coming --------- ");
        List<RouteDefinition> gatewayRouteEntityList = Lists.newArrayList();
        if (null != configMap) {
            configMap.forEach((k, v) -> {
                RouterConfigVO result = ReflectUtil.map2Bean(v, RouterConfigVO.class);
                // 先判断当前网关是否禁用
                if (result.getStatus().equals("1")) {
                    logger.debug(" --------- router can be use name : " + result.getInterfaceName() + " --------- ");
                    RouteDefinition rd = new RouteDefinition();
                    // 定义动态路由的id名称
                    rd.setId(result.getInterfaceName());

                    // 校验域名是否是可用的URL 连接
                    Matcher matcher = HTTP_URL_PATTERN.matcher(result.getMappingPath());
                    if (matcher.matches()) {
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

                        List<FilterDefinition> filters = new ArrayList<>();
                        FilterDefinition filterDefinition = new FilterDefinition();
                        //设置过滤
                        filterDefinition.setName("StripPrefix");
                        filterDefinition.addArg("parts", "1");
                        filters.add(filterDefinition);
                        rd.setFilters(filters);

                        // 将路由发布到网关
                        gatewayRouteEntityList.add(rd);
                    }else {
                        // 打印出不符合规范的路由地址
                        logger.debug(" --------- 不规范的路由地址 : " + result.getMappingPath() + " --------- ");
                    }
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
