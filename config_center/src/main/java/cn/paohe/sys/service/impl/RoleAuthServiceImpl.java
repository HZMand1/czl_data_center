package cn.paohe.sys.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.sys.RoleAuthEntity;
import cn.paohe.entity.model.sys.RoleUserEntity;
import cn.paohe.entity.vo.sys.RoleMenuAuthVo;
import cn.paohe.entity.vo.sys.UserRoleVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.exp.SeedException;
import cn.paohe.sys.dao.RoleAuthMapper;
import cn.paohe.sys.service.IRoleAuthService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * TODO  权限接口实现类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020年10月22日 上午9:40:29
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Transactional(rollbackFor = Exception.class)
@Service("roleAuthService")
@SuppressWarnings("all")
public class RoleAuthServiceImpl implements IRoleAuthService {

    private final static Logger log = LoggerFactory.getLogger(RoleAuthServiceImpl.class);

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    @Autowired
    private IRoleUserService roleUserService;

    @Autowired
    private Environment env;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Fields SSXT : 系统编号
     */
    private static String SSXT = null;

    /**
     * TODO 获取角色下的所有权限
     *
     * @param Auth
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:01:15
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findAuthByRole(RoleMenuAuthVo authVo) {
        try {
            //默认查询可用的角色
            if (ObjectUtils.isNullObj(authVo.getRoleId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "roleId不能为空", authVo);
            }
            List<RoleMenuAuthVo> authList = findUserOrRoleMenuAuth(authVo);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取角色下的所有权限", authList);
        } catch (Exception e) {
            log.error("报错-位置：[RoleAuthServiceImpl->findAuthByRole]" + e);
            throw new SeedException("报错-位置：[RoleAuthServiceImpl->findAuthByRole]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 根据条件获取权限
     *
     * @param authVo
     * @return List<RoleMenuAuthVo>
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月24日 上午11:39:43
     */
    @TargetDataSource(value = "center-r")
    private List<RoleMenuAuthVo> findUserOrRoleMenuAuth(RoleMenuAuthVo authVo) {
        //默认查询可用的菜单功能
        if (ObjectUtils.isNullObj(authVo.getAliveFlag())) {
            authVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
        }
        List<RoleMenuAuthVo> authList = roleAuthMapper.findUserOrRoleMenuAuth(authVo);
        return authList;
    }

    /**
     * TODO 获取用户的所有权限
     *
     * @param Auth
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:05:39
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findAuthByUser(RoleMenuAuthVo authVo) {
        try {
            //默认查询可用的菜单功能
            if (ObjectUtils.isNullObj(authVo.getAliveFlag())) {
                authVo.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            }
            //默认查询可用的角色
            if (ObjectUtils.isNullObj(authVo.getUserId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "userId不能为空", authVo);
            }
            //通过用户获取权限
            List<RoleMenuAuthVo> authList = roleAuthMapper.findUserOrRoleMenuAuth(authVo);
            //然后在查询该用户的所有角色
            UserRoleVo roleVo = new UserRoleVo();
            roleVo.setUserId(authVo.getUserId());
            List<UserRoleVo> userRoleList = roleUserService.findRoleByUserList(roleVo);
            if (CollectionUtil.isEmpty(authList)) {
                authList = new ArrayList<RoleMenuAuthVo>();
            }
            if (CollectionUtil.isNotEmpty(userRoleList)) {
                //遍历角色下的所有权限
                for (UserRoleVo userRoleVo : userRoleList) {
                    RoleMenuAuthVo menuAuthVo = new RoleMenuAuthVo();
                    menuAuthVo.setRoleId(userRoleVo.getRoleId());
                    List<RoleMenuAuthVo> roleAuthList = findUserOrRoleMenuAuth(menuAuthVo);
                    if (CollectionUtil.isNotEmpty(roleAuthList)) {
                        authList.addAll(roleAuthList);
                    }
                }
            }
            Map<Long, RoleMenuAuthVo> map = new HashMap<Long, RoleMenuAuthVo>();
            for (RoleMenuAuthVo vo : authList) {
                map.put(vo.getMenuId(), vo);
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取用户的所有权限", map.values());
        } catch (Exception e) {
            log.error("报错-位置：[RoleAuthServiceImpl->findAuthByUser]" + e);
            throw new SeedException("报错-位置：[RoleAuthServiceImpl->findAuthByUser]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 给角色分配权限
     *
     * @param authVo
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:18:47
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult saveAuthToRole(RoleMenuAuthVo authVo) {
        try {
            authVo.setOprUserId(UserUtil.getUserEntity().getUserId());
            if (ObjectUtils.isNullObj(authVo.getRoleId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "roleId不能为空", authVo);
            }
            //先删除已经分配过得权限
            //组装条件查询
            Condition cond = new Condition(RoleUserEntity.class);
            Criteria criteria = cond.or();
            criteria.andEqualTo(RoleUserEntity.key.roleId.toString(), authVo.getRoleId());
            //执行删除动作
            roleAuthMapper.deleteByCondition(cond);
            if (CollectionUtil.isEmpty(authVo.getMenuIds())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", authVo);
            }

            for (Long menuId : authVo.getMenuIds()) {
                RoleAuthEntity entity = new RoleAuthEntity();
                entity.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
                entity.setRoleId(authVo.getRoleId());
                entity.setMenuId(menuId);
                entity.setAddUserId(UserUtil.getUserEntity().getUserId());
                entity.setAddTime(new Date());
                //当前操作人
                entity.setOprUserId(UserUtil.getUserEntity().getUserId());
                entity.setOprTime(new Date());
                roleAuthMapper.insert(entity);
            }

            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", authVo);
        } catch (Exception e) {
            log.error("报错-位置：[RoleAuthServiceImpl->saveAuthToRole]" + e);
            throw new SeedException("报错-位置：[RoleAuthServiceImpl->saveAuthToRole]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 修改用户权限
     *
     * @param Auth
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:05
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult saveAuthToUser(RoleMenuAuthVo authVo) {
        try {
            authVo.setOprUserId(UserUtil.getUserEntity().getUserId());
            if (ObjectUtils.isNullObj(authVo.getUserId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "userId不能为空", authVo);
            }
            //先删除已经分配过得权限
            //组装条件查询
            Condition cond = new Condition(RoleUserEntity.class);
            Criteria criteria = cond.or();
            criteria.andEqualTo(RoleUserEntity.key.userId.toString(), authVo.getUserId());
            //执行删除动作
            roleAuthMapper.deleteByCondition(cond);
            if (CollectionUtil.isEmpty(authVo.getMenuIds())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", authVo);
            }
            for (Long menuId : authVo.getMenuIds()) {
                RoleAuthEntity entity = new RoleAuthEntity();
                entity.setUserId(authVo.getUserId());
                entity.setMenuId(menuId);
                entity.setAddTime(new Date());
                //当前操作人
                entity.setOprUserId(UserUtil.getUserEntity().getUserId());
                roleAuthMapper.insert(entity);
            }
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "操作成功", authVo);
        } catch (Exception e) {
            log.error("报错-位置：[RoleAuthServiceImpl->saveAuthToUser]" + e);
            throw new SeedException("报错-位置：[RoleAuthServiceImpl->saveAuthToUser]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 查询当前用户权限树
     *
     * @param
     * @return
     * @throws
     * @author 黄芝民
     * @date 2020/11/12 11:14
     */
    @Override
    public List<RoleMenuAuthVo> findAuthTree(RoleMenuAuthVo authVo) {
        AjaxResult ajaxResult = findAuthByUser(authVo);
        Collection<RoleMenuAuthVo> authVos = (Collection<RoleMenuAuthVo>) ajaxResult.getData();
        //得到所有父级
        List<RoleMenuAuthVo> parentList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(authVos) && !ObjectUtils.isNullObj(authVo.getMenuId())) {
            for (RoleMenuAuthVo vo : authVos) {
                if (StringUtil.isNotBlank(vo.getParentId()) && StringUtil.equals(vo.getParentId(),authVo.getMenuId())) {
                    parentList.add(vo);
                }
            }
            return parentList;
        }
        if (authVos != null && authVos.size() > 0) {
            for (RoleMenuAuthVo vo : authVos) {
                if (StringUtil.equals(vo.getParentId(),0)) {
                    parentList.add(vo);
                }
            }
        }
        //调用方法设置所有子级
        if (parentList != null && parentList.size() > 0) {
            getSub(authVos, parentList);
        }
        return parentList;
    }

    /**
     * TODO 获取所有子级
     *
     * @param entityList 源
     * @param parentList 父
     * @return
     * @throws
     * @author 黄芝民
     * @date 2020/11/11 11:17
     */
    private void getSub(Collection<RoleMenuAuthVo> entityList, List<RoleMenuAuthVo> parentList) {
        for (RoleMenuAuthVo entity : parentList) {
            List<RoleMenuAuthVo> list = new ArrayList<>();
            for (RoleMenuAuthVo entity2 : entityList) {
                if (entity.getMenuId().equals(entity2.getParentId())) {
                    list.add(entity2);
                }
                if (list != null) {
                    getSub(entityList, list);
                }
            }
            entity.setChildren(list);
        }
    }

}
