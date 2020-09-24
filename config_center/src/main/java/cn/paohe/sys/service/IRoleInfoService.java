package cn.paohe.sys.service;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.entity.model.sys.RoleInfoEntity;
import cn.paohe.vo.framework.AjaxResult;

import java.util.List;

/**TODO  角色接口
 * @author:      黄芝民
 * @date:        2020年10月21日 上午10:20:33 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
public interface IRoleInfoService {
	
	/**TODO 获取角色列表
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	public AjaxResult findRoleAllList(RoleInfoEntity role);
	
	/**TODO 根据条件查询角色分页
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */ 
	public AjaxResult findRoleAllPage(RoleInfoEntity role);
	
	/**TODO 根据Id获取角色
	 * @param id
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:09:06 
	 * @throws   
	 */ 
	public AjaxResult findRoleById(Long id);
	
	/**TODO 新增角色
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */ 
	public AjaxResult insertRole(RoleInfoEntity role);
	
	/**TODO 修改角色
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:05 
	 * @throws   
	 */ 
	public AjaxResult updateRole(RoleInfoEntity role);
	
	/**TODO 修改角色状态
	 * @param role
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:15 
	 * @throws   
	 */
	@TargetDataSource(value = "seed-w")
	public AjaxResult updateRoleEnable(RoleInfoEntity role);

	/**TODO 删除角色
	 * @param roleIds
	 * @return
	 * @author 黄芝民
	 * @date 2020/12/5 11:11
	 * @throws
	 */
	AjaxResult delRole(List<RoleInfoEntity> roleIds);
}
