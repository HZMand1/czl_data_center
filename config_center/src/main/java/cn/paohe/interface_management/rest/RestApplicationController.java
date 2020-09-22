package cn.paohe.interface_management.rest;

import cn.paohe.entity.model.InterfaceMag.ApplicationInfo;
import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IApplicationService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private IInterfaceService iInterfaceService;

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
        int insertCount = applicationService.insertApp(applicationInfo);
        if (insertCount > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功",applicationInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败",applicationInfo);
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
            // 屏蔽该应用下的所有接口
//            InterfaceInfo param = new InterfaceInfo();
//            param.setAliveFlag(DataCenterCollections.YesOrNo.NO.value);
//            InterfaceInfo condition = new InterfaceInfo();
//            condition.setApplicationId(applicationInfo.getApplicationId());
//            iInterfaceService.updateInterfaceByCondition(param,condition);
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
        int deleteCount = applicationService.deleteAppById(applicationInfo);
        if (deleteCount > 0) {
            // 删除该应用下的所有接口
//            InterfaceInfo interfaceInfo = new InterfaceInfo();
//            interfaceInfo.setApplicationId(applicationInfo.getApplicationId());
//            iInterfaceService.deleteInterfaceByCondition(interfaceInfo);
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
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改应用成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改应用失败");
    }
}
