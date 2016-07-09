package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 添加车主
 * @author cyh 2016.6.15 上午16:25
 */

public class AddOwnerActivity extends Activity implements View.OnClickListener {

    private ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);
        initView();
    }

    //初始化界面视图
    private void initView() {

        imgback = (ImageView) findViewById(R.id.img_back);

        imgback.setOnClickListener(this);
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
