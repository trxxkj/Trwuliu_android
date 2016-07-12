package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.utils.MyContents;

/**
 * 设置功能
 * @author cyh 2016.6.20 下午18:35
 */

public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageView imgback;
    private LinearLayout linearLayout2;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sp = getSharedPreferences(MyContents.SP_NAME, MODE_PRIVATE);

        initView();
    }

    private void initView(){

        imgback = (ImageView) findViewById(R.id.img_back);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);

        imgback.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);

        boolean islogin = sp.getBoolean(MyContents.ISLOGIN , false);

        if (!islogin) {
            linearLayout2.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.linearLayout2: // 退出 登陆

                sp.edit().putBoolean(MyContents.ISLOGIN, false).commit();
                sp.edit().putString(MyContents.ACCOUNTNUMBER , "").commit();
                sp.edit().putString(MyContents.PASSWORD , "").commit();

                Intent intent = new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
                break;
        }
    }

}
