package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.InterfaceTypeInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceTypeInfoVo;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:17
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IInterfaceTypeService {

    /**
     * TODO 新增接口类型
     *
     * @Param: interfaceTypeInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:29
     * @throws:
     */
    public int insertInterfaceType(InterfaceTypeInfo interfaceTypeInfo);

    /**
     * TODO 根据ID修改接口类型信息
     *
     * @Param: interfaceTypeInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int updateInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo);

    /**
     * TODO 逻辑删除接口类型
     *
     * @Param: interfaceTypeInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int enableInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo);

    /**
     * TODO 删除接口类型
     *
     * @Param: interfaceTypeInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int deleteInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo);

    /**
     * TODO 根据接口类型ID获取信息
     *
     * @Param: interfaceTypeInfo
     * @return: interfaceTypeInfo
     * @author: 黄芝民
     * @Date: 2020/9/16 14:37
     * @throws:
     */
    public InterfaceTypeInfo queryInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo);

    /**
     * TODO 查询全部接口类型源信息
     *
     * @Param: interfaceTypeInfo
     * @return: list
     * @author: 黄芝民
     * @Date: 2020/9/16 14:32
     * @throws:
     */
    public List<InterfaceTypeInfoVo> queryInterfaceTypeList(InterfaceTypeInfo interfaceTypeInfo);

    /**
     * TODO 分页查询接口类型信息
     *
     * @Param: interfaceTypeInfo
     * @return: pageAjax
     * @author: 黄芝民
     * @Date: 2020/9/16 14:33
     * @throws:
     */
    public PageAjax<InterfaceTypeInfoVo> queryPageInterfaceTypes(InterfaceTypeInfo interfaceTypeInfo);
}
