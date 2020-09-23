package cn.paohe.utils;

import cn.paohe.entity.model.user.UserEntity;
import cn.paohe.util.basetype.ObjectUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.Map;

/**TODO
 * @author 夏家鹏
 * @date 2019/10/17 9:21
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@SuppressWarnings("all")
public class JwtUtil {

    private static final long EXPIRE_TIME = 5L * 60 * 1000 * 12 * 24 * 10;

    /**
     * 校验token是否正确
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //效验token
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * TODO 获取token 中的用户信息
     * @Param:   null
     * @return:  * @return: null
     * @author:  黄芝民
     * @Date:    2020/9/21 9:37
     * @throws:
     */
    public static UserEntity getuserInfo(String token){
        try {
            Map<String, Claim> map = JWT.decode(token).getClaims();
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, Claim> stringClaimEntry : map.entrySet()) {
                jsonObject.put(stringClaimEntry.getKey(),stringClaimEntry.getValue().as(Object.class));
            }
            if(ObjectUtils.isNullObj(jsonObject)){
                return null;
            }
            UserEntity userInfo = JSON.parseObject(JSON.toJSONString(jsonObject),UserEntity.class);
            if(ObjectUtils.isNullObj(userInfo)){
                return null;
            }
            return userInfo;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username,Long userId,Long applicationId, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username).withClaim("userId",userId).withClaim("applicationId",applicationId)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
