package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 设置功能
 * @author cyh 2016.6.20 下午18:35
 */

public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView(){

        imgback = (ImageView) findViewById(R.id.img_back);

        imgback.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }

}
