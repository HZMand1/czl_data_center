package cn.paohe.sys.service;

import cn.paohe.entity.model.sys.MenuButtonEntity;
import cn.paohe.vo.framework.AjaxResult;

/**TODO  菜单按钮service
 * @author:      黄芝民
 * @date:        2020年10月22日 上午9:05:49 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
public interface IMenuButtonService {
	
	/**TODO 获取菜单按钮列表
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:01:15 
	 * @throws   
	 */ 
	public AjaxResult findMenuAllList(MenuButtonEntity menu);
	
	/**TODO 根据条件查询菜单按钮分页
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:05:39 
	 * @throws   
	 */ 
	public AjaxResult findMenuAllPage(MenuButtonEntity menu);
	
	/**TODO 根据Id获取菜单按钮
	 * @param menuId
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:09:06 
	 * @throws   
	 */ 
	public AjaxResult findMenuById(Long menuId);
	
	/**TODO 新增菜单按钮
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:18:47 
	 * @throws   
	 */ 
	public AjaxResult insertMenu(MenuButtonEntity menu);
	
	/**TODO 修改菜单按钮
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:05 
	 * @throws   
	 */ 
	public AjaxResult updateMenu(MenuButtonEntity menu);
	
	/**TODO 修改菜单状态
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月21日 上午11:19:15 
	 * @throws   
	 */ 
	public AjaxResult updateMenuEnable(MenuButtonEntity menu);
	
	/**TODO 获取当前节点下的所有子节点
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月24日 下午2:24:12 
	 * @throws   
	 */ 
	public AjaxResult findMenuNodes(MenuButtonEntity menu);
	
	/**TODO 获取菜单树结构
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月28日 上午11:16:29 
	 * @throws   
	 */ 
	public AjaxResult findMenuZtree(MenuButtonEntity menu);

	/**TODO 获取菜单树结构
	 * @param menu
	 * @return AjaxResult
	 * @author:    黄芝民
	 * @date:      2020年10月28日 上午11:16:29
	 * @throws
	 */
	public AjaxResult findEnableMenuZtree(MenuButtonEntity menu);

	/**TODO 删除菜单（物理删除）
	 * @param menu
	 * @return
	 * @author 黄芝民
	 * @date 2020/12/4 9:35
	 * @throws
	 */
	AjaxResult delMenu(MenuButtonEntity menu);
}
