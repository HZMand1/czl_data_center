package cn.paohe.sys.service;

import cn.paohe.entity.vo.sys.UserRoleVo;
import cn.paohe.vo.framework.AjaxResult;

import java.util.List;

/**TODO  角色用户service
 * @author:      黄芝民
 * @date:        2020年10月22日 上午10:05:19 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
public interface IRoleUserService {
	
	/**TODO 获取角色下的所有用户
	 * @param user
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	public AjaxResult findUserByRole(UserRoleVo user);
	
	/**TODO 根据用户获取角色
	 * @param user
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */ 
	public AjaxResult findRoleByUser(UserRoleVo user);
	
	/**TODO 给角色分配用户
	 * @param roleVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */ 
	public AjaxResult alterRoleToUser(UserRoleVo roleVo);
	
	/**TODO 给用户分配角色
	 * @param roleVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */ 
	public AjaxResult alterUserToRole(UserRoleVo roleVo);
	
	/**TODO 获取用户所有角色
	 * @param user
	 * @return List<UserRoleVo>
	 * @author:    黄芝民
	 * @date:      2020年10月24日 上午11:03:09 
	 * @throws   
	 */ 
	public List<UserRoleVo> findRoleByUserList(UserRoleVo user);
	
}
