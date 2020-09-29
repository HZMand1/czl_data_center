package cn.paohe.user.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.service.base.RedisDataService;
import cn.paohe.user.dao.UserEntityMapper;
import cn.paohe.user.service.IUserInfoService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.utils.ErrorMessageUtils;
import cn.paohe.utils.Md5;
import cn.paohe.utils.UserUtil;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/10/22 10:03
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private UserEntityMapper userEntityMapper;

    @Autowired
    private RedisDataService redisDataService;

    /**
     * @Fields SEED_ROLE_REDIS : redis当中的key前缀
     */
    private static String SEED_USER_REDIS = "seed_user_";

    /**
     * TODO 获取全部后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: result
     * @author: 黄芝民
     * @date: 2019/10/22 16:08
     */

    @TargetDataSource(value = "center-r")
    public AjaxResult queryUserAllList(UserEntity userEntity) {
        Example example = setLikeQueryParam(userEntity);
        example.setOrderByClause("ADD_TIME desc");
        List<UserEntity> seedUserEntityList = userEntityMapper.selectByExample(example);
        AjaxResult result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取用户信息成功");
        result.setData(seedUserEntityList);
        return result;
    }

    /**
     * TODO 分页获取后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: result
     * @author: 黄芝民
     * @date: 2019/10/22 16:08
     */

    @TargetDataSource(value = "center-r")
    public PageAjax<UserEntity> queryUserAllPage(UserEntity userEntity) {
        PageHelper.startPage(userEntity.getStart(), userEntity.getPageSize());
        Example example = setLikeQueryParam(userEntity);
        example.setOrderByClause("ADD_TIME desc");
        List<UserEntity> resultList = userEntityMapper.selectByExample(example);
        return AppUtil.returnPage(resultList);
    }

    /**
     * TODO 根据ID获取后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: result
     * @author: 黄芝民
     * @date: 2019/10/22 16:08
     */

    @TargetDataSource(value = "center-r")
    public AjaxResult queryUserById(UserEntity userEntity) {
        AjaxResult result = new AjaxResult();
        String seedUserEntityStr = redisDataService.getData(SEED_USER_REDIS + "id_" + userEntity.getUserId());
        UserEntity temp = null;
        //从缓存中获取
        if (StringUtil.isNotBlank(seedUserEntityStr)) {
            temp = JSON.parseObject(seedUserEntityStr, UserEntity.class);
            result.setData(temp);
            return result;
        }
        temp = userEntityMapper.selectByPrimaryKey(userEntity.getUserId());
        if (ObjectUtils.isNullObj(temp)) {
            result.setRetcode(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value);
            result.setRetmsg("无法通过用户ID获取数据，ID ：" + userEntity.getUserId());
            return result;
        }
        //添加到redis缓存中
        redisDataService.setData(SEED_USER_REDIS + "id_" + temp.getUserId(), JSON.toJSONString(temp));
        result.setRetmsg("获取用户信息成功。");
        result.setData(temp);
        return result;
    }

    /**
     * TODO 根据账号获取后台用户信息
     *
     * @param userEntity
     * @throws
     * @param: seedUserEntity
     * @return: AjaxResult
     * @author: 黄芝民
     * @date: 2019/10/22 9:53
     */
    @TargetDataSource(value = "center-r")
    public AjaxResult queryUserByAccount(UserEntity userEntity) {
        String account = userEntity.getAccount();
        if (StringUtil.isBlank(account)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "后台账号不能为空。");
        }
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", userEntity.getAccount());
        UserEntity result = userEntityMapper.selectOneByExample(example);
        if (ObjectUtils.isNullObj(result)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "根据后台账号查取信息失败。");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "根据后台账号查取信息成功。");
    }

    /**
     * TODO 新增后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: result
     * @author: 黄芝民
     * @date: 2019/10/22 16:08
     */

    @TargetDataSource(value = "center-w")
    public AjaxResult insertUser(UserEntity userEntity) {
        AjaxResult result = null;
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
        int i = userEntityMapper.insert(userEntity);
        if (i > 0) {
            //新增成功后添加到redis缓存中
            redisDataService.setData(SEED_USER_REDIS + "id_" + userEntity.getUserId(), JSON.toJSONString(userEntity));
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增后台用户成功");
            result.setData(userEntity);
            return result;
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增后台用户失败", userEntity);
    }

    /**
     * TODO 修改后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: result
     * @author: 黄芝民
     * @date: 2019/10/22 16:08
     */

    @TargetDataSource(value = "center-w")
    public AjaxResult updateUserById(UserEntity userEntity) {
        AjaxResult result = null;
        if (ObjectUtils.isNullObj(userEntity.getUserId())) {
            result = new AjaxResult();
            result.setRetcode(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value);
            result.setRetmsg("后台用户ID不能为空。");
            result.setData(userEntity);
            return result;
        }
        userEntity.setOprTime(new Date());
        userEntity.setOprUserId(UserUtil.getUserEntity().getUserId());
        int count = userEntityMapper.updateByPrimaryKeySelective(userEntity);
        if (count > 0) {
            //修改成功后修改redis缓存
            redisDataService.delete(SEED_USER_REDIS + "id_" + userEntity.getUserId());
            result = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改后台用户信息成功");
            result.setData(userEntity);
            return result;
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改后台用户信息失败", userEntity);
    }

    /**
     * TODO 删除后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: result
     * @author: 黄芝民
     * @date: 2019/10/22 16:08
     */

    @TargetDataSource(value = "center-w")
    public AjaxResult deleteUserById(UserEntity userEntity) {
        if (ObjectUtils.isNullObj(userEntity.getUserId())) {
            AjaxResult physicalResult = new AjaxResult();
            physicalResult.setRetcode(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value);
            physicalResult.setRetmsg("后台用户ID不能为空。");
            return physicalResult;
        }
        int deleteCount = userEntityMapper.deleteByPrimaryKey(userEntity);
        if (deleteCount > 0) {
            //删除redis缓存
            redisDataService.delete(SEED_USER_REDIS + "id_" + userEntity.getUserId());
            AjaxResult ajaxResult = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "后台用户删除成功");
            ajaxResult.setData(userEntity);
            return ajaxResult;
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "后台用户删除失败");
    }

    /**
     * TODO 启用、禁用后台用户信息
     *
     * @throws
     * @param: seedUserEntity
     * @return: AjaxResult
     * @author: 黄芝民
     * @date: 2019/10/22 16:49
     */
    @TargetDataSource(value = "center-w")
    public AjaxResult enableUser(UserEntity userEntity) {
        if (ObjectUtils.isNullObj(userEntity.getUserId())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "后台用户ID不能为空。");
        }
        Integer enable = userEntity.getAliveFlag();
        if (null == enable) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "启用、禁用标识不能为空。");
        }
        //不能禁用admin
        if (StringUtil.equals(userEntity.getUserId(),1)) {
            ErrorMessageUtils.setErrorMessage("不能禁用超级管理员");
        }
        userEntity.setOprUserId(UserUtil.getUserEntity().getUserId());
        userEntity.setOprTime(new Date());
        int updateCount = userEntityMapper.updateByPrimaryKeySelective(userEntity);
        if (updateCount > 0) {
            //删除redis缓存
            redisDataService.delete(SEED_USER_REDIS + "id_" + userEntity.getUserId());
            AjaxResult ajaxResult = new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value);
            ajaxResult.setRetmsg(enable == 1 ? "后台用户启用成功" : "后台用户禁用成功");
            ajaxResult.setData(userEntity);
            return ajaxResult;
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "启用、禁用后台用户失败。");
    }

    /**
     * TODO 删除用户
     *
     * @param ids
     * @return
     * @throws
     * @author 夏家鹏
     * @date 2019/12/5 13:37
     */
    @Override
    public AjaxResult delUser(String[] ids) {
        for (String id : ids) {
            int i = userEntityMapper.deleteByPrimaryKey(id);
            if (i < 1) {
                ErrorMessageUtils.setErrorMessage("删除失败");
            }
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "删除成功");
    }

    /**
     * TODO 修改后台用户密码
     *
     * @param userEntity
     * @throws
     * @param: seedUserEntity
     * @return:
     * @author: 黄芝民
     * @date: 2019/12/5 10:33
     */
    @Override
    @TargetDataSource(value = "center-w")
    public AjaxResult updateUserPassword(UserEntity userEntity) {
        if (StringUtil.isBlank(userEntity.getAccount())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "后台用户账号不能为空");
        }
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", userEntity.getAccount());
        if (StringUtil.isNotBlank(userEntity.getPassword())) {
            userEntity.setPassword(Md5.getMD5(userEntity.getPassword().getBytes()));
        }
        int updateCount = userEntityMapper.updateByExampleSelective(userEntity, example);
        if (updateCount > 0) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "后台用户密码修改成功");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "后台用户密码修改失败");
    }

    /**
     * TODO 校验账号是否为空或已存在
     *
     * @throws
     * @param:
     * @return:
     * @author: 黄芝民
     * @date: 2019/10/29 11:36
     */
    private AjaxResult checkAccount(UserEntity userEntity) {
        if (StringUtil.isBlank(userEntity.getAccount())) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "账号不能为空。");
        }
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", userEntity.getAccount());
        UserEntity result = userEntityMapper.selectOneByExample(example);
        if (ObjectUtils.isNullObj(result)) {
            return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_YES.value, "账号不存在。");
        }
        return new AjaxResult(DataCenterCollections.RestHttpStatus.AJAX_CODE_NO.value, "账号已存在。");
    }

    /**
     * TODO 模糊查询参数设置
     *
     * @throws
     * @param: outletUserEntity
     * @return: Example
     * @author: 黄芝民
     * @date: 2019/10/29 15:57
     */
    private Example setLikeQueryParam(UserEntity userEntity) {
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtils.isNullObj(userEntity.getParentUserId())){
            userEntity.setParentUserId(UserUtil.getUserEntity().getUserId());
        }

        // 设置查询条件
        if (StringUtil.isNotBlank(userEntity.getUseName())) {
            criteria.andLike(UserEntity.key.useName.toString(), userEntity.getUseName());
        }
        if (StringUtil.isNotBlank(userEntity.getUserName())) {
            criteria.andLike(UserEntity.key.userName.toString(), userEntity.getUserName());
        }
        if (StringUtil.isNotBlank(userEntity.getAccount())) {
            criteria.andLike(UserEntity.key.account.toString(), userEntity.getAccount());
        }
        if (StringUtil.isNotBlank(userEntity.getPhone())) {
            criteria.andLike(UserEntity.key.phone.toString(), userEntity.getPhone());
        }
        if (!ObjectUtils.isNullObj(userEntity.getAliveFlag())) {
            criteria.andEqualTo(UserEntity.key.aliveFlag.toString(), userEntity.getAliveFlag());
        }
        if (StringUtil.isNotBlank(userEntity.getEmail())) {
            criteria.andLike(UserEntity.key.email.toString(), userEntity.getEmail());
        }
        if (!ObjectUtils.isNullObj(userEntity.getParentUserId())) {
            criteria.andEqualTo(UserEntity.key.parentUserId.toString(), userEntity.getParentUserId());
        }
        return example;
    }
}
