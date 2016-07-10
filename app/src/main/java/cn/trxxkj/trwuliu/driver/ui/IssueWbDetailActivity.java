package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 运单详情
 * @author cyh 2016.6.1 下午16:20
 */
public class IssueWbDetailActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_wb_detail);

        /**
         * 初始化控件
         */
        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.see_car).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.see_car:
                startActivity(new Intent(IssueWbDetailActivity.this, IssueDriverActivity.class));
                break;
        }
    }

}
