package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.base.ViewHolder;

/**
 * 已拒绝计划列表适配器
 * @author cyh 2016.5.10
 */
public class RefusePlanAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public RefusePlanAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_plan_list, null);
            holder.planCode = (TextView) convertView.findViewById(R.id.plan_code);      //计划编码
            holder.status = (TextView) convertView.findViewById(R.id.status);           //计划状态
            holder.goods = (TextView) convertView.findViewById(R.id.goods);             //货物类型
            holder.dateGoods = (TextView) convertView.findViewById(R.id.date_goods);    //货物日期
            holder.ownerCar = (TextView) convertView.findViewById(R.id.owner_car);      //车主发车

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.planCode.setText("计划编码:JH4");
        holder.status.setText("已拒绝");
        holder.goods.setText("40吨水泥");
        holder.dateGoods.setText("2016-04-16");
        holder.ownerCar.setText("车主发车");

        holder.ownerCar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

}
