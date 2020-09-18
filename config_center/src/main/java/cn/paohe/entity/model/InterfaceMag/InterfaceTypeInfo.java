package cn.paohe.entity.model.InterfaceMag;

import cn.paohe.vo.framework.IfQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 接口分类信息实体类
 *
 * @Author 黄芝民
 * @Date 2020/9/15 17:03
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Table(name = "interface_type_info")
public class InterfaceTypeInfo extends IfQuery {

    public static enum key{
        typeId ,typeName ,aliveFlag ,addUserId ,oprUserId ,
        addTime ,oprTime
    }

    @ApiModelProperty(value = "接口类型ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPE_ID")
    private Long typeId;

    @ApiModelProperty(value = "分类名称")
    @Column(name = "TYPE_NAME")
    private String typeName;

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
