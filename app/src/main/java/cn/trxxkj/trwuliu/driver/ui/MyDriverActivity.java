package cn.trxxkj.trwuliu.driver.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.MyDriverAdapter;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.DriverBean;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.MyDrivers;
import cn.trxxkj.trwuliu.driver.utils.App;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.MyLog;
import cn.trxxkj.trwuliu.driver.utils.TRurl;
import cn.trxxkj.trwuliu.driver.utils.Utils;


/**
 * 我的司机
 * @author cyh 2016.6.14 上午9:21
 */

public class MyDriverActivity extends FragmentActivity implements View.OnClickListener {

    Context context;

    private ImageView imgBack;
    private TextView addDriver;
    private ListView lv_mydrive;
    private ImageView iv_search;
    private EditText search_input;

    private TextView tv_nomsg;

    private MyDriverAdapter adapter;

    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    App app;
    private SharedPreferences sp;
    private DriverBean driverBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_driver);

        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);

        app = (App) this.getApplication();
        context = this;
        initView();

        getData("");
    }

    /**
     * 初始化控件
     */
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        addDriver = (TextView) findViewById(R.id.add_driver);
        lv_mydrive = (ListView) findViewById(R.id.lv_mydrive);
        search_input = (EditText) findViewById(R.id.search_input);

        tv_nomsg = (TextView) findViewById(R.id.tv_nomsg);

        iv_search.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        addDriver.setOnClickListener(this);

    }

    /**
     *  获取数据
     */
    public void getData(String search) {

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<MyDrivers> appParam = new AppParam<>();

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));


        MyDrivers body = new MyDrivers();
        body.setMemberId(sp.getString(MyContents.ID , ""));
        body.setSearch(search);
        body.setPageNo("");

        appParam.setHead(head);
        appParam.setBody(body);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.MYDRIVER);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                MyLog.e("mydriver","-----------" + s + "---------------");
                msg.obj = s;
                msg.what = SUCCESS;
                myDhandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                msg.what = FAILTURE;
                msg.obj = throwable.getMessage();
                myDhandler.sendMessage(msg);

            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onCancelled(CancelledException e) {
            }

        });


    }


    /**
     * 司机 数据 集合
     */
    private List<DriverBean.ReturnData> dirvelist;

    private Handler myDhandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS: // 成功

                    String result = (String) msg.obj;
                    Gson gson = new  Gson();

                    driverBean = gson.fromJson(result, DriverBean.class);

                    if ("000000".equals(driverBean.code)) {

                        // TODO  填充 adapter

                        if (Integer.parseInt(driverBean.total) > 0) {

                            dirvelist = driverBean.returnData;
                            adapter = new MyDriverAdapter(context,dirvelist);
                            lv_mydrive.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            lv_mydrive.setVisibility(View.VISIBLE);
                            tv_nomsg.setVisibility(View.GONE);

                        } else {

                            dirvelist = new ArrayList<>();
                            adapter = new MyDriverAdapter(context,dirvelist);
                            lv_mydrive.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            lv_mydrive.setVisibility(View.GONE);
                            tv_nomsg.setVisibility(View.VISIBLE);

                        }

                    } else {  // E200002
                        Toast.makeText(context , "请求失败" , Toast.LENGTH_SHORT).show();
                    }
                    break;

                case FAILTURE: // 失败
//                    String error = (String) msg.obj;
                    Toast.makeText(context , "网络异常！" ,Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;

            }

        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: // 返回
                finish();
                break;
            case R.id.add_driver:  // 添加 司机
                startActivity(new Intent(this, AddDriverActivity.class));
                break;
            case R.id.iv_search:  // 搜索

                String sear = search_input.getText().toString().trim();
                getData(sear);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData("");
    }
}
