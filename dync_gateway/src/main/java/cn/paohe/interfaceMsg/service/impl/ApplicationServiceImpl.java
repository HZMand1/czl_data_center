package cn.paohe.interfaceMsg.service.impl;

import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.rest.AjaxResult;
import cn.paohe.interfaceMsg.feign.IInterfaceFeign;
import cn.paohe.interfaceMsg.service.IApplicationService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/12 15:31
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Resource
    private IInterfaceFeign iInterfaceFeign;

    @Override
    public JSONObject queryAppById(JSONObject jsonObject) {
        AjaxResult ajaxResult = iInterfaceFeign.queryAppById(jsonObject);
        if(StringUtil.equals(1,ajaxResult.getRetcode())){
            JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(ajaxResult.getData()));
            return jsonObject1;
        }
        return null;
    }
}
