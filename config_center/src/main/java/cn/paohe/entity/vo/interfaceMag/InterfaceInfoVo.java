package cn.paohe.entity.vo.interfaceMag;

import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/17 11:39
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class InterfaceInfoVo extends InterfaceInfo {

    @ApiModelProperty(value = "所属应用ID")
    private Long applicationId;

    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    @ApiModelProperty(value = "应用编号")
    private String applicationCode;

    @ApiModelProperty(value = "数据源名称")
    private String dataSourceName;

    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "应用关联ID")
    private Long appSourceInterId;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getAppSourceInterId() {
        return appSourceInterId;
    }

    public void setAppSourceInterId(Long appSourceInterId) {
        this.appSourceInterId = appSourceInterId;
    }
}
