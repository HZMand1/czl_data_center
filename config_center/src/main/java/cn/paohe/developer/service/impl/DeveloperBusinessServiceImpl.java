package cn.paohe.developer.service.impl;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.developer.service.IDeveloperBusinessService;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.Md5;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/23 11:24
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DeveloperBusinessServiceImpl implements IDeveloperBusinessService {

    @ApiModelProperty("路由前缀地址")
    @Value("${gateway.context}")
    private String gatewayContext;

    @Autowired
    private IAppSourceInterService appSourceInterService;

    @Override
    public PageAjax<AppSourceInterInfoVo> queryDeveloperDateSourcePage(AppSourceInterInfoVo appSourceInterInfoVo) {
        // 超级管理员可以查看全部接口信息
        if(StringUtil.equals(1,UserUtil.getUserEntity().getUserId())){

        }else {
            // 获取开发者所属的应用ID
            Long applicationId = UserUtil.getUserEntity().getApplicationId();
            if (ObjectUtils.isNullObj(applicationId)) {
                return new PageAjax<>();
            }
            appSourceInterInfoVo.setApplicationId(applicationId);
            appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getParentUserId());
        }
        appSourceInterInfoVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        PageAjax<AppSourceInterInfoVo> pageAjax = appSourceInterService.queryCountPageAppInterface(appSourceInterInfoVo);
        return pageAjax;
    }

    @Override
    public PageAjax<AppSourceInterInfoVo> queryDeveloperInterPage(AppSourceInterInfoVo appSourceInterInfoVo) {
        // 超级管理员可以查看全部接口信息
        if(StringUtil.equals(1,UserUtil.getUserEntity().getUserId())){

        }else {
            // 获取开发者所属的应用ID
            Long applicationId = UserUtil.getUserEntity().getApplicationId();
            if (ObjectUtils.isNullObj(applicationId)) {
                return new PageAjax<>();
            }
            appSourceInterInfoVo.setApplicationId(applicationId);
            appSourceInterInfoVo.setAddUserId(UserUtil.getUserEntity().getParentUserId());
        }
        appSourceInterInfoVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        PageAjax<AppSourceInterInfoVo> pageAjax = appSourceInterService.queryPageAppInterface(appSourceInterInfoVo);
        List<AppSourceInterInfoVo> interfaceInfoVos = pageAjax.getRows();
        for (AppSourceInterInfoVo appInfo : interfaceInfoVos) {
            if(StringUtil.isBlank(appInfo.getUrl())){
                continue;
            }
            String temp = appInfo.getUrl().substring(0,1);
            if(!StringUtil.equals(temp,"/")){
                appInfo.setUrl("/" + appInfo.getUrl());
            }
            // 开发者访问地址
            String url = gatewayContext + appInfo.getRouterPath() + appInfo.getUrl();
            appInfo.setUrl(url);
        }

        pageAjax.setRows(interfaceInfoVos);
        return pageAjax;
    }
}
