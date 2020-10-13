package cn.paohe.interface_management.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.InterfaceMag.InterfaceInfo;
import cn.paohe.entity.vo.interfaceMag.InterfaceInfoVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.interface_management.dao.IInterfaceMapper;
import cn.paohe.interface_management.service.IInterfaceService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.SnowFlakeIds;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.PageAjax;
import com.github.pagehelper.page.PageMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class InterfaceServiceImpl implements IInterfaceService {

    @Resource
    private IInterfaceMapper iInterfaceMapper;

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertInterface(InterfaceInfo interfaceInfo) {
        // 新增人和时间
        if (ObjectUtils.isNullObj(interfaceInfo.getAddUserId())) {
            interfaceInfo.setAddUserId(UserUtil.getUserEntity().getUserId());
            interfaceInfo.setAddTime(new Date());
        }
        // 默认可用
        if (ObjectUtils.isNullObj(interfaceInfo.getAliveFlag())) {
            interfaceInfo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        if (StringUtil.isBlank(interfaceInfo.getSecretKey())) {
            // 生成默认密钥
            interfaceInfo.setSecretKey("I" + SnowFlakeIds.get().nextId());
        }
        return iInterfaceMapper.insert(interfaceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateInterfaceById(InterfaceInfo interfaceInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(interfaceInfo.getOprUserId())) {
            interfaceInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            interfaceInfo.setOprTime(new Date());
        }
        return iInterfaceMapper.updateByPrimaryKey(interfaceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateInterfaceByCondition(InterfaceInfo param, InterfaceInfo condition) {
        int count = iInterfaceMapper.updateByConditionSelective(param, condition);
        return count;
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableInterfaceById(InterfaceInfo interfaceInfo) {
        // 修改人和时间
        if (ObjectUtils.isNullObj(interfaceInfo.getOprUserId())) {
            interfaceInfo.setOprUserId(UserUtil.getUserEntity().getUserId());
            interfaceInfo.setOprTime(new Date());
        }
        if (ObjectUtils.isNullObj(interfaceInfo.getAliveFlag())) {
            interfaceInfo.setAliveFlag(DataCenterCollections.YesOrNo.NO.value);
        }
        return iInterfaceMapper.updateByPrimaryKeySelective(interfaceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteInterfaceById(InterfaceInfo interfaceInfo) {
        return iInterfaceMapper.deleteByPrimaryKey(interfaceInfo);
    }

    @TargetDataSource(value = "center-w")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteInterfaceByCondition(InterfaceInfo interfaceInfo) {
        return iInterfaceMapper.delete(interfaceInfo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public InterfaceInfoVo queryInterfaceVoById(InterfaceInfoVo interfaceInfoVo) {
        return iInterfaceMapper.queryInterfaceById(interfaceInfoVo);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public List<InterfaceInfoVo> queryInterfaceVoList(InterfaceInfoVo interfaceInfoVo) {
        //查询
        List<InterfaceInfoVo> list = iInterfaceMapper.queryInterfaceList(interfaceInfoVo);
        return list;
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<InterfaceInfoVo> queryPageInterfaceVoList(InterfaceInfoVo interfaceInfoVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        if(ObjectUtils.isNullObj(interfaceInfoVo.getAddUserId()) && !StringUtil.equals(1,loginUserId)){
            interfaceInfoVo.setAddUserId(loginUserId);
        }
        //分页
        PageMethod.startPage(interfaceInfoVo.getStart(), interfaceInfoVo.getPageSize());
        //查询
        List<InterfaceInfoVo> list = iInterfaceMapper.queryInterfaceList(interfaceInfoVo);
        return AppUtil.returnPage(list);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<InterfaceInfoVo> queryDeveloperPage(InterfaceInfoVo interfaceInfoVo) {
        //分页
        PageMethod.startPage(interfaceInfoVo.getStart(), interfaceInfoVo.getPageSize());
        //查询
        List<InterfaceInfoVo> list = iInterfaceMapper.queryInterfaceList(interfaceInfoVo);
        return AppUtil.returnPage(list);
    }
}
