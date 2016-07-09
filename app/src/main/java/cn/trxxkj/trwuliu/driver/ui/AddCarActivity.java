package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 添加车辆
 * @author cyh 2016.6.10 下午15:20
 */

public class AddCarActivity extends Activity implements View.OnClickListener {

    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        initView();
    }

    //初始化界面视图
    private void initView() {

        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

}
