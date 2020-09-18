package cn.paohe.entity.vo.login;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**TODO 登录后返回信息包装类
 * @author: 夏家鹏
 * @date: 2019/11/5 9:10 
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class LoginVo {

    /**用户信息*/
    private JSONObject userInfo;

    /**拥有的权限*/
    private List<String> authList;

    /**token*/
    private String token;

    public LoginVo() {
    }

    public LoginVo(JSONObject userInfo, List<String> authList, String token) {
        this.userInfo = userInfo;
        this.authList = authList;
        this.token = token;
    }

    public JSONObject getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(JSONObject userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
