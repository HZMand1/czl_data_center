package cn.paohe.user.dao;

import cn.paohe.base.business.dao.MyMapper;
import cn.paohe.entity.model.user.UserEntity;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/22 11:44
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IDeveloperMapper extends MyMapper<UserEntity> {

    /**
     * TODO 获取角色是开发的用户数据
     * @Param:   userEntity
     * @return:  List
     * @author:  黄芝民
     * @Date:    2020/9/22 14:05
     * @throws:
     */
    public List<UserEntity> queryUserList(UserEntity userEntity);
}
