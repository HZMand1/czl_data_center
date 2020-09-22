package cn.paohe.user.rest;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.entity.vo.user.UserEntityVo;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.user.service.IDeveloperService;
import cn.paohe.user.service.IUserInfoService;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/21 11:36
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/developer/")
public class RestDeveloperController {

    @Autowired
    private IDeveloperService developerService;
    @Autowired
    private IUserInfoService userInfoService;

    @RequiresPermissions("developer:insert")
    @ApiOperation(value = "新增后台用户信息")
    @RequestMapping(value = "insertDeveloper", method = RequestMethod.POST)
    public AjaxResult insertUser(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = developerService.insertDeveloper(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("developer:update")
    @ApiOperation(value = "修改后台用户信息")
    @RequestMapping(value = "updateDeveloperId", method = RequestMethod.POST)
    public AjaxResult updateUserById(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.updateUserById(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("developer:delete")
    @ApiOperation(value = "删除后台用户信息")
    @RequestMapping(value = "deleteDeveloperById", method = RequestMethod.POST)
    public AjaxResult deleteUserById(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.deleteUserById(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("developer:enable")
    @ApiOperation(value = "启用、禁用后台用户信息")
    @RequestMapping(value = "enableDeveloper", method = RequestMethod.POST)
    public AjaxResult enableUser(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.enableUser(userEntity);
        return ajaxResult;
    }

    @ApiOperation(value = "获取全部后台用户信息")
    @RequestMapping(value = "queryDeveloperAllList", method = RequestMethod.POST)
    public AjaxResult queryUserAllList(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntityVo userEntityVo) {
        AjaxResult ajaxResult = developerService.queryDeveloperList(userEntityVo);
        return ajaxResult;
    }

    @ApiOperation(value = "分页获取后台用户信息")
    @RequestMapping(value = "queryDeveloperAllPage", method = RequestMethod.POST)
    public PageAjax<UserEntity> queryUserAllPage(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntityVo userEntityVo) {
        return developerService.queryDeveloperPage(userEntityVo);
    }

    @ApiOperation(value = "根据ID获取后台用户信息")
    @RequestMapping(value = "queryDeveloperById", method = RequestMethod.POST)
    public AjaxResult queryUserById(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.queryUserById(userEntity);
        return ajaxResult;
    }
}
