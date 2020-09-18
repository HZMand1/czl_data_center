package cn.paohe.sys.config;

import cn.paohe.vo.framework.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**TODO 统一异常处理
 * @author 夏家鹏
 * @date 2019/11/4 13:41
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@RestControllerAdvice
public class CenterExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public AjaxResult error(Exception e) {
        e.printStackTrace();
        return new AjaxResult("服务器异常，请联系管理员：" + e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public AjaxResult nullPointerError(Exception e) {
        e.printStackTrace();
        return new AjaxResult("空指针异常：请检查您的入参");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public AjaxResult runError(Exception e) {
        e.printStackTrace();
        return new AjaxResult(toStr(e.getMessage()));
    }

    private String toStr(String str) {
        int begin = str.indexOf("seed(((") + 7;
        int end = str.indexOf(")))seed");
        if (begin > 0 && end > 0) {
            str = str.substring(begin, end);
        }
        return str;
    }
}
