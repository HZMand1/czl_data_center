package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.interface_management.dao.IAppSourceInterMapper;
import cn.paohe.interface_management.service.IAppSourceInterService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.PageAjax;
import com.alibaba.druid.sql.visitor.functions.Isnull;
import com.github.pagehelper.page.PageMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
    public int deleteById(AppSourceInterInfo appSourceInterInfo) {
        return appSourceInterMapper.deleteByPrimaryKey(appSourceInterInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBySourceId(AppSourceInterInfo appSourceInterInfo) {
        Condition condition = new Condition(AppSourceInterInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo(AppSourceInterInfo.key.dataSourceId.name(),appSourceInterInfo.getDataSourceId());
        int count = appSourceInterMapper.deleteByExample(condition);
        return count;
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableAppInterfaceById(AppSourceInterInfo appSourceInterInfo) {
        return appSourceInterMapper.updateByPrimaryKeySelective(appSourceInterInfo);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PageAjax<AppSourceInterInfoVo> queryCountPageAppInterface(AppSourceInterInfoVo appSourceInterInfoVo) {
        //分页
        PageMethod.startPage(appSourceInterInfoVo.getStart(), appSourceInterInfoVo.getPageSize());
        //查询
        List<AppSourceInterInfoVo> list = appSourceInterMapper.queryCountAppSourceInterList(appSourceInterInfoVo);
        return AppUtil.returnPage(list);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<DataSourceInfo> addDataSourceList(AppSourceInterInfoVo appSourceInterInfoVo) {
        //查询
        List<DataSourceInfo> list = appSourceInterMapper.addDataSourceList(appSourceInterInfoVo);
        return list;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<AppSourceInterInfo> queryAppInterfaceList(AppSourceInterInfo appSourceInterInfo) {
        Condition condition = new Condition(AppSourceInterInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        if(!ObjectUtils.isNullObj(appSourceInterInfo.getApplicationId())){
            criteria.andEqualTo(AppSourceInterInfo.key.applicationId.name(),appSourceInterInfo.getApplicationId());
        }
        if(!ObjectUtils.isNullObj(appSourceInterInfo.getDataSourceId())){
            criteria.andEqualTo(AppSourceInterInfo.key.dataSourceId.name(),appSourceInterInfo.getDataSourceId());
        }
        if(!ObjectUtils.isNullObj(appSourceInterInfo.getInterfaceId())){
            criteria.andEqualTo(AppSourceInterInfo.key.interfaceId.name(),appSourceInterInfo.getInterfaceId());
        }
        if(!ObjectUtils.isNullObj(appSourceInterInfo.getAddUserId())){
            criteria.andEqualTo(AppSourceInterInfo.key.addUserId.name(),appSourceInterInfo.getAddUserId());
        }
        if(!ObjectUtils.isNullObj(appSourceInterInfo.getSecretKey())){
            criteria.andEqualTo(AppSourceInterInfo.key.secretKey.name(),appSourceInterInfo.getSecretKey());
        }
        List<AppSourceInterInfo> appSourceInterInfos = appSourceInterMapper.selectByCondition(condition);
        return appSourceInterInfos;
    }
}
