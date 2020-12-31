package cn.paohe.utils.DB;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/12/30 16:40
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    public static final String COMMON_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String COMMON_FORMAT_DAY = "yyyy-MM-dd";

    public static String findKeyByVal(Map<String, String> map, String val) {
        String resultStr = "";
        if (CollectionUtils.isEmpty(map)) {
            return resultStr;
        }
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value != null) {
                if (value.equals(val)) {
                    return key;
                }
            }

        }
        return resultStr;
    }


    public static  Map<String, Object> toMap(String key,Object val){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(key,val);
        return resultMap;
    }
    public static Map<String, Object> mapClone(Map<String, Object> map) {
        Set<String> keys = map.keySet();
        if (CollectionUtils.isEmpty(keys)) {
            return map;
        }
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : keys) {
            Object o = map.get(key);
            resultMap.put(key, o);
        }
        return resultMap;
    }

    /**
     * 新增添加操作人员信息
     * @param map
     * @param tokenId
     */
    public static void addMapUser(Map<String, Object> map,String tokenId){
        map.put("createUser", tokenId);
        map.put("updateUser", tokenId);
        map.put("createTime", new Date());
        map.put("updateTime", new Date());
    }

    /**
     * 修改改变人员信息
     * @param map
     * @param tokenId
     */
    public static void editMapUser(Map<String, Object> map,String tokenId){
        map.put("updateUser", tokenId);
        map.put("updateTime", new Date());
    }


    public static Map<String, Object> mapByMsg(String msg) {
        Map<String, Object> resultContentMap = new HashMap<>();
        resultContentMap.put("msg", msg);
        return resultContentMap;
    }

    //拼接查询条件
    public static void spliceMap(Map<String, Object> map, String... inArr) {
        if (inArr.length > 0) {
            for (String inStr : inArr) {
                String val = ValidateUtil.paramIsEmpty(inStr, map);
                if (!StringUtils.isEmpty(val)) {
                    String[] split = val.split(",");
                    if (split != null && split.length > 0) {
                        String newStr = "";
                        for (String s : split) {
                            if (newStr.length() > 0) {
                                newStr = newStr + ",";
                            }
                            newStr += "'" + s + "'";
                        }
                        map.put(inStr, newStr);
                    }

                }
            }
        }
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        for (String key : map.keySet()) {
            Object obj = map.get(key);
            if ("pageSize".equals(key)) {
                Integer pageSize = objToInt(obj);
                map.put("pageSize", pageSize);
                continue;
            }
            if ("pageNum".equals(key)) {
                Integer pageSize = objToInt(map.get("pageSize"));
                Integer pageNum = objToInt(obj) - 1;
                map.put("pageNum", pageNum * pageSize);
                continue;
            }
            if (obj instanceof String) {
                map.put(key, obj.toString());
            }
        }
    }

    public static int objToInt(Object obj) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {

        }
        return 0;
    }

    //拼接查询条件
    public static String spliceString(String stirng) {
        String returnString = "";
        if (!StringUtils.isEmpty(stirng)) {
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
    public static void splitMapItem(Map<String, Object> map, String key){
        if (map.get(key) != null && org.apache.commons.lang.StringUtils.isNotEmpty(map.get(key).toString())){
            String keyValue =  map.get(key).toString();
            if (keyValue.contains(",")){
                keyValue = "'" + org.apache.commons.lang.StringUtils.join(keyValue.split(","),"','") + "'";
            }else{
                keyValue = "'" + keyValue + "'";
            }
            map.put(key,keyValue);
        }else {
            map.put(key,"");
        }
    }

    //把 下划线转换成 首字母大写形式
    public static String xhxToJavaStr(String key) {
        if (StringUtils.isEmpty(key)) {
            return key;
        }
        if (!key.contains("_")) {
            return key;
        }
        String returnStr = "";
        boolean b = false;
        for (char c : key.toCharArray()) {
            if (c == '_') {
                b = true;
                continue;
            }
            String tmpStr = c + "";
            if (b) {
                b = false;
                tmpStr = tmpStr.toUpperCase();
            }
            returnStr += tmpStr;

        }
        return returnStr;
    }

    /**
     * java驼峰字符串转换为数据库_格式字符
     * @param key
     * @return
     */
    public static String javaStrToXhx(String key) {
        if (StringUtils.isEmpty(key)) {
            return key;
        }
        String returnStr = "";
        String tempStr = "";
        for (int i = 0; i < key.length(); i++) {
            tempStr = Character.isUpperCase(key.charAt(i))?("_"+key.charAt(i)).toLowerCase():key.charAt(i)+"";
            returnStr = returnStr + tempStr;
        }
        return returnStr;
    }

    /**
     * 数据库获得查询信息转换为Java驼峰格式
     * @param map
     * @return
     */
    public static Map<String, Object> dataMap2Java(Map<String, Object> map){
        Map<String, Object> newMap = new HashMap<>();
        if(CollectionUtils.isEmpty(map)){
            return newMap;
        }
        for(String key:map.keySet()){
            newMap.put(xhxToJavaStr(key),map.get(key));
        }
        return newMap;
    }

    /**
     * java驼峰格式转换为数据表格式
     * @param map
     * @return
     */
    public static Map<String, Object> javaMap2Data(Map<String, Object> map){
        Map<String, Object> newMap = new HashMap<>();
        if(CollectionUtils.isEmpty(map)){
            return newMap;
        }
        for(String key:map.keySet()){
            newMap.put(javaStrToXhx(key),map.get(key));
        }
        return newMap;
    }

    public static void timestampToStr(Map<String, Object> map, String key, boolean isDay) {
        if (map == null) return;
        Object timestamp = map.get(key);
        if (timestamp instanceof Timestamp) {
            SimpleDateFormat format = new SimpleDateFormat(isDay ? COMMON_FORMAT_DAY : COMMON_FORMAT_DATETIME);
            Date dateTime = new Date(((Timestamp) timestamp).getTime());
            map.put(key, format.format(dateTime));
        }
    }
    public static void diguiTree(Map<String, Object> objectMap, List<Map<String, Object>> list) {
        String key1 = "id";
        String key2 = "pid";
        String id = ValidateUtil.paramIsEmpty(key1, objectMap);
        if (!CollectionUtils.isEmpty(list) && !StringUtils.isEmpty(id)) {
            for (Map<String, Object> map : list) {
                String pid = ValidateUtil.paramIsEmpty(key2, map);
                if (id.equals(pid)) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) objectMap.get("children");
                    if (CollectionUtils.isEmpty(children)) {
                        children = new ArrayList<>();
                    }
                    children.add(map);
                    objectMap.put("children", children);

                    diguiTree(map, list);
                }
            }
        }
    }
    public static void listMapSort(List<Map<String, Object>> maps, String key) {
        if (CollectionUtils.isEmpty(maps)) {
            return;
        }
        maps.sort((a, b) -> {
            String s1 = ValidateUtil.paramIsEmpty(key, a);
            String s2 = ValidateUtil.paramIsEmpty(key, b);
            return s1.compareTo(s2);
        });
    }
}

