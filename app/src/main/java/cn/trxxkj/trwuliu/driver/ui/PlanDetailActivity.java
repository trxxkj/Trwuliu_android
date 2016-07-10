package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 计划详情
 * @author cyh 2016.6.13 上午11:51
 */

public class PlanDetailActivity extends Activity implements View.OnClickListener {

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.confirmplan).setOnClickListener(this);
        findViewById(R.id.rejectplan).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.confirmplan:
                confirmDialog(v);
                break;
            case R.id.rejectplan:
                rejectDialog(v);
                break;
        }
    }

    private void confirmDialog(View v) {
        builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("你的计划已确认");
        builder.setMessage("你的计划已经确认，请及时添加司机，如果有疑问请及时联系客服");

        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(),"你点击了确定按钮",Toast.LENGTH_SHORT).show();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void rejectDialog(View v) {
        builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("填写拒绝运单理由");

        /**
         * 设置内容区域为单选列表项
         */
        final String[] items = {"无法接受这么多货物", "车辆无法使用", "已有货物 无法接受", "身体不适"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), "You clicked "+items[i], Toast.LENGTH_SHORT).show();
            }
        });

        //监听下方button点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(),"你点击了确定按钮",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), "你点击了取消按钮", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
