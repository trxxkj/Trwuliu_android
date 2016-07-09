package cn.trxxkj.trwuliu.driver.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.fragment.MyOwnerFragment;

public class MyOwnerActivity extends FragmentActivity implements View.OnClickListener {

    private EditText searchInput;
    private ViewPager viewPager;
    private ImageView imgBack;
    private TextView addOwner;

    private MyOwnerFragment myOwnerFragment;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_owner);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView(){

        searchInput = (EditText) findViewById(R.id.search_input);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgBack = (ImageView) findViewById(R.id.img_back);
        addOwner = (TextView) findViewById(R.id.add_owner);

        myOwnerFragment = new MyOwnerFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(myOwnerFragment);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

        imgBack.setOnClickListener(this);
        addOwner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.add_owner:
                break;
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
