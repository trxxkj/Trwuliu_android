package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.DriverBean;
import cn.trxxkj.trwuliu.driver.view.ViewHolder;
/**
 * 运力模块   我的司机列表   适配器
 *
 */
public class MyDriverAdapter extends BaseAdapter {

    private List<DriverBean.ReturnData> dirvelist;

    private LayoutInflater mInflater = null;

    private Context context;

    public MyDriverAdapter(Context context, List<DriverBean.ReturnData> dirvelist ) {
        super();
        this.context = context;
        this.dirvelist = dirvelist;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dirvelist.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

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

        holder.mydriver.setText(dirvelist.get(position).drivername);
        holder.verified.setVisibility(View.GONE);
        holder.mydriverPhoneNumber.setText(dirvelist.get(position).drivertel);
        holder.callLogo.setImageResource(R.drawable.call_phone);

        holder.callLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("tel:" + dirvelist.get(position).drivertel));
                context.startActivity(intent);

            }
        });

        return convertView;
    }

}