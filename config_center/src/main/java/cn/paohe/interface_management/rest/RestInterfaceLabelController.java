package cn.paohe.interface_management.rest;

import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IInterfaceLabelService;
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
 * @Date 2020/9/17 10:02
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/interface/label")
public class RestInterfaceLabelController {

    @Autowired
    private IInterfaceLabelService iInterfaceLabelService;

    @ApiOperation(value = "根据ID获取接口标签信息")
    @RequestMapping(value = "queryInterfaceLabelById", method = RequestMethod.POST)
    public AjaxResult queryInterfaceLabelById(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getLabelId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口标签ID不能为空");
        }
        InterfaceLabelInfo result = iInterfaceLabelService.queryInterfaceLabelById(interfaceLabelInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "获取全部接口标签信息")
    @RequestMapping(value = "queryInterfaceLabelList", method = RequestMethod.POST)
    public AjaxResult queryInterfaceLabelList(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        List<InterfaceLabelInfo> result = iInterfaceLabelService.queryInterfaceLabelList(interfaceLabelInfo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "分页获取接口标签信息")
    @RequestMapping(value = "queryPageInterfaceLabels", method = RequestMethod.POST)
    public PageAjax<InterfaceLabelInfo> queryPageInterfaceLabels(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        PageAjax<InterfaceLabelInfo> result = iInterfaceLabelService.queryPageInterfaceLabels(interfaceLabelInfo);
        return result;
    }

    @ApiOperation(value = "新增接口标签信息")
    @RequestMapping(value = "insertInterfaceLabel", method = RequestMethod.POST)
    public AjaxResult insertInterfaceLabel(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        int count = iInterfaceLabelService.insertInterfaceLabel(interfaceLabelInfo);
        if (count > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功", interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败", interfaceLabelInfo);
    }

    @ApiOperation(value = "修改接口标签信息")
    @RequestMapping(value = "updateInterfaceLabelById", method = RequestMethod.POST)
    public AjaxResult updateInterfaceLabelById(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getLabelId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口标签ID不能为空");
        }
        int count = iInterfaceLabelService.updateInterfaceLabelById(interfaceLabelInfo);
        if (count > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改成功", interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改失败", interfaceLabelInfo);
    }

    @ApiOperation(value = "屏蔽接口标签信息")
    @RequestMapping(value = "enableInterfaceLabelById", method = RequestMethod.POST)
    public AjaxResult enableInterfaceLabelById(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getLabelId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口标签ID不能为空");
        }
        int count = iInterfaceLabelService.enableInterfaceLabelById(interfaceLabelInfo);
        if (count > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "屏蔽成功", interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "屏蔽失败", interfaceLabelInfo);
    }

    @ApiOperation(value = "删除接口标签信息")
    @RequestMapping(value = "deleteInterfaceLabelById", method = RequestMethod.POST)
    public AjaxResult deleteInterfaceLabelById(@ApiParam(value = "接口标签信息实体", required = true) @RequestBody InterfaceLabelInfo interfaceLabelInfo) {
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getLabelId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口标签ID不能为空");
        }
        int count = iInterfaceLabelService.deleteInterfaceLabelById(interfaceLabelInfo);
        if (count > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "删除成功", interfaceLabelInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "删除失败", interfaceLabelInfo);
    }
}
