package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.dao.IDataSourceMapper;
import cn.paohe.interface_management.service.IDataSourceService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.PageAjax;
import com.github.pagehelper.page.PageMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:15
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DataSourceServiceImpl implements IDataSourceService {

    @Resource
    private IDataSourceMapper dataSourceMapper;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertDataSource(DataSourceInfo dataSourceInfo) {
        // 新增人和时间
        if (ObjectUtils.isNullObj(dataSourceInfo.getAddUserId())) {
            dataSourceInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            dataSourceInfo.setAddTime(new Date());
        }
        // 默认可用
        if (ObjectUtils.isNullObj(dataSourceInfo.getAliveFlag())) {
            dataSourceInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        return dataSourceMapper.insert(dataSourceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateDataSourceById(DataSourceInfo dataSourceInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(dataSourceInfo.getOprUserId())) {
            dataSourceInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            dataSourceInfo.setOprTime(new Date());
        }
        return dataSourceMapper.updateByPrimaryKeySelective(dataSourceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableDataSourceById(DataSourceInfo dataSourceInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(dataSourceInfo.getOprUserId())) {
            dataSourceInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            dataSourceInfo.setOprTime(new Date());
        }
        if (ObjectUtils.isNullObj(dataSourceInfo.getAliveFlag())) {
            dataSourceInfo.setAliveFlag(DataCenterCollections.YesOrNo.NO.value);
        }
        return dataSourceMapper.updateByPrimaryKeySelective(dataSourceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteDataSourceById(DataSourceInfo dataSourceInfo) {
        return dataSourceMapper.deleteByPrimaryKey(dataSourceInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public DataSourceInfo queryDataSourceById(DataSourceInfo dataSourceInfo) {
        return dataSourceMapper.selectByPrimaryKey(dataSourceInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<DataSourceInfo> queryDataSourceList(DataSourceInfo dataSourceInfo) {
        //条件
        Condition condition = queryCondition(dataSourceInfo);
        //查询
        List<DataSourceInfo> list = dataSourceMapper.selectByCondition(condition);
        return list;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<DataSourceInfo> queryPageDataSourceList(DataSourceInfo dataSourceInfo) {
        //条件
        Condition condition = queryCondition(dataSourceInfo);
        //分页
        PageMethod.startPage(dataSourceInfo.getStart(), dataSourceInfo.getPageSize());
        //查询
        List<DataSourceInfo> list = dataSourceMapper.selectByCondition(condition);
        return AppUtil.returnPage(list);
    }

    private Condition queryCondition(DataSourceInfo dataSourceInfo) {
        //条件
        Condition condition = new Condition(DataSourceInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        // 设置默认值
//        if (ObjectUtils.isNullObj(dataSourceInfo.getAliveFlag())) {
//            dataSourceInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
//        }
        Long loginId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(dataSourceInfo.getAddUserId()) && !StringUtil.equals(1,loginId)) {
            dataSourceInfo.setAddUserId(loginId);
        }

        // 设置查询条件
        if (!ObjectUtils.isNullObj(dataSourceInfo.getAliveFlag())) {
            criteria.andEqualTo(DataSourceInfo.key.aliveFlag.toString(), dataSourceInfo.getAliveFlag());
        }
        if (!ObjectUtils.isNullObj(dataSourceInfo.getAddUserId())) {
            criteria.andEqualTo(DataSourceInfo.key.addUserId.toString(), dataSourceInfo.getAddUserId());
        }
        if (StringUtil.isNotBlank(dataSourceInfo.getDataSourceName())) {
            criteria.andLike(DataSourceInfo.key.dataSourceName.toString(), DataCenterCollections.PERCENT_SIGN + dataSourceInfo.getDataSourceName() + DataCenterCollections.PERCENT_SIGN);
        }
        if (StringUtil.isNotBlank(dataSourceInfo.getDataSourceCode())) {
            criteria.andLike(DataSourceInfo.key.dataSourceCode.toString(), DataCenterCollections.PERCENT_SIGN + dataSourceInfo.getDataSourceCode() + DataCenterCollections.PERCENT_SIGN);
        }
        //排序
        condition.setOrderByClause("ADD_TIME DESC");
        return condition;
    }
}
