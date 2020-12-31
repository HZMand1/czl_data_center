package cn.paohe.user.rest;

import cn.paohe.sys.annotation.AuthExclude;
import cn.paohe.user.service.IDataSourceConnService;
import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/29 17:39
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/data/source/connect/")
public class RestDataSourceConnController {

    @Autowired
    private IDataSourceConnService dataSourceConnService;

    @AuthExclude
    @ApiOperation(value = "连接数据库，执行查询")
    @RequestMapping(value = "sqlQuery", method = RequestMethod.POST)
    public AjaxResult sqlQuery(@ApiParam(value = "用户实体类", required = true) @RequestBody JSONObject jsonObject) {
        AjaxResult ajaxResult = dataSourceConnService.sqlQuery(jsonObject);
        return ajaxResult;
    }
}
