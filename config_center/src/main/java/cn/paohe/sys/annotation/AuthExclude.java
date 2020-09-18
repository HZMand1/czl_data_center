package cn.paohe.sys.annotation;

import java.lang.annotation.*;

/**TODO 排除不需要登录的接口
 * @author 夏家鹏
 * @date 2019/10/28 09:58
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthExclude {
}
