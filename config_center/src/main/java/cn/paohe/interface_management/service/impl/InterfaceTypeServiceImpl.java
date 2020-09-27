package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.BeanCopy;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.DataSourceInfo;
import cn.paohe.entity.model.InterfaceMag.InterfaceLabelInfo;
import cn.paohe.entity.model.InterfaceMag.InterfaceTypeInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceLabelInfoVo;
import cn.paohe.entity.vo.interfaceMag.InterfaceTypeInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.dao.IInterfaceTypeMapper;
import cn.paohe.interface_management.service.IInterfaceLabelService;
import cn.paohe.interface_management.service.IInterfaceTypeService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.PageAjax;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date 2020/9/16 9:17
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class InterfaceTypeServiceImpl implements IInterfaceTypeService {

    @Resource
    private IInterfaceTypeMapper iInterfaceTypeMapper;
    @Autowired
    private IInterfaceLabelService iInterfaceLabelService;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertInterfaceType(InterfaceTypeInfo interfaceTypeInfo) {
        // 新增人和时间
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getAddUserId())) {
            interfaceTypeInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            interfaceTypeInfo.setAddTime(new Date());
        }
        // 默认可用
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getAliveFlag())) {
            interfaceTypeInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        return iInterfaceTypeMapper.insert(interfaceTypeInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getOprUserId())) {
            interfaceTypeInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            interfaceTypeInfo.setOprTime(new Date());
        }
        return iInterfaceTypeMapper.updateByPrimaryKeySelective(interfaceTypeInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getOprUserId())) {
            interfaceTypeInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            interfaceTypeInfo.setOprTime(new Date());
        }
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getAliveFlag())) {
            interfaceTypeInfo.setAliveFlag(DataCenterCollections.YesOrNo.NO.value);
        }
        return iInterfaceTypeMapper.updateByPrimaryKeySelective(interfaceTypeInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo) {
        return iInterfaceTypeMapper.deleteByPrimaryKey(interfaceTypeInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public InterfaceTypeInfo queryInterfaceTypeById(InterfaceTypeInfo interfaceTypeInfo) {
        return iInterfaceTypeMapper.selectByPrimaryKey(interfaceTypeInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<InterfaceTypeInfoVo> queryInterfaceTypeList(InterfaceTypeInfo interfaceTypeInfo) {
        //条件
        Condition condition = queryCondition(interfaceTypeInfo);
        //查询
        List<InterfaceTypeInfo> list = iInterfaceTypeMapper.selectByCondition(condition);
        List<InterfaceTypeInfoVo> interfaceTypeInfoVos = BeanCopy.listCopyASM(list,InterfaceTypeInfoVo.class);
        for (InterfaceTypeInfoVo interfaceTypeInfoVo : interfaceTypeInfoVos) {
            InterfaceLabelInfoVo interfaceLabelInfo = new InterfaceLabelInfoVo();
            interfaceLabelInfo.setTypeId(interfaceTypeInfoVo.getTypeId());
            List<InterfaceLabelInfoVo> interfaceLabelInfos = iInterfaceLabelService.queryInterfaceLabelList(interfaceLabelInfo);
            interfaceTypeInfoVo.setInterfaceLabelInfoVos(interfaceLabelInfos);
        }
        return interfaceTypeInfoVos;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<InterfaceTypeInfoVo> queryPageInterfaceTypes(InterfaceTypeInfo interfaceTypeInfo) {
        //条件
        Condition condition = queryCondition(interfaceTypeInfo);
        //分页
        PageMethod.startPage(interfaceTypeInfo.getStart(), interfaceTypeInfo.getPageSize());
        //查询
        List<InterfaceTypeInfo> list = iInterfaceTypeMapper.selectByCondition(condition);
        PageAjax<InterfaceTypeInfo> interfaceTypeInfoPageAjax = AppUtil.returnPage(list);
        PageAjax<InterfaceTypeInfoVo> result = BeanCopy.objectCopyASM(interfaceTypeInfoPageAjax,PageAjax.class);
        List<InterfaceTypeInfoVo> interfaceTypeInfoVos = BeanCopy.listCopyASM(list,InterfaceTypeInfoVo.class);
        for (InterfaceTypeInfoVo interfaceTypeInfoVo : interfaceTypeInfoVos) {
            InterfaceLabelInfoVo interfaceLabelInfo = new InterfaceLabelInfoVo();
            interfaceLabelInfo.setTypeId(interfaceTypeInfoVo.getTypeId());
            List<InterfaceLabelInfoVo> interfaceLabelInfos = iInterfaceLabelService.queryInterfaceLabelList(interfaceLabelInfo);
            interfaceTypeInfoVo.setInterfaceLabelInfoVos(interfaceLabelInfos);
        }
        result.setRows(interfaceTypeInfoVos);
        return result;
    }

    private Condition queryCondition(InterfaceTypeInfo interfaceTypeInfo) {
        //条件
        Condition condition = new Condition(InterfaceTypeInfo.class);
        Example.Criteria criteria = condition.createCriteria();
        // 设置默认值
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getAliveFlag())) {
            interfaceTypeInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        if (ObjectUtils.isNullObj(interfaceTypeInfo.getAddUserId())) {
            interfaceTypeInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
        }

        // 设置查询条件
        if (!ObjectUtils.isNullObj(interfaceTypeInfo.getAliveFlag())) {
            criteria.andEqualTo(InterfaceTypeInfo.key.aliveFlag.toString(), interfaceTypeInfo.getAliveFlag());
        }
        if (!ObjectUtils.isNullObj(interfaceTypeInfo.getAddUserId())) {
            criteria.andEqualTo(InterfaceTypeInfo.key.addUserId.toString(), interfaceTypeInfo.getAddUserId());
        }
        if (StringUtil.isNotBlank(interfaceTypeInfo.getTypeName())) {
            criteria.andLike(InterfaceTypeInfo.key.typeName.toString(), interfaceTypeInfo.getTypeName());
        }
        //排序
        condition.setOrderByClause("ADD_TIME DESC");
        return condition;
    }
}
