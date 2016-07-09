package cn.trxxkj.trwuliu.driver.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.fragment.RefusePlanFragment;
import cn.trxxkj.trwuliu.driver.ui.fragment.UnconfirmPlanFragment;
import cn.trxxkj.trwuliu.driver.ui.fragment.OngoingPlanFragment;
import cn.trxxkj.trwuliu.driver.ui.fragment.CompletePlanFragment;

/**
 * 计划功能
 * @author cyh 2016.6.12 下午17:10
 */

public class PlanActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    //未确认计划界面
    private UnconfirmPlanFragment unconfirmPlanFragment;
    //进行中计划界面
    private OngoingPlanFragment ongoingPlanFragment;
    //已完成计划界面
    private CompletePlanFragment completePlanFragment;
    //已拒绝计划界面
    private RefusePlanFragment refusePlanFragment;

    private ArrayList<Fragment> fragmentList;

    private ImageView img_back;
    private RelativeLayout ly_page1;
    private TextView tv_page1;
    private RelativeLayout ly_page2;
    private TextView tv_page2;
    private RelativeLayout ly_page3;
    private TextView tv_page3;
    private RelativeLayout ly_page4;
    private TextView tv_page4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        img_back = (ImageView) findViewById(R.id.img_back);

        ly_page1 = (RelativeLayout) findViewById(R.id.ly_page1);
        tv_page1 = (TextView) findViewById(R.id.tv_page1);
        ly_page2 = (RelativeLayout) findViewById(R.id.ly_page2);
        tv_page2 = (TextView) findViewById(R.id.tv_page2);
        ly_page3 = (RelativeLayout) findViewById(R.id.ly_page3);
        tv_page3 = (TextView) findViewById(R.id.tv_page3);
        ly_page4 = (RelativeLayout) findViewById(R.id.ly_page4);
        tv_page4 = (TextView) findViewById(R.id.tv_page4);

        unconfirmPlanFragment = new UnconfirmPlanFragment();
        ongoingPlanFragment = new OngoingPlanFragment();
        completePlanFragment = new CompletePlanFragment();
        refusePlanFragment = new RefusePlanFragment();

        fragmentList = new ArrayList<>();

        fragmentList.add(unconfirmPlanFragment);
        fragmentList.add(ongoingPlanFragment);
        fragmentList.add(completePlanFragment);
        fragmentList.add(refusePlanFragment);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);

        img_back.setOnClickListener(this);
        ly_page1.setOnClickListener(this);
        ly_page2.setOnClickListener(this);
        ly_page3.setOnClickListener(this);
        ly_page4.setOnClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            ly_page1.setBackgroundResource(R.drawable.rectangle_left_select);
            tv_page1.setTextColor(Color.parseColor("#ffffff"));
            ly_page2.setBackgroundResource(R.drawable.rectangle_middle);
            tv_page2.setTextColor(Color.parseColor("#435356"));
            ly_page3.setBackgroundResource(R.drawable.rectangle_right);
            tv_page3.setTextColor(Color.parseColor("#435356"));
            ly_page4.setBackgroundResource(R.drawable.rectangle_end);
            tv_page4.setTextColor(Color.parseColor("#435356"));
        }
        if (position == 1) {
            ly_page1.setBackgroundResource(R.drawable.rectangle_left);
            tv_page1.setTextColor(Color.parseColor("#435356"));
            ly_page2.setBackgroundResource(R.drawable.rectangle_middle_select);
            tv_page2.setTextColor(Color.parseColor("#ffffff"));
            ly_page3.setBackgroundResource(R.drawable.rectangle_right);
            tv_page3.setTextColor(Color.parseColor("#435356"));
            ly_page4.setBackgroundResource(R.drawable.rectangle_end);
            tv_page4.setTextColor(Color.parseColor("#435356"));
        }
        if (position == 2) {
            ly_page1.setBackgroundResource(R.drawable.rectangle_left);
            tv_page1.setTextColor(Color.parseColor("#435356"));
            ly_page2.setBackgroundResource(R.drawable.rectangle_middle);
            tv_page2.setTextColor(Color.parseColor("#435356"));
            ly_page3.setBackgroundResource(R.drawable.rectangle_right_select);
            tv_page3.setTextColor(Color.parseColor("#ffffff"));
            ly_page4.setBackgroundResource(R.drawable.rectangle_end);
            tv_page4.setTextColor(Color.parseColor("#435356"));
        }
        if (position == 3) {
            ly_page1.setBackgroundResource(R.drawable.rectangle_left);
            tv_page1.setTextColor(Color.parseColor("#435356"));
            ly_page2.setBackgroundResource(R.drawable.rectangle_middle);
            tv_page2.setTextColor(Color.parseColor("#435356"));
            ly_page3.setBackgroundResource(R.drawable.rectangle_right);
            tv_page3.setTextColor(Color.parseColor("#435356"));
            ly_page4.setBackgroundResource(R.drawable.rectangle_end_select);
            tv_page4.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ly_page1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.ly_page2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.ly_page3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.ly_page4:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return getCount() > position ? fragmentList.get(position) : null;
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
//            return fragmentList == null ? 0 : fragmentList.size();
            return fragmentList.size();
        }

    }



}
