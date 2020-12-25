package cn.paohe.components.filter;

import cn.paohe.components.constants.CommonConstant;
import cn.paohe.emus.DataCenterCollections;
import cn.paohe.framework.utils.ESUtil;
import cn.paohe.framework.utils.RedisClient;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.cryption.uuid.UUIDUtil;
import cn.paohe.framework.utils.rest.AjaxResult;
import cn.paohe.interfaceMsg.service.IInterfaceService;
import cn.paohe.vo.InterfaceInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/24 15:16
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Component
public class ExceptionFilter implements GlobalFilter, Ordered {

    private static final String MALICIOUS_IP = "MALICIOUS_IP";
    private static final String PRE_REQUEST_PATH = "PRE_REQUEST_PATH";
    private static final String PRE_REQUEST_TIME = "PRE_REQUEST_TIME";
    private static final String MAL_REQUEST_TIMES = "MAL_REQUEST_TIMES";

    public final static Boolean ALL_REQUEST = false;

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private ESUtil esUtil;
    @Autowired
    private IInterfaceService iInterfaceService;

    /**
     * 允许的最小请求间隔
     */
    @Value("${filter.ip.min-interval-time}")
    private Long minRequestIntervalTime;

    /**
     * 允许的最大恶意请求次数
     */
    @Value("${filter.ip.max-malicious-time}")
    private Integer maxMaliciousTimes;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取路由信息信息
        String routerKey = exchange.getRequest().getHeaders().getFirst(DataCenterCollections.ROUTER_KEY);
        // 获取对应的接口信息
        String secretKey = exchange.getRequest().getHeaders().getFirst(DataCenterCollections.SECRET_KEY);

        String key = routerKey + "_"+ secretKey;

        // 上次请求地址
        String LastConnect = String.valueOf(redisClient.hGetHash(MALICIOUS_IP,key + PRE_REQUEST_PATH));
        String preRequestPath = ObjectUtils.isNullObj(LastConnect) ? "" : LastConnect;


        // 上次请求时间
        Object preRequestTimeStr = redisClient.hGetHash(MALICIOUS_IP, key + PRE_REQUEST_TIME);
        Long preRequestTime = ObjectUtils.isNullObj(preRequestTimeStr) ? 0 : Long.valueOf(preRequestTimeStr.toString());

        // 获取接口信息
        InterfaceInfoVo interfaceInfoVo = iInterfaceService.getInterfaceVoByKey(secretKey);
        if (ObjectUtils.isNullObj(interfaceInfoVo)) {
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "can't get interface info by secret key."));
        }
        interfaceInfoVo.setRouterKey(routerKey);
        interfaceInfoVo.setId(UUIDUtil.getUUID());

        // 当前时间
        Long nowtime = System.currentTimeMillis();
        // 过滤频繁操作
        if (StringUtil.isNotEmpty(routerKey) && StringUtil.isNotEmpty(secretKey) && !ObjectUtils.isNullObj(preRequestTime)) {
            boolean flag = (key.equals(preRequestPath) || ALL_REQUEST) && nowtime - preRequestTime < minRequestIntervalTime;
            if (flag) {
                // 非法请求次数
                String malRequestTimesStr = String.valueOf(redisClient.hGetHash(MALICIOUS_IP, key + MAL_REQUEST_TIMES));
                Integer malRequestTimes;
                if (preRequestTime == 0) {
                    malRequestTimes = 1;
                } else {
                    malRequestTimes = Integer.valueOf(malRequestTimesStr) + 1;
                }
                redisClient.hSetHash(MALICIOUS_IP, key + MAL_REQUEST_TIMES, String.valueOf(malRequestTimes));
                if (malRequestTimes > maxMaliciousTimes) {
                    interfaceInfoVo.setConnectStatus(DataCenterCollections.InterfaceConnectEmus.ATTACK_ON_PURPOSE.value);
                    setupRecord2Elasticsearch(interfaceInfoVo);
                }
            } else {
                redisClient.hSetHash(MALICIOUS_IP, key + MAL_REQUEST_TIMES, "0");
            }
        }
        interfaceInfoVo.setConnectStatus(DataCenterCollections.InterfaceConnectEmus.SUCCESS.value);
        setupRecord2Elasticsearch(interfaceInfoVo);
        redisClient.hSetHash(MALICIOUS_IP, key + PRE_REQUEST_PATH, key);
        redisClient.hSetHash(MALICIOUS_IP, key + PRE_REQUEST_TIME, String.valueOf(nowtime));
        return chain.filter(exchange);
    }

    private void setupRecord2Elasticsearch(InterfaceInfoVo interfaceInfoVo) {
        esUtil.save(CommonConstant.CONNECTION_INTERFACE_LIST,interfaceInfoVo);
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
