package cn.trxxkj.trwuliu.driver.utils;

import android.util.Log;

/**
 * Created by guozhengwei on 2016/7/13.
 */
public class MyLog {


    public static void i(String tag, String msg) {
        if (MyContents.ISLOG)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (MyContents.ISLOG)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (MyContents.ISLOG)
            Log.d(tag, msg);

    }

    public static void w(String tag, String msg) {
        if (MyContents.ISLOG)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (MyContents.ISLOG)
            Log.e(tag, msg);
    }

}
