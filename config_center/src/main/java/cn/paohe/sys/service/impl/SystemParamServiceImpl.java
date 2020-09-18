package cn.paohe.sys.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.sys.SystemParamEntity;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.exp.SeedException;
import cn.paohe.sys.dao.SystemParamMapper;
import cn.paohe.sys.service.ISystemParamService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.util.cache.RedisClientUtil;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * TODO 系统参数配置
 *
 * @author 黄芝民
 * @version V1.0
 * @date 2020/10/23 15:00
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemParamServiceImpl implements ISystemParamService {

    private final static Logger log = LoggerFactory.getLogger(SystemParamServiceImpl.class);

    private static final String ALL_SYS_PARAM = "ALL_SYS_PARAM";

    @Resource
    private SystemParamMapper systemParamMapper;

    @Autowired
    private RedisClientUtil redisClientUtil;

    @Resource
    private HttpServletRequest request;

    /**
     * TODO 新增
     *
     * @param entity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:38
     */

    @TargetDataSource(value = "center-w")
    public AjaxResult add(SystemParamEntity entity) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (StringUtil.isEmpty(entity.getType())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "type类型不能为空", entity);
            }
            if (StringUtil.isEmpty(entity.getCode())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "code不能为空", entity);
            }
            if (StringUtil.isEmpty(entity.getCode())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "val不能为空", entity);
            }
            entity.setAddTime(new Date());
            entity.setOprUserId(UserUtil.getUserEntity().getUserId());
            entity.setAliveFlag(DataCenterCollections.YesOrNo.YES.value);
            int i = systemParamMapper.insertSelective(entity);
            if (i < 1) {
                ajaxResult.setRetcode(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value);
                ajaxResult.setRetmsg("添加失败");
            }
        } catch (Exception e) {
            log.error("报错-位置：[SystemParamServiceImpl->add]" + e);
            throw new SeedException("报错-位置：[SystemParamServiceImpl->add]" + e.getMessage(), e);
        }
        redisClientUtil.deleteKey(ALL_SYS_PARAM);
        return ajaxResult;
    }

    /**
     * TODO 修改
     *
     * @param entity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:38
     */

    @TargetDataSource(value = "center-w")
    public AjaxResult update(SystemParamEntity entity) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            if (ObjectUtils.isNullObj(entity.getId())) {
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "id不能为空", entity);
            }
            entity.setOprTime(new Date());
            entity.setOprUserId(UserUtil.getUserEntity().getUserId());
            int i = systemParamMapper.updateByPrimaryKeySelective(entity);
            if (i < 1) {
                ajaxResult.setRetcode(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value);
                ajaxResult.setRetmsg("修改失败");
            }
        } catch (Exception e) {
            log.error("报错-位置：[SystemParamServiceImpl->update]" + e);
            throw new SeedException("报错-位置：[SystemParamServiceImpl->update]" + e.getMessage(), e);
        }
        redisClientUtil.deleteKey(ALL_SYS_PARAM);
        return ajaxResult;
    }

    /**
     * TODO 批量删除
     *
     * @param ids
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:38
     */

    @Transactional
    @TargetDataSource(value = "center-w")
    public AjaxResult del(List<Long> ids) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            for (Long id : ids) {
                int i = systemParamMapper.deleteByPrimaryKey(id);
                if (i < 1) {
                    ajaxResult.setRetcode(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value);
                    ajaxResult.setRetmsg("删除失败");
                }
            }
        } catch (Exception e) {
            log.error("报错-位置：[SystemParamServiceImpl->del]" + e);
            throw new SeedException("报错-位置：[SystemParamServiceImpl->del]" + e.getMessage(), e);
        }
        redisClientUtil.deleteKey(ALL_SYS_PARAM);
        return ajaxResult;
    }

    /**
     * TODO 列表条件查询
     *
     * @param entity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/10/23 16:38
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult findListPage(SystemParamEntity entity) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Condition condition = new Condition(SystemParamEntity.class);
            Example.Criteria criteria = condition.createCriteria();
            if (ObjectUtils.isNullObj(entity.getId())) {
                criteria.andEqualTo("id", entity.getId());
            }
            if (StringUtil.isNotEmpty(entity.getType())) {
                criteria.andEqualTo("type", entity.getType());
            }
            if (StringUtil.isNotEmpty(entity.getCode())) {
                criteria.andEqualTo("code", entity.getCode());
            }
            if (StringUtil.isNotEmpty(entity.getVal())) {
                criteria.andEqualTo("val", entity.getVal());
            }
            PageHelper.startPage(entity.getStart(), entity.getPageSize(), "ADDTIME desc,UPDATETIME DESC");
            List<SystemParamEntity> list = systemParamMapper.selectByCondition(condition);
            PageInfo<SystemParamEntity> pageInfo = new PageInfo<SystemParamEntity>(list);
            ajaxResult.setData(pageInfo);
        } catch (Exception e) {
            log.error("报错-位置：[SystemParamServiceImpl->findList]" + e);
            throw new SeedException("报错-位置：[SystemParamServiceImpl->findList]" + e.getMessage(), e);
        }
        return ajaxResult;
    }

    /**
     * TODO 根据条件获取参数配置
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年11月4日 上午11:07:46
     */
    public AjaxResult findListByCondition(SystemParamEntity systemParamEntity) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Condition condition = new Condition(SystemParamEntity.class);
            Example.Criteria criteria = condition.createCriteria();
            if (ObjectUtils.isNullObj(systemParamEntity.getId())) {
                criteria.andEqualTo("id", systemParamEntity.getId());
            }
            if (StringUtil.isNotEmpty(systemParamEntity.getType())) {
                criteria.andEqualTo("type", systemParamEntity.getType());
            }
            if (StringUtil.isNotEmpty(systemParamEntity.getCode())) {
                criteria.andEqualTo("code", systemParamEntity.getCode());
            }
            if (StringUtil.isNotEmpty(systemParamEntity.getVal())) {
                criteria.andEqualTo("val", systemParamEntity.getVal());
            }
            List<SystemParamEntity> list = systemParamMapper.selectByCondition(condition);
            ajaxResult.setData(list);
        } catch (Exception e) {
            log.error("报错-位置：[SystemParamServiceImpl->findListByCondition]" + e);
            throw new SeedException("报错-位置：[SystemParamServiceImpl->findListByCondition]" + e.getMessage(), e);
        }
        return ajaxResult;
    }

    /**
     * TODO 根据条件获取参数配置
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author: 黄芝民
     * @date: 2020年11月4日 上午11:07:46
     */
    public List<SystemParamEntity> findListByCond(SystemParamEntity systemParamEntity) {
        try {
            Condition condition = new Condition(SystemParamEntity.class);
            Example.Criteria criteria = condition.createCriteria();
            if (ObjectUtils.isNullObj(systemParamEntity.getId())) {
                criteria.andEqualTo("id", systemParamEntity.getId());
            }
            if (StringUtil.isNotEmpty(systemParamEntity.getType())) {
                criteria.andEqualTo("type", systemParamEntity.getType());
            }
            if (StringUtil.isNotEmpty(systemParamEntity.getCode())) {
                criteria.andEqualTo("code", systemParamEntity.getCode());
            }
            if (StringUtil.isNotEmpty(systemParamEntity.getVal())) {
                criteria.andEqualTo("val", systemParamEntity.getVal());
            }
            List<SystemParamEntity> list = systemParamMapper.selectByCondition(condition);
            return list;
        } catch (Exception e) {
            log.error("报错-位置：[SystemParamServiceImpl->findListByCond]" + e);
            throw new SeedException("报错-位置：[SystemParamServiceImpl->findListByCond]" + e.getMessage(), e);
        }
    }

    /**
     * TODO 查询全部系统参数
     *
     * @param
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/11/5 10:06
     */
    public AjaxResult findAll() {
        if (redisClientUtil.hasKey(ALL_SYS_PARAM)) {
            //先从缓存中查
            List<Object> list = redisClientUtil.lRangeList(ALL_SYS_PARAM, Long.valueOf(0), Long.valueOf(-1));
            return new AjaxResult(list);
        } else {
            //从数据库查
            List<SystemParamEntity> selectAll = systemParamMapper.selectAll();
            //添加缓存
            redisClientUtil.setKey(ALL_SYS_PARAM, selectAll);
            return new AjaxResult(selectAll);
        }
    }

    /**
     * TODO 查询当前类型下所有的系统参数配置
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/11/7 16:46
     */
    @Override
    public AjaxResult findListByType(SystemParamEntity systemParamEntity) {
        if (systemParamEntity == null || StringUtil.isEmpty(systemParamEntity.getType())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "type不能为空", systemParamEntity);
        }
        Condition condition = new Condition(SystemParamEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("type", systemParamEntity.getType());
        return new AjaxResult(systemParamMapper.selectByCondition(condition));
    }

    /**
     * TODO 查询当前类型下的系统参数配置
     *
     * @param systemParamEntity
     * @return AjaxResult
     * @throws
     * @author 黄芝民
     * @date 2020/11/7 16:46
     */
    @Override
    public SystemParamEntity findSystemParamByType(SystemParamEntity systemParamEntity) {
        if (systemParamEntity == null || StringUtil.isEmpty(systemParamEntity.getType())) {
            return null;
        }
        Condition condition = new Condition(SystemParamEntity.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("type", systemParamEntity.getType());
        SystemParamEntity result = systemParamMapper.selectOneByExample(condition);
        return result;
    }
}
