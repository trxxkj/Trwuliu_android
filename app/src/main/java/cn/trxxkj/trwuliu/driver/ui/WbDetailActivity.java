package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 运单详情
 * @author cyh 2016.5.26 下午14:37
 */

public class WbDetailActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_detail);

        findViewById(R.id.img_back).setOnClickListener(this);
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
