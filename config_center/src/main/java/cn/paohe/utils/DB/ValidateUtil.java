package cn.paohe.utils.DB;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 16:42
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by zhoujiayuan on 2018/6/4.
 * 验证传入参数是否为空的工具类
 */
public class ValidateUtil {

    public static String paramIsEmpty(String key, Map<String, Object> map) {
        if (map != null) {
            String newString = map.get(key) == null ? "" : map.get(key).toString().trim();
            return newString;
        } else {
            return "";
        }
    }

    public static String validateParamContainKey(String key, Map<String, Object> map) {
        if (map != null) {
            if (map.containsKey(key) && map.get(key) != null && StringUtils.isNotEmpty(map.get(key).toString())) {
                return map.get(key).toString();
            } else {
                throw new RuntimeException("param " + key + " is null or empty");
            }
        } else {
            throw new RuntimeException("param " + key + " is null");
        }
    }

    public static int paramByInt(String key, Map<String, Object> map) {

        try {
            return paramByDouble(key, map).intValue();
        } catch (Exception e) {

        }
        return 0;
    }

    public static Double paramByDouble(String key, Map<String, Object> map) {
        String str = paramIsEmpty(key, map);
        try {
            if (StringUtils.isNotBlank(str)) {
                return Double.parseDouble(str.trim());
            }
        } catch (Exception e) {

        }
        return 0.0;
    }

    //拼接查询条件
    public static String spliceString(String stirng) {
        String returnString = "";
        if (!"".equals(stirng)) {
            String newStrings[] = stirng.split(",");
            for (int i = 0; i < newStrings.length; i++) {
                if (i == newStrings.length - 1) {
                    returnString = returnString + "'" + newStrings[i] + "'";
                } else {
                    returnString = returnString + "'" + newStrings[i] + "',";
                }
            }
        }
        return returnString;
    }
}

