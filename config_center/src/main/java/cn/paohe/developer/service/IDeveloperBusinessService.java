package cn.paohe.developer.service;

import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.vo.framework.PageAjax;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/23 11:09
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDeveloperBusinessService {

    /**
     * TODO 开发者查询接口信息
     *
     * @Param: interfaceLabelInfoVo
     * @return: interfaceLabelInfoVo
     * @author: 黄芝民
     * @Date: 2020/9/23 11:12
     * @throws:
     */
    public PageAjax<InterfaceInfoVo> queryDeveloperInterPage(InterfaceInfoVo interfaceInfoVo);
}
