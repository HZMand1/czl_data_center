package cn.paohe.entity.model.sys;


import cn.paohe.entity.vo.sys.ZTree;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 
 * @Param:   菜单实体类
 * @author:  黄芝民
 * @Date:    2020/9/16 9:37
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Table(name = "menu_button")
public class MenuButtonEntity extends ZTree {
	
	/** 
	 * @Fields serialVersionUID : 用一句话描述这个变量表示什么 
	 */ 
	private static final long serialVersionUID = -8701192023672621074L;
	
	public static enum key{
        id ,parentId ,name ,path ,appCode ,function ,
        type ,aliveFlag ,addUserId
    }

	@ApiModelProperty(value = "主键ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_ID")
	private Long menuId;

	@ApiModelProperty(value = "父ID")
	@Column(name = "PARENT_ID")
	private String parentId;

	@ApiModelProperty(value = "类型,0:菜单，1：按钮")
	@Column(name = "TYPE")
	private Integer type;

	@ApiModelProperty(value = "名称")
	@Column(name = "NAME")
	private String name;

	@ApiModelProperty(value = "路径")
	@Column(name = "PATH")
	private String path;

	@ApiModelProperty(value = "请求标识")
	@Column(name = "AUTH")
	private String auth;
	
	@ApiModelProperty(value = "方法")
	@Column(name = "FUNCTIONS")
	private String functions;

	@ApiModelProperty(value = "说明")
	@Column(name = "REMARK")
	private String remark;

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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
