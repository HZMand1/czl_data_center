package cn.paohe.interfaceMsg.service.impl;

import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.interfaceMsg.feign.IInterfaceFeign;
import cn.paohe.interfaceMsg.service.IInterfaceService;
import cn.paohe.vo.InterfaceInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/29 16:45
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service("filterInterfaceServiceImpl")
public class InterfaceServiceImpl implements IInterfaceService {

    @Resource
    private IInterfaceFeign iInterfaceFeign;

    @Override
    public InterfaceInfoVo getInterfaceVoByKey(String key) {
        InterfaceInfoVo interfaceInfoVo = new InterfaceInfoVo();
        interfaceInfoVo.setSecretKey(key);
        List<InterfaceInfoVo> interfaceInfoVoList = iInterfaceFeign.getInterfaceList(interfaceInfoVo);
        if(ObjectUtils.isNullObj(interfaceInfoVoList) || interfaceInfoVoList.isEmpty()){
            return null;
        }
        InterfaceInfoVo result = interfaceInfoVoList.get(0);
        return result;
    }
}
