package cn.trxxkj.trwuliu.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.MyCarActivity;
import cn.trxxkj.trwuliu.driver.ui.MyDriverActivity;

/**
 * 运力模块
 * @author cyh 2016.7.1 下午20:30
 */

public class CapacityFragment extends Fragment implements View.OnClickListener {

    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.activity_capacity, container, false);
        initView();
        return layout;
    }

    private void initView() {
        //我的车辆
        layout.findViewById(R.id.my_car).setOnClickListener(this);
        //我的司机
        layout.findViewById(R.id.my_driver).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_car:
                startActivity(new Intent(getActivity(), MyCarActivity.class));      //打开我的车辆界面
                break;
            case R.id.my_driver:
                startActivity(new Intent(getActivity(),MyDriverActivity.class));    //打开我的司机界面
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 将layout从父组件中移除
        ViewGroup parent = (ViewGroup) layout.getParent();
        parent.removeView(layout);
    }
    
}
