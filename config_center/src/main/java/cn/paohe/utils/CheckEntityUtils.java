package cn.paohe.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**TODO 校验实体
 * @author: 夏家鹏
 * @date: 2019/11/4 13:52 
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class CheckEntityUtils {

    /**TODO 批量判断实体字段是否为空
     * @param obj 需要校验的实体对象
     * @param fieldMap (key为实体里需要校验的字段，value为该字段的描述，描述最终会展示给前端)
     * @return
     * @author 夏家鹏
     * @date 2019/11/4 13:53
     * @throws
     */
    public static void checkEntity(Object obj, Map<String, String> fieldMap) {
        try {
            Class<?> objClass = obj.getClass();
            for (String fieldName : fieldMap.keySet()) {
                Field field = objClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object objValue = field.get(obj);
                if (objValue == null || "".equals(objValue)) {
                    ErrorMessageUtils.setErrorMessage(fieldMap.get(fieldName) + "不能为空");
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
