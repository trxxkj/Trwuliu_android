package cn.trxxkj.trwuliu.driver.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.ArrayList;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 系统消息列表界面
 * @author cyh 2016.6.30 上午11:30
 */

public class MessageFragment extends Fragment implements View.OnClickListener {

    private View layout;

//    private ListView listView;
//    private PushMessageAdapter messageAdapter;
    private ImageView imgBack;
    private TextView seeMore;

    private ViewPager viewPager;

    private PushMessageFragment pushMessageFragment;
    private ArrayList<Fragment> fragmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        return layout;
    }

    //初始化界面视图
    private void initView() {
//        layout.findViewById(R.id.img_back).setOnClickListener(this);
//        layout.findViewById(R.id.see_more).setOnClickListener(this);



//        listView = (ListView) layout.findViewById(R.id.listView7);
//        PushMessageAdapter messageAdapter = new PushMessageAdapter(this);

//        listView.setAdapter(messageAdapter);
//        imgBack = (ImageView) findViewById(R.id.img_back);
//        seeMore = (TextView) findViewById(R.id.see_more);
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);


        viewPager = (ViewPager) layout.findViewById(R.id.viewPager);

        pushMessageFragment = new PushMessageFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(pushMessageFragment);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager()));

    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // 将layout从父组件中移除
//        ViewGroup parent = (ViewGroup) layout.getParent();
//        parent.removeView(layout);
//    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_back:
//                getActivity().finish();
//                break;
//            case R.id.see_more:
//                break;
//        }
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
