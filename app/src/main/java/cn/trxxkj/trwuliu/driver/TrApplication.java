package cn.trxxkj.trwuliu.driver;


import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * 全局Application
 * @author cyh 2016.5.10 下午15:35
 */
public class TrApplication extends Application {

    //全局变量
    public Bitmap mBitmap;

    private ArrayList<Activity> mList = new ArrayList<Activity>();
    private ArrayList<Activity> detailList = new ArrayList<Activity>();
    private static TrApplication instance;

    private TrApplication() {

    }

    public synchronized static TrApplication getInstance() {
        if (null == instance) {
            instance = new TrApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }



    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /*
    * 作用：结束一个 Activity
    * 更新：2016-06-14
    */
    public void finishActivity(Activity activity) {

        activity.finish();

    }

}




