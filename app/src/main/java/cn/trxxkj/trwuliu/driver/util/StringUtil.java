package cn.trxxkj.trwuliu.driver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/5/4.
 */
public class StringUtil {

    /* 说明：移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
      * 联通：130、131、132、152、155、156、185、186
      * 电信：133、153、180、189
      * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
      * 验证号码 手机号 固话均可
      * 作者：曹玉贺
      * 2016年4月30日 13:52:35
      */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    /**
     * 判断文本是否不为空
     * @param text
     * @return true:不为空；false：空
     * @author hedeyou
     */
    public static final boolean isNotEmpty(String text) {
        if (null != text && !"".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断文本是否不为空
     * @param text
     * @return true:不为空；false：空
     * @author hedeyou
     */
    public static final boolean isEmpty(String text) {
        if (null == text) {
            return true;
        } else if ("".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断一个字符串是否是数字
     * @author hedeyou
     * @date 2013-10-11
     */
    public static boolean isNum(String str) {
        if (isNotEmpty(str)) {
            return str
                    .matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        } else {
            return false;
        }
    }

}
