package cn.paohe.entity.vo.data_statistics;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/24 13:36
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class DataStatisticsVo {

    @ApiModelProperty(value = "所属标签ID")
    private Long labelId;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "接口类型ID")
    private Long typeId;

    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "开始时间")
    private Date startAddDate;

    @ApiModelProperty(value = "结束时间")
    private Date endAddDate;

    @ApiModelProperty(value = "用户ID")
    private Long addUserId;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getStartAddDate() {
        return startAddDate;
    }

    public void setStartAddDate(Date startAddDate) {
        this.startAddDate = startAddDate;
    }

    public Date getEndAddDate() {
        return endAddDate;
    }

    public void setEndAddDate(Date endAddDate) {
        this.endAddDate = endAddDate;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }
}
