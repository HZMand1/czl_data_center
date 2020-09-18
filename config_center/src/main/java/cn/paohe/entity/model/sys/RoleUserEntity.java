package cn.paohe.entity.model.sys;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO
 * @Param:   角色用户实体类
 * @author:  黄芝民
 * @Date:    2020/9/16 9:37
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Table(name = "role_user")
public class
RoleUserEntity extends IfQuery{
	
	/** 
	 * @Fields serialVersionUID : 用一句话描述这个变量表示什么 
	 */ 
	private static final long serialVersionUID = -8701192023672621074L;
	
	public static enum key{
        id ,roleName ,roleId ,userId ,addTime ,
        updateTime ,appCode
    }

	@ApiModelProperty(value = "主键ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ROLE_USER_ID")
	private Long roleUserId;

	@ApiModelProperty(value = "角色名称")
	@Column(name = "ROLE_NAME")
	private String roleName;

	@ApiModelProperty(value = "角色id")
	@Column(name = "ROLE_ID")
	private Long roleId;

	@ApiModelProperty(value = "用户ID")
	@Column(name = "USER_ID")
	private Long userId;

	@ApiModelProperty(value = "逻辑状态标识，0:失效,1:启用")
	@Column(name = "ALIVE_FLAG")
	private Integer aliveFlag;

	@ApiModelProperty(value = "创建用户")
	@Column(name = "ADD_USER_ID")
	private Long addUserId;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "ADD_TIME")
	private Date addTime;

	@ApiModelProperty(value = "修改用户")
	@Column(name = "OPR_USER_ID")
	private Long oprUserId;

	@ApiModelProperty(value = "修改时间")
	@Column(name = "OPR_TIME")
	private Date oprTime;

	public Long getRoleUserId() {
		return roleUserId;
	}

	public void setRoleUserId(Long roleUserId) {
		this.roleUserId = roleUserId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getAliveFlag() {
		return aliveFlag;
	}

	public void setAliveFlag(Integer aliveFlag) {
		this.aliveFlag = aliveFlag;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Long getOprUserId() {
		return oprUserId;
	}

	public void setOprUserId(Long oprUserId) {
		this.oprUserId = oprUserId;
	}

	public Date getOprTime() {
		return oprTime;
	}

	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}
}
