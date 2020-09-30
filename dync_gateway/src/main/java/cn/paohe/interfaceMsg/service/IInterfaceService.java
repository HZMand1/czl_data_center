package cn.paohe.interfaceMsg.service;

import cn.paohe.vo.InterfaceInfoVo;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/29 16:43
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IInterfaceService {
    /**
     * TODO 获取接口详情
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/9/29 16:44
     * @throws:
     */
    public InterfaceInfoVo getInterfaceVoByKey(String key);
}
