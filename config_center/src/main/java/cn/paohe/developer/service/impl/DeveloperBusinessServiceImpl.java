package cn.paohe.developer.service.impl;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.developer.service.IDeveloperBusinessService;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.Md5;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.PageAjax;
import io.swagger.annotations.ApiModelProperty;
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

    @Resource
    private IInterfaceService iInterfaceService;

    @Override
    public PageAjax<InterfaceInfoVo> queryDeveloperInterPage(InterfaceInfoVo interfaceLabelInfoVo) {
        // 超级管理员可以查看全部接口信息
        if(StringUtil.equals(1,UserUtil.getUserEntity().getUserId())){

        }else {
            // 获取开发者所属的应用ID
            Long applicationId = UserUtil.getUserEntity().getApplicationId();
            if (ObjectUtils.isNullObj(applicationId)) {
                return new PageAjax<>();
            }
            interfaceLabelInfoVo.setApplicationId(applicationId);
        }
        PageAjax<InterfaceInfoVo> pageAjax = iInterfaceService.queryDeveloperPage(interfaceLabelInfoVo);
        List<InterfaceInfoVo> interfaceInfoVos = pageAjax.getRows();
        for (InterfaceInfoVo interfaceInfoVo : interfaceInfoVos) {
            // 加密 取MD5 的后8位作为密钥
            String secretKey = Md5.getMD5(interfaceInfoVo.getSecretKey().getBytes());
            secretKey = secretKey.substring(secretKey.length() - 8, secretKey.length());
            interfaceInfoVo.setSecretKey(secretKey);

            // 开发者访问地址
            String url = gatewayContext + interfaceInfoVo.getApplicationCode() + interfaceInfoVo.getUrl();
            interfaceInfoVo.setUrl(url);
        }

        pageAjax.setRows(interfaceInfoVos);
        return pageAjax;
    }
}
