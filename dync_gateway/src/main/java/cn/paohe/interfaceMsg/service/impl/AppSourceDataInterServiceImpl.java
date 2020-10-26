package cn.paohe.interfaceMsg.service.impl;

import cn.paohe.interfaceMsg.feign.IInterfaceFeign;
import cn.paohe.interfaceMsg.service.IAppSourceDataInterService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/26 12:29
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class AppSourceDataInterServiceImpl implements IAppSourceDataInterService {

    @Resource
    private IInterfaceFeign interfaceFeign;

    @Override
    public JSONObject getAppDataSourceBySecretKey(JSONObject jsonObject) {
        JSONObject jsonObject1 = interfaceFeign.getAppDataSourceBySecretKey(jsonObject);
        return jsonObject1;
    }
}
