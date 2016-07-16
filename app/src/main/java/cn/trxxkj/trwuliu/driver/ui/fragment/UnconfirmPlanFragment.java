package cn.trxxkj.trwuliu.driver.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.UnconfirmPlanAdapter;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.MyPlan;
import cn.trxxkj.trwuliu.driver.bean.PlanBean;
import cn.trxxkj.trwuliu.driver.ui.UnconfirmPlanActivity;
import cn.trxxkj.trwuliu.driver.utils.App;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.TRurl1;

/**
 * 未确认计划界面
 * @author cyh 2016.6.18 下午14:39
 */

public class UnconfirmPlanFragment extends ListFragment {

    public Context context;
    public App app;
    private SharedPreferences sp;

    //计划plan对象
    private PlanBean planBean;

    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    private UnconfirmPlanAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        app = (App)getActivity().getApplication();
        sp = getActivity().getSharedPreferences(MyContents.SP_NAME, getActivity().MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);


        AppParam<MyPlan> appParam = new AppParam<>();
        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER, ""));
        head.setTokenId(sp.getString(MyContents.TOKENID, ""));

        MyPlan body = new MyPlan();
        body.setPageNo("");
        body.setStatus("0");

        appParam.setHead(head);
        appParam.setBody(body);
        appParam.setSign("!&@#2016#");

        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl1.PLANPAGE);
        params.addBodyParameter("param", JSON.toJSONString(appParam));
        params.setConnectTimeout(15 * 1000);

        x.http().post(params, new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();

            @Override
            public void onSuccess(String s) {
                System.out.println("-----------" + s
                        + "-------------------");

                msg.obj = s;
                msg.what = SUCCESS;
                planhandler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                msg.what = FAILTURE;
                msg.obj = throwable.getMessage();
                planhandler.sendMessage(msg);

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

        return v;

    }

    /**
     * 未确认计划数据集合
     */
    private List<PlanBean.ReturnData> planlist;
    private Handler planhandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SUCCESS: // 成功

                    String result = (String) msg.obj;
                    Gson gson = new Gson();
                    planBean = gson.fromJson(result, PlanBean.class);

                    if ("000000".equals(planBean.code)) {

                        // TODO  填充 adapter

                        if (Integer.parseInt(planBean.total) > 0) {

                            planlist = planBean.returnData;
                            adapter = new UnconfirmPlanAdapter(context,planlist);
//                            .setAdapter(adapter);
                            setListAdapter(adapter);
                            adapter.notifyDataSetChanged();
//                            lv_mydrive.setVisibility(View.VISIBLE);
//                            tv_nomsg.setVisibility(View.GONE);

                        } else {

                            planlist = new ArrayList<>();
                            adapter = new UnconfirmPlanAdapter(context,planlist);
//                            lv_mydrive.setAdapter(adapter);
                            setListAdapter(adapter);
                            adapter.notifyDataSetChanged();

//                            lv_mydrive.setVisibility(View.GONE);
//                            tv_nomsg.setVisibility(View.VISIBLE);

                        }

                    } else {  //E200002
                        Toast.makeText(context , "请求失败" , Toast.LENGTH_SHORT).show();
                    }

//                    app.setToken(userBean.returnData.tokenId);
//                    app.setUserid(userBean.returnData.id);
                    break;
                case FAILTURE: // 失败

                    String error = (String) msg.obj;

                    Toast.makeText(context, "网络异常！", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;

            }


        }

    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(getActivity(), UnconfirmPlanActivity.class));
    }


}
