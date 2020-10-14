package cn.paohe.user.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.sys.RoleInfoEntity;
import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.entity.vo.sys.UserRoleVo;
import cn.paohe.entity.vo.user.UserEntityVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.sys.dao.RoleInfoMapper;
import cn.paohe.sys.service.IRoleUserService;
import cn.paohe.user.dao.IDeveloperMapper;
import cn.paohe.user.dao.UserEntityMapper;
import cn.paohe.user.service.IDeveloperService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.Md5;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/22 11:43
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Service
public class DeveloperServiceImpl implements IDeveloperService {
    @Resource
    private IDeveloperMapper developerMapper;
    @Autowired
    private IRoleUserService roleUserService;
    @Resource
    private RoleInfoMapper roleInfoMapper;
    @Resource
    private UserEntityMapper userEntityMapper;

    @TargetDataSource(value = "center-r")
    @Override
    public AjaxResult queryDeveloperList(UserEntityVo userEntityVo) {
        // 设置默认值
        if (ObjectUtils.isNullObj(userEntityVo.getAliveFlag())) {
            userEntityVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        if (ObjectUtils.isNullObj(userEntityVo.getParentUserId())) {
            userEntityVo.setParentUserId(UserUtil.getUserEntity().getUserId());
        }
        // 默认只查开发者的 id 为 2
        if(ObjectUtils.isNullObj(userEntityVo.getRoleId())){
            userEntityVo.setRoleId(Long.valueOf(2));
        }
        //查询
        List<UserEntity> list = developerMapper.queryUserList(userEntityVo);
        return new AjaxResult(list);
    }

    @TargetDataSource(value = "center-r")
    @Override
    public PageAjax<UserEntity> queryDeveloperPage(UserEntityVo userEntityVo) {
        Long loginUserId = UserUtil.getUserEntity().getUserId();
        //条件
        if (ObjectUtils.isNullObj(userEntityVo.getParentUserId()) && !StringUtil.equals(1,loginUserId)) {
            userEntityVo.setParentUserId(loginUserId);
        }
        // 默认只查开发者的 id 为 2
        if(ObjectUtils.isNullObj(userEntityVo.getRoleId())){
            userEntityVo.setRoleId(Long.valueOf(2));
        }
        //分页
        PageMethod.startPage(userEntityVo.getStart(), userEntityVo.getPageSize());
        //查询
        List<UserEntity> list = developerMapper.queryUserList(userEntityVo);
        return AppUtil.returnPage(list);
    }

    @TargetDataSource(value = "center-w")
    @Override
    public AjaxResult insertDeveloper(UserEntity userEntity) {
        AjaxResult checkAccount = checkAccount(userEntity);
        if (StringUtil.equals(checkAccount.getRetcode(), DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value)) {
            return checkAccount;
        }
        userEntity.setAddTime(new Date());
        if (ObjectUtils.isNullObj(userEntity.getAliveFlag())) {
            userEntity.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        userEntity.setAddUserId(UserUtil.getUserEntity().getUserId());
        userEntity.setParentUserId(UserUtil.getUserEntity().getUserId());
        if (StringUtil.isNotBlank(userEntity.getPassword())) {
            userEntity.setPassword(Md5.getMD5(userEntity.getPassword().getBytes()));
        }
        int i = developerMapper.insert(userEntity);
        if(i > 0){
            // 获取角色信息'
            RoleInfoEntity roleInfoEntity = new RoleInfoEntity();
            roleInfoEntity.setRoleId(Long.valueOf(2));
            RoleInfoEntity roleInfo = roleInfoMapper.selectByPrimaryKey(roleInfoEntity);
            if(!ObjectUtils.isNullObj(roleInfo)){
                // 指定角色 是开发者
                UserRoleVo userRoleVo = new UserRoleVo();
                // 用户ID
                userRoleVo.setUserId(userEntity.getUserId());
                // 角色信息
                List<RoleInfoEntity> roleInfoEntities = new ArrayList<>();
                roleInfoEntities.add(roleInfo);
                userRoleVo.setRoleInfoList(roleInfoEntities);
                roleUserService.alterUserToRole(userRoleVo);
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增开发者成功",userEntity);
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增开发者失败",userEntity);
    }

    private AjaxResult checkAccount(UserEntity userEntity) {
        if (StringUtil.isBlank(userEntity.getAccount())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "账号不能为空。");
        }
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", userEntity.getAccount());
        List<UserEntity> userEntities = userEntityMapper.selectByExample(example);
        if (CollectionUtil.isEmpty(userEntities)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "账号不存在。");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "账号已存在。");
    }

    private Condition queryCondition(UserEntityVo userEntityVo) {
        //条件
        Condition condition = new Condition(UserEntityVo.class);
        Example.Criteria criteria = condition.createCriteria();

        if (ObjectUtils.isNullObj(userEntityVo.getParentUserId())) {
            userEntityVo.setParentUserId(UserUtil.getUserEntity().getUserId());
        }
        // 默认只查开发者的 id 为 2
        if(ObjectUtils.isNullObj(userEntityVo.getRoleId())){
            userEntityVo.setRoleId(Long.valueOf(2));
        }

        // 设置查询条件
        if (!ObjectUtils.isNullObj(userEntityVo.getAliveFlag())) {
            criteria.andEqualTo(UserEntity.key.aliveFlag.toString(), userEntityVo.getAliveFlag());
        }
        if (!ObjectUtils.isNullObj(userEntityVo.getParentUserId())) {
            criteria.andEqualTo(UserEntity.key.parentUserId.toString(), userEntityVo.getParentUserId());
        }
        if (!ObjectUtils.isNullObj(userEntityVo.getRoleId())) {
            criteria.andEqualTo(UserEntityVo.key.roleId.toString(), userEntityVo.getRoleId());
        }
        if (StringUtil.isNotBlank(userEntityVo.getUseName())) {
            criteria.andLike(UserEntity.key.useName.toString(), DataCenterCollections.PERCENT_SIGN + userEntityVo.getUseName() + DataCenterCollections.PERCENT_SIGN);
        }
        if (StringUtil.isNotBlank(userEntityVo.getUserName())) {
            criteria.andLike(UserEntity.key.userName.toString(), DataCenterCollections.PERCENT_SIGN + userEntityVo.getUserName() + DataCenterCollections.PERCENT_SIGN);
        }
        //排序
        condition.setOrderByClause("ADD_TIME DESC");
        return condition;
    }
}
