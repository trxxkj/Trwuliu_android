package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.DriverAdapter;
import cn.trxxkj.trwuliu.driver.base.JudgeDate;
import cn.trxxkj.trwuliu.driver.bean.ScreenInfo;
import cn.trxxkj.trwuliu.driver.bean.WheelMain;

public class PostWaybillActivity extends Activity implements View.OnClickListener {

    //    private Context context = null;
    private LayoutInflater inflater;
    private PopupWindow window;
    private ListView listView;
    private EditText editText;

    private ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
    private ArrayList<String> carNumber = new ArrayList<String>();
    private ArrayList<String> driverNumber = new ArrayList<String>();

    private DriverAdapter driverAdapter;

    private Handler handler = new Handler();

    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private ImageView back;

    private TextView startDate;
    private TextView endDate;
    private TextView traffic;
    private TextView transportUnit;

    private TextView selectcar;

    private Button postWaybill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_waybill);
//        context = this;
        initView();
    }

    private void initView() {

        back = (ImageView) findViewById(R.id.img_back);

        startDate = (TextView) findViewById(R.id.select_start_date);
        endDate = (TextView) findViewById(R.id.select_end_date);

        traffic = (TextView) findViewById(R.id.traffic);
        transportUnit = (TextView) findViewById(R.id.transport_unit);

        selectcar = (TextView) findViewById(R.id.select_car);

        postWaybill = (Button) findViewById(R.id.post_waybill);

        back.setOnClickListener(this);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        traffic.setOnClickListener(this);
        transportUnit.setOnClickListener(this);

        selectcar.setOnClickListener(this);

        postWaybill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.select_start_date:
                startTime();
                break;
            case R.id.select_end_date:
                endTime();
                break;
            case R.id.select_car:
//                showPopupWindow(v);
                startActivity(new Intent(this, SelectCarActivity.class));
//                startActivityForResult();
                break;
        }
    }

    private void startTime() {

        View timepickerview = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(PostWaybillActivity.this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = startDate.getText().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int h = calendar.getTime().getHours();
//        int m = calendar.getTime().getMinutes();
        wheelMain.initDateTimePicker(year, month, day, h);
        new AlertDialog.Builder(PostWaybillActivity.this)
                .setTitle("选择时间")
                .setView(timepickerview)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                startDate.setText(wheelMain.getTime());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();

    }

    private void endTime() {

        View timepickerview = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(PostWaybillActivity.this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = endDate.getText().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int h = calendar.getTime().getHours();
//        int m = calendar.getTime().getMinutes();
        wheelMain.initDateTimePicker(year, month, day, h);
        new AlertDialog.Builder(PostWaybillActivity.this)
                .setTitle("选择时间")
                .setView(timepickerview)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                endDate.setText(wheelMain.getTime());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();

    }

//    private void showPopupWindow(View view) {
//
//        if (window == null) {
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View v = inflater.inflate(R.layout.item_select_car, null);
//            v.setBackgroundColor(Color.parseColor("#ffffff"));
////            v.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_view));
//
//            editText = (EditText) findViewById(R.id.edit_car);
//
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
////                    if (editText.getText() != null) {
////                        String input_info = editText.getText().toString();
//////                        DriverAdapter driverAdapter = new DriverAdapter(this,);
//////                        listView.setAdapter(driverAdapter);
////                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    driverAdapter.getFilter().filter(s);
////                    handler.post(eChanged);
//                }
//            });
////
////            Runnable eChanged = new Runnable() {
////
////                @Override
////                public void run() {
////                    // TODO Auto-generated method stub
////                    String data = editText.getText().toString();
////
////                    mData.clear();//先要清空，不然会叠加
//////
////                    getmDataSub(mData, data);//获取更新数据
////
////                    driverAdapter.notifyDataSetChanged();//更新
////
////                }
////            };
//
//            //初始化listview，加载数据。
//            listView = (ListView) v.findViewById(R.id.listView5);
////            DriverAdapter driverAdapter = new DriverAdapter(this);
//            listView.setAdapter(driverAdapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                }
//            });
//
//            window = new PopupWindow(v, 1000, 1200);
//        }
//
//        //设置整个popupwindow的样式。
////        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.bdp_update_bg_dialog_btn));
//        //使窗口里面的空间显示其相应的效果，比较点击button时背景颜色改变。
//        //如果为false点击相关的空间表面上没有反应，但事件是可以监听到的。
//        //listview的话就没有了作用。
//        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        window.setFocusable(true);//如果不设置setFocusable为true，popupwindow里面是获取不到焦点的，那么如果popupwindow里面有输入框等的话就无法输入。
//        window.setBackgroundDrawable(new BitmapDrawable());
//        backgroundAlpha(0.5f);
//
//        window.update();
//        window.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);
//
//        //添加pop窗口关闭事件
//        window.setOnDismissListener(new poponDismissListener());
//    }

//    /**
//     * 获得根据搜索框的数据data来从元数据筛选，筛选出来的数据放入mDataSubs里
//     * @param mDataSubs
//     * @param data
//     */
//
//    private void getmDataSub(ArrayList<Map<String, Object>> mDataSubs, String data)
//    {
//        int length = carNumber.size();
//        for(int i = 0; i < length; ++i){
//            if(carNumber.get(i).contains(data) || driverNumber.get(i).contains(data)){
//                Map<String,Object> item = new HashMap<String,Object>();
//                item.put("title", carNumber.get(i));
//                item.put("text",  driverNumber.get(i));
//                mDataSubs.add(item);
//            }
//        }
//    }
//
//        /**
//         * 获得元数据 并初始化mDatas
//         * @param mDatas
//         */
//        private void getmData(ArrayList<Map<String, Object>> mDatas)
//        {
//            Map<String, Object> item = new HashMap<String, Object>();
//            carNumber.add("豫A00000");
//            driverNumber.add("老张:15868687676");
//
//            item.put("title", carNumber.get(0));
//            item.put("text", driverNumber.get(0));
//            mDatas.add(item);
//
//
//            carNumber.add("豫A00000");
//            driverNumber.add("老张:15868687673");
//
//            item = new HashMap<String, Object>();
//            item.put("title", carNumber.get(1));
//            item.put("text", driverNumber.get(1));
//            mDatas.add(item);
//        }


//    /**
//     * 设置添加屏幕的背景透明度
//     *
//     * @param bgAlpha
//     */
//    public void backgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha;  //0.0-1.0
//        getWindow().setAttributes(lp);
//    }
//
//
//    /**
//     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
//     *
//     * @author cg
//     */
//    public class poponDismissListener implements PopupWindow.OnDismissListener {
//
//        @Override
//        public void onDismiss() {
//            //Log.v("List_noteTypeActivity:", "我是关闭事件");
//            backgroundAlpha(1f);
//        }
//
//    }

}
