package cn.paohe.entity.vo.sys;


import cn.paohe.entity.model.sys.RoleInfoEntity;
import cn.paohe.entity.model.user.UserEntity;

import java.util.List;

/**TODO  用户角色扩展类
 * @author:      黄芝民
 * @date:        2010年10月22日 下午2:26:14
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
public class UserRoleVo extends UserEntity {
	
	/** 
	 * @Fields serialVersionUID : 用一句话描述这个变量表示什么 
	 */ 
	private static final long serialVersionUID = 1L;

	private List<RoleInfoEntity> roleInfoList;
	
	/** 
	 * @Fields userIds : 用户Id数组 
	 */ 
	private Long[] userIds;
	
	/** 
	 * @Fields roleIds : 角色Id数组 
	 */ 
	private Long[] roleIds;
	
	/** 
	 * @Fields roleIds : 角色名称数组 
	 */ 
	private String[] roleNames;

	/** 
	 * @Fields roleId : 角色Id
	 */ 
	private Long roleId;
	
	/** 
	 * @Fields roleCode : 角色编号 
	 */ 
	private String roleCode;
	
	/** 
	 * @Fields roleName : 角色名称 
	 */ 
	private String roleName;
	
	/** 
	 * @Fields roleEnable : 角色状态 
	 */ 
	private Integer roleEnable;
	
	/** 
	 * @Fields roleRemark : 角色备注 
	 */ 
	private String roleRemark;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleRemark() {
		return roleRemark;
	}

	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

	public Integer getRoleEnable() {
		return roleEnable;
	}

	public void setRoleEnable(Integer roleEnable) {
		this.roleEnable = roleEnable;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}

	public List<RoleInfoEntity> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(List<RoleInfoEntity> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}
}
