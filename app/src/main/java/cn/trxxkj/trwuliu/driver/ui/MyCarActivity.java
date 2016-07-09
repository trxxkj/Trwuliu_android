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
import cn.trxxkj.trwuliu.driver.ui.fragment.MyCarFragment;

/**
 * 我的车辆
 * @author cyh 2016.6.13 下午15:56
 */
public class MyCarActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView imgBack;
    private TextView addCar;

    private MyCarFragment myCarFragment;

    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        addCar = (TextView) findViewById(R.id.add_car);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        myCarFragment = new MyCarFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(myCarFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        imgBack.setOnClickListener(this);
        addCar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_car:
                startActivity(new Intent(this, AddCarActivity.class));
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
