package cn.paohe.entity.vo.interfaceMag;

import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.entity.model.InterfaceMag.InterfaceTypeInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 16:45
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class InterfaceTypeInfoVo extends InterfaceTypeInfo {

    @ApiModelProperty(value = "接口标签集合")
    private List<InterfaceLabelInfoVo> interfaceLabelInfoVos;

    public List<InterfaceLabelInfoVo> getInterfaceLabelInfoVos() {
        return interfaceLabelInfoVos;
    }

    public void setInterfaceLabelInfoVos(List<InterfaceLabelInfoVo> interfaceLabelInfoVos) {
        this.interfaceLabelInfoVos = interfaceLabelInfoVos;
    }
}
