//package cn.trxxkj.trwuliu.driver.utils;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest;
//
///**
// * Created by admin on 2016/5/5.
// */
//public class HttpUtil {
//
//    private static String json  = "";
//
//    public static String sendPost(String url, StringBuffer param, final Context context) {
//
//        HttpUtils httpUtils = new HttpUtils();
////        httpUtils.send(HttpRequest.HttpMethod.POST, url, param,
//                new RequestCallBack<String>() {
//                    @Override
//                    public void onStart() {
//                        // 加载对话框弹出
//                        // 如：dialog.show();
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onFailure(HttpException arg0, String arg1) {
//                        // 加载对话框关闭
//                        // 如：dialog.dissmis();
//                        Toast.makeText(context, "网络不给力",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        // 加载对话框关闭
//                        json = responseInfo.result;// 接口返回的数据
//                    }
//                });
//        return json;
//    }
//}
