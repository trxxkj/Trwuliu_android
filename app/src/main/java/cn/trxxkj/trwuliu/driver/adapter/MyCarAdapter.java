package cn.trxxkj.trwuliu.driver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.MyCarReturn;
import cn.trxxkj.trwuliu.driver.ui.ChoiceDriver;
import cn.trxxkj.trwuliu.driver.ui.MyCarActivity;
import cn.trxxkj.trwuliu.driver.ui.MyDriverActivity;
import cn.trxxkj.trwuliu.driver.utils.BitmapCache;
import cn.trxxkj.trwuliu.driver.utils.Utils;
import cn.trxxkj.trwuliu.driver.view.ViewHolder;

/**
 * 运力模块我的车辆列表适配器
 * @author cyh 2016.6.19 上午10:45
 */

public class MyCarAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;
    private List<MyCarReturn.Mycar> mycars;

    WeakReference<MyCarActivity> weak;

    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    public MyCarAdapter(List<MyCarReturn.Mycar> mycars, Context myCarActivity) {
        super();
        this.mycars = mycars;

        this.weak = new WeakReference<MyCarActivity>((MyCarActivity)myCarActivity);

        mQueue = Volley.newRequestQueue(myCarActivity);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache());

        mInflater = (LayoutInflater) myCarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return mycars.size();
    }

    @Override
    public Object getItem(int position) {
        return mycars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_my_car, null);
            holder.carLogo = (ImageView) convertView.findViewById(R.id.car_logo);          //车辆图标
            holder.cementTanker = (TextView) convertView.findViewById(R.id.cement_tanker);  //车辆类型
            holder.length = (TextView) convertView.findViewById(R.id.length);               //车辆长度
            holder.ton = (TextView) convertView.findViewById(R.id.ton);                     //车辆载重
            holder.numberPlate = (TextView) convertView.findViewById(R.id.number_plate);    //车辆牌照
            holder.driverPhone = (TextView) convertView.findViewById(R.id.driver_phone);    //司机电话
            holder.bindDriver = (Button) convertView.findViewById(R.id.bind_driver);        //绑定司机

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        ImageListener listener = ImageLoader.getImageListener(holder.carLogo, R.drawable.sn, android.R.drawable.ic_delete);
        mImageLoader.get(mycars.get(position).vehiHeadImgPath, listener);

        switch (Integer.parseInt(mycars.get(position).vehiType)) {

            case 1: // 箱式
                holder.cementTanker.setText("箱式");
                break;
            case 2:  // 板车
                holder.cementTanker.setText("板车");
                break;
            case 3:  // 冷藏
                holder.cementTanker.setText("冷藏");
                break;
            default:
                break;

        }

        holder.length.setText(mycars.get(position).vehiLength + "米");
        holder.ton.setText(mycars.get(position).vehiWeight + "吨");
        holder.numberPlate.setText(mycars.get(position).vehiPrefix + mycars.get(position).vehiNo);

        if (!TextUtils.isEmpty(mycars.get(position).driverTel)) {

            holder.driverPhone.setText(mycars.get(position).driverName + mycars.get(position).driverTel);
            holder.bindDriver.setText("解除绑定");
            holder.bindDriver.setBackgroundResource(R.color.btn_mycar1);

        } else {

            holder.driverPhone.setText("");
            holder.bindDriver.setText("绑定司机");
            holder.bindDriver.setBackgroundResource(R.color.btn_mycar2);

        }

        holder.bindDriver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final MyCarActivity activity = weak.get();
                //  解除  绑定
                if (!TextUtils.isEmpty(mycars.get(position).driverTel)) {
                    // 解除绑定
                    // 判断状态
                    if ("5".equals(mycars.get(position).billstatus)) {

                        activity.removeDriver(mycars.get(position));
                    } else {
                        Utils.showToast(activity,"不可操作");
                    }

                } else {  // 绑定司机

                    //TODO  跳转 新的 activity

                    Intent intent = new Intent(activity, ChoiceDriver.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vehicleId",mycars.get(position).id);
                    bundle.putString("vehicleNo",mycars.get(position).vehiPrefix + mycars.get(position).vehiNo);

                    switch (Integer.parseInt(mycars.get(position).vehiType)) {

                        case 1: // 箱式
                            bundle.putString("vehicleTypeName","箱式");
                            break;
                        case 2:  // 板车
                            bundle.putString("vehicleTypeName","板车");
                            break;
                        case 3:  // 冷藏
                            bundle.putString("vehicleTypeName","冷藏");
                            break;
                        default:
                            break;

                    }

                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }


            }
        });
        return convertView;
    }





}