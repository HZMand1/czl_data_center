package cn.paohe.interface_management.rest;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.encryption.UUIDGenerator;
import cn.paohe.entity.model.InterfaceMag.ApplicationInfo;
import cn.paohe.entity.model.routeConfig.RouterConfig;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IApplicationService;
import cn.paohe.router_config.service.RouterConfigService;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
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
 * @Date 2020/9/17 9:41
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/app/")
public class RestApplicationController {

    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private RouterConfigService routerConfigService;

    @ApiOperation(value = "根据ID获取应用信息")
    @RequestMapping(value = "queryAppById", method = RequestMethod.POST)
    public AjaxResult queryAppById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        if (ObjectUtils.isNullObj(applicationInfo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        ApplicationInfo result = applicationService.queryAppById(applicationInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "获取全部用户应用")
    @RequestMapping(value = "queryAppList", method = RequestMethod.POST)
    public AjaxResult queryAppList(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        List<ApplicationInfo> applicationInfos = applicationService.queryAppList(applicationInfo);
        return new AjaxResult(applicationInfos);
    }

    @ApiOperation(value = "获取用户分页应用")
    @RequestMapping(value = "queryPageAppList", method = RequestMethod.POST)
    public PageAjax<ApplicationInfo> queryPageAppList(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        PageAjax<ApplicationInfo> applicationInfos = applicationService.queryPageAppList(applicationInfo);
        return applicationInfos;
    }

    @RequiresPermissions("app:insert")
    @ApiOperation(value = "新增应用")
    @RequestMapping(value = "insertApp", method = RequestMethod.POST)
    public AjaxResult insertApp(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        Set<String> errorMsg = new HashSet<>(1);
        boolean isPass = nullCheck(applicationInfo, errorMsg);
        if (!isPass) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, errorMsg.iterator().next());
        }
        int insertCount = applicationService.insertApp(applicationInfo);
        if (insertCount > 0) {
            // update route config
            insertRouter(applicationInfo);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功", applicationInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败", applicationInfo);
    }

    /**
     * TODO 更新路由信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/9/30 14:08
     * @throws:
     */
    private void insertRouter(ApplicationInfo applicationInfo){
        RouterConfig routerConfig = new RouterConfig();
        routerConfig.setId(UUIDGenerator.getUUID());
        routerConfig.setInterfaceName(applicationInfo.getApplicationCode());
        routerConfig.setContextName(applicationInfo.getContextName());
        routerConfig.setDescription(applicationInfo.getDescription());
        routerConfig.setMappingPath(applicationInfo.getMappingPath());
        routerConfig.setStatus(DataCenterCollections.YesOrNo.YES.value + "");
        routerConfigService.insertRouterConfig(routerConfig);
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
    private boolean nullCheck(ApplicationInfo applicationInfo, Set<String> errorMsg) {
        if (StringUtil.isBlank(applicationInfo.getApplicationName())) {
            errorMsg.add("应用名称不能为空");
            return false;
        }
        if (ObjectUtils.isNullObj(applicationInfo.getMappingPath())) {
            errorMsg.add("应用转发地址不能为空");
            return false;
        }
        return true;
    }

    @RequiresPermissions("app:enable")
    @ApiOperation(value = "屏蔽应用")
    @RequestMapping(value = "enableAppById", method = RequestMethod.POST)
    public AjaxResult enableAppById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        if (ObjectUtils.isNullObj(applicationInfo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        int enableCount = applicationService.enableAppById(applicationInfo);
        if (enableCount > 0) {
            ApplicationInfo applicationInfo1 = applicationService.queryAppById(applicationInfo);
            if (!ObjectUtils.isNullObj(applicationInfo1)) {
                // 更新路由
                List<RouterConfig> routerConfigList = routerConfigService.searchRouterConfigByName(applicationInfo1.getApplicationCode());
                if(CollectionUtil.isNotEmpty(routerConfigList)){
                    RouterConfig param = new RouterConfig();
                    param.setId(routerConfigList.get(0).getId());
                    param.setStatus(String.valueOf(applicationInfo.getAliveFlag()));
                    routerConfigService.updateRouterConfig(param);
                }
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "屏蔽应用成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "屏蔽应用失败");
    }

    @RequiresPermissions("app:delete")
    @ApiOperation(value = "删除应用")
    @RequestMapping(value = "deleteAppById", method = RequestMethod.POST)
    public AjaxResult deleteAppById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        if (ObjectUtils.isNullObj(applicationInfo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        ApplicationInfo applicationInfo1 = applicationService.queryAppById(applicationInfo);
        int deleteCount = applicationService.deleteAppById(applicationInfo);
        if (deleteCount > 0) {
            if (!ObjectUtils.isNullObj(applicationInfo1)) {
                // 删除路由
                List<RouterConfig> routerConfigList = routerConfigService.searchRouterConfigByName(applicationInfo1.getApplicationCode());
                if(CollectionUtil.isNotEmpty(routerConfigList)){
                    routerConfigService.deleteRouterConfig(routerConfigList.get(0).getId());
                }
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "删除应用成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "删除应用失败");
    }

    @RequiresPermissions("app:update")
    @ApiOperation(value = "修改应用")
    @RequestMapping(value = "updateAppById", method = RequestMethod.POST)
    public AjaxResult updateAppById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody ApplicationInfo applicationInfo) {
        if (ObjectUtils.isNullObj(applicationInfo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        int insertCount = applicationService.updateAppById(applicationInfo);
        if (insertCount > 0) {
            ApplicationInfo applicationInfo1 = applicationService.queryAppById(applicationInfo);
            if (!ObjectUtils.isNullObj(applicationInfo1)) {
                // 删除路由
                List<RouterConfig> routerConfigList = routerConfigService.searchRouterConfigByName(applicationInfo1.getApplicationCode());
                if(CollectionUtil.isNotEmpty(routerConfigList)){
                    routerConfigService.deleteRouterConfig(routerConfigList.get(0).getId());
                }
                insertRouter(applicationInfo1);
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改应用成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改应用失败");
    }
}
