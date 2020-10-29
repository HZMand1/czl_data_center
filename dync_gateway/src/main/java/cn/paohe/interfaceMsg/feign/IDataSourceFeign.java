package cn.paohe.interfaceMsg.feign;

import cn.paohe.framework.utils.rest.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/29 13:53
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@FeignClient(value = "config-center")
public interface IDataSourceFeign {

    /**
     * TODO 获取应用接口关联信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/12 15:14
     * @throws:
     */
    @PostMapping("/czl/rest/data/center/source/queryDataSourceByKey")
    public AjaxResult queryDataSourceByKey(JSONObject jsonObject);
}
