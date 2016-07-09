package cn.trxxkj.trwuliu.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.ui.AuthActivity;
import cn.trxxkj.trwuliu.driver.ui.CapacityActivity;
import cn.trxxkj.trwuliu.driver.ui.PlanActivity;
import cn.trxxkj.trwuliu.driver.ui.SettingActivity;
import cn.trxxkj.trwuliu.driver.ui.WaybillActivity;

/**
 * 我的界面
 * @author cyh 2016.5.10 下午15:52
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return layout;
    }

    //初始化视图
    private void initView() {
        //计划
        layout.findViewById(R.id.layout_mine_plan).setOnClickListener(this);
        //我发布的运单
        layout.findViewById(R.id.layout_mine_postwaybill).setOnClickListener(this);
        //我接收的运单
        layout.findViewById(R.id.layout_mine_receivewaybill).setOnClickListener(this);
        //我的运力
        layout.findViewById(R.id.layout_mine_capacity).setOnClickListener(this);
        //我的认证
        layout.findViewById(R.id.layout_mine_auth).setOnClickListener(this);
        //设置
        layout.findViewById(R.id.layout_mine_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_mine_plan:
                startActivity(new Intent(getActivity(), PlanActivity.class));
                break;
            case R.id.layout_mine_postwaybill:
//                startActivity(new Intent(getActivity(), WaybillActivity.class));
                break;
            case R.id.layout_mine_receivewaybill:
                break;
            case R.id.layout_mine_capacity:
                startActivity(new Intent(getActivity(), CapacityActivity.class));
                break;
            case R.id.layout_mine_auth:
                startActivity(new Intent(getActivity(), AuthActivity.class));
                break;
            case R.id.layout_mine_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
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
