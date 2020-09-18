package cn.paohe.entity.model.user;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 用户信息表
 *
 * @Author 黄芝民
 * @Date 2020/9/15 11:39
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Table(name = "user_info")
public class UserEntity extends IfQuery {

    public static enum key{
        userId ,account ,userName ,useName  ,phone ,
        email ,aliveFlag,addUserId,addTime,oprUserId,oprTime
    }

    @ApiModelProperty(value = "主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @ApiModelProperty(value = "主键ID")
    @Column(name = "P_USER_ID")
    private Long parentUserId;

    @ApiModelProperty(value = "账号")
    @Column(name = "ACCOUNT")
    private String account;

    @ApiModelProperty(value = "密码")
    @Column(name = "PASSWORD")
    private String password;

    @ApiModelProperty(value = "用户名")
    @Column(name = "USER_NAME")
    private String userName;

    @ApiModelProperty(value = "使用名")
    @Column(name = "USE_NAME")
    private String useName;

    @ApiModelProperty(value = "移动电话")
    @Column(name = "PHONE")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Column(name = "EMAIL")
    private String email;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
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

    public Long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }
}
