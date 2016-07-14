package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.AppParam;
import cn.trxxkj.trwuliu.driver.bean.BindDriverPost;
import cn.trxxkj.trwuliu.driver.bean.DriverBean;
import cn.trxxkj.trwuliu.driver.bean.Head;
import cn.trxxkj.trwuliu.driver.bean.MyDrivers;
import cn.trxxkj.trwuliu.driver.utils.Md5Utils;
import cn.trxxkj.trwuliu.driver.utils.MyContents;
import cn.trxxkj.trwuliu.driver.utils.MyLog;
import cn.trxxkj.trwuliu.driver.utils.TRurl;
import cn.trxxkj.trwuliu.driver.utils.Utils;
import cn.trxxkj.trwuliu.driver.utils.XUtilsPost;

/**
 * 选择  司机
 */
public class ChoiceDriver extends Activity implements View.OnClickListener{

    private ImageView img_back;
    private TextView btn_driver_ok;  // 确定
    private EditText search_input;  // 查询 输入框
    private ImageView iv_search;  // 查询

    private TextView tv_nomsg;  // 没有数据
    private ListView lv_mydrive; // 显示 数据

    /**
     * 是否  选中
     */
    private HashMap<Integer, Boolean> choice = new HashMap<>();


    protected static final int SUCCESS = 0;
    protected static final int FAILTURE = 1;
    protected static final int ERROR = 2;

    private Context context;
    private SharedPreferences sp;
    private XUtilsPost post;
    private DriverBean driverBean;

    private DriverBean.ReturnData returnData;

    private String vehicleId;
    private String vehicleNo;
    private String vehicleTypeName;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_driver);

        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);
        context = this;

        post = new XUtilsPost(context, myDhandler,sp);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        vehicleId = bundle.getString("vehicleId");
        vehicleNo = bundle.getString("vehicleNo");
        vehicleTypeName = bundle.getString("vehicleTypeName");


        initView();

        getData("");

        setListener();
    }


    /**
     * 添加  view
     */
    public void initView() {
        btn_driver_ok = (TextView) findViewById(R.id.btn_driver_ok);
        img_back = (ImageView) findViewById(R.id.img_back);
        lv_mydrive = (ListView) findViewById(R.id.lv_mydrive);
        tv_nomsg = (TextView) findViewById(R.id.tv_nomsg);


        btn_driver_ok.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.btn_driver_ok: // 确定

                boolean istrue = false;

                if(adapter != null){
                    for (int i = 0; i < adapter.getStates().size(); i++) {
                        istrue = adapter.getStates().get(i);
                        if(istrue){
                            returnData = adapter.getDirvelist().get(i);
                            if ("0".equals(returnData.count)) {
                                //TODO 添加司机

                                addDriverpost();

                            } else {
                                Utils.showToast(this,"该司机已绑定车辆");
                            }

                        }
                    }
                }

                break;

            default:
                break;
        }
    }


    private void setListener() {

        lv_mydrive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<Integer, Boolean> choice = new HashMap<>();

                choice.putAll(adapter.getStates());

                for (int i = 0;i<choice.size();i++) {

                    if (i == position) {
                        choice.put(i,true);
                    } else {
                        choice.put(i,false);
                    }

                }

                adapter.setStates(choice);
                adapter.notifyDataSetChanged();

            }
        });

    }

    /**
     *  获取数据
     */
    public void getData(String search) {

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<MyDrivers> appParam = new AppParam<>();

        Head head = new Head();
        head.setAppVersion("1.0.0");
        head.setCallType("android");
        head.setAccount(sp.getString(MyContents.ACCOUNTNUMBER , ""));
        head.setTokenId(sp.getString(MyContents.TOKENID , ""));


        MyDrivers body = new MyDrivers();
        body.setMemberId(sp.getString(MyContents.ID , ""));
        body.setSearch(search);
        body.setPageNo("");

        appParam.setHead(head);
        appParam.setBody(body);

        appParam.setSign("!&@#2016#");
        String sign = Md5Utils.getMD5Code(JSON.toJSONString(appParam));
        appParam.setSign(sign);

        RequestParams params = new RequestParams(TRurl.MYDRIVER);

        params.addBodyParameter("param" , JSON.toJSONString(appParam));

        params.setConnectTimeout(15*1000);

        x.http().post(params,new Callback.CommonCallback<String>() {
            Message msg = Message.obtain();
            @Override
            public void onSuccess(String s) {

                MyLog.e("mydriver","-----------" + s + "---------------");
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
     * 绑定司机
     */
    private void addDriverpost() {

        if (!Utils.isNetworkConnected(context)){
            Toast.makeText(context,"请检查网络" ,Toast.LENGTH_SHORT).show();
            return;
        }

        AppParam<BindDriverPost> appParam = new AppParam<>();

        BindDriverPost body = new BindDriverPost();

        body.setDriverId(returnData.driverid);
        body.setDriverName(returnData.drivername);
        body.setDriverTel(returnData.drivertel);
        body.setDriverRemark("");

        body.setVehicleId(bundle.getString("vehicleId"));
        body.setVehicleNo(bundle.getString("vehicleNo"));
        body.setVehicleTypeName(bundle.getString("vehicleTypeName"));

        appParam.setBody(body);

        post.doPost(TRurl.CARADDDRIVER, appParam);

    }


    /**
     * 司机 数据 集合
     */
    private List<DriverBean.ReturnData> dirvelist;

    private Handler myDhandler = new Handler() {

        public void handleMessage(Message msg) {

            String result;
            Gson gson = new  Gson();

            switch (msg.what) {

                case SUCCESS: // 成功
                    result = (String) msg.obj;
                    driverBean = gson.fromJson(result, DriverBean.class);

                    if ("000000".equals(driverBean.code)) {

                        if (Integer.parseInt(driverBean.total) > 0) {

                            dirvelist = driverBean.returnData;

                            for (int i = 0; i < dirvelist.size(); i++) {
                                choice.put(i, false);
                            }

                            adapter = new MyAdapter(choice,context,dirvelist);
                            lv_mydrive.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            lv_mydrive.setVisibility(View.VISIBLE);
                            tv_nomsg.setVisibility(View.GONE);

                        } else {

                            choice.clear();

                            lv_mydrive.setVisibility(View.GONE);
                            tv_nomsg.setVisibility(View.VISIBLE);

                        }

                    } else {  // E200002
                        Toast.makeText(context , "请求失败" , Toast.LENGTH_SHORT).show();
                    }
                    break;

                case FAILTURE: // 失败
                    Toast.makeText(context , "网络异常！" ,Toast.LENGTH_SHORT).show();

                    break;

                case XUtilsPost.FINALHTTP_RETURN_SUCCESS_ONE: // 添加成功

                    result = (String) msg.obj;

                    try {

                        JSONObject jsonObject = new JSONObject(result);

                        if ("000000".equals(jsonObject.getString("code"))) {

                            Utils.showToast(ChoiceDriver.this,"绑定成功！");
                            finish();

                        } else {
                            Utils.showToast(ChoiceDriver.this,jsonObject.getString("message"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case XUtilsPost.FINALHTTP_RETURN_FAILED_ONE:  // 添加 失败

                    Utils.showToast(ChoiceDriver.this,"添加失败");

                    break;
                default:
                    break;

            }

        }

    };

    /**
     *  显示 数据
     */
    private MyAdapter adapter;

    public class MyAdapter extends BaseAdapter {

        // 用于记录每个RadioButton的状态，并保证只可选一个
        private HashMap<Integer, Boolean> states;

        private Context context;
        private List<DriverBean.ReturnData> dirvelist;


        public MyAdapter(HashMap<Integer, Boolean> states, Context context,
                         List<DriverBean.ReturnData> dirvelist) {
            super();
            this.states = states;
            this.context = context;
            this.dirvelist = dirvelist;
        }

        public List<DriverBean.ReturnData> getDirvelist() {
            return dirvelist;
        }

        public void setDirvelist(List<DriverBean.ReturnData> dirvelist) {
            this.dirvelist = dirvelist;
        }

        public HashMap<Integer, Boolean> getStates() {
            return states;
        }

        public void setStates(HashMap<Integer, Boolean> states) {
            this.states = states;
        }

        @Override
        public int getCount() {
            return dirvelist.size();
        }

        @Override
        public Object getItem(int position) {
            return dirvelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // 页面
            ViewHolder holder;

            String bean = dirvelist.get(position).drivername;

            LayoutInflater inflater = LayoutInflater.from(context);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_choicestaff,null);
                holder = new ViewHolder();

                holder.tvName = (TextView) convertView.findViewById(R.id.staff_name);
                holder.staff_phone = (TextView) convertView.findViewById(R.id.staff_phone);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(bean);
            holder.staff_phone.setText(dirvelist.get(position).drivertel);

            final RadioButton radio = (RadioButton) convertView.findViewById(R.id.checkbox);

            holder.rb_state = radio;
            holder.rb_state.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    for (int i = 0; i < dirvelist.size(); i++) {
                        states.put(i, false);
                    }

                    states.put(position, radio.isChecked());
                    MyAdapter.this.notifyDataSetChanged();
                }
            });

            boolean res = false;
            if (states.get(position) == null
                    || !states.get(position)) {
                res = false;
                states.put(position, false);
            } else {
                res = true;
            }

            holder.rb_state.setChecked(res);
            return convertView;
        }
    }

    public class ViewHolder {

        TextView tvName;
        RadioButton rb_state;
        TextView staff_phone;
    }


}
