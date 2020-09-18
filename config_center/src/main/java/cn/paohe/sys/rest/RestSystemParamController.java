package cn.paohe.sys.rest;

import cn.paohe.entity.model.sys.SystemParamEntity;
import cn.paohe.sys.annotation.AuthExclude;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.sys.service.ISystemParamService;
import cn.paohe.vo.framework.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO 系统参数配置
 *
 * @author 黄芝民
 * @version V1.0
 * @date 2020/10/23 14:58
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/systemParam")
public class RestSystemParamController {

    @Autowired
    private ISystemParamService systemParamService;

    /**
     * TODO 新增
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:39
     */
    @RequiresPermissions("sys:add")
    @ApiOperation(value = "新增", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResult add(@RequestBody SystemParamEntity systemParamEntity) {
        return systemParamService.add(systemParamEntity);
    }

    /**
     * TODO 修改
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:39
     */
    @RequiresPermissions("sys:update")
    @ApiOperation(value = "修改", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResult update(@RequestBody SystemParamEntity systemParamEntity) {
        return systemParamService.update(systemParamEntity);
    }

    /**
     * TODO 批量删除
     *
     * @param ids
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:39
     */
    @RequiresPermissions("sys:del")
    @ApiOperation(value = "批量删除", httpMethod = "POST")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public AjaxResult del(@RequestBody List<Long> ids) {
        return systemParamService.del(ids);
    }

    /**
     * TODO 列表条件查询
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:38
     */
    @AuthExclude
    @RequiresPermissions("sys:findList")
    @ApiOperation(value = "列表条件查询", httpMethod = "POST")
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public AjaxResult findList(@RequestBody SystemParamEntity systemParamEntity) {
        return systemParamService.findListPage(systemParamEntity);
    }

    /**
     * TODO 列表条件查询
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:38
     */
    @ApiOperation(value = "列表条件查询", httpMethod = "POST")
    @RequestMapping(value = "/findListByCondition", method = RequestMethod.POST)
    public AjaxResult findListByCondition(@RequestBody SystemParamEntity systemParamEntity) {
        return systemParamService.findListByCondition(systemParamEntity);
    }

    /**
     * TODO 查询全部系统参数
     *
     * @param
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/11/5 10:30
     */
    @AuthExclude
    @ApiOperation(value = "查询全部系统参数", httpMethod = "POST")
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public AjaxResult findAll() {
        return systemParamService.findAll();
    }

    /**
     * TODO 查询当前类型下所有的系统参数配置
     *
     * @param
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/11/11 10:30
     */
    @AuthExclude
    @ApiOperation(value = "查询当前类型下所有的系统参数配置", httpMethod = "POST")
    @RequestMapping(value = "/findListByType", method = RequestMethod.POST)
    public AjaxResult findListByType(@RequestBody SystemParamEntity systemParamEntity) {
        return systemParamService.findListByType(systemParamEntity);
    }
}
