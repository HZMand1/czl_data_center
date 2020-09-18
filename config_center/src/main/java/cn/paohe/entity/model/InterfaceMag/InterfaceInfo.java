package cn.paohe.entity.model.InterfaceMag;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 接口信息实体类
 *
 * @Author 黄芝民
 * @Date 2020/9/15 16:07
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Table(name = "interface_info")
public class InterfaceInfo extends IfQuery {

    public static enum key {
        interfaceId, interfaceName, applicationId, applicationName,
        applicationCode, dataSourceId, dataSourceName, typeId, typeName,
        labelId, labelName, startTime, endTime, aliveFlag, addUserId, oprUserId,
        addTime, oprTime
    }

    @ApiModelProperty(value = "接口ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERFACE_ID")
    private Long interfaceId;

    @ApiModelProperty(value = "接口名称")
    @Column(name = "INTERFACE_NAME")
    private String interfaceName;

    @ApiModelProperty(value = "所属应用ID")
    @Column(name = "APPLICATION_ID")
    private Long applicationId;

    @ApiModelProperty(value = "所属数据源ID")
    @Column(name = "DATA_SOURCE_ID")
    private Long dataSourceId;

    @ApiModelProperty(value = "所属分类ID")
    @Column(name = "TYPE_ID")
    private Long typeId;

    @ApiModelProperty(value = "所属标签ID")
    @Column(name = "LABEL_ID")
    private Long labelId;

    @ApiModelProperty(value = "请求URL")
    @Column(name = "URL")
    private String url;

    @ApiModelProperty(value = "请求头信息")
    @Column(name = "HEADER_INFO")
    private String headerInfo;

    @ApiModelProperty(value = "请求体信息")
    @Column(name = "BODY_INFO")
    private String bodyInfo;

    @ApiModelProperty(value = "返回信息信息")
    @Column(name = "RESPONSE_INFO")
    private String responseInfo;

    @ApiModelProperty(value = "接口描述")
    @Column(name = "DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "密钥")
    @Column(name = "SECRET_KEY")
    private String secretKey;

    @ApiModelProperty(value = "接口开始时间")
    @Column(name = "START_TIME")
    private Date startTime;

    @ApiModelProperty(value = "接口结束时间")
    @Column(name = "END_TIME")
    private Date endTime;

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

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getBodyInfo() {
        return bodyInfo;
    }

    public void setBodyInfo(String bodyInfo) {
        this.bodyInfo = bodyInfo;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(String responseInfo) {
        this.responseInfo = responseInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
