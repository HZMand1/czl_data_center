package cn.paohe.interface_management.dao;

import cn.paohe.base.business.dao.MyMapper;
import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/20 13:56
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IAppSourceInterMapper extends MyMapper<AppSourceInterInfo> {

    /**
     * TODO 查询统计的应用接口信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/20 14:18
     * @throws:
     */
    List<AppSourceInterInfoVo> queryCountAppSourceInterList(AppSourceInterInfoVo appSourceInterInfoVo);

    /**
     * TODO 查询应用接口信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/10/20 14:18
     * @throws:
     */
    List<AppSourceInterInfoVo> queryAppSourceInterList(AppSourceInterInfoVo appSourceInterInfoVo);
}
