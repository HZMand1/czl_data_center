package cn.paohe.interface_management.rest;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IInterfaceService;
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
 * @Date 2020/9/17 10:02
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/interface")
public class RestInterfaceController {

    @Autowired
    private IInterfaceService iInterfaceService;

    @ApiOperation(value = "根据ID获取接口信息")
    @RequestMapping(value = "queryInterfaceById", method = RequestMethod.POST)
    public AjaxResult queryInterfaceById(@ApiParam(value = "接口信息实体Vo", required = true) @RequestBody InterfaceInfoVo interfaceInfoVo) {
        if (ObjectUtils.isNullObj(interfaceInfoVo.getInterfaceId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "接口ID不能为空");
        }
        InterfaceInfo result = iInterfaceService.queryInterfaceVoById(interfaceInfoVo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "查询全部接口信息")
    @RequestMapping(value = "queryInterfaceList", method = RequestMethod.POST)
    public AjaxResult queryInterfaceList(@ApiParam(value = "接口信息实体Vo", required = true) @RequestBody InterfaceInfoVo interfaceInfoVo) {
        List<InterfaceInfoVo> result = iInterfaceService.queryInterfaceVoList(interfaceInfoVo);
        return new AjaxResult(result);
    }

    @ApiOperation(value = "分页查询接口信息")
    @RequestMapping(value = "queryPageInterfaceList", method = RequestMethod.POST)
    public PageAjax<InterfaceInfoVo> queryPageInterfaceList(@ApiParam(value = "接口信息实体Vo", required = true) @RequestBody InterfaceInfoVo interfaceInfoVo) {
        PageAjax<InterfaceInfoVo> result = iInterfaceService.queryPageInterfaceVoList(interfaceInfoVo);
        return result;
    }

    @RequiresPermissions("interface:insert")
    @ApiOperation(value = "新增接口信息")
    @RequestMapping(value = "insertInterface", method = RequestMethod.POST)
    public AjaxResult insertInterface(@ApiParam(value = "接口信息实体", required = true) @RequestBody InterfaceInfo interfaceInfo) {
        Set<String> errorMsg = new HashSet<>(1);
        boolean isPass = nullCheck(interfaceInfo, errorMsg);
        if (!isPass) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, errorMsg.iterator().next());
        }
        int insertCount = iInterfaceService.insertInterface(interfaceInfo);
        if(insertCount > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"新增成功",interfaceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"新增失败",interfaceInfo);
    }

    /**
     * TODO 新增操作的非空判断
     *
     * @Param: purchaseOrderInfo
     * @Param: errorMsg
     * @return: boolean
     * @author: 黄芝民
     * @Date: 2020/5/26 16:06
     * @throws:
     */
    private boolean nullCheck(InterfaceInfo interfaceInfo, Set<String> errorMsg) {
        if (StringUtil.isBlank(interfaceInfo.getInterfaceName())) {
            errorMsg.add("接口名称不能为空");
            return false;
        }
        if (ObjectUtils.isNullObj(interfaceInfo.getApplicationId())) {
            errorMsg.add("所属应用不能为空");
            return false;
        }
        if (ObjectUtils.isNullObj(interfaceInfo.getDataSourceId())) {
            errorMsg.add("所属数据源不能为空");
            return false;
        }
        if (ObjectUtils.isNullObj(interfaceInfo.getTypeId())) {
            errorMsg.add("所属类型不能为空");
            return false;
        }
        if (ObjectUtils.isNullObj(interfaceInfo.getLabelId())) {
            errorMsg.add("所属标签不能为空");
            return false;
        }
        if (StringUtil.isBlank(interfaceInfo.getUrl())) {
            errorMsg.add("请求连接不能为空");
            return false;
        }
        return true;
    }

    @RequiresPermissions("interface:update")
    @ApiOperation(value = "修改接口信息")
    @RequestMapping(value = "updateInterfaceById", method = RequestMethod.POST)
    public AjaxResult updateInterfaceById(@ApiParam(value = "接口信息实体", required = true) @RequestBody InterfaceInfo interfaceInfo) {
        int count = iInterfaceService.updateInterfaceById(interfaceInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"修改成功",interfaceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"修改失败",interfaceInfo);
    }

    @RequiresPermissions("interface:delete")
    @ApiOperation(value = "删除接口信息")
    @RequestMapping(value = "deleteInterfaceById", method = RequestMethod.POST)
    public AjaxResult deleteInterfaceById(@ApiParam(value = "接口信息实体", required = true) @RequestBody InterfaceInfo interfaceInfo) {
        int count = iInterfaceService.deleteInterfaceById(interfaceInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"删除成功",interfaceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"删除失败",interfaceInfo);
    }

    @RequiresPermissions("interface:enable")
    @ApiOperation(value = "屏蔽接口信息")
    @RequestMapping(value = "enableInterfaceById", method = RequestMethod.POST)
    public AjaxResult enableInterfaceById(@ApiParam(value = "接口信息实体", required = true) @RequestBody InterfaceInfo interfaceInfo) {
        int count = iInterfaceService.enableInterfaceById(interfaceInfo);
        if(count > 0){
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"屏蔽成功",interfaceInfo);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"屏蔽失败",interfaceInfo);
    }
}
