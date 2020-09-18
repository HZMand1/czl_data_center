package cn.paohe.utils;

/**TODO 异常信息设置
 * @author 夏家鹏
 * @date 2019/11/4 13:41
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class ErrorMessageUtils {

    /**TODO 设置返回给前端的异常信息
     * @param massage 要在前端展示的提示信息
     * @return
     * @author 夏家鹏
     * @date 2019/11/4 14:03
     * @throws
     */
    public static void setErrorMessage(String massage) {
        throw new RuntimeException("seed(((" + massage + ")))seed");
    }
}
