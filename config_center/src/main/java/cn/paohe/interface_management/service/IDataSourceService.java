package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:14
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDataSourceService {

    /**
     * TODO 新增数据源
     *
     * @Param: dataSourceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:29
     * @throws:
     */
    public int insertDataSource(DataSourceInfo dataSourceInfo);

    /**
     * TODO 根据ID修改数据源信息
     *
     * @Param: dataSourceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int updateDataSourceById(DataSourceInfo dataSourceInfo);

    /**
     * TODO 逻辑删除数据源
     *
     * @Param: dataSourceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int enableDataSourceById(DataSourceInfo dataSourceInfo);

    /**
     * TODO 删除数据源
     *
     * @Param: dataSourceInfo
     * @return: int
     * @author: 黄芝民
     * @Date: 2020/9/16 14:30
     * @throws:
     */
    public int deleteDataSourceById(DataSourceInfo dataSourceInfo);

    /**
     * TODO 根据数据源ID获取信息
     *
     * @Param: dataSourceInfo
     * @return: dataSourceInfo
     * @author: 黄芝民
     * @Date: 2020/9/16 14:37
     * @throws:
     */
    public DataSourceInfo queryDataSourceById(DataSourceInfo dataSourceInfo);

    /**
     * TODO 查询全部应用数据源信息
     *
     * @Param: dataSourceInfo
     * @return: list
     * @author: 黄芝民
     * @Date: 2020/9/16 14:32
     * @throws:
     */
    public List<DataSourceInfo> queryDataSourceList(DataSourceInfo dataSourceInfo);

    /**
     * TODO 分页查询数据源信息
     *
     * @Param: dataSourceInfo
     * @return: pageAjax
     * @author: 黄芝民
     * @Date: 2020/9/16 14:33
     * @throws:
     */
    public PageAjax<DataSourceInfo> queryPageDataSourceList(DataSourceInfo dataSourceInfo);
}
