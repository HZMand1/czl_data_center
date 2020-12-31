package cn.paohe.user.service;

import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSONObject;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 13:51
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataSourceConnService {

    /**
     * TODO 获取接口信息 ，根据sql获取数据
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/12/30 13:51
     * @throws:
     */
    AjaxResult sqlQuery(JSONObject jsonObject);
}
