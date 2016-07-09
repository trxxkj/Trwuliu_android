package cn.trxxkj.trwuliu.driver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @描述: 效验工具类
 * @作者: hedeyou
 * @时间: 2014-4-23 下午3:35:02 
 * @版本: V1.0.0.0
 */
public class CheckUtil {
    /**
     * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
     * 国家号码段分配如下： 　　移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 　　联通：130、131、132、152、155、156、185、186 　　电信：133、153、180、189、（1349卫通）
     */
    public static boolean checkMobile(String mobile) {
        if (mobile.startsWith("1") && mobile.length() == 11) {
            return true;//mobile.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        } else {
            return false;
        }
    }

    public static boolean checkMail(String mailLink) {
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(mailLink);
        return m.find();
    }

    /**
     * 检查字符中是否含有特殊字符 add by dyhe
     **/
    public static boolean checkSpecialChar(String scStr) {
        Pattern s = Pattern.compile("\\p{Punct}+");
        Matcher m4 = s.matcher(scStr);//判断是否含有特殊字符
        return m4.find(0);
    }

    /**
     * 提供密码复杂度自检能力，
     * 使用大写字母、小写字母、数字、标点及特殊字符四种字符中至少三种的组合方式
     **/
    public static boolean checkPassword(String password) {
        int countRight = 0;
        Pattern p = Pattern.compile("[A-Z]+");
        Pattern q = Pattern.compile("[a-z]+");
        Pattern r = Pattern.compile("[0-9]+");
        Pattern s = Pattern.compile("\\p{Punct}+");
        Matcher m1 = p.matcher(password); //判断是否含有大写字符
        Matcher m2 = q.matcher(password);  //判断是否含有小写字符
        Matcher m3 = r.matcher(password);//判断是否含有数字
        Matcher m4 = s.matcher(password);//判断是否含有特殊字符
        countRight = m1.find(0) ? countRight + 1 : countRight;
        countRight = m2.find(0) ? countRight + 1 : countRight;
        countRight = m3.find(0) ? countRight + 1 : countRight;
        countRight = m4.find(0) ? countRight + 1 : countRight;
        return countRight >= 3;
    }

    /**
     * 判断是否含有特殊字符
     * @param str
     * @author hedeyou
     * @date 2013-10-24
     * @return 正确：true 错误：false
     */
//    public static boolean checkIndexPunct(String str){
//    	return check("\\p{Punct}+", str);
//    }
    private static boolean check(String regex, String str) {
        Pattern patt = Pattern.compile(regex);
        Matcher matcher = patt.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断一个字符串是否包含字符字母
     * @param
     * @author hedeyou
     * @date 2013-10-24
     * @return 正确：true 错误：false
     */
    public static boolean checkStr(String urlStr) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(urlStr);
        return matcher.find();
    }

    public static boolean checkIsNumeric(String str) {
        if (StringUtil.isNotEmpty(str)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(str).matches();
        } else {
            return false;
        }
    }

    /**
     * 判断一个链接是否正确
     * @param urlString
     * 网络链接字符串
     * @author hedeyou
     * @date 2013-10-24
     * @return 正确：true 错误：false
     */
    public static boolean checkUrl(String urlStr) {
        if (StringUtil.isNotEmpty(urlStr)) {
            return check("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", urlStr);
        } else {
            return false;
        }
    }
}
