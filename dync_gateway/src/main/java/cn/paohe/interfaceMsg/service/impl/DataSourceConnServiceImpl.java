package cn.paohe.interfaceMsg.service.impl;

import cn.paohe.framework.utils.rest.AjaxResult;
import cn.paohe.interfaceMsg.feign.IDataSourceConnFeign;
import cn.paohe.interfaceMsg.service.IDataSourceConnService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 16:59
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DataSourceConnServiceImpl implements IDataSourceConnService {

    @Resource
    private IDataSourceConnFeign dataSourceConnFeign;

    @Override
    public AjaxResult sqlQuery(JSONObject jsonObject) {
        return dataSourceConnFeign.sqlQuery(jsonObject);
    }
}
