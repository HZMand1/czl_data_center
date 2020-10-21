package cn.paohe.interface_management.dao;

import cn.paohe.base.business.dao.MyMapper;
import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:06
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IInterfaceMapper extends MyMapper<InterfaceInfo> {

    /**
     * TODO 根据ID查询接口信息
     * @Param:   interfaceInfoVo
     * @return:  InterfaceInfoVo
     * @author:  黄芝民
     * @Date:    2020/9/17 11:55
     * @throws:
     */
    public InterfaceInfoVo queryInterfaceById(InterfaceInfoVo interfaceInfoVo);

    /**
     * TODO 查询全部接口信息
     * @Param:   interfaceInfoVo
     * @return:  List
     * @author:  黄芝民
     * @Date:    2020/9/17 11:56
     * @throws:
     */
    public List<InterfaceInfoVo> queryInterfaceList(InterfaceInfoVo interfaceInfoVo);

    /**
     * TODO 根据ID跟新接口的时间
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/13 10:45
     * @throws:
     */
    public int updateInterfaceInfoById(InterfaceInfo param);

    /**
     * TODO 应用管理页面-通过数据源ID获取未关联应用的接口数据
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/21 9:15
     * @throws:
     */
    List<InterfaceInfoVo> queryPageAppInterfaceBySourceId(InterfaceInfoVo interfaceInfoVo);
}
