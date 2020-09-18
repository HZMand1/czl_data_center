package cn.paohe.entity.vo.sys;


import cn.paohe.entity.model.sys.MenuButtonEntity;

import java.io.Serializable;

/**TODO  角色菜单权限扩展类
 * @author: 黄芝民
 * @Date:    2020/9/16 9:37
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public class RoleMenuAuthVo extends MenuButtonEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : 用一句话描述这个变量表示什么
	 */
	private static final long serialVersionUID = -3820771342589545126L;

	/** 
	 * @Fields authId : 权限Id 
	 */ 
	private Long roleAuthId;
	
	/** 
	 * @Fields authIds : 菜单权限Id数组 
	 */ 
	private Long[] menuIds;
	
	/** 
	 * @Fields roleId : 角色id 
	 */ 
	private Long roleId;
	
	/** 
	 * @Fields userId : 用户Id 
	 */ 
	private Long userId;
	
	/** 
	 * @Fields menuId : 菜单Id 
	 */ 
	private String menuId;

	public Long getRoleAuthId() {
		return roleAuthId;
	}

	public void setRoleAuthId(Long roleAuthId) {
		this.roleAuthId = roleAuthId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
