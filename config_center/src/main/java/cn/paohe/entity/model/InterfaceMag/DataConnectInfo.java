package cn.paohe.entity.model.InterfaceMag;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 数据库连接信息
 *
 * @Author 黄芝民
 * @Date 2020/12/16 11:53
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Table(name = "data_connect_info")
public class DataConnectInfo extends IfQuery {

    public static enum key{
        dataConnectId ,dataSourceId,connectName,connectAddress ,connectDriver ,connectUser,aliveFlag ,addUserId ,oprUserId ,
        addTime ,oprTime
    }

    @ApiModelProperty(value = "数据库连接信息主键ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONNECT_ID")
    private Long dataConnectId;

    @Column(name = "DATA_SOURCE_ID")
    private Long dataSourceId;

    @ApiModelProperty(value = "数据库名称")
    @Column(name = "CONNECT_NAME")
    private String connectName;

    @ApiModelProperty(value = "数据库连接地址")
    @Column(name = "CONNECT_ADDRESS")
    private String connectAddress;

    @ApiModelProperty(value = "数据库连接驱动")
    @Column(name = "CONNECT_DRIVER")
    private String connectDriver;

    @ApiModelProperty(value = "数据库连接用户")
    @Column(name = "CONNECT_USER")
    private String connectUser;

    @ApiModelProperty(value = "数据库连接密码")
    @Column(name = "CONNECT_PASSWORD")
    private String connectPassword;

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

    public Long getDataConnectId() {
        return dataConnectId;
    }

    public void setDataConnectId(Long dataConnectId) {
        this.dataConnectId = dataConnectId;
    }

    public String getConnectName() {
        return connectName;
    }

    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    public String getConnectAddress() {
        return connectAddress;
    }

    public void setConnectAddress(String connectAddress) {
        this.connectAddress = connectAddress;
    }

    public String getConnectDriver() {
        return connectDriver;
    }

    public void setConnectDriver(String connectDriver) {
        this.connectDriver = connectDriver;
    }

    public String getConnectUser() {
        return connectUser;
    }

    public void setConnectUser(String connectUser) {
        this.connectUser = connectUser;
    }

    public String getConnectPassword() {
        return connectPassword;
    }

    public void setConnectPassword(String connectPassword) {
        this.connectPassword = connectPassword;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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
