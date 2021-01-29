package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.DataConnectInfo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.dao.IDataConnectMapper;
import cn.paohe.interface_management.service.IDataConnectService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.page.PageMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/16 12:39
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DataConnectServiceImpl implements IDataConnectService {

    @Resource
    private IDataConnectMapper dataConnectMapper;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertConnect(DataConnectInfo dataConnectInfo) {
        // 新增人和时间
        if (ObjectUtils.isNullObj(dataConnectInfo.getAddUserId())) {
            dataConnectInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            dataConnectInfo.setAddTime(new Date());
        }
        //默认可用
        if (ObjectUtils.isNullObj(dataConnectInfo.getAliveFlag())) {
            dataConnectInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        return dataConnectMapper.insert(dataConnectInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateConnectById(DataConnectInfo dataConnectInfo) {
        if (ObjectUtils.isNullObj(dataConnectInfo.getOprUserId())) {
            dataConnectInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            dataConnectInfo.setOprTime(new Date());
        }
        return dataConnectMapper.updateByPrimaryKeySelective(dataConnectInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteConnectById(DataConnectInfo dataConnectInfo) {
        return dataConnectMapper.deleteByPrimaryKey(dataConnectInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public DataConnectInfo queryConnectById(DataConnectInfo dataConnectInfo) {
        return dataConnectMapper.selectByPrimaryKey(dataConnectInfo);
    }

    @Override
    public DataConnectInfo queryConnectBySourceId(DataConnectInfo dataConnectInfo) {
        if(ObjectUtils.isNullObj(dataConnectInfo.getDataSourceId())){
            return null;
        }
        DataConnectInfo result = dataConnectMapper.selectOne(dataConnectInfo);
        return result;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<DataConnectInfo> queryConnectList(DataConnectInfo dataConnectInfo) {
        //条件
        Condition condition = queryCondition(dataConnectInfo);
        //查询
        List<DataConnectInfo> list = dataConnectMapper.selectByCondition(condition);
        return list;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<DataConnectInfo> queryConnectPage(DataConnectInfo dataConnectInfo) {
        //条件
        Condition condition = queryCondition(dataConnectInfo);
        //分页
        PageMethod.startPage(dataConnectInfo.getStart(), dataConnectInfo.getPageSize());
        //查询
        List<DataConnectInfo> list = dataConnectMapper.selectByCondition(condition);
        return AppUtil.returnPage(list);
    }

    @Override
    public AjaxResult testConnect(DataConnectInfo dataConnectInfo) {
        Connection conn = null;
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataConnectInfo.getConnectDriver());
        dataSource.setUrl(dataConnectInfo.getConnectAddress());
        dataSource.setUsername(dataConnectInfo.getConnectUser());
        dataSource.setPassword(dataConnectInfo.getConnectPassword());
        try {
            conn = dataSource.getConnection(1000);
            if(ObjectUtils.isNullObj(conn)){
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"连接失败");
            }
        } catch (Exception e) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value,"连接异常");
        }finally {
            try {
                if(!ObjectUtils.isNullObj(conn)){
                    conn.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value,"连接成功");
    }

    private Condition queryCondition(DataConnectInfo dataConnectInfo) {
        //条件
        Condition condition = new Condition(DataConnectInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        Long loginId = ObjectUtils.isNullObj(UserUtil.getUserEntity()) ? null : UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(dataConnectInfo.getAddUserId()) && !StringUtil.equals(1, loginId)) {
            dataConnectInfo.setAddUserId(loginId);
        }

        // 设置查询条件
        if (!ObjectUtils.isNullObj(dataConnectInfo.getAliveFlag())) {
            criteria.andEqualTo(DataConnectInfo.key.aliveFlag.name(), dataConnectInfo.getAliveFlag());
        }
        if (!ObjectUtils.isNullObj(dataConnectInfo.getAddUserId())) {
            criteria.andEqualTo(DataConnectInfo.key.addUserId.name(), dataConnectInfo.getAddUserId());
        }
        if (!ObjectUtils.isNullObj(dataConnectInfo.getDataSourceId())) {
            criteria.andEqualTo(DataConnectInfo.key.dataSourceId.name(), dataConnectInfo.getDataSourceId());
        }
        if (StringUtil.isNotBlank(dataConnectInfo.getConnectName())) {
            criteria.andLike(DataConnectInfo.key.connectName.name(), DataCenterCollections.PERCENT_SIGN + dataConnectInfo.getConnectName() + DataCenterCollections.PERCENT_SIGN);
        }
        //排序
        condition.setOrderByClause("ADD_TIME DESC");
        return condition;
    }
}
