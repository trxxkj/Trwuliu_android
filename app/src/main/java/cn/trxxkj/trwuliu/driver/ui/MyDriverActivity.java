package cn.trxxkj.trwuliu.driver.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.fragment.MyDriverFragment;

/**
 * 我的司机
 * @author cyh 2016.6.14 上午9:21
 */

public class MyDriverActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView imgBack;
    private TextView addDriver;

    private MyDriverFragment myDriverFragment;

    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_driver);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        addDriver = (TextView) findViewById(R.id.add_driver);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myDriverFragment = new MyDriverFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(myDriverFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));


        imgBack.setOnClickListener(this);
        addDriver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_driver:
                startActivity(new Intent(this, AddDriverActivity.class));
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
