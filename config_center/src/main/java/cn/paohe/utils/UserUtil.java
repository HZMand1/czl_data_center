package cn.paohe.utils;

import cn.paohe.base.utils.basetype.StringUtil;
import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.model.auth.UserInfo;
import cn.paohe.util.basetype.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        UserEntity ui = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(ObjectUtils.isNullObj(attributes)){
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        String userInfo = request.getHeader("user-info");
        if(StringUtil.isBlank(userInfo)){
            return null;
        }
        String decode = null;
        try {
            decode = URLDecoder.decode(userInfo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(decode)) {
            ui = JSON.parseObject(decode, UserEntity.class);
        }
        return ui;
    }
}
