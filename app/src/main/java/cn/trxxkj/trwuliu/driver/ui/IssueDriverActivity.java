package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.DriverAdapter;

/**
 *
 */

public class IssueDriverActivity extends Activity implements View.OnClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_driver);

        findViewById(R.id.img_back).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView4);

//        DriverAdapter driverAdapter = new DriverAdapter(this);

//        listView.setAdapter(driverAdapter);
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
