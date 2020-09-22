package cn.paohe.user.service;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.entity.vo.user.UserEntityVo;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import com.github.pagehelper.Page;

/**
 * TODO
 * 开发者信息接口
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/10/22 9:36
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public interface IDeveloperService {

    /**
     * TODO 获取全部用户信息
     *
     * @throws
     * @param: userEntity
     * @return: AjaxResult
     * @author: 黄芝民
     * @date: 2019/10/22 9:48
     */
    public AjaxResult queryDeveloperList(UserEntityVo userEntityVo);

    /**
     * TODO 分页获取用户信息
     *
     * @throws
     * @param: userEntity
     * @return: AjaxResult
     * @author: 黄芝民
     * @date: 2019/10/22 9:53
     */
    public PageAjax<UserEntity> queryDeveloperPage(UserEntityVo userEntityVo);

    /**TODO 新增后台用户信息
     * @param:  userEntity
     * @return: AjaxResult
     * @author:    黄芝民
     * @date:      2019/10/22 9:59
     * @throws
     */
    public AjaxResult insertDeveloper(UserEntity userEntity);
}
