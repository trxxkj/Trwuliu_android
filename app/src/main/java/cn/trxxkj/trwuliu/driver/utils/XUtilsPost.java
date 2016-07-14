package cn.trxxkj.trwuliu.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.Head;

/**
 * 联网 工具类
 */
public class XUtilsPost {

    public static final int FINALHTTP_RETURN_SUCCESS_ONE = 300;// 访问成功
    public static final int FINALHTTP_RETURN_FAILED_ONE = 301;// 访问失败
    public static final int FINALHTTP_RETURN_SUCCESS_TWO = 400;// 访问成功
    public static final int FINALHTTP_RETURN_FAILED_TWO = 401;// 访问失败
    public static final int FINALHTTP_RETURN_SUCCESS_THREE = 500;// 访问成功
    public static final int FINALHTTP_RETURN_FAILED_THREE = 501;// 访问失败
    public static final int FINALHTTP_RETURN_SUCCESS_FOUR = 600;// 访问成功
    public static final int FINALHTTP_RETURN_FAILED_FOUR  = 601;// 访问失败


    private Context mContext;
    private Handler handler;
    private SharedPreferences sp;


    public XUtilsPost(Context context, Handler handler ,SharedPreferences sp) {
        super();
        this.mContext = context;
        this.handler = handler;
        this.sp = sp;
    }

    /**
     *
     * post = new XUtilsPost(ctx, handler);
     post.doPost(UrlAdress.order_staff, null);
     *
     *
     */

    public void doPost(String url,AppParam appParam) {

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));

        appParam.setHead(head);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(url);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                MyLog.e("xutils----One","-----------" + s + "---------------");

                msg.obj = s;
                msg.what = FINALHTTP_RETURN_SUCCESS_ONE;
                handler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                msg.what = FINALHTTP_RETURN_FAILED_ONE;
                msg.obj = throwable.getMessage();
                handler.sendMessage(msg);

            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onCancelled(CancelledException e) {
            }

        });

    }


    public void doPostTwo(String url,AppParam appParam) {

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));

        appParam.setHead(head);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(url);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                MyLog.e("xutils----Two","-----------" + s + "---------------");

                msg.obj = s;
                msg.what = FINALHTTP_RETURN_SUCCESS_TWO;
                handler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                msg.what = FINALHTTP_RETURN_FAILED_TWO;
                msg.obj = throwable.getMessage();
                handler.sendMessage(msg);

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
