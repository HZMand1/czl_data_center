package cn.paohe.interface_management.rest;

import cn.paohe.entity.model.InterfaceMag.InterfaceTypeInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceTypeInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IInterfaceTypeService;
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
 * @Date 2020/9/17 10:03
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/interface/type")
public class RestInterfaceTypeController {

    @Autowired
    private IInterfaceTypeService iInterfaceTypeService;

    @ApiOperation(value = "根据ID获取接口类型信息")
    @RequestMapping(value = "queryInterfaceLabelById", method = RequestMethod.POST)
    public AjaxResult queryInterfaceLabelById(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getTypeId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口类型ID不能为空");
        }
        InterfaceTypeInfo result = iInterfaceTypeService.queryInterfaceTypeById(interfaceLabelInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "查询全部接口类型信息")
    @RequestMapping(value = "queryInterfaceTypeList", method = RequestMethod.POST)
    public AjaxResult queryInterfaceTypeList(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        List<InterfaceTypeInfoVo> result = iInterfaceTypeService.queryInterfaceTypeList(interfaceLabelInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "分页查询接口类型信息")
    @RequestMapping(value = "queryPageInterfaceTypes", method = RequestMethod.POST)
    public PageAjax<InterfaceTypeInfoVo> queryPageInterfaceTypes(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        PageAjax<InterfaceTypeInfoVo> result = iInterfaceTypeService.queryPageInterfaceTypes(interfaceLabelInfo);
        return result;
    }

    @ApiOperation(value = "新增接口类型信息")
    @RequestMapping(value = "insertInterfaceType", method = RequestMethod.POST)
    public AjaxResult insertInterfaceType(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        int count = iInterfaceTypeService.insertInterfaceType(interfaceLabelInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"新增成功",interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"新增失败",interfaceLabelInfo);
    }

    @ApiOperation(value = "修改接口类型信息")
    @RequestMapping(value = "updateInterfaceTypeById", method = RequestMethod.POST)
    public AjaxResult updateInterfaceTypeById(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getTypeId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口类型ID不能为空");
        }
        int count = iInterfaceTypeService.updateInterfaceTypeById(interfaceLabelInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"修改成功",interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"修改失败",interfaceLabelInfo);
    }

    @ApiOperation(value = "屏蔽接口类型信息")
    @RequestMapping(value = "enableInterfaceTypeById", method = RequestMethod.POST)
    public AjaxResult enableInterfaceTypeById(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getTypeId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口类型ID不能为空");
        }
        int count = iInterfaceTypeService.enableInterfaceTypeById(interfaceLabelInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"屏蔽成功",interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"屏蔽失败",interfaceLabelInfo);
    }

    @ApiOperation(value = "删除接口类型信息")
    @RequestMapping(value = "deleteInterfaceTypeById", method = RequestMethod.POST)
    public AjaxResult deleteInterfaceTypeById(@ApiParam(value = "接口类型信息实体", required = true) @RequestBody InterfaceTypeInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getTypeId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口类型ID不能为空");
        }
        int count = iInterfaceTypeService.deleteInterfaceTypeById(interfaceLabelInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"删除成功",interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"删除失败",interfaceLabelInfo);
    }
}
