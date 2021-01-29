package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.DataConnectInfo;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/16 12:33
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataConnectService {

    /**
     * TODO 新增数据库连接
     * @Param:   dataConnectInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:29
     * @throws:
     */
    public int insertConnect(DataConnectInfo dataConnectInfo);

    /**
     * TODO 根据ID修改数据库连接
     * @Param:   dataConnectInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:30
     * @throws:
     */
    public int updateConnectById(DataConnectInfo dataConnectInfo);

    /**
     * TODO 删除数据库连接
     * @Param:   dataConnectInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:30
     * @throws:
     */
    public int deleteConnectById(DataConnectInfo dataConnectInfo);

    /**
     * TODO 根据应用ID获取信息
     * @Param:   dataConnectInfo
     * @return:  dataConnectInfo
     * @author:  黄芝民
     * @Date:    2020/9/16 14:37
     * @throws:
     */
    public DataConnectInfo queryConnectById(DataConnectInfo dataConnectInfo);

    /**
     * TODO 根据应用ID获取信息
     * @Param:   dataConnectInfo
     * @return:  dataConnectInfo
     * @author:  黄芝民
     * @Date:    2020/9/16 14:37
     * @throws:
     */
    public DataConnectInfo queryConnectBySourceId(DataConnectInfo dataConnectInfo);

    /**
     * TODO 查询全部应用信息
     * @Param:   dataConnectInfo
     * @return:  list
     * @author:  黄芝民
     * @Date:    2020/9/16 14:32
     * @throws:
     */
    public List<DataConnectInfo> queryConnectList(DataConnectInfo dataConnectInfo);

    /**
     * TODO 分页查询应用信息
     * @Param:   dataConnectInfo
     * @return:  pageAjax
     * @author:  黄芝民
     * @Date:    2020/9/16 14:33
     * @throws:
     */
    public PageAjax<DataConnectInfo> queryConnectPage(DataConnectInfo dataConnectInfo);

    /**
     * TODO 测试数据库连接
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/12/16 13:57
     * @throws:
     */
    AjaxResult testConnect(DataConnectInfo dataConnectInfo);
}
