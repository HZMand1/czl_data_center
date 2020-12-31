package cn.paohe.user.service.impl;

import cn.paohe.base.utils.basetype.BeanUtil;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.InterfaceMag.DataConnectInfo;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.interface_management.service.IDataConnectService;
import cn.paohe.interface_management.service.IDataSourceService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.user.service.IDataSourceConnService;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.DB.CommonUtils;
import cn.paohe.utils.DB.ConnectionPool;
import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 13:52
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DataSourceConnServiceImpl implements IDataSourceConnService {

    @Autowired
    private IDataSourceService dataSourceService;
    @Autowired
    private IDataConnectService dataConnectService;
    @Autowired
    private IInterfaceService interfaceService;

    @Override
    public AjaxResult sqlQuery(JSONObject jsonObject) {
        // 接口key
        String secretKey = jsonObject.getString("secretKey");
        // 路由key
        String routerKey = jsonObject.getString("routerKey");
        if (StringUtil.isBlank(secretKey)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "secretKey can't be empty");
        }
        if (StringUtil.isBlank(routerKey)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "routerKey can't be empty");
        }
        // 获取路由信息
        DataSourceInfo dataSourceInfo = getDataSourceInfo(routerKey);
        if (ObjectUtils.isNullObj(dataSourceInfo)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "Can't get route info by routerKey");
        }
        // 获取数据库配置信息
        DataConnectInfo dataConnectInfo = getDataConnection(dataSourceInfo);
        if (ObjectUtils.isNullObj(dataConnectInfo)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "Can't get database connect info");
        }
        InterfaceInfoVo interfaceInfoVo = getInterface(secretKey);
        if (ObjectUtils.isNullObj(interfaceInfoVo)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "Can't get interface info by secretKey");
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
     * TODO 获取接口信息
     *
     * @Param: null
     * @return: * @return: null
     * @author: 黄芝民
     * @Date: 2020/12/30 14:14
     * @throws:
     */
    private InterfaceInfoVo getInterface(String secretKey) {
        InterfaceInfoVo queryParam = new InterfaceInfoVo();
        queryParam.setSecretKey(secretKey);
        List<InterfaceInfoVo> interfaceInfoVoList = interfaceService.queryInterfaceVoList(queryParam);
        if (CollectionUtil.isEmpty(interfaceInfoVoList)) {
            return null;
        }
        return interfaceInfoVoList.get(0);
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
        DataConnectInfo queryParam = new DataConnectInfo();
        queryParam.setDataSourceId(dataSourceInfo.getDataSourceId());
        List<DataConnectInfo> dataConnectInfoList = dataConnectService.queryConnectList(queryParam);
        if (CollectionUtil.isEmpty(dataConnectInfoList)) {
            return null;
        }
        return dataConnectInfoList.get(0);
    }

    /**
     * TODO 获取路由信息
     *
     * @Param: null
     * @return: * @return: null
     * @author: 黄芝民
     * @Date: 2020/12/30 14:04
     * @throws:
     */
    private DataSourceInfo getDataSourceInfo(String routerKey) {
        DataSourceInfo queryParam = new DataSourceInfo();
        queryParam.setRouterKey(routerKey);
        List<DataSourceInfo> dataSourceInfoList = dataSourceService.queryDataSourceList(queryParam);
        if (CollectionUtil.isEmpty(dataSourceInfoList)) {
            return null;
        }
        return dataSourceInfoList.get(0);
    }
}
