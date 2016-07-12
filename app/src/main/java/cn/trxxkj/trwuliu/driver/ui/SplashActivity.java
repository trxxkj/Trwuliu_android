package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.AppMemberReq;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.UserBean;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.TRurl;

/**
 * 闪屏页面
 */
public class SplashActivity extends Activity{


    RelativeLayout rlRoot;

    private SharedPreferences sp;
    //  登陆 ueser 对象
    private UserBean userBean;

    private Context context;

    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);

        startAnim();

    }

    /**
     * 开启动画
     */
    private void startAnim() {

        // 动画集合
        AnimationSet set = new AnimationSet(false);


        // 缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale.setDuration(1000);// 动画时间
        scale.setFillAfter(true);// 保持动画状态

        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态

        // set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {

                jumpToMain();

            }
        });

        rlRoot.startAnimation(set);
    }


    /**
     * 判断 是否登录  跳转主页
     */
    public void jumpToMain() {

        // 登陆 成功  改为 true  账号密码 都 记住了  自动登陆
        boolean isLogin = sp.getBoolean(MyContents.ISLOGIN,false);
        if (isLogin) {

            LoginApp();

        } else {  // 没有登陆  去登陆 页面

            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }

    }

    /**
     * 自动登录   账号密码 登陆
     */
    private void LoginApp() {

        AppParam<AppMemberReq> appParam = new AppParam<AppMemberReq>();

        Head head = new Head();
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER,""));
        head.setAppVersion("1.0.0");
        head.setCallType("android");

        // body 层
        AppMemberReq req = new AppMemberReq();
        req.setAccount(sp.getString(MyContents.ACCOUNTNUMBER,""));
        req.setLoginType(0);
        req.setPswdMd5(Md5Utils.getMD5Code(sp.getString(MyContents.PASSWORD, "")));

        appParam.setHead(head);
        appParam.setBody(req);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.LOGIN_URL);

        params.setConnectTimeout(15*1000);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                System.out.println("-----------" + s
                        + "-------------------");

                msg.obj = s;
                msg.what = SUCCESS;
                userhandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                msg.what = FAILTURE;
                msg.obj = throwable.getMessage();;
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

                        sp.edit().putString(MyContents.TOKENID, userBean.returnData.tokenId).commit();
                        sp.edit().putString(MyContents.ID, userBean.returnData.id).commit();

                        startActivity(new Intent(context, MainActivity.class));
                        finish();

                    } else {   //  如果自动登录 报错 就跳转  登陆页面

                        startActivity(new Intent(context, LoginActivity.class));
                        Toast.makeText(context , userBean.message , Toast.LENGTH_SHORT).show();
                        finish();

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



}
