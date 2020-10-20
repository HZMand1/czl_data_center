package cn.paohe.developer.rest;

import cn.paohe.developer.service.IDeveloperBusinessService;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/23 11:10
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/developer")
public class RestDeveloperBusinessController {

    @Autowired
    private IDeveloperBusinessService developerBusinessService;

    @ApiOperation(value = "分页统计查询接口信息")
    @RequestMapping(value = "queryDeveloperDateSourcePage", method = RequestMethod.POST)
    public PageAjax<AppSourceInterInfoVo> queryDeveloperDateSourcePage(@ApiParam(value = "接口信息实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        PageAjax<AppSourceInterInfoVo> result = developerBusinessService.queryDeveloperDateSourcePage(appSourceInterInfoVo);
        return result;
    }

    @ApiOperation(value = "分页统计查询接口信息")
    @RequestMapping(value = "queryDeveloperInterPage", method = RequestMethod.POST)
    public PageAjax<AppSourceInterInfoVo> queryDeveloperInterPage(@ApiParam(value = "接口信息实体Vo", required = true) @RequestBody AppSourceInterInfoVo appSourceInterInfoVo) {
        PageAjax<AppSourceInterInfoVo> result = developerBusinessService.queryDeveloperInterPage(appSourceInterInfoVo);
        return result;
    }
}
