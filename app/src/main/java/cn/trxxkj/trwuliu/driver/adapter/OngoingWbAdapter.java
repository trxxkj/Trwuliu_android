package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.base.ViewHolder;

/**
 * 进行中运单列表适配器
 * @author cyh 2016.5.25
 */

public class OngoingWbAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public OngoingWbAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_waybill_list, null);
            holder.waybillNumber = (TextView) convertView.findViewById(R.id.waybill_number);    //运单号
            holder.goods = (TextView) convertView.findViewById(R.id.goods);                     //货物类型
            holder.startpoint = (TextView) convertView.findViewById(R.id.start_point);          //起点
            holder.endpoint = (TextView) convertView.findViewById(R.id.end_point);              //终点
            holder.dateGoods = (TextView) convertView.findViewById(R.id.date_goods);            //货物日期
            holder.licensePlate = (TextView) convertView.findViewById(R.id.license_plate);      //车辆牌照
            holder.certifyCar = (TextView) convertView.findViewById(R.id.certify_car);          //车辆认证
            holder.degreeComplete = (TextView) convertView.findViewById(R.id.degree_of_complete); //完成度
            holder.percentage = (TextView) convertView.findViewById(R.id.percentage);             //完成百分比

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.waybillNumber.setText("运单号:yd1");
        holder.goods.setText("水泥");
        holder.startpoint.setText("起点：河南郑州市");
        holder.endpoint.setText("终点：河南汝州市");
        holder.dateGoods.setText("2016-05-25");
        holder.licensePlate.setText("豫A00000");
        holder.certifyCar.setText("认证车辆");
        holder.degreeComplete.setText("完成度");
        holder.percentage.setText("40%");

        holder.percentage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Click on the loc image on ListItem ");
            }
        });

        return convertView;
    }

}
