package cn.paohe.developer.service.impl;

import cn.paohe.developer.service.IDeveloperBusinessService;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.Md5;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.PageAjax;
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

    @Resource
    private IInterfaceService iInterfaceService;

    @Override
    public PageAjax<InterfaceInfoVo> queryDeveloperInterPage(InterfaceInfoVo interfaceLabelInfoVo) {
        // 获取开发者所属的应用ID
        Long applicationId = UserUtil.getUserEntity().getApplicationId();
        if(ObjectUtils.isNullObj(applicationId)){
            return new PageAjax<>();
        }
        interfaceLabelInfoVo.setApplicationId(applicationId);
        PageAjax<InterfaceInfoVo> pageAjax = iInterfaceService.queryPageInterfaceVoList(interfaceLabelInfoVo);
        List<InterfaceInfoVo> interfaceInfoVos = pageAjax.getRows();
        for (InterfaceInfoVo interfaceInfoVo : interfaceInfoVos) {
            // 加密 取MD5 的后8位作为密钥
            String secretKey = Md5.getMD5(interfaceInfoVo.getSecretKey().getBytes());
            secretKey = secretKey.substring(secretKey.length() - 8,secretKey.length());
            interfaceInfoVo.setSecretKey(secretKey);
        }
        pageAjax.setRows(interfaceInfoVos);
        return pageAjax;
    }
}
