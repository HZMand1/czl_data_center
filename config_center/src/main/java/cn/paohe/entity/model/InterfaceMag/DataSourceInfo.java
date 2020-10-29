package cn.paohe.entity.model.InterfaceMag;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 数据源信息实体类
 *
 * @Author 黄芝民
 * @Date 2020/9/15 16:02
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Table(name = "data_source_info")
public class DataSourceInfo extends IfQuery {

    public static enum key{
        dataSourceId ,dataSourceName ,dataSourceCode,routerKey ,aliveFlag ,addUserId ,oprUserId ,
        addTime ,oprTime
    }

    @ApiModelProperty(value = "数据源ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATA_SOURCE_ID")
    private Long dataSourceId;

    @ApiModelProperty(value = "数据源名称")
    @Column(name = "DATA_SOURCE_NAME")
    private String dataSourceName;

    @ApiModelProperty(value = "数据源编号")
    @Column(name = "DATA_SOURCE_CODE")
    private String dataSourceCode;

    @ApiModelProperty(value = "上下文名称")
    @Column(name = "CONTEXT_NAME")
    private String contextName;

    @ApiModelProperty(value = "描述")
    @Column(name = "DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "路由器密钥")
    @Column(name = "ROUTER_KEY")
    private String routerKey;

    @ApiModelProperty(value = "路由地址")
    @Column(name = "ROUTER_PATH")
    private String routerPath;

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

    public String getRouterKey() {
        return routerKey;
    }

    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
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

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
