package cn.paohe.entity.model.InterfaceMag;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 接口标签信息实体类
 *
 * @Author 黄芝民
 * @Date 2020/9/15 17:06
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Table(name = "interface_label_info")
public class InterfaceLabelInfo extends IfQuery {

    public static enum key{
        labelId ,labelName ,typeId,aliveFlag ,addUserId ,oprUserId ,
        addTime ,oprTime
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "所属标签ID")
    @Column(name = "LABEL_ID")
    private Long labelId;

    @ApiModelProperty(value = "标签名称")
    @Column(name = "LABEL_NAME")
    private String labelName;

    @ApiModelProperty(value = "接口类型ID")
    @Column(name = "TYPE_ID")
    private Long typeId;

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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
