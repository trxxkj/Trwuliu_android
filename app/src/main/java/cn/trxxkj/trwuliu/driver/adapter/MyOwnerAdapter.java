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
 * 运力模块我的车主列表适配器
 * @author cyh 2016.6.27 下午16:30
 */
public class MyOwnerAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public MyOwnerAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_myowner_list, null);
            holder.driverName = (TextView) convertView.findViewById(R.id.driver_name);      //司机姓名
            holder.driverPhone = (TextView) convertView.findViewById(R.id.driver_phone);    //司机号码
            holder.agreement = (TextView) convertView.findViewById(R.id.agreement);         //已同意
            holder.modify = (TextView) convertView.findViewById(R.id.modify);               //修改

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.driverName.setText("张司机");
        holder.driverPhone.setText("13000000000");
        holder.agreement.setText("已同意");
        holder.modify.setText("修改");

        holder.modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

}
