package cn.paohe.sys.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.sys.RoleUserEntity;
import cn.paohe.entity.vo.sys.UserRoleVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.exp.SeedException;
import cn.paohe.sys.dao.RoleUserMapper;
import cn.paohe.sys.service.IRoleUserService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.CollectionUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

/**
 * TODO  角色用户接口实现
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020年10月22日 上午10:05:33
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Transactional(rollbackFor = Exception.class)
@Service("roleUserService")
@SuppressWarnings("all")
public class RoleUserServiceImpl implements IRoleUserService {

    private final static Logger log = LoggerFactory.getLogger(RoleUserServiceImpl.class);

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private Environment env;

    /**
     * @Fields SSXT : 系统编号
     */
    private static String SSXT = null;

    /**
     * TODO 获取角色下的所有用户
     *
     * @param user
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:01:15
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findUserByRole(UserRoleVo user) {
        try {
            //默认查询可用的用户
            if (ObjectUtils.isNullObj(user.getAliveFlag())) {
                user.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            }
            //默认查询可用的角色
            if (user.getRoleEnable() == null) {
                user.setRoleEnable(DataCenterCollections.YesOrNo.YES.value);
            }
            List<UserRoleVo> userRoleList = roleUserMapper.findUserByRole(user);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取角色下的所有用户", userRoleList);
        } catch (Exception e) {
            log.error("报错-位置：[RoleUserServiceImpl->findUserByRole]" + e);
            throw new SeedException("报错-位置：[RoleUserServiceImpl->findUserByRole]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 根据用户获取角色
     *
     * @param user
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:05:39
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findRoleByUser(UserRoleVo user) {
        try {
            //默认查询可用的用户
            if (ObjectUtils.isNullObj(user.getAliveFlag())) {
                user.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            }
            //默认查询可用的角色
            if (user.getRoleEnable() == null) {
                user.setRoleEnable(DataCenterCollections.YesOrNo.YES.value);
            }
            if (ObjectUtils.isNullObj(user.getUserId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户id不能为空");
            }
            List<UserRoleVo> userRoleList = roleUserMapper.findUserByRole(user);
            //判断是否存在数据
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取角色下的所有用户", userRoleList);
        } catch (Exception e) {
            log.error("报错-位置：[RoleUserServiceImpl->findRoleByUser]" + e);
            throw new SeedException("报错-位置：[RoleUserServiceImpl->findRoleByUser]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 获取用户所有角色
     *
     * @param user
     * @return List<UserRoleVo>
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 上午11:03:09
     */
    @TargetDataSource(value = "center-r")
    public List<UserRoleVo> findRoleByUserList(UserRoleVo user) {
        //默认查询可用的用户
        if (ObjectUtils.isNullObj(user.getAliveFlag())) {
            user.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        //默认查询可用的角色
        if (user.getRoleEnable() == null) {
            user.setRoleEnable(DataCenterCollections.YesOrNo.YES.value);
        }
        if (ObjectUtils.isNullObj(user.getUserId())) {
            return null;
        }
        List<UserRoleVo> userRoleList = roleUserMapper.findUserByRole(user);
        return userRoleList;
    }

    /**
     * TODO 给角色分配用户
     *
     * @param roleVo
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:18:47
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult alterRoleToUser(UserRoleVo roleVo) {
        try {
            /*判断角色Id是否为空*/
            if (ObjectUtils.isNullObj(roleVo.getRoleId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "角色id不能为空");
            }
            /*判断角色名称是否为空*/
            if (StringUtil.isEmpty(roleVo.getRoleName())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "角色名称不能为空");
            }
            if (CollectionUtil.isEmpty(roleVo.getUserIds())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "userIds不能为空");
            }
            //删除角色下的所有用户
            //组装条件查询
            Condition cond = new Condition(RoleUserEntity.class);
            Criteria criteria = cond.or();
            criteria.andEqualTo(RoleUserEntity.key.roleId.toString(), roleVo.getRoleId());
            int count = roleUserMapper.deleteByCondition(cond);
            if (CollectionUtil.isNotEmpty(roleVo.getUserIds())) {
                for (Long userId : roleVo.getUserIds()) {
                    RoleUserEntity userVo = new RoleUserEntity();
                    userVo.setAddTime(new Date());
//                    userVo.setRoleUserId(String.valueOf(SnowFlakeIds.get().nextId()));
                    userVo.setUserId(userId);
                    userVo.setRoleId(roleVo.getRoleId());
                    userVo.setRoleName(roleVo.getRoleName());
                    userVo.setOprUserId(UserUtil.getUserEntity().getUserId());
                    roleUserMapper.insert(userVo);
                }
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "给角色分配用户成功", roleVo);
            } else {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", roleVo);
            }
        } catch (Exception e) {
            log.error("报错-位置：[RoleUserServiceImpl->alterRoleToUser]" + e);
            throw new SeedException("报错-位置：[RoleUserServiceImpl->alterRoleToUser]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 给角色分配用户
     *
     * @param roleVo
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:18:47
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult alterUserToRole(UserRoleVo roleVo) {
        try {
            /*判断角色Id是否为空*/
            if (ObjectUtils.isNullObj(roleVo.getRoleId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "用户id不能为空");
            }
            /*判断角色名称是否为空*/
			/*if(CollectionUtil.isEmpty(roleVo.getRoleNames())){
				return new AjaxResult(RestHttpStatus.AJAX_CODE_NO.value, "角色名称数组roleNames不能为空");
			}
			if(CollectionUtil.isEmpty(roleVo.getRoleIds())){
				return new AjaxResult(RestHttpStatus.AJAX_CODE_NO.value, "角色id数组roleIds不能为空");
			}*/
            //删除角色下的所有用户
            //组装条件查询
            Condition cond = new Condition(RoleUserEntity.class);
            Criteria criteria = cond.or();
            criteria.andEqualTo(RoleUserEntity.key.userId.toString(), roleVo.getUserId());
            int count = roleUserMapper.deleteByCondition(cond);
            if (CollectionUtil.isNotEmpty(roleVo.getRoleNames()) && CollectionUtil.isNotEmpty(roleVo.getRoleIds())) {
                for (int i = 0; i < roleVo.getRoleIds().length; i++) {
                    RoleUserEntity userVo = new RoleUserEntity();
                    userVo.setAddTime(new Date());
//                    userVo.setRoleUserId(String.valueOf(SnowFlakeIds.get().nextId()));
                    userVo.setUserId(roleVo.getUserId());
                    userVo.setRoleId(roleVo.getRoleIds()[i]);
                    if (i < roleVo.getRoleNames().length) {
                        userVo.setRoleName(roleVo.getRoleNames()[i]);
                    }
                    userVo.setOprUserId(UserUtil.getUserEntity().getUserId());
                    roleUserMapper.insert(userVo);
                }
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "给用户分配角色成功", roleVo);
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", roleVo);
        } catch (Exception e) {
            log.error("报错-位置：[RoleUserServiceImpl->alterRoleToUser]" + e);
            throw new SeedException("报错-位置：[RoleUserServiceImpl->alterRoleToUser]" + e.getMessage(), e);
        }
    }

}
