package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.AppGetCodeReq;
import cn.trxxkj.trwuliu.driver.bean.AppMemberReq;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.UserBean;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.TRurl;

/**
 * 用户注册功能
 * @author cyh 2016.6.15 上午9:12
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageView imgback;

    private EditText account;
    private EditText pswdMd5;
    private EditText authCode;

    private Button code;
    private Button next;

    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    //  登陆 ueser 对象
    private UserBean userBean;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        imgback = (ImageView) findViewById(R.id.img_back);
        account = (EditText) findViewById(R.id.edit_phone);
        pswdMd5 = (EditText) findViewById(R.id.edit_psw);
        authCode = (EditText) findViewById(R.id.edit_code);
        code = (Button) findViewById(R.id.btn_code);
        next = (Button) findViewById(R.id.btn_next);
        imgback.setOnClickListener(this);
        code.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_code:
                getCode();
                break;
            case R.id.btn_next:
                try {
                    Register();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 用户注册
     */
    private void Register() {

        AppParam<AppMemberReq> appParam = new AppParam<AppMemberReq>();
        Head head = new Head();
        head.setAccount(account.getText().toString());
        head.setAppVersion("1.0.0");
        head.setCallType("android");

        AppMemberReq req = new AppMemberReq();
        req.setAccount(account.getText().toString());
        req.setPswdMd5(Md5Utils.getMD5Code(pswdMd5.getText().toString()));
        req.setAuthCode(authCode.getText().toString());


        appParam.setHead(head);
        appParam.setBody(req);
        appParam.setSign("!&@#2016#");

        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.REGISTER);
        params.setConnectTimeout(15*1000);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                System.out.println("-----------" +  s + "-------------------");

                msg.obj = s;
                msg.what = SUCCESS;
                userhandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                msg.what = FAILTURE;
                msg.obj = throwable.getMessage();
                userhandler.sendMessage(msg);
            }

            @Override
            public void onFinished() {
            }
            @Override
            public void onCancelled(CancelledException e) {
            }

        });

    }


    private Handler userhandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS: // 成功

                    String result = (String) msg.obj;
                    Gson gson = new  Gson();
                    userBean = gson.fromJson(result, UserBean.class);

                    if ("000000".equals(userBean.code)) {

                        Toast.makeText(context , "注册成功" , Toast.LENGTH_SHORT).show();
                        finish();

                    } else { // 出现  异常

                        Toast.makeText(context , userBean.message , Toast.LENGTH_SHORT).show();

                    }

                    break;

                case FAILTURE: // 失败

                    String error = (String) msg.obj;

                    Toast.makeText(context , "网络异常！" ,Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;

            }

        }

    };



    /**
     * 60秒定时器
     */
    Timer timer;
    int second = 60;

    //处理界面控件
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            second--;
            if (second == 0) {
                timer.cancel();   //如果倒计时完成,取消定时事件
                code.setEnabled(true);
                second = 60;
                code.setText("重新获取");
            } else {
                code.setText("重新获取(" + second + ")");
            }
        }
    };

    /**
     * 获取验证码
     */
    private void getCode() {

        //获取到网络请求回应后,开始禁止用户60秒时间重新获取验证码
        code.setEnabled(false);

        new Thread() {
            @Override
            public void run() {
                ///
                /// 这里是获取网络数据请求
                /// 根据自己的需求进行处理
                /// 通过HTTPURLConnection或者HttpClient来发送获取手机验证码的请求
                ///
                //获取网络数据后,开始执行倒计时事件
                URL url = null;
                try {
                    url = new URL("http://172.19.4.23:8091/app/member/getValCode");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // 打开url连接
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 设置url请求方式 ‘get’ 或者 ‘post’
                try {
                    connection.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                StringBuffer params = new StringBuffer();
                AppParam<AppGetCodeReq> appParam = new AppParam<AppGetCodeReq>();
                Head head = new Head();
                head.setAccount(account.getText().toString());
                head.setAppVersion("1.0.0");
                head.setCallType("android");

                AppGetCodeReq codeReq = new AppGetCodeReq();
                codeReq.setAccount(account.getText().toString());
                codeReq.setType("0");

                appParam.setHead(head);
                appParam.setBody(codeReq);
                appParam.setSign("!&@#2016#");



                String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
                appParam.setSign(sign);


                // 表单参数与get形式一样
                connection.setDoOutput(true);// 是否输入参数
                params.append("param").append("=").append(JSON.toJSONString(appParam));
                byte[] bypes = params.toString().getBytes();
                try {
                    connection.getOutputStream().write(bypes);// 输入参数
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 发送
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String response = null;
                try {
                    response = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(response);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0);
                    }
                }, 0, 1000);//任务事件,延迟执行时间,定时执行时间

            }
        }.start();
    }

}

