package cn.trxxkj.trwuliu.driver.utils;

import android.app.Application;

import org.xutils.x;

/**
 * Created by guozhengwei on 2016/7/10.
 */
public class App extends Application {


    private String Token;

    private String userid;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
