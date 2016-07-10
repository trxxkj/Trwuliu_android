package cn.trxxkj.trwuliu.driver.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.MyDriverAdapter;
import cn.trxxkj.trwuliu.driver.ui.fragment.MyDriverFragment;
import cn.trxxkj.trwuliu.driver.utils.TRurl;

/**
 * 我的司机
 * @author cyh 2016.6.14 上午9:21
 */

public class MyDriverActivity extends FragmentActivity implements View.OnClickListener {

    Context context;

    private ViewPager viewPager;
    private ImageView imgBack;
    private TextView addDriver;
    private ListView lv_mydrive;

    private MyDriverFragment myDriverFragment;

    private ArrayList<Fragment> fragmentList;


    private MyDriverAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_driver);

        context = this;
        initView();

        getData();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        addDriver = (TextView) findViewById(R.id.add_driver);
        lv_mydrive = (ListView) findViewById(R.id.lv_mydrive);

        adapter = new MyDriverAdapter(context);
        lv_mydrive.setAdapter(adapter);



//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        myDriverFragment = new MyDriverFragment();
//        fragmentList = new ArrayList<>();
//        fragmentList.add(myDriverFragment);
//        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));


        imgBack.setOnClickListener(this);
        addDriver.setOnClickListener(this);

    }

    /**
     *  获取数据
     */
    public void getData() {

        //TODO  获取数据

        RequestParams params = new RequestParams();

        params.addBodyParameter("pageNo", "10");
        params.addBodyParameter("memberId", "");
        params.addBodyParameter("search", "");

//        requestUrl(HttpMethod.POST, TRurl.MYDRIVER, params,
//                new RequestCallBack<String>() {
//
//                    Message msg = Message.obtain();
//
//                    @Override
//                    public void onSuccess( ResponseInfo<String> responseInfo) {
//                        String json = responseInfo.result;
//                        System.out.println("-----------" + json
//                                + "-------------------");
//
////                        msg.obj = json;
////                        msg.what = SUCCESS;
////                        handler.sendMessage(msg);
//
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msgback) {
//
////                        msg.what = FAILTURE;
////                        handler.sendMessage(msg);
//
//                    }
//
//                });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_driver:
                startActivity(new Intent(this, AddDriverActivity.class));
                break;
        }
    }


    /**
     * 判断网络连接状态
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

//    // 访问网络
//    public <T> void requestUrl(HttpMethod method, String url,
//                               RequestParams params, RequestCallBack<T> callBack) {
//
//        HttpUtils httpUtils = new HttpUtils();
//        httpUtils.configCurrentHttpCacheExpiry(5000);// 设置缓存5秒,5秒内直接返回上次成功请求的结果。
////        DefaultHttpClient httpClient = (DefaultHttpClient) httpUtils
////                .getHttpClient();
//
//        boolean i = isNetworkConnected(this);
//        if (i == false) {
//
//            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        httpUtils.send(method, url, params, callBack);
//
//    }




}
