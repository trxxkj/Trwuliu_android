package cn.trxxkj.trwuliu.driver.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.trxxkj.trwuliu.driver.R;
import cn.trxxkj.trwuliu.driver.adapter.UnconfirmWbAdapter;
import cn.trxxkj.trwuliu.driver.ui.WbDetailActivity;

/**
 * 已确认运单界面
 * @author cyh 2016.6.17下午14:39
 */
public class UnconfirmWbFragment extends ListFragment {

    private UnconfirmWbAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UnconfirmWbAdapter(getActivity());
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
        startActivity(new Intent(getActivity(), WbDetailActivity.class));
    }

}
