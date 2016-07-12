package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 认证模块
 * @author cyh 2016.6.16 上午11:25
 */

public class AuthActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //个人认证
    private EditText personalName;
    private EditText personalPhoneNumber;
    private EditText identificationNumber;
    private ImageView personalUpload;
    //司机认证
    private EditText driverName;
    private EditText driverPhoneNumber;
    private EditText driverLicense;
    private ImageView driverUpload;

    private ImageView imageBack;

    //个人认证界面
    private TextView personalAuth;
    private View indicator1;
    //司机认证界面
    private TextView driverAuth;
    private View indicator2;

    private ViewPager viewPager;
    private List<View> viewList;

    //个人提交审核
    private Button submitReviewPersonal;
    //司机提交审核
    private Button submitReviewDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initView();

    }

    private void initView(){
        personalAuth = (TextView) findViewById(R.id.tv_personal_auth);
        indicator1 = findViewById(R.id.indicator1);
        driverAuth = (TextView) findViewById(R.id.tv_driver_auth);
        indicator2 = findViewById(R.id.indicator2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewList = new ArrayList<View>();

        //将布局转化为视图
        View view1 = View.inflate(this, R.layout.item_personal_auth, null);
        View view2 = View.inflate(this, R.layout.item_driver_auth, null);

        viewList.add(view1);
        viewList.add(view2);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.layout_personal_auth).setOnClickListener(this);
        findViewById(R.id.layout_driver_auth).setOnClickListener(this);

        personalName = (EditText) view1.findViewById(R.id.et_personal_name);
        personalPhoneNumber = (EditText) view1.findViewById(R.id.et_personal_phone_number);
        identificationNumber = (EditText) view1.findViewById(R.id.et_identification_number);
        personalUpload = (ImageView) view1.findViewById(R.id.img_personal_upload);

        submitReviewPersonal = (Button) view1.findViewById(R.id.submit_review_personal);

        driverName = (EditText) view2.findViewById(R.id.et_driver_name);
        driverPhoneNumber = (EditText) view2.findViewById(R.id.et_driver_phone_number);
        driverLicense = (EditText) view2.findViewById(R.id.et_driver_license);
        driverUpload = (ImageView) view2.findViewById(R.id.img_driver_upload);

        submitReviewDriver = (Button) view2.findViewById(R.id.submit_review_driver);

        submitReviewPersonal.setOnClickListener(this);
        submitReviewDriver.setOnClickListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            personalAuth.setTextColor(Color.BLACK);
            driverAuth.setTextColor(getResources().getColor(R.color.dark));
            indicator1.setVisibility(View.VISIBLE);
            indicator2.setVisibility(View.INVISIBLE);
        }
        if (position == 1) {
            driverAuth.setTextColor(Color.BLACK);
            personalAuth.setTextColor(getResources().getColor(R.color.dark));
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
            case R.id.img_back:     //返回按钮
                finish();
                break;
            case R.id.layout_personal_auth:     //个人认证界面
                viewPager.setCurrentItem(0);
                break;
            case R.id.layout_driver_auth:       //司机认证界面
                viewPager.setCurrentItem(1);
                break;
            case R.id.img_personal_upload:      //个人身份证上传
                startActivity(new Intent(this, UploadImageActivity.class));
                break;
            case R.id.img_driver_upload:        //司机驾照上传
                startActivity(new Intent(this,UploadImageActivity.class));
                break;
            case R.id.submit_review_personal:   //个人提交审核
               submitReviewPersonal();
                break;
            case R.id.submit_review_driver:     //司机提交审核
                submitReviewDriver();
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

    /**
     * 个人认证
     */
    private void submitReviewPersonal() {

    }

    /**
     * 司机认证
     */
    private void submitReviewDriver(){

    }
}
