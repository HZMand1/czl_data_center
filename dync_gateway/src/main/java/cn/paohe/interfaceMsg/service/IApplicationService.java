package cn.paohe.interfaceMsg.service;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/12 15:29
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IApplicationService {

    /**
     * TODO 获取接口详情
     *
     * @Param: null
     * @return: * @return: null
     * @author: 黄芝民
     * @Date: 2020/9/29 16:44
     * @throws:
     */
    public JSONObject queryAppById(JSONObject jsonObject);

    /**
     * TODO 根据key获取接口详情
     *
     * @Param: null
     * @return: * @return: null
     * @author: 黄芝民
     * @Date: 2020/9/29 16:44
     * @throws:
     */
    public JSONObject queryAppInfoByKey(JSONObject jsonObject);
}
