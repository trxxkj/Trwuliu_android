package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.MyDriverAdapter;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.DriverBean;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.MyDrivers;
import cn.trxxkj.trwuliu.driver.bean.UnconfirmPlan;
import cn.trxxkj.trwuliu.driver.bean.UnconfirmPlanBean;
import cn.trxxkj.trwuliu.driver.utils.App;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.TRurl;
import cn.trxxkj.trwuliu.driver.utils.TRurl1;
import cn.trxxkj.trwuliu.driver.utils.Utils;

/**
 * 计划详情
 * @author cyh 2016.6.20 上午11:51
 */

public class UnconfirmPlanActivity extends Activity implements View.OnClickListener {

    private AlertDialog.Builder builder;

    private ImageView imgBack;
    private Button confirmPlan;
    private Button rejectPlan;

    Context context;

    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    App app;
    private SharedPreferences sp;
    private UnconfirmPlanBean unconfirmPlanBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_unconfirm);

        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);
        app = (App) this.getApplication();
        context = this;

        initView();

    }

    private void initView(){

        imgBack = (ImageView) findViewById(R.id.img_back);
        confirmPlan = (Button) findViewById(R.id.confirmplan);
        rejectPlan = (Button) findViewById(R.id.rejectplan);

        imgBack.setOnClickListener(this);
        confirmPlan.setOnClickListener(this);
        rejectPlan.setOnClickListener(this);

    }

    public void getUnconfirmPlanDetail(){

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<UnconfirmPlan> appParam = new AppParam<>();

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));


        UnconfirmPlan body = new UnconfirmPlan();
        body.setId("");

        appParam.setHead(head);
        appParam.setBody(body);
        appParam.setSign("!&@#2016#");

        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl1.PLANDETAIL);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                System.out.println("-----------" + s + "---------------");
                msg.obj = s;
                msg.what = SUCCESS;
                unconfirmPlanHandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                msg.what = FAILTURE;
                msg.obj = throwable.getMessage();
                unconfirmPlanHandler.sendMessage(msg);

            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onCancelled(CancelledException e) {
            }

        });
    }

    private Handler unconfirmPlanHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS: // 成功

                    String result = (String) msg.obj;
                    Gson gson = new  Gson();

                    unconfirmPlanBean = gson.fromJson(result, UnconfirmPlanBean.class);

                    if ("000000".equals(unconfirmPlanBean.code)) {

                        // TODO  填充 adapter

//                        if (Integer.parseInt(driverBean.total) > 0) {
//
//                            dirvelist = driverBean.returnData;
//                            adapter = new MyDriverAdapter(context,dirvelist);
//                            lv_mydrive.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            lv_mydrive.setVisibility(View.VISIBLE);
//                            tv_nomsg.setVisibility(View.GONE);
//
//                        } else {
//
//                            dirvelist = new ArrayList<>();
//                            adapter = new MyDriverAdapter(context,dirvelist);
//                            lv_mydrive.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//
//                            lv_mydrive.setVisibility(View.GONE);
//                            tv_nomsg.setVisibility(View.VISIBLE);
//
//                        }

                    } else {  //E200002
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
            case R.id.img_back:
                finish();
                break;
            case R.id.confirmplan:
                confirmDialog(v);
                break;
            case R.id.rejectplan:
                rejectDialog(v);
                break;
        }
    }

    private void confirmDialog(View v) {
        builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("你的计划已确认");
        builder.setMessage("你的计划已经确认，请及时添加司机，如果有疑问请及时联系客服");

        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(),"你点击了确定按钮",Toast.LENGTH_SHORT).show();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void rejectDialog(View v) {
        builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("填写拒绝运单理由");

        /**
         * 设置内容区域为单选列表项
         */
        final String[] items = {"无法接受这么多货物", "车辆无法使用", "已有货物 无法接受", "身体不适"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), "You clicked "+items[i], Toast.LENGTH_SHORT).show();
            }
        });

        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(),"你点击了确定按钮",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), "你点击了取消按钮", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUnconfirmPlanDetail();
    }

}
