package cn.paohe.sys.service;

import cn.paohe.entity.vo.sys.RoleMenuAuthVo;
import cn.paohe.vo.framework.AjaxResult;

import java.util.List;

/**TODO  权限service
 * @author:      黄芝民
 * @date:        2020年10月22日 上午9:40:16 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
public interface IRoleAuthService {
	
	/**TODO 获取角色下的所有权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	public AjaxResult findAuthByRole(RoleMenuAuthVo authVo);
	
	/**TODO 获取用户的所有权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */ 
	public AjaxResult findAuthByUser(RoleMenuAuthVo authVo);
	
	/**TODO 给角色分配权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */ 
	public AjaxResult saveAuthToRole(RoleMenuAuthVo authVo);
	
	/**TODO 修改用户权限
	 * @param authVo
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:05 
	 * @throws   
	 */ 
	public AjaxResult saveAuthToUser(RoleMenuAuthVo authVo);

	/**TODO 查询当前用户权限树
	 * @param authVo
	 * @return
	 * @author 黄芝民
	 * @date 2020/11/12 11:14
	 * @throws
	 */
	List<RoleMenuAuthVo> findAuthTree(RoleMenuAuthVo authVo);
}
