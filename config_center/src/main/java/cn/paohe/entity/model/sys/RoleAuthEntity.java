package cn.paohe.entity.model.sys;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO
 * @Param:   权限表
 * @author:  黄芝民
 * @Date:    2020/9/16 9:37
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Table(name = "role_auth")
public class RoleAuthEntity extends IfQuery {

    /**
     * @Fields serialVersionUID : 用一句话描述这个变量表示什么
     */
    private static final long serialVersionUID = -8701192023672621074L;

    @ApiModelProperty(value = "主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ROLE_AUTH_ID")
    private Long roleAuthId;

    @ApiModelProperty(value = "角色ID")
    @Column(name = "ROLE_ID")
    private Long roleId;

    @ApiModelProperty(value = "用户ID")
    @Column(name = "USER_ID")
    private Long userId;

    @ApiModelProperty(value = "菜单功能ID")
    @Column(name = "MENU_ID")
    private Long menuId;

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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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
