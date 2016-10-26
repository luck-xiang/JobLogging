package com.kxiang.job.select;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kxiang.job.R;
import com.kxiang.job.select.check.CheckAdapter;
import com.kxiang.job.select.check.CheckBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {


    public CheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleCheckView();
        Log.e("onHiddenChanged","onActivityCreated："+rlv_check.getWidth());
        rlv_check.post(new Runnable() {
            @Override
            public void run() {
                Log.e("onHiddenChanged","post："+rlv_check.getWidth());
            }
        });
    }

    private RecyclerView rlv_check;
    private CheckAdapter checkAdapter;
    private List<CheckBean> listSingle;

    public void initRecycleCheckView() {
        rlv_check = (RecyclerView) getView().findViewById(R.id.rlv_check);
        listSingle = new ArrayList<>();
        int temp = 50;
        for (int i = 0; i < temp; i++) {
            CheckBean bean = new CheckBean();
            bean.setName("" + i);
            listSingle.add(bean);
        }

        checkAdapter = new CheckAdapter(getActivity(), listSingle);
        rlv_check.setAdapter(checkAdapter);
        rlv_check.setLayoutManager(new GridLayoutManager(getContext(), 5));

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.e("onHiddenChanged","rlv_check："+rlv_check.getWidth());

    }
}
