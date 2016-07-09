package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 进行中运单
 * @author cyh 2016.6.15 下午16:35
 */

public class PlanOngoingActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_ongoing);

        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.generate_waybill).setOnClickListener(this);

//        findViewById(R.id.issue_waybill).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.generate_waybill:
                startActivity(new Intent(PlanOngoingActivity.this, PostWaybillActivity.class));
                break;
//            case R.id.issue_waybill:
//                startActivity(new Intent(PlanOngoingActivity.this, IssueWaybillActivity.class));
        }
    }

}

