package cn.paohe.login.rest;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.login.service.ILoginService;
import cn.paohe.vo.framework.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 登录控制层
 *
 * @author 夏家鹏
 * @version V1.0
 * @date 2019/10/21 16:50
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@RestController
@CrossOrigin
@RequestMapping("/rest/data/center/")
public class RestCenterLoginController {

    @Autowired
    private ILoginService loginService;

    /**
     * TODO 登陆
     *
     * @param userEntity
     * @return AjaxResult
     * @throws
     * @author 夏家鹏
     * @date 2019/10/24 9:50
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AjaxResult login(@RequestBody UserEntity userEntity) {
        return loginService.login(userEntity);
    }

    /**
     * TODO 注销
     *
     * @param request
     * @return AjaxResult
     * @throws
     * @author 夏家鹏
     * @date 2019/10/24 9:51
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public AjaxResult logout(HttpServletRequest request) {
        return loginService.logout(request);
    }
}
