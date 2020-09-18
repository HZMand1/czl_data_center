package cn.paohe.login.service;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.vo.framework.AjaxResult;

import javax.servlet.http.HttpServletRequest;

/**TODO 登录服务层
 * @author 夏家鹏
 * @date 2019/10/22 13:40
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public interface ILoginService {

    /**TODO 登录
     * @param user
     * @return AjaxResult
     * @author 夏家鹏
     * @date 2019/10/24 9:25
     * @throws
     */
    AjaxResult login(UserEntity user);

    /**TODO 注销
     * @param request
     * @return AjaxResult
     * @author 夏家鹏
     * @date 2019/10/24 9:25
     * @throws
     */
    AjaxResult logout(HttpServletRequest request);
}
