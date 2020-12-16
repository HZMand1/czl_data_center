package cn.paohe.interface_management.rest;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.InterfaceMag.DataConnectInfo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IDataConnectService;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.util.basetype.ObjectUtils;
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
 * @Date 2020/12/16 12:32
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/connect/")
public class RestDataConnectController {

    @Autowired
    private IDataConnectService dataConnectService;

    @ApiOperation(value = "根据ID获取连接信息")
    @RequestMapping(value = "queryConnectById", method = RequestMethod.POST)
    public AjaxResult queryAppById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        if (ObjectUtils.isNullObj(dataConnectInfo.getDataConnectId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "连接ID不能为空");
        }
        DataConnectInfo result = dataConnectService.queryConnectById(dataConnectInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "获取全部连接信息")
    @RequestMapping(value = "queryConnectList", method = RequestMethod.POST)
    public AjaxResult queryConnectList(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        List<DataConnectInfo> dataConnectInfos = dataConnectService.queryConnectList(dataConnectInfo);
        return new AjaxResult(dataConnectInfos);
    }

    @ApiOperation(value = "分页查询连接信息")
    @RequestMapping(value = "queryConnectPage", method = RequestMethod.POST)
    public PageAjax<DataConnectInfo> queryConnectPage(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        PageAjax<DataConnectInfo> applicationInfos = dataConnectService.queryConnectPage(dataConnectInfo);
        return applicationInfos;
    }

    @RequiresPermissions("connect:insert")
    @ApiOperation(value = "新增应用")
    @RequestMapping(value = "insertConnect", method = RequestMethod.POST)
    public AjaxResult insertConnect(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        Set<String> errorMsg = new HashSet<>(1);
        boolean isPass = nullCheck(dataConnectInfo, errorMsg);
        if (!isPass) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, errorMsg.iterator().next());
        }
        int insertCount = dataConnectService.insertConnect(dataConnectInfo);
        if (insertCount > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功", dataConnectInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败", dataConnectInfo);
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
    private boolean nullCheck(DataConnectInfo dataConnectInfo, Set<String> errorMsg) {
        if (ObjectUtils.isNullObj(dataConnectInfo.getDataSourceId())) {
            errorMsg.add("数据源ID不能为空");
            return false;
        }
        if (StringUtil.isBlank(dataConnectInfo.getConnectName())) {
            errorMsg.add("连接名称不能为空");
            return false;
        }
        if (StringUtil.isBlank(dataConnectInfo.getConnectAddress())) {
            errorMsg.add("连接驱动不能为空");
            return false;
        }
        if (StringUtil.isBlank(dataConnectInfo.getConnectAddress())) {
            errorMsg.add("连接地址不能为空");
            return false;
        }
        if (StringUtil.isBlank(dataConnectInfo.getConnectUser())) {
            errorMsg.add("连接用户不能为空");
            return false;
        }
        if (StringUtil.isBlank(dataConnectInfo.getConnectPassword())) {
            errorMsg.add("连接密码不能为空");
            return false;
        }
        return true;
    }

    @RequiresPermissions("connect:delete")
    @ApiOperation(value = "删除数据库连接")
    @RequestMapping(value = "deleteConnectById", method = RequestMethod.POST)
    public AjaxResult deleteConnectById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        if (ObjectUtils.isNullObj(dataConnectInfo.getDataConnectId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        int deleteCount = dataConnectService.deleteConnectById(dataConnectInfo);
        if (deleteCount > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "删除应用成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "删除应用失败");
    }

    @RequiresPermissions("connect:update")
    @ApiOperation(value = "修改数据库连接")
    @RequestMapping(value = "updateAppById", method = RequestMethod.POST)
    public AjaxResult updateAppById(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        if (ObjectUtils.isNullObj(dataConnectInfo.getDataConnectId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        int insertCount = dataConnectService.updateConnectById(dataConnectInfo);
        if (insertCount > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改应用成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改应用失败");
    }

    @ApiOperation(value = "测试数据库连接")
    @RequestMapping(value = "testConnect", method = RequestMethod.POST)
    public AjaxResult testConnect(@ApiParam(value = "应用实体Vo", required = true) @RequestBody DataConnectInfo dataConnectInfo) {
        Set<String> errorMsg = new HashSet<>(1);
        boolean isPass = nullCheck(dataConnectInfo, errorMsg);
        if (!isPass) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, errorMsg.iterator().next());
        }
        return dataConnectService.testConnect(dataConnectInfo);
    }
}
