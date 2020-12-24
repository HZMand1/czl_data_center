package cn.paohe.user.rest;

import cn.paohe.entity.vo.data_statistics.DataStatisticsVo;
import cn.paohe.user.service.IDataStatisticsService;
import cn.paohe.vo.framework.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/24 12:07
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/statistics/")
public class RestDataStatisticsController {

    @Autowired
    private IDataStatisticsService dataStatisticsService;

    @ApiOperation(value = "统计本周、月、季、年,分类标签下接口新增的情况")
    @RequestMapping(value = "queryNewInterfaceByType", method = RequestMethod.POST)
    public AjaxResult queryNewInterfaceByType(@ApiParam(value = "用户实体类", required = true) @RequestBody DataStatisticsVo dataStatisticsVo) {
        AjaxResult ajaxResult = dataStatisticsService.queryNewInterfaceByType(dataStatisticsVo);
        return ajaxResult;
    }

    @ApiOperation(value = "统计本周、月、季、年,分类标签下接口调用的情况")
    @RequestMapping(value = "queryInterfaceConnectByType", method = RequestMethod.POST)
    public AjaxResult queryInterfaceConnectByType(@ApiParam(value = "用户实体类", required = true) @RequestBody DataStatisticsVo dataStatisticsVo) {
        AjaxResult ajaxResult = dataStatisticsService.queryInterfaceConnectByType(dataStatisticsVo);
        return ajaxResult;
    }

    @ApiOperation(value = "统计本周、月、季、年,分类标签下接口调用的情况")
    @RequestMapping(value = "queryInterfaceConnectLog", method = RequestMethod.POST)
    public AjaxResult queryInterfaceConnectLog(@ApiParam(value = "用户实体类", required = true) @RequestBody DataStatisticsVo dataStatisticsVo) {
        AjaxResult ajaxResult = dataStatisticsService.queryInterfaceConnectLog(dataStatisticsVo);
        return ajaxResult;
    }
}
