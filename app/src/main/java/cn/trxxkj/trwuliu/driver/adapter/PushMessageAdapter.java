package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.view.ViewHolder;

/**
 * 百度推送模块列表适配器
 * @author cyh 2016.4.15 下午15:35
 */
public class PushMessageAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;

    public PushMessageAdapter(Context context) {
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
            convertView = mInflater.inflate(R.layout.item_message, null);
            holder.messageLogo = (ImageView) convertView.findViewById(R.id.message_logo);   //通知图标
            holder.notice = (TextView) convertView.findViewById(R.id.notice);               //通知提示
            holder.noticeDate = (TextView) convertView.findViewById(R.id.notice_date);      //通知日期
            holder.noticeMessage = (TextView) convertView.findViewById(R.id.notice_message);    //通知内容

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.messageLogo.setImageResource(R.drawable.icon_xx2x);
        holder.notice.setText("通知");
        holder.noticeDate.setText("2016-03-02");
        holder.noticeMessage.setText("尊敬的用户 车主XXX请求添加您为他的车辆");

        holder.noticeMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

}