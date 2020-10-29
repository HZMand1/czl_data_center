package cn.paohe.interface_management.rest;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.encryption.UUIDGenerator;
import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.model.InterfaceMag.ApplicationInfo;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.model.routeConfig.RouterConfig;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.interface_management.service.IDataSourceService;
import cn.paohe.router_config.service.RouterConfigService;
import cn.paohe.sys.annotation.AuthExclude;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/17 10:02
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/source")
public class RestDataSourceController {

    @Autowired
    private IDataSourceService dataSourceService;
    @Autowired
    private IAppSourceInterService appSourceInterService;
    @Autowired
    private RouterConfigService routerConfigService;

    @ApiOperation(value = "根据ID获取数据源信息")
    @RequestMapping(value = "queryDataSourceById", method = RequestMethod.POST)
    public AjaxResult queryDataSourceById(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        if (ObjectUtils.isNullObj(dataSourceInfo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        DataSourceInfo result = dataSourceService.queryDataSourceById(dataSourceInfo);
        return new AjaxResult(result);
    }

    @AuthExclude
    @ApiOperation(value = "根据key获取应用信息")
    @RequestMapping(value = "queryDataSourceByKey", method = RequestMethod.POST)
    public AjaxResult queryDataSourceByKey(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        if (ObjectUtils.isNullObj(dataSourceInfo.getRouterKey())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "router key can't empty");
        }
        List<DataSourceInfo> result = dataSourceService.queryDataSourceList(dataSourceInfo);
        if(CollectionUtil.isNotEmpty(result)){
            return new AjaxResult(result.get(0));
        }
        return new AjaxResult();
    }

    @ApiOperation(value = "获取全部数据源信息")
    @RequestMapping(value = "queryDataSourceList", method = RequestMethod.POST)
    public AjaxResult queryDataSourceList(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        List<DataSourceInfo> result = dataSourceService.queryDataSourceList(dataSourceInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "分页获取数据源信息")
    @RequestMapping(value = "queryPageDataSourceList", method = RequestMethod.POST)
    public PageAjax<DataSourceInfo> queryPageDataSourceList(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        PageAjax<DataSourceInfo> result = dataSourceService.queryPageDataSourceList(dataSourceInfo);
        return result;
    }

    @RequiresPermissions("dataSource:insert")
    @ApiOperation(value = "新增数据源信息")
    @RequestMapping(value = "insertDataSource", method = RequestMethod.POST)
    public AjaxResult insertDataSource(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        Set<String> errorMsg = new HashSet<>(1);
        boolean isPass = nullCheck(dataSourceInfo, errorMsg);
        if (!isPass) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, errorMsg.iterator().next());
        }
        int insertCount = dataSourceService.insertDataSource(dataSourceInfo);
        if(insertCount > 0){
            insertRouter(dataSourceInfo);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"新增成功",dataSourceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"新增失败",dataSourceInfo);
    }

    /**
     * TODO 新增操作的非空判断
     *
     * @Param: interfaceInfo
     * @Param: errorMsg
     * @return: boolean
     * @author: 黄芝民
     * @Date: 2020/5/26 16:06
     * @throws:
     */
    private boolean nullCheck(DataSourceInfo dataSourceInfo, Set<String> errorMsg) {
        if (StringUtil.isBlank(dataSourceInfo.getDataSourceName())) {
            errorMsg.add("数据源名称不能为空");
            return false;
        }
        if (StringUtil.isBlank(dataSourceInfo.getRouterPath())) {
            errorMsg.add("路由地址不能为空");
            return false;
        }
        return true;
    }

    @RequiresPermissions("dataSource:enable")
    @ApiOperation(value = "屏蔽数据源信息")
    @RequestMapping(value = "enableDataSourceById", method = RequestMethod.POST)
    public AjaxResult enableDataSourceById(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        if (ObjectUtils.isNullObj(dataSourceInfo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        int enableCount = dataSourceService.enableDataSourceById(dataSourceInfo);
        if(enableCount > 0){
            DataSourceInfo currentSourceData = dataSourceService.queryDataSourceById(dataSourceInfo);
            if (!ObjectUtils.isNullObj(currentSourceData)) {
                // 更新路由
                List<RouterConfig> routerConfigList = routerConfigService.searchRouterConfigByName(currentSourceData.getRouterKey());
                if(CollectionUtil.isNotEmpty(routerConfigList)){
                    RouterConfig param = new RouterConfig();
                    param.setId(routerConfigList.get(0).getId());
                    param.setStatus(String.valueOf(currentSourceData.getAliveFlag()));
                    routerConfigService.updateRouterConfig(param);
                }
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"屏蔽成功",dataSourceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"屏蔽失败",dataSourceInfo);
    }

    @RequiresPermissions("dataSource:delete")
    @ApiOperation(value = "删除数据源信息")
    @RequestMapping(value = "deleteDataSourceById", method = RequestMethod.POST)
    public AjaxResult deleteDataSourceById(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        if (ObjectUtils.isNullObj(dataSourceInfo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }

        // 校验是否关联了应用
        AppSourceInterInfo appSourceInterInfo = new AppSourceInterInfo();
        appSourceInterInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
        appSourceInterInfo.setDataSourceId(dataSourceInfo.getDataSourceId());
        List<AppSourceInterInfo> appSourceInterInfoVos = appSourceInterService.queryAppInterfaceList(appSourceInterInfo);
        if(CollectionUtil.isNotEmpty(appSourceInterInfoVos)){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"该数据源已关联应用，请先删除关联关系",dataSourceInfo);
        }
        DataSourceInfo currentSourceData = dataSourceService.queryDataSourceById(dataSourceInfo);
        int enableCount = dataSourceService.deleteDataSourceById(dataSourceInfo);
        if(enableCount > 0){
            // 删除路由
            List<RouterConfig> routerConfigList = routerConfigService.searchRouterConfigByName(currentSourceData.getRouterKey());
            if(CollectionUtil.isNotEmpty(routerConfigList)){
                routerConfigService.deleteRouterConfig(routerConfigList.get(0).getId());
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"删除成功",dataSourceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"删除失败",dataSourceInfo);
    }

    @RequiresPermissions("dataSource:update")
    @ApiOperation(value = "修改数据源信息")
    @RequestMapping(value = "updateDataSourceById", method = RequestMethod.POST)
    public AjaxResult updateDataSourceById(@ApiParam(value = "数据源实体", required = true) @RequestBody DataSourceInfo dataSourceInfo) {
        if (ObjectUtils.isNullObj(dataSourceInfo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        int enableCount = dataSourceService.updateDataSourceById(dataSourceInfo);
        if(enableCount > 0){
            DataSourceInfo currentSourceData = dataSourceService.queryDataSourceById(dataSourceInfo);
            if (!ObjectUtils.isNullObj(currentSourceData)) {
                // 删除路由
                List<RouterConfig> routerConfigList = routerConfigService.searchRouterConfigByName(currentSourceData.getRouterKey());
                if(CollectionUtil.isNotEmpty(routerConfigList)){
                    routerConfigService.deleteRouterConfig(routerConfigList.get(0).getId());
                }
                insertRouter(currentSourceData);
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"修改成功",dataSourceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"修改失败",dataSourceInfo);
    }

    /**
     * TODO 更新路由信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/9/30 14:08
     * @throws:
     */
    private void insertRouter(DataSourceInfo dataSourceInfo){
        RouterConfig routerConfig = new RouterConfig();
        routerConfig.setId(UUIDGenerator.getUUID());
        routerConfig.setInterfaceName(dataSourceInfo.getRouterKey());
        routerConfig.setContextName(dataSourceInfo.getContextName());
        routerConfig.setDescription(dataSourceInfo.getDescription());
        routerConfig.setMappingPath(dataSourceInfo.getRouterPath());
        routerConfig.setStatus(DataCenterCollections.YesOrNo.YES.value + "");
        routerConfigService.insertRouterConfig(routerConfig);
    }
}
