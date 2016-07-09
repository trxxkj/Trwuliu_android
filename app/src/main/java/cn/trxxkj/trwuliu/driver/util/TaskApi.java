package cn.trxxkj.trwuliu.driver.util;

/**
 * 服务端接口
 * @author cyh 2016.4.30 下午16:35
 */
public class TaskApi {

    private static String ip = "http://172.19.4.23:8091";
    /**
     * 数据静态初始化
     */

    // 用户正常登陆方式
    public static String userlogin = ip + "/app/member/login";
    // 用户注册
    public static String userreg = ip + "/publicMember/register";
    // 修改用户密码
    public static String updatepwd = ip + "";

}
