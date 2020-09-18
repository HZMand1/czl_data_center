package cn.paohe.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**TODO MD5密码加密
 * @author: 黄芝民
 * @date: 2020/10/21 16:50
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public class Md5 {

    private final static Logger logger = LoggerFactory.getLogger(Md5.class);

    public static String getMD5(byte[] source) {
        String s = null;
        // 用来将字节转换成 16 进制表示的字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("Md5");
            md.update(source);
            // Md5 的计算结果是一个 128 位的长整数，
            byte tmp[] = md.digest();
            // 用字节表示就是 16 个字节
            // 每个字节用 16 进制表示的话，使用两个字符，
            char str[] = new char[16 * 2];
            // 所以表示成 16 进制需要 32 个字符
            // 表示转换结果中对应的字符位置
            int k = 0;
            // 从第一个字节开始，对 Md5 的每一个字节
            for (int i = 0; i < 16; i++) {
                // 转换成 16 进制字符的转换
                // 取第 i 个字节
                byte byte0 = tmp[i];
                // 取字节中高 4 位的数字转换,
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                // >>>
                // 为逻辑右移，将符号位一起右移
                // 取字节中低 4 位的数字转换
                str[k++] = hexDigits[byte0 & 0xf];
            }
            // 换后的结果转换为字符串
            s = new String(str);
        } catch (Exception e) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", e);
        }
        return s;
    }

//    // 96e79218965eb72c92a549dd5a330112
//    public static void main(String[] args) {
//        System.out.println(Md5.getMD5("111111".getBytes()));
//    }
}
