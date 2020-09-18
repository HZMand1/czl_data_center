package cn.paohe.sys.dao;



import cn.paohe.base.business.dao.MyMapper;
import cn.paohe.entity.model.sys.RoleUserEntity;
import cn.paohe.entity.vo.sys.UserRoleVo;

import java.util.List;

/**TODO  角色用户dao
 * @author:      吕观林
 * @date:        2019年10月22日 上午10:10:46 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2019
 */
public interface RoleUserMapper extends MyMapper<RoleUserEntity> {
	
	/**TODO 获取角色下的所有用户
	 * @param userRoleVo
	 * @return AjaxResult
	 * @author:    吕观林
	 * @date:      2019年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	public List<UserRoleVo> findUserByRole(UserRoleVo userRoleVo);
	
}
