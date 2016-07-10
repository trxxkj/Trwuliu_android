package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.view.ViewHolder;

/**
 * 运力模块我的车辆列表适配器
 * @author cyh 2016.6.19 上午10:45
 */

public class MyCarAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public MyCarAdapter(Context context) {
        super();
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_my_car, null);
            holder.carLogo = (ImageView) convertView.findViewById(R.id.car_logo);           //车辆图标
            holder.cementTanker = (TextView) convertView.findViewById(R.id.cement_tanker);  //车辆类型
            holder.length = (TextView) convertView.findViewById(R.id.length);               //车辆长度
            holder.ton = (TextView) convertView.findViewById(R.id.ton);                     //车辆载重
            holder.numberPlate = (TextView) convertView.findViewById(R.id.number_plate);    //车辆牌照
            holder.driverPhone = (TextView) convertView.findViewById(R.id.driver_phone);    //司机电话
            holder.seeWaybill = (Button) convertView.findViewById(R.id.see_waybill);        //查看运单
            holder.bindDriver = (Button) convertView.findViewById(R.id.bind_driver);        //绑定司机

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.carLogo.setImageResource(R.drawable.sn);
        holder.cementTanker.setText("水泥罐车");
        holder.length.setText("11.9米");
        holder.ton.setText("40.0吨");
        holder.numberPlate.setText("豫A00000");
        holder.driverPhone.setText("张司机13000000000");
        holder.seeWaybill.setText("查看运单");
        holder.bindDriver.setText("绑定司机");
        holder.bindDriver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

}