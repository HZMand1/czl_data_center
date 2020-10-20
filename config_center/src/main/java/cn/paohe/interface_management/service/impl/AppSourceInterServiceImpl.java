package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.interface_management.dao.IAppSourceInterMapper;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/20 13:59
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class AppSourceInterServiceImpl implements IAppSourceInterService {

    @Autowired
    private IInterfaceService interfaceService;

    @Resource
    private IAppSourceInterMapper appSourceInterMapper;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAppInterface(AppSourceInterInfo appSourceInterInfo) {
        return appSourceInterMapper.insert(appSourceInterInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAppInterfaceList(List<AppSourceInterInfo> appSourceInterInfoList) {
        return appSourceInterMapper.insertList(appSourceInterInfoList);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableAppInterfaceById(AppSourceInterInfo appSourceInterInfo) {
        return appSourceInterMapper.updateByPrimaryKey(appSourceInterInfo);
    }

    @TargetDataSource(value = "center-r")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PageAjax<AppSourceInterInfoVo> queryPageAppInterface(AppSourceInterInfoVo appSourceInterInfoVo) {
        //分页
        PageMethod.startPage(appSourceInterInfoVo.getStart(), appSourceInterInfoVo.getPageSize());
        //查询
        List<AppSourceInterInfoVo> list = appSourceInterMapper.queryAppSourceInterList(appSourceInterInfoVo);
        return AppUtil.returnPage(list);
    }

    @TargetDataSource(value = "center-r")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PageAjax<AppSourceInterInfoVo> queryCountPageAppInterface(AppSourceInterInfoVo appSourceInterInfoVo) {
        //分页
        PageMethod.startPage(appSourceInterInfoVo.getStart(), appSourceInterInfoVo.getPageSize());
        //查询
        List<AppSourceInterInfoVo> list = appSourceInterMapper.queryCountAppSourceInterList(appSourceInterInfoVo);
        return AppUtil.returnPage(list);
    }
}
