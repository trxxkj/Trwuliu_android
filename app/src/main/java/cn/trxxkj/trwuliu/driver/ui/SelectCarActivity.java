package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 选择车辆功能
 * @author cyh 2016.6.22 上午11:04
 */

public class SelectCarActivity extends Activity {

    private List<String> mData = new ArrayList<String>();  // 这个数据会改变
    private List<String> mBackData;  // 这是原始的数据

    private ListView mListView;

    private RadioButton radioButton;

    private EditText search;

    private Button determine;

    private DriverAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);

        mListView = (ListView) super.findViewById(R.id.listView);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new ItemClick());

        search = (EditText) super.findViewById(R.id.edit_car);
        determine = (Button) super.findViewById(R.id.determine);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mListView.clearTextFilter();    //清除ListView的过滤
                } else {
                    mListView.setFilterText(s.toString());    //设置ListView的过滤关键词
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initData();
        mAdapter = new DriverAdapter();
        mListView.setAdapter(mAdapter);
    }

    // 必须实现Filterable接口
    private class DriverAdapter extends BaseAdapter implements Filterable {

        private MyFilter mFilter;

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = View.inflate(SelectCarActivity.this, R.layout.item_driver_list,
                        null);
            }
            RadioButton radioBtn = (RadioButton) convertView.findViewById(R.id.radio_btn);
            TextView licensePlate = (TextView) convertView.findViewById(R.id.license_plate);
            TextView driverName = (TextView) convertView.findViewById(R.id.driver_name);
            TextView driverPhoneNumber = (TextView) convertView.findViewById(R.id.driver_phone_number);


            licensePlate.setText(mData.get(position));
            driverName.setText(mData.get(position));
            driverPhoneNumber.setText(mData.get(position));

            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (null == mFilter) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }

        // 自定义Filter类
        class MyFilter extends Filter {
            @Override
            // 该方法在子线程中执行
            // 自定义过滤规则
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                List<String> newValues = new ArrayList<String>();
                String filterString = constraint.toString().trim()
                        .toLowerCase();

                // 如果搜索框内容为空，就恢复原始数据
                if (TextUtils.isEmpty(filterString)) {
                    newValues = mBackData;
                } else {
                    // 过滤出新数据
                    for (String str : mBackData) {
                        if (-1 != str.toLowerCase().indexOf(filterString)) {
                            newValues.add(str);
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                mData = (List<String>) results.values;

                if (results.count > 0) {
                    mAdapter.notifyDataSetChanged();  // 通知数据发生了改变
                } else {
                    mAdapter.notifyDataSetInvalidated(); // 通知数据失效
                }
            }
        }
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mData.add("豫A00000 " + i);
        }
        mBackData = mData;
    }

    private class ItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

        }
    }

}
