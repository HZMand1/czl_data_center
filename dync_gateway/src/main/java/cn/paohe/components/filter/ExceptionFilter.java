package cn.paohe.components.filter;

import cn.paohe.components.constants.CommonConstant;
import cn.paohe.emus.DataCenterCollections;
import cn.paohe.framework.utils.ESUtil;
import cn.paohe.framework.utils.RedisClient;
import cn.paohe.framework.utils.base.DateUtil;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.cryption.uuid.UUIDUtil;
import cn.paohe.framework.utils.http.IPUtil;
import cn.paohe.framework.utils.rest.AjaxResult;
import cn.paohe.interfaceMsg.service.IDataSourceConnService;
import cn.paohe.interfaceMsg.service.IDataSourceService;
import cn.paohe.interfaceMsg.service.IInterfaceService;
import cn.paohe.util.DB.CommonUtils;
import cn.paohe.util.DB.ConnectionPool;
import cn.paohe.vo.InterfaceInfoVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private IDataSourceService dataSourceService;
    @Autowired
    private IDataSourceConnService dataSourceConnService;

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
        // ip地址
        ServerHttpRequest shr = exchange.getRequest();
        String requestIp = IPUtil.getIpAdd(shr);
        interfaceInfoVo.setIp(requestIp);

        JSONObject routerParam = new JSONObject();
        routerParam.put("routerKey",routerKey);
        JSONObject dataSourceInfo = dataSourceService.queryDataSourceByKey(routerParam);
        if(!ObjectUtils.isNullObj(dataSourceInfo)){
            interfaceInfoVo.setDataSourceId(dataSourceInfo.getLong("dataSourceId"));
            interfaceInfoVo.setDataSourceName(dataSourceInfo.getString("dataSourceName"));
        }

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

        if(StringUtil.equals(1,interfaceInfoVo.getInterfaceType())){
            JSONObject queryParam = new JSONObject();
            queryParam.put("dataSourceId",interfaceInfoVo.getDataSourceId());
            JSONObject connectionInfo = dataSourceConnService.queryConnectInfo(queryParam);
            // 获取数据源信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secretKey",secretKey);
            jsonObject.put("routerKey",routerKey);

            ConnectionPool connPool = new ConnectionPool(connectionInfo.getString("connectDriver"),
                    connectionInfo.getString("connectAddress"), connectionInfo.getString("connectUser"), connectionInfo.getString("connectPassword"));
            try {
                connPool.createPool();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // 结果集
            List<Map<String,Object>> mapList = new ArrayList<>();
            try {
                Connection conn = connPool.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(interfaceInfoVo.getSqlMsg());
                ResultSet result = preparedStatement.executeQuery();
                ResultSetMetaData md = result.getMetaData();
                int columnCount = md.getColumnCount();
                while (result.next()) {
                    JSONObject vo = new JSONObject();
                    for (int i = 1; i <= columnCount; i++) {
                        vo.put(md.getColumnName(i), result.getObject(i));
                    }
                    mapList.add(CommonUtils.dataMap2Java(vo));
                }
                connPool.closeConnectionPool();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            // 获取接口信息
            AjaxResult ajaxResult = new AjaxResult(mapList);
            return FilterErrorUtil.errorInfo(exchange,ajaxResult);
        }
        return chain.filter(exchange);
    }

    private void setupRecord2Elasticsearch(InterfaceInfoVo interfaceInfoVo) {
        interfaceInfoVo.setConnectTime(new Date());
        interfaceInfoVo.setConnectTimeStr(DateUtil.getCurDate(DateUtil.yyyyMMdd_EN));
        esUtil.save(CommonConstant.CONNECTION_INTERFACE_LIST,interfaceInfoVo);
    }

    @Override
    public int getOrder() {
        return 5;
    }
}
