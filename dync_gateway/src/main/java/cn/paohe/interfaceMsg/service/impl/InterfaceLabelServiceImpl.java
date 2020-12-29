package cn.paohe.interfaceMsg.service.impl;

import cn.paohe.interfaceMsg.feign.IInterfaceLabelFeign;
import cn.paohe.interfaceMsg.service.IInterfaceLabelService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/28 17:37
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class InterfaceLabelServiceImpl implements IInterfaceLabelService {

    @Resource
    private IInterfaceLabelFeign iInterfaceLabelFeign;

    @Override
    public JSONObject queryLabelById(JSONObject jsonObject) {
        return iInterfaceLabelFeign.queryLabelById(jsonObject);
    }
}
