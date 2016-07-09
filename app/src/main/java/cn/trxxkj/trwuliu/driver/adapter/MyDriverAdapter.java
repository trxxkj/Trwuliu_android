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
 * 运力模块我的司机列表适配器
 * @author cyh 2016.6.20 上午10:45
 */
public class MyDriverAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public MyDriverAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_mydriver_list, null);
            holder.mydriver = (TextView) convertView.findViewById(R.id.mydriver);    //司机姓名
            holder.verified = (TextView) convertView.findViewById(R.id.verified);    //认证信息
            holder.mydriverPhoneNumber = (TextView) convertView.findViewById(R.id.mydriver_phone_number);    //司机号码
            holder.callLogo = (ImageView) convertView.findViewById(R.id.call_logo);  //拨打电话

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mydriver.setText("张司机");
        holder.verified.setText("已认证");
        holder.mydriverPhoneNumber.setText("电话:13000000000");
        holder.callLogo.setImageResource(R.drawable.call_phone);

        holder.callLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

}