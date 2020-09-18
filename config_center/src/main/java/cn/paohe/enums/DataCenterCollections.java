package cn.paohe.enums;

import java.util.Calendar;
import java.util.Date;

/**
 * TODO  采芝林数据中心枚举类集合
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020年09月18日 上午9:09:10
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public class DataCenterCollections {

    /**
     * TODO  http请求响应状态码
     *
     * @version V1.0
     * @author: 黄芝民
     * @date: 2020年6月26日 下午2:36:21
     * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
     */
    public static enum RestHttpStatus {
        OK(200, "成功"), BAD(300, "不存在"), REDICT(302, "重定向"),
        PARAM_ERROR(400, "参数错误或其他"), AUTH_ERROR(403, "权限不足"), NOT_FOUND(404, "请求资源不存在"),
        ERROR(500, "服务器出错"), AJAX_CODE_YES(1, "AjaxRest对象retCode为1"), AJAX_CODE_NO(0, "AjaxRest对象retCode为0");

        public final int value;

        private final String desc;

        private RestHttpStatus(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDesc(int type) {
            for (RestHttpStatus enumType : RestHttpStatus.values()) {
                if (enumType.value == type) {
                    return enumType.getDesc();
                }
            }
            return "" + type;
        }
    }


    /**
     * TODO  是否可用枚举
     *
     * @version V1.0
     * @author: 黄芝民
     * @date: 2020年10月18日 上午9:11:22
     * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
     */
    public static enum YesOrNo {
        YES(1, "是"), NO(0, "否");

        public final int value;

        private final String desc;

        private YesOrNo(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDesc(int type) {
            for (YesOrNo enumType : YesOrNo.values()) {
                if (enumType.value == type) {
                    return enumType.getDesc();
                }
            }
            return "" + type;
        }
    }


    /**
     * TODO  审核枚举类
     *
     * @version V1.0
     * @author: 黄芝民
     * @date: 2020年6月26日 下午2:36:01
     * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
     */
    public static enum MenuTypeEnum {
        MENU(0, "菜单"), BUTTON(1, "按钮");

        public final int value;

        private final String desc;

        private MenuTypeEnum(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDesc(int type) {
            for (MenuTypeEnum enumType : MenuTypeEnum.values()) {
                if (enumType.value == type) {
                    return enumType.getDesc();
                }
            }
            return "" + type;
        }
    }

    /**
     * 签署方式-自动签
     *
     * @version V1.0
     * @author: 黄芝民
     * @date: 2020年7月11日 下午1:50:54
     * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
     */
    public static enum ContractTimeLimitEnum {

        ONEMATH("oneMath", "1个月"), THREEMATH("threeMath", "3个月"), ONEYEAR("other", "一年");

        public final String value;

        private final String desc;

        private ContractTimeLimitEnum(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static String getDesc(String type) {
            for (ContractTimeLimitEnum enumType : ContractTimeLimitEnum.values()) {
                if (enumType.value.equalsIgnoreCase(type)) {
                    return enumType.getDesc();
                }
            }
            return "" + type;
        }

        public static Date finishTime(Date date, String type) {
            Date time = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (type.equals(ContractTimeLimitEnum.ONEMATH.value)) {
                //一个月
                calendar.add(Calendar.MONTH, 1);
                time = calendar.getTime();
            }
            if (type.equals(ContractTimeLimitEnum.THREEMATH.value)) {
                //一个月
                calendar.add(Calendar.MONTH, 3);
                time = calendar.getTime();
            }
            if (type.equals(ContractTimeLimitEnum.ONEYEAR.value)) {
                //一个月
                calendar.add(Calendar.YEAR, 1);
                time = calendar.getTime();
            }

            return time;
        }
    }
}
