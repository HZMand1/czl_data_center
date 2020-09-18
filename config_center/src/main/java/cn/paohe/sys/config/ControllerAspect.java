package cn.paohe.sys.config;

import cn.paohe.enums.DataCenterCollections;
import cn.paohe.sys.annotation.AnnotationParse;
import cn.paohe.sys.annotation.AuthExclude;
import cn.paohe.util.cache.RedisClientUtil;
import cn.paohe.utils.RedisUtil;
import cn.paohe.vo.framework.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;

/**
 * TODO 权限AOP
 *
 * @version V1.0
 * @author: 夏家鹏
 * @date: 2019/10/28 9:11
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@Aspect
@Component
public class ControllerAspect {

    private static final long EXPIRE_TIME = 30L * 60;

    public static final String ADMIN = "ADMIN";

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("execution(public * cn.paohe.*.rest.*.*(..)) && !execution(* cn.paohe.*.rest.*.*login(..))")
    public void privilege() {
    }

    /**
     * 校验当前用户的访问权限
     *
     * @param joinPoint
     * @throws Throwable
     */
    @ResponseBody
    @Around("privilege()")
    public Object isAccessMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //本地请求直接放行
        String remoteAddr = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(remoteAddr) || "127.0.0.1".equals(remoteAddr)) {
            return joinPoint.proceed();
        }
        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        //看是否被排除
        if (targetMethod.isAnnotationPresent(AuthExclude.class)) {
            return joinPoint.proceed();
        }

        //得到方法的访问权限
        final String methodAccess = AnnotationParse.privilegeParse(targetMethod);

        String token = request.getHeader("token");
        String userInfos = request.getHeader("user-info");
        if (StringUtils.isNoneBlank(userInfos)) {
            userInfos = URLDecoder.decode(userInfos, "UTF-8");
            JSONObject obj = JSON.parseObject(userInfos);
            String account = obj.getString("account");
            if (StringUtils.isNoneBlank(account) && "admin".equals(account)) {
                //刷新过期时间
                if (StringUtils.isNoneBlank(token)) {
                    refreshRedis(token);
                }
                return joinPoint.proceed();
            }
        }
        if (token == null || "".equals(token) || !redisUtil.hasKey(token)) {
            response.setStatus(DataCenterCollections.RestHttpStatus.BAD.value);
            return new AjaxResult(DataCenterCollections.RestHttpStatus.BAD.value, "请登录……");
        }
        //如果该方法上有权限注解
        if (!StringUtils.isBlank(methodAccess)) {
            String authListStr = redisUtil.get(token).toString();
            List<String> authList = JSON.parseArray(authListStr, String.class);
            if (authList == null || authList.size() < 1 || (!authList.contains(methodAccess) && !authList.contains(ADMIN))) {
                response.setStatus(DataCenterCollections.RestHttpStatus.AUTH_ERROR.value);
                return new AjaxResult(DataCenterCollections.RestHttpStatus.AUTH_ERROR.value, "请求已被拒绝,当前权限不足");
            }
        }
        //刷新过期时间
        refreshRedis(token);
        return joinPoint.proceed();
    }

    /**
     * TODO 更新过期时间
     *
     * @param token
     * @return
     * @throws
     * @author 夏家鹏
     * @date 2019/11/4 17:47
     */
    private void refreshRedis(String token) {
        if (token != null) {
            redisUtil.expire(token, EXPIRE_TIME);
        }
    }
}
