package cn.paohe.interface_management.dao;

import cn.paohe.base.business.dao.MyMapper;
import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceLabelInfoVo;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:07
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IInterfaceLabelMapper extends MyMapper<InterfaceLabelInfo> {

    /**
     * TODO ID获取标签信息
     *
     * @Param: interfaceLabelInfoVo
     * @return: InterfaceLabelInfoVo
     * @author: 黄芝民
     * @Date: 2020/9/23 10:23
     * @throws:
     */
    public InterfaceLabelInfoVo queryLabelInfoById(InterfaceLabelInfoVo interfaceLabelInfoVo);

    /**
     * TODO 条件获取标签信息
     *
     * @Param: interfaceLabelInfoVo
     * @return: interfaceLabelInfoVo
     * @author: 黄芝民
     * @Date: 2020/9/23 10:23
     * @throws:
     */
    public List<InterfaceLabelInfoVo> queryLabelInfoList(InterfaceLabelInfoVo interfaceLabelInfoVo);
}
