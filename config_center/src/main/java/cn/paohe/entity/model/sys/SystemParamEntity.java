package cn.paohe.entity.model.sys;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO
 * @Param:   系统参数实体类
 * @author:  黄芝民
 * @Date:    2020/9/16 9:37
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Table(name = "system_param")
public class SystemParamEntity extends IfQuery{
	
	/** 
	 * @Fields serialVersionUID : 用一句话描述这个变量表示什么 
	 */ 
	private static final long serialVersionUID = -8701192023672621074L;

	@ApiModelProperty(value = "主键ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ApiModelProperty(value = "类型")
	@Column(name = "TYPE")
	private String type;
	
	@ApiModelProperty(value = "key关键字")
	@Column(name = "CODE")
	private String code;

	@ApiModelProperty(value = "值")
	@Column(name = "VAL")
	private String val;

	@ApiModelProperty(value = "排序")
	@Column(name = "ORDERS")
	private Integer orders;
	
	@ApiModelProperty(value = "备注")
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
