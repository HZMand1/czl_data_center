package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
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
public interface IInterfaceService {

    /**
     * TODO 新增接口信息
     *
     * @Param: interfaceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:29
     * @throws:
     */
    public int insertInterface(InterfaceInfo interfaceInfo);

    /**
     * TODO 根据ID修改接口信息
     *
     * @Param: interfaceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int updateInterfaceById(InterfaceInfo interfaceInfo);

    /**
     * TODO 根据条件修改接口信息
     *
     * @Param: interfaceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     * @param param
     * @param condition
     */
    public int updateInterfaceByCondition(InterfaceInfo param,InterfaceInfo condition);

    /**
     * TODO 逻辑删除接口
     *
     * @Param: interfaceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int enableInterfaceById(InterfaceInfo interfaceInfo);

    /**
     * TODO 删除接口
     *
     * @Param: interfaceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int deleteInterfaceById(InterfaceInfo interfaceInfo);

    /**
     * TODO 条件删除接口
     *
     * @Param: interfaceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int deleteInterfaceByCondition(InterfaceInfo interfaceInfo);

    /**
     * TODO 根据接口ID获取信息
     *
     * @Param: interfaceInfo
     * @return: dataSourceInfo
     * @author: 黄芝民
     * @Date: 2020/9/16 14:37
     * @throws:
     * @param interfaceInfoVo
     */
    public InterfaceInfoVo queryInterfaceVoById(InterfaceInfoVo interfaceInfoVo);

    /**
     * TODO 查询全部接口信息
     *
     * @Param: interfaceInfo
     * @return: list
     * @author: 黄芝民
     * @Date: 2020/9/16 14:32
     * @throws:
     */
    public List<InterfaceInfoVo> queryInterfaceVoList(InterfaceInfoVo interfaceInfoVo);

    /**
     * TODO 分页查询接口信息
     *
     * @Param: interfaceInfo
     * @return: pageAjax
     * @author: 黄芝民
     * @Date: 2020/9/16 14:33
     * @throws:
     */
    public PageAjax<InterfaceInfoVo> queryPageInterfaceVoList(InterfaceInfoVo interfaceInfoVo);

    /**
     * TODO 分页查询接口信息
     *
     * @Param: interfaceInfo
     * @return: pageAjax
     * @author: 黄芝民
     * @Date: 2020/9/16 14:33
     * @throws:
     */
    public PageAjax<InterfaceInfoVo> queryDeveloperPage(InterfaceInfoVo interfaceInfoVo);
}
