package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.view.ViewHolder;
import cn.trxxkj.trwuliu.driver.bean.Track;

/**
 * 运单轨迹列表适配器
 * @author cyh 2016.6.25 上午11:20
 */

public class TrackAdapter extends BaseAdapter {

    private List<Track> mList = null;

private LayoutInflater mInflater = null;

    public TrackAdapter(Context context) {

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
        if (mList != null) {
            return mList.get(position);
        }
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
            convertView = mInflater.inflate(R.layout.item_wbtrack_list, null);
            holder.dateTrack = (TextView) convertView.findViewById(R.id.date_track);    //轨迹日期
            holder.trackLine = convertView.findViewById(R.id.track_line1);              //轨迹分割线
            holder.trackStatus = (ImageView) convertView.findViewById(R.id.track_status);   //轨迹状态
            holder.track = (TextView) convertView.findViewById(R.id.track);                 //轨迹
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.trackLine.setVisibility(View.INVISIBLE);
            holder.trackStatus.setImageResource(R.drawable.dot);
        } else {
            holder.trackLine.setVisibility(View.VISIBLE);
            holder.trackStatus.setImageResource(R.drawable.dot);
        }
        holder.dateTrack.setText(mList.get(position).getDateTrack());
        holder.track.setText(mList.get(position).getTrack());

        return convertView;
    }

}