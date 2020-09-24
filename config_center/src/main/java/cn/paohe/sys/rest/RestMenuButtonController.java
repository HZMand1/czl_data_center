package cn.paohe.sys.rest;

import cn.paohe.entity.model.sys.MenuButtonEntity;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.sys.service.IMenuButtonService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO  菜单按钮controller接口
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020年10月22日 上午9:30:22
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Api("菜单按钮controller接口")
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/menu/")
public class RestMenuButtonController {

    @Autowired
    private IMenuButtonService menuButtonService;

    /**
     * TODO 获取菜单按钮列表
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:01:15
     */
    @ApiOperation(value = "获取所有菜单按钮列表")
    @RequestMapping(value = "findMenuAllList", method = RequestMethod.POST)
    public AjaxResult findMenuAllList(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.findMenuAllList(menu);
    }

    /**
     * TODO 根据条件获取所有菜单按钮分页
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:05:39
     */
    @ApiOperation(value = "根据条件获取所有菜单按钮分页")
    @RequestMapping(value = "findMenuAllPage", method = RequestMethod.POST)
    public AjaxResult findMenuAllPage(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.findMenuAllPage(menu);
    }

    /**
     * TODO 获取菜单按钮可用列表
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:01:15
     */
    @ApiOperation(value = "获取菜单按钮可用列表")
    @RequestMapping(value = "findMenuEnableList", method = RequestMethod.POST)
    public AjaxResult findMenuEnableList(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        /*是否存在可用标识*/
        if (ObjectUtils.isNullObj(menu.getAliveFlag())) {
            menu.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        return menuButtonService.findMenuAllList(menu);
    }

    /**
     * TODO 根据条件获取所有菜单按钮可用分页
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:05:39
     */
    @ApiOperation(value = "根据条件获取所有菜单按钮可用分页")
    @RequestMapping(value = "findMenuEnablePage", method = RequestMethod.POST)
    public AjaxResult findMenuEnablePage(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        /*是否存在可用标识*/
        if (ObjectUtils.isNullObj(menu.getAliveFlag())) {
            menu.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        return menuButtonService.findMenuAllPage(menu);
    }

    /**
     * TODO 根据Id获取菜单按钮
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:09:06
     */
    @ApiOperation(value = "根据Id获取菜单按钮")
    @RequestMapping(value = "findMenuById", method = RequestMethod.POST)
    public AjaxResult findMenuById(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.findMenuById(menu.getMenuId());
    }

    /**
     * TODO 新增菜单按钮
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:18:47
     */
    @RequiresPermissions("menu:insert")
    @ApiOperation(value = "新增菜单按钮")
    @RequestMapping(value = "insertMenu", method = RequestMethod.POST)
    public AjaxResult insertMenu(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.insertMenu(menu);
    }

    /**
     * TODO 修改菜单按钮
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:05
     */
    @RequiresPermissions("menu:update")
    @ApiOperation(value = "修改菜单按钮")
    @RequestMapping(value = "updateMenu", method = RequestMethod.POST)
    public AjaxResult updateMenu(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.updateMenu(menu);
    }

    /**
     * TODO 修改菜单状态
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:15
     */
    @RequiresPermissions("menu:enable")
    @ApiOperation(value = "禁用启用菜单按钮")
    @RequestMapping(value = "enableMenu", method = RequestMethod.POST)
    public AjaxResult updateMenuEnable(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.updateMenuEnable(menu);
    }

    /**
     * TODO 获取当前节点下的所有子节点
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 下午2:24:12
     */
    @ApiOperation(value = "获取当前节点下的所有子节点")
    @RequestMapping(value = "findMenuNodes", method = RequestMethod.POST)
    public AjaxResult findMenuNodes(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.findMenuNodes(menu);
    }

    /**
     * TODO 获取菜单树结构
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 下午2:24:12
     */
    @ApiOperation(value = "获取菜单树结构")
    @RequestMapping(value = "findMenuZtree", method = RequestMethod.POST)
    public AjaxResult findMenuZtree(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.findMenuZtree(menu);
    }

    /**
     * TODO 获取菜单树结构
     *
     * @param menu
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 下午2:24:12
     */
    @ApiOperation(value = "获取菜单树结构")
    @RequestMapping(value = "findEnableMenuZtree", method = RequestMethod.POST)
    public AjaxResult findEnableMenuZtree(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.findEnableMenuZtree(menu);
    }

    /**
     * TODO 删除菜单（物理删除）
     *
     * @param menu
     * @return
     * @throws
     * @author 黄芝民
     * @date 2020/12/4 9:34
     */
    @RequiresPermissions("menu:delete")
    @ApiOperation(value = "修改菜单状态")
    @RequestMapping(value = "delMenu", method = RequestMethod.POST)
    public AjaxResult delMenu(@ApiParam(value = "menu实体vo", required = true) @RequestBody MenuButtonEntity menu) {
        return menuButtonService.delMenu(menu);
    }

}
