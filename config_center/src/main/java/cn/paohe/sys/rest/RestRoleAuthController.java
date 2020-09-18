package cn.paohe.sys.rest;

import cn.paohe.entity.vo.sys.RoleMenuAuthVo;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.sys.service.IRoleAuthService;
import cn.paohe.vo.framework.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**TODO  角色权限控制类
 * @author:      黄芝民
 * @date:        2020年10月21日 上午10:22:49 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
@Api(description = "角色controller接口", tags = { "rest-seed-roleauth" })
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/roleauth/")
public class RestRoleAuthController {
	
	@Autowired
	private IRoleAuthService roleAuthService;
	
	/**TODO 获取角色下的所有权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */
	@ApiOperation(value = "获取角色下的所有权限")
	@RequestMapping(value = "findAuthByRole", method = RequestMethod.POST)
	public AjaxResult findAuthByRole(@ApiParam(value = "RoleMenuAuthVo实体vo", required = true) @RequestBody RoleMenuAuthVo authVo){
		return roleAuthService.findAuthByRole(authVo);
	}
	
	/**TODO 获取用户的所有权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */ 
	@ApiOperation(value = "获取用户的所有权限")
	@RequestMapping(value = "findAuthByUser", method = RequestMethod.POST)
	public AjaxResult findAuthByUser(@ApiParam(value = "RoleMenuAuthVo实体vo", required = true) @RequestBody RoleMenuAuthVo authVo){
		return roleAuthService.findAuthByUser(authVo);
	}
	
	/**TODO 给角色分配权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */
	@RequiresPermissions("roleAuth:toRole")
	@ApiOperation(value = "给角色分配权限")
	@RequestMapping(value = "saveAuthToRole", method = RequestMethod.POST)
	public AjaxResult saveAuthToRole(@ApiParam(value = "RoleMenuAuthVo实体vo", required = true) @RequestBody RoleMenuAuthVo authVo){
		return roleAuthService.saveAuthToRole(authVo);
	}
	
	/**TODO 给用户分配权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:05 
	 * @throws   
	 */
	@RequiresPermissions("roleAuth:toUser")
	@ApiOperation(value = "给用户分配权限")
	@RequestMapping(value = "saveAuthToUser", method = RequestMethod.POST)
	public AjaxResult saveAuthToUser(@ApiParam(value = "RoleMenuAuthVo实体vo", required = true) @RequestBody RoleMenuAuthVo authVo){
		return roleAuthService.saveAuthToUser(authVo);
	}

	/**TODO 查询当前用户权限树
	 * @param
	 * @return AjaxResult
	 * @author 黄芝民
	 * @date 2020/11/12 11:23
	 * @throws
	 */
	@ApiOperation(value = "查询当前用户权限树")
	@RequestMapping(value = "findAuthTree", method = RequestMethod.POST)
	public AjaxResult findAuthTree(@RequestBody RoleMenuAuthVo authVo){
		return new AjaxResult(roleAuthService.findAuthTree(authVo));
	}

}
