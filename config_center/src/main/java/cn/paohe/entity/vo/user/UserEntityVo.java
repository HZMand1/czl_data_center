package cn.paohe.entity.vo.user;

import cn.paohe.entity.model.user.UserEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/22 14:14
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public class UserEntityVo extends UserEntity {

    public static enum key{
        roleId
    }

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
