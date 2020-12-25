package cn.paohe.components.filter;

import cn.paohe.emus.DataCenterCollections;
import cn.paohe.framework.utils.base.DateUtil;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.rest.AjaxResult;
import cn.paohe.interfaceMsg.service.IAppSourceDataInterService;
import cn.paohe.interfaceMsg.service.IApplicationService;
import cn.paohe.interfaceMsg.service.IDataSourceService;
import cn.paohe.interfaceMsg.service.IInterfaceService;
import cn.paohe.vo.InterfaceInfoVo;
import cn.paohe.vo.RouterConfigVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/29 15:22
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Component
public class RequestHeaterFilter implements GlobalFilter, Ordered {

    @Autowired
    private IInterfaceService iInterfaceService;
    @Autowired
    private IDataSourceService dataSourceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取对应的接口信息
        String secretKey = exchange.getRequest().getHeaders().getFirst(DataCenterCollections.SECRET_KEY);
        if (StringUtil.isBlank(secretKey)) {
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "secret Key can't empty."));
        }
        // 获取接口信息
        InterfaceInfoVo interfaceInfoVo = iInterfaceService.getInterfaceVoByKey(secretKey);
        if (ObjectUtils.isNullObj(interfaceInfoVo)) {
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "can't get interface info by secret key."));
        }
        //校验当前密钥的接口信息是否一致
        String currentUrl = exchange.getRequest().getURI().getPath();
        String path = interfaceInfoVo.getUrl();
        if(path.contains("?")){
            path = path.substring(0,path.indexOf("?"));
        }
        if(!StringUtil.equals(currentUrl,path)){
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "The secretKey key does not belong to the current interface."));
        }

        // 获取数据源信息
        String routerKey = exchange.getRequest().getHeaders().getFirst(DataCenterCollections.ROUTER_KEY);
        JSONObject routerParam = new JSONObject();
        routerParam.put("routerKey",routerKey);
        JSONObject dataSourceInfo = dataSourceService.queryDataSourceByKey(routerParam);
        if(ObjectUtils.isNullObj(dataSourceInfo)){
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "Can't get app info by router key : " + routerKey));
        }
        JSONObject routerJson = JSON.parseObject(JSON.toJSONString(exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR)));
        if(ObjectUtils.isNullObj(routerJson)){
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "Can't get router info."));
        }
        String targetRouter = routerJson.getString("uri");
        String routerUrl = dataSourceInfo.getString("routerPath");
        if(!targetRouter.contains(routerUrl)){
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "router address can't match"));
        }

        // 校验接口是否被屏蔽了
        if (StringUtil.equals(0, interfaceInfoVo.getAliveFlag())) {
            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "current interface enable now."));
        }
        // 校验接口 是否超出了访问时间
        if (!ObjectUtils.isNullObj(interfaceInfoVo.getStartTime()) && !ObjectUtils.isNullObj(interfaceInfoVo.getEndTime())) {
            // 当前时间
            if (DateUtil.compareDateStr(new Date(),interfaceInfoVo.getStartTime()) > 0 || DateUtil.compareDateStr(new Date(),interfaceInfoVo.getEndTime()) < 0) {
                return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "current interface out of connection time range."));
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 4;
    }

}
