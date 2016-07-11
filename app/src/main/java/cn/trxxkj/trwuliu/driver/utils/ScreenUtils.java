package cn.trxxkj.trwuliu.driver.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * 日期轮播选择框基础控件
 * @author cyh 2016.5.30 下午19:30
 */
public final class ScreenUtils {

    public static int getScreenWidth(Context context) {
        WindowManager windowMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowMgr.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowMgr = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowMgr.getDefaultDisplay().getHeight();
    }

    private ScreenUtils() {
    }

}
