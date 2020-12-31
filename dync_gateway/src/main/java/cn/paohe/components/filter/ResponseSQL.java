//package cn.paohe.components.filter;
//
//import cn.paohe.emus.DataCenterCollections;
//import cn.paohe.framework.utils.base.ObjectUtils;
//import cn.paohe.framework.utils.base.StringUtil;
//import cn.paohe.framework.utils.rest.AjaxResult;
//import cn.paohe.interfaceMsg.service.IDataSourceConnService;
//import cn.paohe.interfaceMsg.service.IInterfaceService;
//import cn.paohe.vo.InterfaceInfoVo;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * TODO
// *
// * @Author 黄芝民
// * @Date 2020/12/30 16:54
// * @Version V1.0
// * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
// **/
//public class ResponseSQL implements GlobalFilter, Ordered {
//
//    @Autowired
//    private IInterfaceService iInterfaceService;
//    @Autowired
//    private IDataSourceConnService dataSourceConnService;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 获取对应的接口信息
//        String secretKey = exchange.getRequest().getHeaders().getFirst(DataCenterCollections.SECRET_KEY);
//        if (StringUtil.isBlank(secretKey)) {
//            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "secret Key can't empty."));
//        }
//        // 获取接口信息
//        InterfaceInfoVo interfaceInfoVo = iInterfaceService.getInterfaceVoByKey(secretKey);
//        if (ObjectUtils.isNullObj(interfaceInfoVo)) {
//            return FilterErrorUtil.errorInfo(exchange, new AjaxResult(DataCenterCollections.YesOrNo.NO.value, "can't get interface info by secret key."));
//        }
//
//        if(StringUtil.equals(1,interfaceInfoVo.getInterfaceType())){
//            // 获取数据源信息
//            String routerKey = exchange.getRequest().getHeaders().getFirst(DataCenterCollections.ROUTER_KEY);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("secretKey",secretKey);
//            jsonObject.put("routerKey",routerKey);
//            AjaxResult ajaxResult = dataSourceConnService.sqlQuery(jsonObject);
//            return FilterErrorUtil.errorInfo(exchange,ajaxResult);
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 7;
//    }
//}
