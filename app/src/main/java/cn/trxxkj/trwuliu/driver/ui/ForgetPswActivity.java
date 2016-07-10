package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

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
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;

/**
 * 用户忘记密码找回功能
 * @author cyh 2016.5.24 下午15:10
 */
public class ForgetPswActivity extends Activity implements View.OnClickListener {

    private ImageView imgback;

    private EditText account;
    private EditText pswdMd5;
    private EditText authCode;

    private Button code;
    private Button ensure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
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
        authCode = (EditText) findViewById(R.id.edit_code);
        pswdMd5 = (EditText) findViewById(R.id.edit_psw);

        code = (Button) findViewById(R.id.btn_code);
        ensure = (Button) findViewById(R.id.btn_sure);
        imgback.setOnClickListener(this);
        code.setOnClickListener(this);
        ensure.setOnClickListener(this);

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
            case R.id.btn_sure:
                try {
                    findPsw();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 找回密码
     */
    private void findPsw() {

        URL url = null;
        try {
            url = new URL("http://172.19.4.23:8091/app/member/updatePass");
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
        AppParam<AppMemberReq> appParam = new AppParam<AppMemberReq>();
        Head head = new Head();
        head.setAccount(account.getText().toString());
        head.setAppVersion("1.0.0");
        head.setCallType("android");

        AppMemberReq req = new AppMemberReq();
        req.setAccount(account.getText().toString());
        req.setPswdMd5(Md5Utils.getMD5Code(pswdMd5.getText().toString()));
        req.setAuthCode(authCode.toString());

        appParam.setHead(head);
        appParam.setBody(req);

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

    }

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
                timer.cancel();//如果倒计时完成,取消定时事件
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
                AppParam<AppGetCodeReq> appParam = new AppParam<>();
                Head head = new Head();
                head.setAccount(account.getText().toString());
                head.setAppVersion("1.0.0");
                head.setCallType("android");

//                AppMemberReq req = new AppMemberReq();
//                req.setAccount(account.getText().toString());

                AppGetCodeReq codeReq = new AppGetCodeReq();
                codeReq.setAccount(account.getText().toString());
                codeReq.setType("1");

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
