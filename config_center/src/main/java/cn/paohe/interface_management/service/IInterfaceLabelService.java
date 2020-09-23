package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceLabelInfoVo;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:16
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IInterfaceLabelService {

    /**
     * TODO 新增接口标签
     *
     * @Param: interfaceLabelInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:29
     * @throws:
     */
    public int insertInterfaceLabel(InterfaceLabelInfo interfaceLabelInfo);

    /**
     * TODO 根据ID修改接口标签信息
     *
     * @Param: interfaceLabelInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int updateInterfaceLabelById(InterfaceLabelInfo interfaceLabelInfo);

    /**
     * TODO 逻辑删除接口标签
     *
     * @Param: interfaceLabelInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int enableInterfaceLabelById(InterfaceLabelInfo interfaceLabelInfo);

    /**
     * TODO 删除接口标签
     *
     * @Param: interfaceLabelInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int deleteInterfaceLabelById(InterfaceLabelInfo interfaceLabelInfo);

    /**
     * TODO 根据接口标签ID获取信息
     *
     * @Param: interfaceLabelInfo
     * @return: interfaceLabelInfo
     * @author: 黄芝民
     * @Date: 2020/9/16 14:37
     * @throws:
     */
    public InterfaceLabelInfoVo queryInterfaceLabelById(InterfaceLabelInfoVo interfaceLabelInfoVo);

    /**
     * TODO 查询全部接口标签源信息
     *
     * @Param: interfaceLabelInfo
     * @return: list
     * @author: 黄芝民
     * @Date: 2020/9/16 14:32
     * @throws:
     */
    public List<InterfaceLabelInfoVo> queryInterfaceLabelList(InterfaceLabelInfoVo interfaceLabelInfoVo);

    /**
     * TODO 分页查询接口标签信息
     *
     * @Param: interfaceLabelInfo
     * @return: pageAjax
     * @author: 黄芝民
     * @Date: 2020/9/16 14:33
     * @throws:
     */
    public PageAjax<InterfaceLabelInfoVo> queryPageInterfaceLabels(InterfaceLabelInfoVo interfaceLabelInfoVo);
}
