package cn.trxxkj.trwuliu.driver.ui;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.TrApplication;
import cn.trxxkj.trwuliu.driver.ui.fragment.CapacityFragment;
import cn.trxxkj.trwuliu.driver.ui.fragment.HomeFragment;
import cn.trxxkj.trwuliu.driver.ui.fragment.MessageFragment;
import cn.trxxkj.trwuliu.driver.ui.fragment.MineFragment;

/**
 * 主界面布局
 * @author cyh 2016.4.1 下午14:25
 */
public class MainActivity extends FragmentActivity {

    private ProgressDialog dialog;

    private FragmentTabHost mTabHost;
    private Class[] mFragments = new Class[]{HomeFragment.class,
            MessageFragment.class, CapacityFragment.class, MineFragment.class};
    private int[] mTabSelectors = new int[]{R.drawable.main_bottom_tab_home,
            R.drawable.main_bottom_tab_message, R.drawable.main_bottom_tab_capacity,
            R.drawable.main_bottom_tab_mine};
     //Tab选项卡的文字
    private String mTextviewArray[] = { "首页", "车信", "运力", "我" };
    private String[] mTabSpecs = new String[]{"home", "message", "capacity", "mine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);

        //百度云推送
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "B0pttqMTh7f1zOR5YpfR2aXl");

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        addTab();
    }

    private void addTab() {
//        int len = mTabSelectors.length;
        for (int i = 0; i < 4; i++) {
            View tabIndicator = getLayoutInflater().inflate(
                    R.layout.tab_indicator, null);
            ImageView imageView = (ImageView) tabIndicator
                    .findViewById(R.id.imageView);
            imageView.setImageResource(mTabSelectors[i]);
            TextView textView = (TextView) tabIndicator.findViewById(R.id.textView);
            textView.setText(mTextviewArray[i]);
            mTabHost.addTab(
                    mTabHost.newTabSpec(mTabSpecs[i])
                            .setIndicator(tabIndicator), mFragments[i], null);
        }
    }

    @Override
    protected void onDestroy() {
        dialog.dismiss();
        super.onDestroy();
    }

}
