package cn.paohe.entity.vo.interfaceMag;

import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/23 10:11
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class InterfaceLabelInfoVo extends InterfaceLabelInfo {

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
