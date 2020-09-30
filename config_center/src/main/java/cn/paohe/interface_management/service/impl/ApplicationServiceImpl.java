package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.ApplicationInfo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.dao.IApplicationMapper;
import cn.paohe.interface_management.service.IApplicationService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.SnowFlakeIds;
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
 * @Date 2020/9/16 9:13
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Resource
    private IApplicationMapper applicationMapper;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertApp(ApplicationInfo applicationInfo) {
        // 新增人和时间
        if (ObjectUtils.isNullObj(applicationInfo.getAddUserId())) {
            applicationInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            applicationInfo.setAddTime(new Date());
        }
        //默认可用
        if(ObjectUtils.isNullObj(applicationInfo.getAliveFlag())){
            applicationInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        // 设置应用编号
        String code = SnowFlakeIds.get().nextId() + "";
        if(StringUtil.isBlank(applicationInfo.getApplicationCode())){
            applicationInfo.setApplicationCode(code);
        }
        // 设置上下文根
        if(StringUtil.isBlank(applicationInfo.getContextName())){
            applicationInfo.setContextName("/" + code + "/**");
        }
        return applicationMapper.insert(applicationInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAppById(ApplicationInfo applicationInfo) {
        if(ObjectUtils.isNullObj(applicationInfo.getOprUserId())){
            applicationInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            applicationInfo.setOprTime(new Date());
        }
        return applicationMapper.updateByPrimaryKeySelective(applicationInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableAppById(ApplicationInfo applicationInfo) {
        if(ObjectUtils.isNullObj(applicationInfo.getOprUserId())){
            applicationInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            applicationInfo.setOprTime(new Date());
        }
        if (ObjectUtils.isNullObj(applicationInfo.getAliveFlag())) {
            applicationInfo.setAliveFlag(DataCenterCollections.YesOrNo.NO.value);
        }
        return applicationMapper.updateByPrimaryKeySelective(applicationInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAppById(ApplicationInfo applicationInfo) {
        return applicationMapper.deleteByPrimaryKey(applicationInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public ApplicationInfo queryAppById(ApplicationInfo applicationInfo) {
        return applicationMapper.selectByPrimaryKey(applicationInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<ApplicationInfo> queryAppList(ApplicationInfo applicationInfo) {
        //条件
        Condition condition = queryCondition(applicationInfo);
        //查询
        List<ApplicationInfo> list = applicationMapper.selectByCondition(condition);
        return list;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<ApplicationInfo> queryPageAppList(ApplicationInfo applicationInfo) {
        //条件
        Condition condition = queryCondition(applicationInfo);
        //分页
        PageMethod.startPage(applicationInfo.getStart(), applicationInfo.getPageSize());
        //查询
        List<ApplicationInfo> list = applicationMapper.selectByCondition(condition);
        return AppUtil.returnPage(list);
    }

    private Condition queryCondition(ApplicationInfo applicationInfo) {
        //条件
        Condition condition = new Condition(ApplicationInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        // 设置默认值
//        if (ObjectUtils.isNullObj(applicationInfo.getAliveFlag())) {
//            applicationInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
//        }
        Long loginId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(applicationInfo.getAddUserId()) && StringUtil.equals(1,loginId)) {
            applicationInfo.setAddUserId(loginId);
        }

        // 设置查询条件
        if (!ObjectUtils.isNullObj(applicationInfo.getAliveFlag())) {
            criteria.andEqualTo(ApplicationInfo.key.aliveFlag.toString(), applicationInfo.getAliveFlag());
        }
        if (!ObjectUtils.isNullObj(applicationInfo.getAddUserId())) {
            criteria.andEqualTo(ApplicationInfo.key.addUserId.toString(), applicationInfo.getAddUserId());
        }
        if (StringUtil.isNotBlank(applicationInfo.getApplicationName())) {
            criteria.andLike(ApplicationInfo.key.applicationName.toString(), applicationInfo.getApplicationName());
        }
        if (StringUtil.isNotBlank(applicationInfo.getApplicationCode())) {
            criteria.andEqualTo(ApplicationInfo.key.applicationCode.toString(), applicationInfo.getApplicationCode());
        }
        //排序
        condition.setOrderByClause("ADD_TIME DESC");
        return condition;
    }
}
