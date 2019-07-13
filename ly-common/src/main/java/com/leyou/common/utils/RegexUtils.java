package com.leyou.common.utils;

import com.leyou.common.utils.constants.RegexPatterns;
import org.apache.commons.lang3.StringUtils;


public class RegexUtils {
    /**
     * 是否符合手机格式
     *
     * @param phone 要校验的手机号
     * @return true:符合，false：不符合
     */
    public static boolean isPhone(String phone) {
        return matches(phone, RegexPatterns.PHONE_REGEX);
    }

    /**
     * 是否符合邮箱格式
     *
     * @param email 要校验的邮箱
     * @return true:符合，false：不符合
     */
    public static boolean isEmail(String email) {
        return matches(email, RegexPatterns.EMAIL_REGEX);
    }

    /**
     * 是否符合用户名格式
     * <pre>createTime:
     * 7/13/19 7:17 PM</pre>
     *
     * @param userName 要校验的用户名
     * @return true:符合，false：不符合
     */
    public static boolean isUserName(String userName) {
        return matches(userName, RegexPatterns.USERNAME_REGEX);
    }

    /**
     * 是否符合密码格式
     * <pre>createTime:
     * 7/13/19 7:25 PM</pre>
     *
     * @param userName 要校验的密码
     * @return true:符合，false：不符合
     */
    public static boolean isPassword(String userName) {
        return matches(userName, RegexPatterns.PASSWORD_REGEX);
    }

    private static boolean matches(String str, String regex) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.matches(regex);
    }
}
