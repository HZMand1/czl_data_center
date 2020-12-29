package cn.paohe.interfaceMsg.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/28 17:35
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@FeignClient(value = "config-center")
public interface IInterfaceLabelFeign {
    /**
     * TODO 获取接口信息
     *
     * @Param: null
     * @return: * @return: null
     * @author: 黄芝民
     * @Date: 2020/10/12 15:13
     * @throws:
     */
    @PostMapping("/czl/rest/data/center/interface/label/queryLabelById")
    public JSONObject queryLabelById(JSONObject jsonObject);
}
