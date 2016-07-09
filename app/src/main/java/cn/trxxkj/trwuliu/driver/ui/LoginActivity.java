package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.AppGetCodeReq;
import cn.trxxkj.trwuliu.driver.bean.AppMemberReq;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.util.Md5Utils;

/**
 * 用户登录功能
 * @author cyh 2016.4.1 上午8:49
 */
public class LoginActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    //账号登录
    private EditText account;
    private EditText passWord;
    //手机号快捷登录
    private EditText phoneNumber;
    private EditText loginCode;

    private TextView findPsw;
    private ImageView imgback;

    //账号登录界面
    private TextView accountTextView;
    //手机号快捷登录界面
    private TextView phoneTextView;

    private View indicator1;
    private View indicator2;

    private ViewPager viewPager;
    private List<View> viewList;
    //账号登录注册
    private Button accountLogin;
    private Button accountRegister;
    //手机快捷登录
    private Button phoneLogin;
    private Button phoneRegister;
    private Button btnLoginCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initView();
    }

    private void initView(){
        accountTextView = (TextView) findViewById(R.id.tv_account);
        phoneTextView = (TextView) findViewById(R.id.tv_phone_number);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewList = new ArrayList<View>();

        //将布局转化为视图
        View view1 = View.inflate(this, R.layout.item_account_login, null);
        View view2 = View.inflate(this, R.layout.item_phone_login, null);

        viewList.add(view1);
        viewList.add(view2);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.layout_account_login).setOnClickListener(this);
        findViewById(R.id.layout_phone_number_login).setOnClickListener(this);

        account = (EditText) view1.findViewById(R.id.edit_user);
        passWord = (EditText) view1.findViewById(R.id.edit_psw);
        findPsw = (TextView) view1.findViewById(R.id.tv_find_psw);
        accountLogin = (Button) view1.findViewById(R.id.btn_account_login);
        accountRegister = (Button) view1.findViewById(R.id.btn_account_register);

        phoneNumber = (EditText) view2.findViewById(R.id.edit_phone_number_login);
        loginCode = (EditText) view2.findViewById(R.id.edit_login_code);
        btnLoginCode = (Button) view2.findViewById(R.id.btn_code_login);
        phoneLogin = (Button) view2.findViewById(R.id.btn_phone_login);
        phoneRegister = (Button) view2.findViewById(R.id.btn_phone_register);

        findPsw.setOnClickListener(this);
        accountLogin.setOnClickListener(this);
        accountRegister.setOnClickListener(this);
        phoneLogin.setOnClickListener(this);
        phoneRegister.setOnClickListener(this);
        btnLoginCode.setOnClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            accountTextView.setTextColor(Color.BLACK);
            phoneTextView.setTextColor(getResources().getColor(R.color.dark));
            indicator1.setVisibility(View.VISIBLE);
            indicator2.setVisibility(View.INVISIBLE);
        }
        if (position == 1) {
            phoneTextView.setTextColor(Color.BLACK);
            accountTextView.setTextColor(getResources().getColor(R.color.dark));
            indicator1.setVisibility(View.INVISIBLE);
            indicator2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:    //返回
                finish();
                break;
            case R.id.layout_account_login:  //账号登录
                viewPager.setCurrentItem(0);
                break;
            case R.id.layout_phone_number_login:  //手机号快捷登录
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_find_psw:
                startActivity(new Intent(this, ForgetPswActivity.class));
                break;
            case R.id.btn_account_login:
                try {
                    loginAccount();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_account_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_code_login:
                getCode();
                break;
            case R.id.btn_phone_login:
                try {
                    loginPhone();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_phone_register:
                break;
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private List<View> viewList;

        public MyViewPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            //返回页卡的数量
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

    }

    /**
     * 用户账号登录
     */
    private void loginAccount() throws IOException {

        URL url = null;
        try {
            url = new URL("http://172.19.4.23:8091/app/member/login");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");
        StringBuffer params = new StringBuffer();
        AppParam<AppMemberReq> appParam = new AppParam<AppMemberReq>();
        Head head = new Head();
        head.setAccount(account.getText().toString());
        head.setAppVersion("1.0.0");
        head.setCallType("android");

        AppMemberReq req = new AppMemberReq();
        req.setAccount(account.getText().toString());
        req.setLoginType(0);
        req.setPswdMd5(Md5Utils.getMD5Code(passWord.getText().toString()));


        appParam.setHead(head);
        appParam.setBody(req);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        // 表单参数与get形式一样
        connection.setDoOutput(true);// 是否输入参数
        params.append("param").append("=").append(JSON.toJSONString(appParam));
        byte[] bypes = params.toString().getBytes();
        connection.getOutputStream().write(bypes);// 输入参数

        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = in.readLine();
        System.out.println(response);
    }

    /**
     * 手机号快捷登录
     */
    private void loginPhone() throws IOException {

        URL url = null;
        try {
            url = new URL("http://172.19.4.23:8091/app/member/login");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");
        StringBuffer params = new StringBuffer();
        AppParam<AppMemberReq> appParam = new AppParam<AppMemberReq>();
        Head head = new Head();
        head.setAccount(phoneNumber.getText().toString());
        head.setAppVersion("1.0.0");
        head.setCallType("android");

        AppMemberReq req = new AppMemberReq();
        req.setAccount(phoneNumber.getText().toString());
        req.setLoginType(0);
        req.setAuthCode(btnLoginCode.getText().toString());
//        req.setPswdMd5(Md5Utils.getMD5Code(passWord.getText().toString()));

        appParam.setHead(head);
        appParam.setBody(req);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        // 表单参数与get形式一样
        connection.setDoOutput(true);// 是否输入参数
        params.append("param").append("=").append(JSON.toJSONString(appParam));
        byte[] bypes = params.toString().getBytes();
        connection.getOutputStream().write(bypes);// 输入参数

        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = in.readLine();
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
                btnLoginCode.setEnabled(true);
                second = 60;
                btnLoginCode.setText("重新获取");
            } else {
                btnLoginCode.setText("重新获取(" + second + ")");
            }
        }
    };

    /**
     * 获取手机验证码
     */
    private void getCode() {

        //获取到网络请求回应后,开始禁止用户60秒时间重新获取验证码
        btnLoginCode.setEnabled(false);

        new Thread() {
            @Override
            public void run() {
                // 这里是获取网络数据请求
                // 根据自己的需求进行处理
                // 通过HTTPURLConnection或者HttpClient来发送获取手机验证码的请求
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
                head.setAccount(phoneNumber.getText().toString());
                head.setAppVersion("1.0.0");
                head.setCallType("android");

//                AppMemberReq req = new AppMemberReq();
//                req.setAccount(account.getText().toString());

                AppGetCodeReq codeReq = new AppGetCodeReq();
                codeReq.setAccount(phoneNumber.getText().toString());
                codeReq.setType("2");

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

