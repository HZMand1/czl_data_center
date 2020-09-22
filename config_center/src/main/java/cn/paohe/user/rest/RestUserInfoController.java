package cn.paohe.user.rest;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.user.service.IUserInfoService;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO 用户接口管理类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/10/22 9:23
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/user/")
public class RestUserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value = "获取全部后台用户信息")
    @RequestMapping(value = "queryUserAllList", method = RequestMethod.POST)
    public AjaxResult queryUserAllList(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.queryUserAllList(userEntity);
        return ajaxResult;
    }

    @ApiOperation(value = "分页获取后台用户信息")
    @RequestMapping(value = "queryUserAllPage", method = RequestMethod.POST)
    public PageAjax<UserEntity> queryUserAllPage(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        return userInfoService.queryUserAllPage(userEntity);
    }

    @ApiOperation(value = "根据ID获取后台用户信息")
    @RequestMapping(value = "queryUserById", method = RequestMethod.POST)
    public AjaxResult queryUserById(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.queryUserById(userEntity);
        return ajaxResult;
    }

    @ApiOperation(value = "根据Account查询会员信息")
    @RequestMapping(value = "queryUserByAccount", method = RequestMethod.POST)
    public AjaxResult queryUserByAccount(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.queryUserByAccount(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("user:insert")
    @ApiOperation(value = "新增后台用户信息")
    @RequestMapping(value = "insertUser", method = RequestMethod.POST)
    public AjaxResult insertUser(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.insertUser(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("user:update")
    @ApiOperation(value = "修改后台用户信息")
    @RequestMapping(value = "updateUserById", method = RequestMethod.POST)
    public AjaxResult updateUserById(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.updateUserById(userEntity);
        return ajaxResult;
    }

    @ApiOperation(value = "修改后台用户密码")
    @RequestMapping(value = "updateUserPassword", method = RequestMethod.POST)
    public AjaxResult updateUserPassword(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.updateUserPassword(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("user:delete")
    @ApiOperation(value = "删除后台用户信息")
    @RequestMapping(value = "deleteUserById", method = RequestMethod.POST)
    public AjaxResult deleteUserById(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.deleteUserById(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("user:enable")
    @ApiOperation(value = "启用、禁用后台用户信息")
    @RequestMapping(value = "enableUser", method = RequestMethod.POST)
    public AjaxResult enableUser(@ApiParam(value = "用户实体类", required = true) @RequestBody UserEntity userEntity) {
        AjaxResult ajaxResult = userInfoService.enableUser(userEntity);
        return ajaxResult;
    }

    @RequiresPermissions("user:deleteRole")
    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "delUser", method = RequestMethod.POST)
    public AjaxResult delRole(@ApiParam(value = "userId数组", required = true) @RequestBody String[] ids) {
        return userInfoService.delUser(ids);
    }
}
