package cn.paohe.interfaceMsg.feign;

import cn.paohe.framework.utils.rest.AjaxResult;
import cn.paohe.vo.InterfaceInfoVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/29 16:41
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@FeignClient(value = "config-center")
public interface IInterfaceFeign {

    /**
     * TODO 获取接口信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/12 15:13
     * @throws:
     */
    @PostMapping("/czl/rest/data/center/interface/getInterfaceList")
    public List<InterfaceInfoVo> getInterfaceList(InterfaceInfoVo interfaceInfoVo);

    /**
     * TODO 获取应用信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/12 15:14
     * @throws:
     */
    @PostMapping("/czl/rest/data/center/app/queryAppInfoById")
    public AjaxResult queryAppById(JSONObject jsonObject);

    /**
     * TODO 获取应用信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/12 15:14
     * @throws:
     */
    @PostMapping("/czl/rest/data/center/app/queryAppInfoByKey")
    public AjaxResult queryAppInfoByKey(JSONObject jsonObject);

    /**
     * TODO 获取应用接口关联信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/12 15:14
     * @throws:
     */
    @PostMapping("/czl/rest/data/center/app/interface/getAppDataSourceBySecretKey")
    public AjaxResult getAppDataSourceBySecretKey(JSONObject jsonObject);


}
