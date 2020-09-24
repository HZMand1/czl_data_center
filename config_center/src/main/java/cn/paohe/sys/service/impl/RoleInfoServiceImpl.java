package cn.paohe.sys.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.sys.RoleInfoEntity;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.exp.SeedException;
import cn.paohe.service.base.RedisDataService;
import cn.paohe.sys.dao.RoleInfoMapper;
import cn.paohe.sys.service.IRoleInfoService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.ErrorMessageUtils;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * TODO  角色service实现类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020年10月21日 上午10:20:14
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Transactional(rollbackFor = Exception.class)
@Service("roleInfoService")
@SuppressWarnings("all")
public class RoleInfoServiceImpl implements IRoleInfoService {

    private final static Logger log = LoggerFactory.getLogger(RoleInfoServiceImpl.class);

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private RedisDataService redisDataService;

    @Autowired
    private Environment env;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Fields SSXT : 系统编号
     */
    private static String SSXT = null;

    /**
     * @Fields SEED_ROLE_REDIS : redis当中的key前缀
     */
    private static String SEED_ROLE_REDIS = "seed_role_";

    /**
     * TODO 获取角色列表
     *
     * @param role
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:01:15
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findRoleAllList(RoleInfoEntity role) {
        try {
            Example example = setLikeQueryParam(role);
            List<RoleInfoEntity> configList = roleInfoMapper.selectByExample(example);
            AjaxResult result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取角色列表");
            result.setData(configList);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[RoleInfoServiceImpl->findRoleList]" + e);
            throw new SeedException("报错-位置：[RoleInfoServiceImpl->findRoleList]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 根据条件查询角色分页
     *
     * @param role
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:05:39
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findRoleAllPage(RoleInfoEntity role) {
        try {
            PageHelper.startPage(role.getStart(), role.getPageSize());
            Example example = setLikeQueryParam(role);
            example.orderBy("addTime").desc();
            List<RoleInfoEntity> list = roleInfoMapper.selectByExample(example);
            PageInfo<RoleInfoEntity> pageInfo = new PageInfo<RoleInfoEntity>(list);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "根据条件查询角色分页", pageInfo);
        } catch (Exception e) {
            log.error("报错-位置：[RoleInfoServiceImpl->findRolePage]" + e);
            throw new SeedException("报错-位置：[RoleInfoServiceImpl->findRolePage]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 根据Id获取角色
     *
     * @param id
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:09:06
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findRoleById(Long id) {
        try {
            if (ObjectUtils.isNullObj(id)) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "角色Id不能为空", id);
            }
            AjaxResult result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "根据Id获取角色");
            String roleEnitys = redisDataService.getData(SEED_ROLE_REDIS + "id_" + id);
            RoleInfoEntity roleEnity = null;
            //从缓存中获取
            if (StringUtil.isNotBlank(roleEnitys)) {
                roleEnity = JSON.parseObject(roleEnitys, RoleInfoEntity.class);
                result.setData(roleEnity);
                return result;
            }
            roleEnity = roleInfoMapper.selectByPrimaryKey(id);
            if (roleEnity == null) {
                return result;
            }
            //添加到redis缓存中
            redisDataService.setData(SEED_ROLE_REDIS + "id_" + roleEnity.getRoleId(), JSON.toJSONString(roleEnity));
            result.setData(roleEnity);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[RoleInfoServiceImpl->findRoleById]" + e);
            throw new SeedException("报错-位置：[RoleInfoServiceImpl->findRoleById]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 新增角色
     *
     * @param role
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:18:47
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult insertRole(RoleInfoEntity role) {
        try {
            //获取主键ID
            AjaxResult result = null;
//            String id;
//            id = String.valueOf(SnowFlakeIds.get().nextId());
//            role.setId(id);
            if (StringUtil.isBlank(role.getRoleName())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "角色名称不能为空", role);
            }
            role.setAddTime(new Date());
            /*是否存在角色编码*/
            if (StringUtil.isBlank(role.getRoleCode())) {
                role.setRoleCode(role.getRoleId() + "");
            }
            role.setAddUserId(UserUtil.getUserEntity().getUserId());
            if (ObjectUtils.isNullObj(role.getAliveFlag())) {
                role.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            }
            int i = roleInfoMapper.insert(role);
            if (i > 0) {
                //新增成功后添加到redis缓存中
                redisDataService.setData(SEED_ROLE_REDIS + "id_" + role.getRoleId(), JSON.toJSONString(role));
                result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功");
                result.setData(role);
                return result;
            }
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败");
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[RoleInfoServiceImpl->insertRole]" + e);
            throw new SeedException("报错-位置：[RoleInfoServiceImpl->insertRole]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 修改角色
     *
     * @param role
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:05
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult updateRole(RoleInfoEntity role) {
        try {
            AjaxResult result = null;
            if (ObjectUtils.isNullObj(role.getRoleId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "角色id不能为空", role);
            }
            role.setOprTime(new Date());
            role.setOprUserId(UserUtil.getUserEntity().getUserId());
            int i = roleInfoMapper.updateByPrimaryKeySelective(role);
            if (i > 0) {
                //修改成功后修改redis缓存
                redisDataService.delete(SEED_ROLE_REDIS + "id_" + role.getRoleId());
                result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改成功", role);
                result.setData(role);
                return result;
            }
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改失败");
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[RoleInfoServiceImpl->updateRole]" + e);
            throw new SeedException("报错-位置：[RoleInfoServiceImpl->updateRole]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 修改角色状态
     *
     * @param role
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年10月21日 上午11:19:15
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult updateRoleEnable(RoleInfoEntity role) {
        try {
            AjaxResult result = null;
            if (ObjectUtils.isNullObj(role.getRoleId())) {
                result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "角色id不能为空", role);
            }
            if (ObjectUtils.isNullObj(role.getAliveFlag())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "状态不能为空", role);
            }
            //不能禁用超管角色
            if (role.getRoleId().equals("1")) {
                ErrorMessageUtils.setErrorMessage("不能禁用超管角色");
            }
            role.setOprUserId(UserUtil.getUserEntity().getUserId());
            role.setOprTime(new Date());
            int i = roleInfoMapper.updateByPrimaryKeySelective(role);
            if (i > 0) {
                //删除redis缓存
                redisDataService.delete(SEED_ROLE_REDIS + "id_" + role.getRoleId());
                result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改成功", role);
                return result;
            }
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改失败", role);
            return result;
        } catch (Exception e) {
            log.error("报错-位置：[RoleInfoServiceImpl->deleteContractConfig]" + e);
            throw new SeedException("报错-位置：[RoleInfoServiceImpl->deleteContractConfig]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 删除角色
     *
     * @param roleIds
     * @return
     * @throws
     * @author 黄芝民
     * @date 2020/12/5 11:11
     */
    @Override
    @Transactional
    public AjaxResult delRole(List<RoleInfoEntity> roleIds) {
        for (RoleInfoEntity roleInfoEntity : roleIds) {
            int i = roleInfoMapper.deleteByPrimaryKey(roleInfoEntity);
            if (i < 1) {
                ErrorMessageUtils.setErrorMessage("删除失败");
            }
        }
        return new AjaxResult(1, "删除成功");
    }

    /**
     * TODO 模糊查询参数设置
     *
     * @throws
     * @param: outletUserEntity
     * @return: Example
     * @author: 黄芝民
     * @date: 2020/10/29 15:57
     */
    private Example setLikeQueryParam(RoleInfoEntity roleInfoEntity) {
        Example example = new Example(RoleInfoEntity.class);
        Example.Criteria criteria = example.createCriteria();
        if (!ObjectUtils.isNullObj(roleInfoEntity.getAliveFlag())) {
            criteria.andEqualTo("aliveFlag", roleInfoEntity.getAliveFlag());
        }
        if (StringUtil.isNotBlank(roleInfoEntity.getRoleName())) {
            criteria.andLike("roleName", roleInfoEntity.getRoleName());
        }
        return example;
    }
}
