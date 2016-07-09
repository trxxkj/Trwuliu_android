package cn.trxxkj.trwuliu.driver.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.fragment.ListViewFragment7;

/**
 * 已发出运单
 * @author cyh 2016.6.9 上午10:20
 */

public class IssueWaybillActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView img_back;

    private ListViewFragment7 listViewFragment7;

    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_waybill);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        img_back = (ImageView) findViewById(R.id.img_back);

        listViewFragment7 = new ListViewFragment7();
        fragmentList = new ArrayList<>();
        fragmentList.add(listViewFragment7);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
//        viewPager.addOnPageChangeListener(this);

        img_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
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
