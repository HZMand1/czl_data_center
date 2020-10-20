package cn.paohe.entity.vo.interfaceMag;

import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;


/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/20 14:15
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class AppSourceInterInfoVo extends AppSourceInterInfo {

    @ApiModelProperty(value = "接口数量")
    private Integer interfaceCount;

    public Integer getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Integer interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    @ApiModelProperty(value = "接口名称")
    @Column(name = "INTERFACE_NAME")
    private String interfaceName;

    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "路由器映射地址")
    @Column(name = "ROUTER_PATH")
    private String routerPath;

    @ApiModelProperty(value = "请求URL")
    @Column(name = "URL")
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
