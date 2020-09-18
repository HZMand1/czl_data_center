package cn.paohe.sys.rest;

import cn.paohe.entity.vo.sys.UserRoleVo;
import cn.paohe.sys.annotation.RequiresPermissions;
import cn.paohe.sys.service.IRoleUserService;
import cn.paohe.vo.framework.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**TODO  角色用户控制类
 * @author:      黄芝民
 * @date:        2020年10月24日 下午4:20:02 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
@Api(description = "角色controller接口", tags = { "rest-seed-role" })
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/roleuser/")
public class RestRoleUserController {
	
	@Autowired
	private IRoleUserService roleUserService;
	
	/**TODO 获取角色下的所有用户
	 * @param user
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */
	@RequiresPermissions("user:findRole")
	@ApiOperation(value = "获取角色下的所有用户")
	@RequestMapping(value = "findUserByRole", method = RequestMethod.POST)
	public AjaxResult findUserByRole(@ApiParam(value = "roleUser实体vo", required = true) @RequestBody UserRoleVo user){
		return roleUserService.findUserByRole(user);
	}
	
	/**TODO 根据用户获取角色
	 * @param user
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */
	@ApiOperation(value = "根据用户获取角色")
	@RequestMapping(value = "findRoleByUser", method = RequestMethod.POST)
	public AjaxResult findRoleByUser(@ApiParam(value = "roleUser实体vo", required = true) @RequestBody UserRoleVo user){
		return roleUserService.findRoleByUser(user);
	}
	
	/**TODO 给角色分配用户
	 * @param user
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */
	@RequiresPermissions("roleUser:toUser")
	@ApiOperation(value = "给角色分配用户")
	@RequestMapping(value = "alterRoleToUser", method = RequestMethod.POST)
	public AjaxResult alterRoleToUser(@ApiParam(value = "roleUser实体vo", required = true) @RequestBody UserRoleVo user){
		return roleUserService.alterRoleToUser(user);
	}
	
	/**TODO 给用户分配角色
	 * @param user
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */
	@RequiresPermissions("roleUser:toRole")
	@ApiOperation(value = "给用户分配角色")
	@RequestMapping(value = "alterUserToRole", method = RequestMethod.POST)
	public AjaxResult alterUserToRole(@ApiParam(value = "roleUser实体vo", required = true) @RequestBody UserRoleVo user){
		return roleUserService.alterUserToRole(user);
	}
	
}
