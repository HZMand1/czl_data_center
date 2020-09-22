package cn.paohe.login.service.impl;

import cn.paohe.base.component.annotation.TargetDataSource;
import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.entity.vo.login.LoginVo;
import cn.paohe.entity.vo.sys.RoleMenuAuthVo;
import cn.paohe.enums.DataCenterCollections;
import cn.paohe.enums.Md5;
import cn.paohe.exp.SeedException;
import cn.paohe.login.service.ILoginService;
import cn.paohe.sys.service.IRoleAuthService;
import cn.paohe.user.dao.UserEntityMapper;
import cn.paohe.utils.CheckEntityUtils;
import cn.paohe.utils.JwtUtil;
import cn.paohe.utils.RedisUtil;
import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 登录服务层实现类
 *
 * @author 夏家鹏
 * @version V1.0
 * @date 2019/10/22 13:40
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private final static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private static final long EXPIRE_TIME = 30L * 60;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private UserEntityMapper userEntityMapper;
    @Autowired
    private IRoleAuthService roleAuthService;

    /**
     * TODO 登录
     *
     * @param user
     * @return AjaxResult
     * @throws
     * @author 夏家鹏
     * @date 2019/10/24 9:25
     */
    @TargetDataSource(value = "seed-r")
    public AjaxResult login(UserEntity user) {
        AjaxResult ajaxResult = new AjaxResult();
        String jsonString = null;
        try {
            //判空
            Map<String, String> map = new HashMap<>();
            map.put("account", "账号");
            map.put("password", "密码");
            CheckEntityUtils.checkEntity(user, map);
            user.setPassword(Md5.getMD5(user.getPassword().getBytes()));
            ArrayList<String> authList = new ArrayList<String>();
            user = userEntityMapper.selectOne(user);
            if (user == null) {
                ajaxResult.setRetcode(0);
                ajaxResult.setRetmsg("用户名或密码不正确");
                return ajaxResult;
            } else if (user.getAliveFlag().equals(DataCenterCollections.YesOrNo.NO.value)) {
                ajaxResult.setRetcode(0);
                ajaxResult.setRetmsg("该用户已被禁用");
                return ajaxResult;
            }
            //查询当前用户所有权限
            RoleMenuAuthVo roleMenuAuthVo = new RoleMenuAuthVo();
            roleMenuAuthVo.setUserId(user.getUserId());
            AjaxResult authByUser = roleAuthService.findAuthByUser(roleMenuAuthVo);
            Collection<RoleMenuAuthVo> authVos = (Collection<RoleMenuAuthVo>) authByUser.getData();
            for (RoleMenuAuthVo authVo : authVos) {
                String auth = authVo.getAuth();
                if (auth != null && !"".equals(auth)) {
                    authList.add(auth);
                }
            }
            jsonString = JSONObject.toJSONString(user);
            //为了通用，转成json
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            //生成token,并存入redis
            String token = JwtUtil.sign(user.getAccount(),user.getUserId(),user.getPassword());
            //添加到redis并设置到期时间
            redisUtil.set(token, JSONObject.toJSONString(authList), EXPIRE_TIME);
            jsonObject.put("password", null);
            ajaxResult.setData(new LoginVo(jsonObject, authList, token));
        } catch (Exception e) {
            log.error("报错-位置：[LoginServiceImpl->login]" + e);
            throw new SeedException("报错-位置：[LoginServiceImpl->login]" + e.getMessage(), e);
        }
        return ajaxResult;
    }

    /**
     * TODO 注销
     *
     * @param request
     * @return AjaxResult
     * @throws
     * @author 夏家鹏
     * @date 2019/10/24 9:25
     */
    public AjaxResult logout(HttpServletRequest request) {
        try {
            redisUtil.del(request.getHeader("token"));
        } catch (Exception e) {
            log.error("报错-位置：[LoginServiceImpl->logout]" + e);
            throw new SeedException("报错-位置：[LoginServiceImpl->logout]" + e.getMessage(), e);
        }
        return new AjaxResult();
    }
}
