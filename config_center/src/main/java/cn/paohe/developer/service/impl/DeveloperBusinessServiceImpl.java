package cn.paohe.developer.service.impl;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.developer.service.IDeveloperBusinessService;
import cn.paohe.entity.model.InterfaceMag.DataConnectInfo;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.interface_management.service.IDataConnectService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.user.service.IDataSourceConnService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.DB.CommonUtils;
import cn.paohe.utils.DB.ConnectionPool;
import cn.paohe.utils.Md5;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/23 11:24
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DeveloperBusinessServiceImpl implements IDeveloperBusinessService {

    @ApiModelProperty("路由前缀地址")
    @Value("${gateway.context}")
    private String gatewayContext;

    @Autowired
    private IAppSourceInterService appSourceInterService;

    @Autowired
    private IInterfaceService iInterfaceService;

    @Autowired
    private IDataConnectService dataConnectService;

    @Override
    public PageAjax<AppSourceInterInfoVo> queryDeveloperDateSourcePage(AppSourceInterInfoVo appSourceInterInfoVo) {
        // 超级管理员可以查看全部接口信息
        if(StringUtil.equals(1,UserUtil.getUserEntity().getUserId())){

        }else {
            // 获取开发者所属的应用ID
            Long applicationId = UserUtil.getUserEntity().getApplicationId();
            if (ObjectUtils.isNullObj(applicationId)) {
                return new PageAjax<>();
            }
            appSourceInterInfoVo.setApplicationId(applicationId);
            appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getParentUserId());
        }
        appSourceInterInfoVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        PageAjax<AppSourceInterInfoVo> pageAjax = appSourceInterService.queryCountPageAppInterface(appSourceInterInfoVo);
        return pageAjax;
    }

    @Override
    public PageAjax<AppSourceInterInfoVo> queryDeveloperInterPage(AppSourceInterInfoVo appSourceInterInfoVo) {
        // 超级管理员可以查看全部接口信息
        if(StringUtil.equals(1,UserUtil.getUserEntity().getUserId())){

        }else {
            // 获取开发者所属的应用ID
            Long applicationId = UserUtil.getUserEntity().getApplicationId();
            if (ObjectUtils.isNullObj(applicationId)) {
                return new PageAjax<>();
            }
            appSourceInterInfoVo.setApplicationId(applicationId);
            appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getParentUserId());
        }
        appSourceInterInfoVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        PageAjax<AppSourceInterInfoVo> pageAjax = appSourceInterService.queryPageAppInterface(appSourceInterInfoVo);
        List<AppSourceInterInfoVo> interfaceInfoVos = pageAjax.getRows();
        for (AppSourceInterInfoVo appInfo : interfaceInfoVos) {
            if(StringUtil.isBlank(appInfo.getUrl())){
                continue;
            }
            String temp = appInfo.getUrl().substring(0,1);
            if(!StringUtil.equals(temp,"/")){
                appInfo.setUrl("/" + appInfo.getUrl());
            }
            // 开发者访问地址
            String url = gatewayContext + appInfo.getRouterKey() + appInfo.getUrl();
            appInfo.setUrl(url);
        }

        pageAjax.setRows(interfaceInfoVos);
        return pageAjax;
    }

    @Override
    public AjaxResult interfaceTest(InterfaceInfoVo interfaceInfoVo) {
        // 获取接口信息
        InterfaceInfoVo result = iInterfaceService.queryInterfaceVoById(interfaceInfoVo);
        if (ObjectUtils.isNullObj(result)){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"无法获取接口信息",interfaceInfoVo);
        }
        if(StringUtil.isNotBlank(result.getSqlMsg())){
            return getSqlData(result);
        }
        return getInterfaceAjax(result);
    }

    /**
     * TODO 接口数据获取
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2021/1/29 16:36
     * @throws:
     */
    private AjaxResult getInterfaceAjax(InterfaceInfoVo result) {
        return null;
    }

    /**
     * TODO 数据库数据获取
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2021/1/29 16:35
     * @throws:
     */
    private AjaxResult getSqlData(InterfaceInfoVo interfaceInfoVo) {
        // 获取数据库连接信息
        DataSourceInfo queryParam = new DataSourceInfo();
        queryParam.setDataSourceId(interfaceInfoVo.getDataSourceId());
        DataConnectInfo dataConnectInfo = getDataConnection(queryParam);
        if(ObjectUtils.isNullObj(dataConnectInfo)){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"无法获取数据库配置信息");
        }
        ConnectionPool connPool = new ConnectionPool(dataConnectInfo.getConnectDriver(),
                dataConnectInfo.getConnectAddress(), dataConnectInfo.getConnectUser(), dataConnectInfo.getConnectPassword());
        try {
            connPool.createPool();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 结果集
        List<Map<String,Object>> mapList = new ArrayList<>();
        try {
            Connection conn = connPool.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(interfaceInfoVo.getSqlMsg());
            ResultSet result = preparedStatement.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int columnCount = md.getColumnCount();
            while (result.next()) {
                JSONObject vo = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    vo.put(md.getColumnName(i), result.getObject(i));
                }
                mapList.add(CommonUtils.dataMap2Java(vo));
            }
            connPool.closeConnectionPool();
        } catch (SQLException ex1) {
            ex1.printStackTrace();
        }
        // 获取接口信息
        return new AjaxResult(mapList);
    }

    /**
     * TODO 获取数据库配置信息
     *
     * @Param: null
     * @return: * @return: null
     * @author: 黄芝民
     * @Date: 2020/12/30 14:08
     * @throws:
     */
    private DataConnectInfo getDataConnection(DataSourceInfo dataSourceInfo) {
        DataConnectInfo dataConnectInfo = new DataConnectInfo();
        dataConnectInfo.setDataSourceId(dataSourceInfo.getDataSourceId());
        DataConnectInfo queryParam = dataConnectService.queryConnectBySourceId(dataConnectInfo);
        if (ObjectUtils.isNullObj(queryParam)) {
            return null;
        }
        return queryParam;
    }
}
