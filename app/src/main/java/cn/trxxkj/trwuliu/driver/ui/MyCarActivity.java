package cn.trxxkj.trwuliu.driver.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.MyCarAdapter;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.BindDriverPost;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.MyCarReturn;
import cn.trxxkj.trwuliu.driver.bean.MyDrivers;
import cn.trxxkj.trwuliu.driver.bean.RemoveDriverToCar;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.MyLog;
import cn.trxxkj.trwuliu.driver.utils.TRurl;
import cn.trxxkj.trwuliu.driver.utils.Utils;
import cn.trxxkj.trwuliu.driver.utils.XUtilsPost;

/**
 * 我的车辆
 *
 */
public class MyCarActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView imgBack;
    private TextView addCar;

    private ListView lv_mydrive;
    private ImageView iv_search;
    private EditText search_input;
    private TextView tv_nomsg;

    private MyCarAdapter myCarAdapter;

    private MyCarReturn myCarReturn;

    private SharedPreferences sp;
    private XUtilsPost post;

    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        context = this;
        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);

        post = new XUtilsPost(context, myDhandler,sp);

        initView();

        getData("");


    }

    /**
     * 初始化控件
     */
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        addCar = (TextView) findViewById(R.id.add_car);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        search_input = (EditText) findViewById(R.id.search_input);
        tv_nomsg = (TextView) findViewById(R.id.tv_nomsg);

        lv_mydrive = (ListView) findViewById(R.id.lv_mydrive);


        iv_search.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        addCar.setOnClickListener(this);

    }

    /**
     *  获取数据
     */
    public void getData(String search) {

        if (!Utils.isNetworkConnected(this)){
            Toast.makeText(this,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<MyDrivers> appParam = new AppParam<>();

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));

        // 和 我的 司机 参数一样
        MyDrivers body = new MyDrivers();
        body.setMemberId(sp.getString(MyContents.ID , ""));
        body.setSearch(search);
        body.setPageNo("");

        appParam.setHead(head);
        appParam.setBody(body);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.MYCAR);

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
    private List<MyCarReturn.Mycar> mycarList;

    private Handler myDhandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS: // 成功

                    String result = (String) msg.obj;
                    Gson gson = new  Gson();

                    myCarReturn = gson.fromJson(result, MyCarReturn.class);

                    if ("000000".equals(myCarReturn.code)) {

                        if (Integer.parseInt(myCarReturn.total) > 0) {

                            mycarList = myCarReturn.returnData;

                            myCarAdapter = new MyCarAdapter(mycarList,context);
                            lv_mydrive.setAdapter(myCarAdapter);

                            myCarAdapter.notifyDataSetChanged();
                            lv_mydrive.setVisibility(View.VISIBLE);
                            tv_nomsg.setVisibility(View.GONE);

                        } else {

                            mycarList = new ArrayList<>();

                            myCarAdapter = new MyCarAdapter(mycarList,context );
                            lv_mydrive.setAdapter(myCarAdapter);
                            myCarAdapter.notifyDataSetChanged();

                            lv_mydrive.setVisibility(View.GONE);
                            tv_nomsg.setVisibility(View.VISIBLE);

                        }

                    } else {  //E200002
                        Toast.makeText(getApplicationContext(), "请求失败" , Toast.LENGTH_SHORT).show();
                    }
                    break;

                case FAILTURE: // 失败
//                    String error = (String) msg.obj;
                    Toast.makeText(getApplicationContext() , "网络异常！" ,Toast.LENGTH_SHORT).show();

                    break;
                case XUtilsPost.FINALHTTP_RETURN_SUCCESS_ONE: // 添加成功

                    result = (String) msg.obj;

                    try {

                        JSONObject jsonObject = new JSONObject(result);

                        if ("000000".equals(jsonObject.getString("code"))) {

                            getData("");

                        } else {
                            Utils.showToast(MyCarActivity.this,jsonObject.getString("message"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case XUtilsPost.FINALHTTP_RETURN_FAILED_ONE:  // 添加 失败

                    Utils.showToast(MyCarActivity.this,"添加失败");

                    break;


                default:
                    break;

            }

        }

    };


    /**
     * 解除绑定
     */
    public void removeDriver(MyCarReturn.Mycar mycar) {

        // CARREMOVEDRIVER

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<RemoveDriverToCar> appParam = new AppParam<>();

        RemoveDriverToCar body = new RemoveDriverToCar();

        body.setId(mycar.vehiDriverId);

        appParam.setBody(body);

        post.doPost(TRurl.CARREMOVEDRIVER, appParam);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:  // 返回
                finish();
                break;
            case R.id.add_car: // 添加 车辆
                startActivity(new Intent(this, AddCarActivity.class));
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
