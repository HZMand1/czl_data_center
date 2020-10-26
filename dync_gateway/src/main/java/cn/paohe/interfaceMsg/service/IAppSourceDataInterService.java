package cn.paohe.interfaceMsg.service;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/26 12:28
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IAppSourceDataInterService {

    /**
     * TODO 通过接口密钥获取关联关系
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/26 12:28
     * @throws:
     */
    public JSONObject getAppDataSourceBySecretKey(JSONObject jsonObject);
}
