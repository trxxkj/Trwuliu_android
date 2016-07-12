package cn.trxxkj.trwuliu.driver.bean;


import cn.trxxkj.trwuliu.driver.base.BaseReq;

/**
 * 获取手机验证码请求参数
 * @author cyh 2016.6.22下午15:23
 */

public class AppGetCodeReq extends BaseReq {
    //手机号
    private String account;
    // 0-注册；1-密码找回;2-登录验证码
    private String type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
