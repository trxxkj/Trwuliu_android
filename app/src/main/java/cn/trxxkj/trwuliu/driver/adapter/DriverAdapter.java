package cn.trxxkj.trwuliu.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.bean.Driver;

/**
 * 司机基础类
 * @author cyh 2016.6.17 下午19:30
 */
public class DriverAdapter extends BaseAdapter implements Filterable{

    private List<Driver> list;

    private Context context;
    private LayoutInflater mInflater = null;

    private DriverFilter filter;

    public DriverAdapter() {
        this.list = list;
        this.context = context;
//        super();
//        mInflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_driver_list, null);
        }
        Driver driver = list.get(position);
//        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio_btn);
        TextView licensePlate = (TextView) convertView.findViewById(R.id.license_plate);
        TextView driverName = (TextView) convertView.findViewById(R.id.driver_name);
        TextView driverPhoneNumber = (TextView) convertView.findViewById(R.id.driver_phone_number);
//        ImageView callPhone = (ImageView) convertView.findViewById(R.id.call_phone);


        licensePlate.setText("豫A00000");
        driverName.setText("张师傅");
        driverPhoneNumber.setText("13000000000");
//        driverPhoneNumber.setImageResource(R.drawable.sn);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new DriverFilter(list);
        }
        return filter;
    }

    private class DriverFilter extends Filter {

        private List<Driver> original;

        public DriverFilter(List<Driver> list) {
            this.original = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = original;
                results.count = original.size();
            } else {
                List<Driver> mList = new ArrayList<Driver>();
                for (Driver driver: original) {
                    if (driver.licensePlate.toUpperCase().startsWith(constraint.toString().toUpperCase())
                            || driver.driverPhone.toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        mList.add(driver);
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            list = (List<Driver>)results.values;
            notifyDataSetChanged();
        }

    }
}
