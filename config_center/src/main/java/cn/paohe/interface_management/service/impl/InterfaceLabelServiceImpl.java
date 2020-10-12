package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceLabelInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.dao.IInterfaceLabelMapper;
import cn.paohe.interface_management.service.IInterfaceLabelService;
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
 * @Date 2020/9/16 9:16
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class InterfaceLabelServiceImpl implements IInterfaceLabelService {


    @Resource
    private IInterfaceLabelMapper iInterfaceLabelMapper;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertInterfaceLabel(InterfaceLabelInfo interfaceLabelInfo) {
        // 新增人和时间
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getAddUserId())) {
            interfaceLabelInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            interfaceLabelInfo.setAddTime(new Date());
        }
        // 默认可用
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getAliveFlag())) {
            interfaceLabelInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        return iInterfaceLabelMapper.insert(interfaceLabelInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateInterfaceLabelById(InterfaceLabelInfo interfaceLabelInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getOprUserId())) {
            interfaceLabelInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            interfaceLabelInfo.setOprTime(new Date());
        }
        return iInterfaceLabelMapper.updateByPrimaryKeySelective(interfaceLabelInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableInterfaceLabelById(InterfaceLabelInfo interfaceLabelInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getOprUserId())) {
            interfaceLabelInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            interfaceLabelInfo.setOprTime(new Date());
        }
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getAliveFlag())) {
            interfaceLabelInfo.setAliveFlag(DataCenterCollections.YesOrNo.NO.value);
        }
        return iInterfaceLabelMapper.updateByPrimaryKeySelective(interfaceLabelInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteInterfaceLabelById(InterfaceLabelInfo interfaceLabelInfo) {
        return iInterfaceLabelMapper.deleteByPrimaryKey(interfaceLabelInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public InterfaceLabelInfoVo queryInterfaceLabelById(InterfaceLabelInfoVo interfaceLabelInfoVo) {
        return iInterfaceLabelMapper.queryLabelInfoById(interfaceLabelInfoVo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<InterfaceLabelInfoVo> queryInterfaceLabelList(InterfaceLabelInfoVo interfaceLabelInfoVo) {
        // 设置默认值
        if (ObjectUtils.isNullObj(interfaceLabelInfoVo.getAliveFlag())) {
            interfaceLabelInfoVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        //查询
        List<InterfaceLabelInfoVo> list = iInterfaceLabelMapper.queryLabelInfoList(interfaceLabelInfoVo);
        return list;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<InterfaceLabelInfoVo> queryPageInterfaceLabels(InterfaceLabelInfoVo interfaceLabelInfoVo) {
        //分页
        PageMethod.startPage(interfaceLabelInfoVo.getStart(), interfaceLabelInfoVo.getPageSize());
        //查询
        List<InterfaceLabelInfoVo> list = iInterfaceLabelMapper.queryLabelInfoList(interfaceLabelInfoVo);
        return AppUtil.returnPage(list);
    }

    private Condition queryCondition(InterfaceLabelInfo interfaceLabelInfo) {
        //条件
        Condition condition = new Condition(InterfaceLabelInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        Long loginId = UserUtil.getUserEntity().getUserId();
        if (ObjectUtils.isNullObj(interfaceLabelInfo.getAddUserId()) && !StringUtil.equals(1,loginId)) {
            interfaceLabelInfo.setAddUserId(loginId);
        }

        // 设置查询条件
        if (!ObjectUtils.isNullObj(interfaceLabelInfo.getAliveFlag())) {
            criteria.andEqualTo(InterfaceLabelInfo.key.aliveFlag.toString(), interfaceLabelInfo.getAliveFlag());
        }
        if (!ObjectUtils.isNullObj(interfaceLabelInfo.getAddUserId())) {
            criteria.andEqualTo(InterfaceLabelInfo.key.addUserId.toString(), interfaceLabelInfo.getAddUserId());
        }
        if (StringUtil.isNotBlank(interfaceLabelInfo.getLabelName())) {
            criteria.andLike(InterfaceLabelInfo.key.labelName.toString(), interfaceLabelInfo.getLabelName());
        }
        if (!ObjectUtils.isNullObj(interfaceLabelInfo.getTypeId())) {
            criteria.andEqualTo(InterfaceLabelInfo.key.typeId.toString(), interfaceLabelInfo.getTypeId());
        }
        //排序
        condition.setOrderByClause("ADD_TIME DESC");
        return condition;
    }
}
