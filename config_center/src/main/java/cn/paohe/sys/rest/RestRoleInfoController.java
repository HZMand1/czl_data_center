package cn.paohe.sys.rest;

import cn.paohe.entity.model.sys.RoleInfoEntity;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.sys.service.IRoleInfoService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**TODO  角色控制类
 * @author:      黄芝民
 * @date:        2020年10月21日 上午10:22:49 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
@Api(description = "角色controller接口", tags = { "rest-seed-role" })
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/role/")
public class RestRoleInfoController {
	
	@Autowired
	private IRoleInfoService roleInfoService;
	
	/**TODO 获取角色列表
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	@ApiOperation(value = "获取所有角色列表")
	@RequestMapping(value = "findRoleAllList", method = RequestMethod.POST)
	public AjaxResult findRoleAllList(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		return roleInfoService.findRoleAllList(role);
	}
	
	/**TODO 根据条件获取所有角色分页
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */
	@ApiOperation(value = "根据条件获取所有角色分页")
	@RequestMapping(value = "findRoleAllPage", method = RequestMethod.POST)
	public AjaxResult findRoleAllPage(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		return roleInfoService.findRoleAllPage(role);
	}
	
	/**TODO 获取角色列表
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	@ApiOperation(value = "获取所有角色列表")
	@RequestMapping(value = "findRoleEnableList", method = RequestMethod.POST)
	public AjaxResult findRoleEnableList(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		/*是否存在可用标识*/
		if(ObjectUtils.isNullObj(role.getAliveFlag())){
			role.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
		}
		return roleInfoService.findRoleAllList(role);
	}
	
	/**TODO 根据条件获取所有角色分页
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */
	@ApiOperation(value = "根据条件获取所有角色分页")
	@RequestMapping(value = "findRoleEnablePage", method = RequestMethod.POST)
	public AjaxResult findRoleEnablePage(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		/*是否存在可用标识*/
		if(ObjectUtils.isNullObj(role.getAliveFlag())){
			role.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
		}
		return roleInfoService.findRoleAllPage(role);
	}
	
	/**TODO 根据Id获取角色
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:09:06 
	 * @throws   
	 */ 
	@ApiOperation(value = "根据Id获取角色")
	@RequestMapping(value = "findRoleById", method = RequestMethod.POST)
	public AjaxResult findRoleById(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		return roleInfoService.findRoleById(role.getRoleId());
	}
	
	/**TODO 新增角色
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */
	@RequiresPermissions("role:insert")
	@ApiOperation(value = "新增角色")
	@RequestMapping(value = "insertRole", method = RequestMethod.POST)
	public AjaxResult insertRole(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		return roleInfoService.insertRole(role);
	}
	
	/**TODO 修改角色
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:05 
	 * @throws   
	 */
	@RequiresPermissions("role:update")
	@ApiOperation(value = "修改角色")
	@RequestMapping(value = "updateRole", method = RequestMethod.POST)
	public AjaxResult updateRole(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		return roleInfoService.updateRole(role);
	}
	
	/**TODO 修改角色状态
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:15 
	 * @throws   
	 */
	@RequiresPermissions("role:enable")
	@ApiOperation(value = "修改角色状态")
	@RequestMapping(value = "updateRoleEnable", method = RequestMethod.POST)
	public AjaxResult updateRoleEnable(@ApiParam(value = "role实体vo", required = true) @RequestBody RoleInfoEntity role){
		return roleInfoService.updateRoleEnable(role);
	}

	/**TODO 删除角色
	 * @param ids
	 * @return
	 * @author 黄芝民
	 * @date 2020/12/5 11:11
	 * @throws
	 */
	@RequiresPermissions("role:delete")
	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "delRole", method = RequestMethod.POST)
	public AjaxResult delRole(@ApiParam(value = "roleId数组", required = true) @RequestBody String[] ids){
		return roleInfoService.delRole(ids);
	}
}
