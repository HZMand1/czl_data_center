package cn.paohe.interfaceMsg.service;

import cn.paohe.framework.utils.rest.AjaxResult;
import com.alibaba.fastjson.JSONObject;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 16:58
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataSourceConnService {

    /**
     * TODO 获取sql的结果集
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/12/30 16:58
     * @throws:
     */
    public AjaxResult sqlQuery(JSONObject jsonObject);
}
