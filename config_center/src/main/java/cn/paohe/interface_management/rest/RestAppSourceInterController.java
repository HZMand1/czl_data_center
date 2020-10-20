package cn.paohe.interface_management.rest;

import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.interface_management.service.IApplicationService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
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
            return new PageAjax<AppSourceInterInfoVo>(Collections.emptyList(),DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        PageAjax<AppSourceInterInfoVo> result = appSourceInterService.queryCountPageAppInterface(appSourceInterInfoVo);
        return result;
    }

    @ApiOperation(value = "分页获取应用接口信息")
    @RequestMapping(value = "queryPageAppInterface", method = RequestMethod.POST)
    public PageAjax<AppSourceInterInfoVo> queryPageAppInterface(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getApplicationId())) {
            return new PageAjax<AppSourceInterInfoVo>(Collections.emptyList(),DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "应用ID不能为空");
        }
        PageAjax<AppSourceInterInfoVo> result = appSourceInterService.queryPageAppInterface(appSourceInterInfoVo);
        return result;
    }

    @ApiOperation(value = "删除数据源")
    @RequestMapping(value = "deleteBySourceId", method = RequestMethod.POST)
    public AjaxResult deleteBySourceId(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"数据源ID不能为空");
        }
        int count = appSourceInterService.deleteBySourceId(appSourceInterInfoVo);
        if(count > 0){
            return new AjaxResult();
        }
        return new AjaxResult(DataCenterCollections.YesOrNo.NO.value);
    }

    @ApiOperation(value = "禁用/启用接口关系")
    @RequestMapping(value = "enableAppInterfaceById", method = RequestMethod.POST)
    public AjaxResult enableAppInterfaceById(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"主键ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getAliveFlag())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"操作标识不能为空");
        }
        int count = appSourceInterService.enableAppInterfaceById(appSourceInterInfoVo);
        if(count > 0){
            return new AjaxResult();
        }
        return new AjaxResult(DataCenterCollections.YesOrNo.NO.value);
    }

    @ApiOperation(value = "应用添加数据源")
    @RequestMapping(value = "appAddDataSource", method = RequestMethod.POST)
    public AjaxResult appAddDataSource(@ApiParam(value = "应用接口实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getApplicationId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"应用ID不能为空");
        }
        if (ObjectUtils.isNullObj(appSourceInterInfoVo.getDataSourceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"数据源ID不能为空");
        }
        // 获取接口信息
        InterfaceInfoVo interfaceInfo = new InterfaceInfoVo();
        interfaceInfo.setDataSourceId(appSourceInterInfoVo.getDataSourceId());
        List<InterfaceInfoVo> interfaceInfos = interfaceService.queryInterfaceVoList(interfaceInfo);
        if(CollectionUtil.isEmpty(interfaceInfos)){
            return new AjaxResult();
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
}
