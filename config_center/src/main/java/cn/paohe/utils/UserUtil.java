package cn.paohe.utils;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.model.auth.UserInfo;
import cn.paohe.util.basetype.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**TODO 用户工具类
 * @author 夏家鹏
 * @date 2019/11/4 10:20
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class UserUtil {

    /**TODO 获取当前登录用户
     * @param
     * @return Map<String , String>
     * @author 夏家鹏
     * @date 2019/11/4 10:20
     * @throws
     */
    public static Map<String, String> getLoginUser(HttpServletRequest request) {
        String userInfo = request.getHeader("user-info");
        Map<String, String> map = new HashMap<>();
        if (userInfo != null) {
            try {
                String decode = URLDecoder.decode(userInfo, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(decode);
                Set<String> keys = jsonObject.keySet();
                for (String key : keys) {
                    map.put(key, String.valueOf(jsonObject.get(key)));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
//            ErrorMessageUtils.setErrorMessage("无法获取当前用户的登录信息！");
        }
        return map;
    }

    public static UserEntity getUserEntity() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(ObjectUtils.isNullObj(attributes)){
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String userInfo = request.getHeader("user-info");
        String token = request.getHeader("token");
        if(StringUtil.isBlank(userInfo) && StringUtil.isBlank(token)){
            return null;
        }
        if(StringUtil.isBlank(userInfo)){
            if(StringUtil.isBlank(token)){
                return null;
            }
            UserEntity userEntity = JwtUtil.getuserInfo(token);
            if(!ObjectUtils.isNullObj(userEntity)){
                return userEntity;
            }
            return null;
        }else {
            String decode = null;
            try {
                decode = URLDecoder.decode(userInfo, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (StringUtil.isBlank(decode)){
                return null;
            }
            UserEntity ui = JSON.parseObject(decode, UserEntity.class);
            if(!ObjectUtils.isNullObj(ui)){
                return ui;
            }
        }
        return null;
    }
}
