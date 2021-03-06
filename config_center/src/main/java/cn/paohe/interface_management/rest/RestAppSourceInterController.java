package cn.paohe.interface_management.rest;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.sys.annotation.AuthExclude;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/20 13:55
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/app/interface/")
public class RestAppSourceInterController {

    @Autowired
    private IInterfaceService interfaceService;
    @Autowired
    private IAppSourceInterService appSourceInterService;

    @ApiOperation(value = "分页获取统计的应用接口信息")
    @RequestMapping(value = "queryCountPageAppInterface", method = RequestMethod.POST)
    public PageAjax<AppSourceInterInfoVo> queryCountPageAppInterface(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getApplicationId())) {
            return new PageAjax<AppSourceInterInfoVo>(Collections.emptyList(), DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getUserId());
        PageAjax<AppSourceInterInfoVo> result = appSourceInterService.queryCountPageAppInterface(appSourceInterInfoVo);
        return result;
    }

    @ApiOperation(value = "分页获取应用接口信息")
    @RequestMapping(value = "queryPageAppInterface", method = RequestMethod.POST)
    public PageAjax<AppSourceInterInfoVo> queryPageAppInterface(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getApplicationId())) {
            return new PageAjax<AppSourceInterInfoVo>(Collections.emptyList(), DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getUserId());
        PageAjax<AppSourceInterInfoVo> result = appSourceInterService.queryPageAppInterface(appSourceInterInfoVo);
        return result;
    }

    @ApiOperation(value = "删除接口关联关系")
    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    public AjaxResult deleteById(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfo appSourceInterInfo) {
        if (ObjectUtils.isNullObj(appSourceInterInfo.getId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用接口关联关系ID不能为空");
        }
        int count = appSourceInterService.deleteById(appSourceInterInfo);
        if (count > 0) {
            return new AjaxResult();
        }
        return new AjaxResult(DataCenterCollections.YesOrNo.NO.value);
    }

    @ApiOperation(value = "删除数据源")
    @RequestMapping(value = "deleteBySourceId", method = RequestMethod.POST)
    public AjaxResult deleteBySourceId(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfo appSourceInterInfo) {
        if (ObjectUtils.isNullObj(appSourceInterInfo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        List<AppSourceInterInfo> appSourceInterInfoList = appSourceInterService.queryAppInterfaceList(appSourceInterInfo);
        int deleteCount = 0;
        for (AppSourceInterInfo sourceInterInfo : appSourceInterInfoList) {
            AppSourceInterInfo appSourceInterInfo1 = new AppSourceInterInfo();
            appSourceInterInfo1.setId(sourceInterInfo.getId());
            deleteCount = deleteCount + appSourceInterService.deleteById(appSourceInterInfo1);
        }
        if(deleteCount > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"删除成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"删除失败");
    }

    @ApiOperation(value = "关联/取关")
    @RequestMapping(value = "enableAppDataInter", method = RequestMethod.POST)
    public AjaxResult enableAppDataInter(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfo appSourceInterInfo) {
        if (ObjectUtils.isNullObj(appSourceInterInfo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfo.getInterfaceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口ID不能为空");
        }
        // 新增关系
        if(StringUtil.equals(DataCenterCollections.YesOrNo.YES.value,appSourceInterInfo.getAliveFlag())){
            // 默认全部关联
            appSourceInterInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            // 获取接口信息
            InterfaceInfoVo interfaceInfo = new InterfaceInfoVo();
            interfaceInfo.setInterfaceId(appSourceInterInfo.getInterfaceId());
            InterfaceInfoVo interfaceInfoVo = interfaceService.queryInterfaceVoById(interfaceInfo);

            appSourceInterInfo.setSecretKey(interfaceInfoVo.getSecretKey());

            // 新增人和时间
            appSourceInterInfo.setAddTime(new Date());
            appSourceInterInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            appSourceInterService.insertAppInterface(appSourceInterInfo);
            return new AjaxResult(appSourceInterInfo);
        }
        // 删除关系
        List<AppSourceInterInfo> appSourceInterInfos = appSourceInterService.queryAppInterfaceList(appSourceInterInfo);
        if (CollectionUtil.isNotEmpty(appSourceInterInfos)) {
            AppSourceInterInfo result = appSourceInterInfos.get(0);
            AppSourceInterInfo param = new AppSourceInterInfo();
            param.setId(result.getId());
            appSourceInterService.deleteById(result);
            return new AjaxResult(result);
        }
        return new AjaxResult(DataCenterCollections.YesOrNo.NO.value,"操作关联关系异常");
    }

    @ApiOperation(value = "禁用/启用接口关系")
    @RequestMapping(value = "enableAppInterfaceById", method = RequestMethod.POST)
    public AjaxResult enableAppInterfaceById(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "主键ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getAliveFlag())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "操作标识不能为空");
        }
        int count = appSourceInterService.enableAppInterfaceById(appSourceInterInfoVo);
        if (count > 0) {
            return new AjaxResult();
        }
        return new AjaxResult(DataCenterCollections.YesOrNo.NO.value);
    }

    @ApiOperation(value = "获取数据源的接口信息")
    @RequestMapping(value = "queryInterfaceByDataSourceId", method = RequestMethod.POST)
    public PageAjax<InterfaceInfoVo> queryInterfaceByDataSourceId(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody InterfaceInfoVo interfaceInfoVo) {
        if (ObjectUtils.isNullObj(interfaceInfoVo.getDataSourceId())) {
            return new PageAjax<InterfaceInfoVo>(Collections.EMPTY_LIST,DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        if (ObjectUtils.isNullObj(interfaceInfoVo.getApplicationId())) {
            return new PageAjax<InterfaceInfoVo>(Collections.EMPTY_LIST,DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        interfaceInfoVo.setAddUserId(UserUtil.getUserEntity().getUserId());
        PageAjax<InterfaceInfoVo> interfaceInfos = interfaceService.queryPageAppInterfaceBySourceId(interfaceInfoVo);
        return interfaceInfos;
    }

    @ApiOperation(value = "应用添加数据源")
    @RequestMapping(value = "appAddDataSource", method = RequestMethod.POST)
    public AjaxResult appAddDataSource(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "数据源ID不能为空");
        }
        // 获取接口信息
        InterfaceInfoVo interfaceInfo = new InterfaceInfoVo();
        interfaceInfo.setDataSourceId(appSourceInterInfoVo.getDataSourceId());
        interfaceInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
        List<InterfaceInfoVo> interfaceInfos = interfaceService.queryInterfaceVoList(interfaceInfo);
        if(CollectionUtil.isEmpty(interfaceInfos)){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "该数据源没有接口信息,请先添加接口");
        }
        List<AppSourceInterInfo> appSourceInterInfos = new ArrayList<>(interfaceInfos.size());
        for (InterfaceInfoVo info : interfaceInfos) {
            AppSourceInterInfo appSourceInterInfo = new AppSourceInterInfoVo();
            // 应用ID
            appSourceInterInfo.setApplicationId(appSourceInterInfoVo.getApplicationId());
            // 数据源ID
            appSourceInterInfo.setDataSourceId(appSourceInterInfoVo.getDataSourceId());
            // 接口ID
            appSourceInterInfo.setInterfaceId(info.getInterfaceId());
            // 默认全部关联
            appSourceInterInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            // 接口密钥
            appSourceInterInfo.setSecretKey(info.getSecretKey());
            // 新增人和时间
            appSourceInterInfo.setAddTime(new Date());
            appSourceInterInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            appSourceInterInfos.add(appSourceInterInfo);
        }
        if(CollectionUtil.isNotEmpty(appSourceInterInfos)){
            appSourceInterService.insertAppInterfaceList(appSourceInterInfos);
        }
        return new AjaxResult();

    }

    @ApiOperation(value = "数据源信息列表")
    @RequestMapping(value = "addDataSourceList", method = RequestMethod.POST)
    public AjaxResult addDataSourceList(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getUserId());
        List<DataSourceInfo> dataSourceInfos = appSourceInterService.addDataSourceList(appSourceInterInfoVo);
        return new AjaxResult(dataSourceInfos);
    }

    @ApiOperation(value = "根据接口密钥ID获取关联信息")
    @RequestMapping(value = "getAppDataSourceBySecretKey", method = RequestMethod.POST)
    @AuthExclude
    public AjaxResult getAppDataSourceBySecretKey(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody JSONObject jsonObject) {
        AppSourceInterInfoVo appSourceInterInfoVo = new AppSourceInterInfoVo();
        appSourceInterInfoVo.setSecretKey(jsonObject.getString("secretKey"));
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getSecretKey())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO);
        }
        List<AppSourceInterInfo> appSourceInterInfos = appSourceInterService.queryAppInterfaceList(appSourceInterInfoVo);
        if(CollectionUtil.isNotEmpty(appSourceInterInfos)){
            return new AjaxResult(appSourceInterInfos.get(0));
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO);
    }
}
