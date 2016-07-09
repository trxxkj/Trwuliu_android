package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 我的消息
 * @author cyh 2016.6.20 上午10:01
 */

public class MyMessageActivity extends Activity implements View.OnClickListener {

    private ImageView imgBack;

    private Button addMessage;
    private Button cancelMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        imgBack = (ImageView) findViewById(R.id.img_back);

        addMessage = (Button) findViewById(R.id.add_message);
        cancelMessage = (Button) findViewById(R.id.cancel_message);

        imgBack.setOnClickListener(this);
        addMessage.setOnClickListener(this);
        cancelMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_message:
                break;
            case R.id.cancel_message:
                break;
        }
    }

}
