package cn.paohe.interfaceMsg.feign;

import cn.paohe.framework.utils.rest.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 16:56
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@FeignClient(value = "config-center")
public interface IDataSourceConnFeign {

    /**
     * TODO 获取sql的结果集
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/12 15:14
     * @throws:
     */
    @PostMapping("czl/rest/data/center/data/source/connect/sqlQuery")
    public AjaxResult sqlQuery(JSONObject jsonObject);
}
