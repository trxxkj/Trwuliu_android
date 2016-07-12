package cn.trxxkj.trwuliu.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.OngoingPlanAdapter;
import cn.trxxkj.trwuliu.driver.ui.PlanOngoingActivity;

/**
 * 进行中计划界面
 * @author cyh 2016.6.16 上午10:30
 */

public class OngoingPlanFragment extends ListFragment {

    private OngoingPlanAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new OngoingPlanAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(getActivity(), PlanOngoingActivity.class));
    }

}

