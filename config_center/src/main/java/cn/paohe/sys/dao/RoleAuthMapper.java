package cn.paohe.sys.dao;



import cn.paohe.base.business.dao.MyMapper;
import cn.paohe.entity.model.sys.RoleAuthEntity;
import cn.paohe.entity.vo.sys.RoleMenuAuthVo;

import java.util.List;

/**TODO  权限dao
 * @author:      吕观林
 * @date:        2019年10月22日 上午10:01:26 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2019
 */
public interface RoleAuthMapper extends MyMapper<RoleAuthEntity> {
	
	/**TODO 查询角色用户权限信息
	 * @param authVo
	 * @return List<RoleMenuAuthVo>
	 * @author:    吕观林
	 * @date:      2019年10月24日 上午10:56:19 
	 * @throws   
	 */ 
	public List<RoleMenuAuthVo> findUserOrRoleMenuAuth(RoleMenuAuthVo authVo);

}
