package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.AdddriverPost;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.DriverSearchPostBean;
import cn.trxxkj.trwuliu.driver.bean.DriverSearchReturn;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.utils.BitmapCache;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.TRurl;
import cn.trxxkj.trwuliu.driver.utils.Utils;

/**
 * 添加司机
 * @author cyh 2016.6.10 上午9:25
 */

public class AddDriverActivity extends Activity implements View.OnClickListener {

    private ImageView imgBack;

    private EditText search_input;  // 输入的账号
    private ImageView iv_search;
    private ImageView iv_license;
    private Button btn_add; // 确定
    private Button btn_cancel;  // 取消

    private EditText et_nickname;

    private TextView driver_name;


    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    // 查询 出的  司机信息 layout
    private RelativeLayout rl_drivermsg;
    private SharedPreferences sp;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        context = this;

        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);

        initView();
    }

    //初始化视图界面
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        rl_drivermsg = (RelativeLayout) findViewById(R.id.rl_drivermsg);
        search_input = (EditText) findViewById(R.id.search_input);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        iv_license = (ImageView) findViewById(R.id.iv_license);

        driver_name = (TextView) findViewById(R.id.driver_name);
        et_nickname = (EditText) findViewById(R.id.et_nickname);

        iv_search.setOnClickListener(this);
        imgBack.setOnClickListener(this);

        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: // 返回
                finish();
                break;
            case R.id.iv_search:  // 搜索

                searchDriver(search_input.getText().toString());
                break;

            case R.id.btn_add: // 添加

                addDriver();

                break;
            case R.id.btn_cancel: // 取消

                rl_drivermsg.setVisibility(View.GONE);
                break;
        }
    }

    /**'
     * 查询 司机  根据账号
     */
    private void searchDriver(String search) {

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<DriverSearchPostBean> appParam = new AppParam<>();

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));

        DriverSearchPostBean dr = new DriverSearchPostBean();
        dr.setTelnum(search);

        appParam.setHead(head);
        appParam.setBody(dr);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.SEARCHDRIVER);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                System.out.println("-----------" + s + "---------------");

                msg.obj = s;
                msg.what = SUCCESS;
                myDhandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                System.out.println("-----------" + throwable.getMessage() + "---------------");

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

    public DriverSearchReturn dsr;

    private Handler myDhandler = new Handler() {

        public void handleMessage(Message msg) {

            String result;

            switch (msg.what) {

                case SUCCESS: // 成功

                    result = (String) msg.obj;
                    Gson gson = new  Gson();
                    dsr = gson.fromJson(result, DriverSearchReturn.class);

                    if ("000000".equals(dsr.code)) {

                        setData();

                    } else {  // E200002

                        setData2();
                        Toast.makeText(context , "没有信息" , Toast.LENGTH_SHORT).show();
                    }

                    break;

                case FAILTURE: // 失败
//                    String error = (String) msg.obj;
                    Toast.makeText(context , "网络异常！" ,Toast.LENGTH_SHORT).show();

                    break;

                case 101:  // 添加司机  成功  返回数据

                    result = (String) msg.obj;

                    try {

                        JSONObject jsonObject = new JSONObject(result);

                        if ("000000".equals(jsonObject.getString("code"))) {

                            Toast.makeText(context , "添加成功！" ,Toast.LENGTH_SHORT).show();
                            rl_drivermsg.setVisibility(View.GONE);

                        } else {

                            rl_drivermsg.setVisibility(View.GONE);

                            Toast.makeText(context , jsonObject.getString("message") ,Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if () {
//
//                    }

                    break;

                case 102 :  //  请求失败



                    break;
                default:
                    break;

            }

        }

    };

    /**
     *  将 数据 设置到view
     */
    public void setData() {

        rl_drivermsg.setVisibility(View.VISIBLE);
        driver_name.setText(dsr.returnData.userName);

        RequestQueue mQueue  = Volley.newRequestQueue(context);

        ImageLoader mImageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_license,
                R.drawable.pic_bg_normal, android.R.drawable.ic_delete);

        //   http://images.csdn.net/20130609/zhuanti.jpg
        mImageLoader.get(dsr.returnData.driveImagePath , listener);

    }

    /**
     *  隐藏 布局
     */
    public void setData2() {

        rl_drivermsg.setVisibility(View.GONE);

    }


    /**
     * 添加  司机
     */
    public void addDriver() {

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if (!"1".equals(dsr.returnData.driverpercheck)) {

            Toast.makeText(context,"该账户不是司机,请检查" ,Toast.LENGTH_LONG).show();
            return;
        }


        AppParam<AdddriverPost> appParam = new AppParam<>();

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));

        AdddriverPost adddriverPost = new AdddriverPost();

        adddriverPost.setDriverid(dsr.returnData.id);
        adddriverPost.setDrivername(dsr.returnData.userName);
        adddriverPost.setDrivertel(dsr.returnData.cellPhone);
        adddriverPost.setRemarkname(et_nickname.getText().toString().trim());


        appParam.setHead(head);
        appParam.setBody(adddriverPost);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.ADDDRIVER);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                System.out.println("-----------" + s + "---------------");

                msg.obj = s;
                msg.what = 101;
                myDhandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                System.out.println("-----------" + throwable.getMessage() + "---------------");

                msg.what = 102;
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

}
