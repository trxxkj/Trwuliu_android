package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 运力模块
 * @author cyh 2016.6.17 上午10:10
 */

public class CapacityActivity extends Activity implements View.OnClickListener {

//    private ImageView imgBack;
    private TextView myOwner;
    private TextView myCar;
    private TextView myDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);
        initView();
    }

    //初始化界面视图
    private void initView() {
//        imgBack = (ImageView) findViewById(R.id.img_back);

        myOwner = (TextView) findViewById(R.id.my_owner);
        myCar = (TextView) findViewById(R.id.my_car);
        myDriver = (TextView) findViewById(R.id.my_driver);

//        imgBack.setOnClickListener(this);
        myOwner.setOnClickListener(this);
        myCar.setOnClickListener(this);
        myDriver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.my_owner:
                startActivity(new Intent(this, AddOwnerActivity.class));
                break;
            case R.id.my_car:
                startActivity(new Intent(this, MyCarActivity.class));
                break;
            case R.id.my_driver:
                startActivity(new Intent(this, MyDriverActivity.class));
                break;
        }
    }

}
