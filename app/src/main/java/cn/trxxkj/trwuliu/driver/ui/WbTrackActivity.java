package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 运单轨迹
 * @author cyh 2016.5.25
 */

public class WbTrackActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //    private ListView mListView;
    private List<View> viewList;
    private ViewPager viewPager1;

    private ListView listView;

//    private WaybillTrackFragment waybillStateFragment;
//    private WaybillDetailFragment waybillDetailFragment;

//    private ArrayList<Fragment> fragmentList;

    private ImageView img_back;

    //运单状态
    private TextView status;
    //运单详情
    private TextView detail;

    private View indicator1;
    private View indicator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_track);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        viewList = new ArrayList<View>();

        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);

        //将布局转化为视图
        View view1 = View.inflate(this, R.layout.item_wbtrack_list, null);
        View view2 = View.inflate(this, R.layout.item_wbdetail_list, null);

        viewList.add(view1);
        viewList.add(view2);

        img_back = (ImageView) findViewById(R.id.img_back);

        status = (TextView) findViewById(R.id.tv_status);
        detail = (TextView) findViewById(R.id.tv_detail);
        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList);
        viewPager1.setAdapter(adapter);
        viewPager1.addOnPageChangeListener(this);

//        img_back.setOnClickListener(this);
        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.layout_status).setOnClickListener(this);

        findViewById(R.id.layout_detail).setOnClickListener(this);
//        status.setOnClickListener(this);
//        detail.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            status.setTextColor(Color.RED);
            detail.setTextColor(getResources().getColor(R.color.dark));
            indicator1.setVisibility(View.VISIBLE);
            indicator2.setVisibility(View.INVISIBLE);
        }
        if (position == 1) {
            detail.setTextColor(Color.RED);
            status.setTextColor(getResources().getColor(R.color.dark));
            indicator1.setVisibility(View.INVISIBLE);
            indicator2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:    //返回
                finish();
                break;
            case R.id.layout_status:  //运单状态
//                StatusClick();
                viewPager1.setCurrentItem(0);
                break;
            case R.id.layout_detail:  //运单详情
//                DetailClick();
                viewPager1.setCurrentItem(1);
                break;
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private List<View> viewList;

        public MyViewPagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            //返回页卡的数量
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

    }

}
