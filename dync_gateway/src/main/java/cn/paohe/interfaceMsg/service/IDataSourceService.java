package cn.paohe.interfaceMsg.service;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/29 13:55
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataSourceService {

    /**
     * TODO 通过路由密钥获取数据源信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/29 13:56
     * @throws:
     */
    public JSONObject queryDataSourceByKey(JSONObject jsonObject);
}
