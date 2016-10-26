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
import com.kxiang.job.custom.rlv.BaseRecycleViewRadioAdapter;
import com.kxiang.job.custom.rlv.OnRecycleItemSelectListener;
import com.kxiang.job.select.radio.RadioAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {


    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_radio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleRadioView();
        Log.e("onHiddenChanged","onActivityCreated："+rlv_radio.getWidth());
        rlv_radio.post(new Runnable() {
            @Override
            public void run() {
                Log.e("onHiddenChanged","post："+rlv_radio.getWidth());
            }
        });

    }


    private RecyclerView rlv_radio;
    private RadioAdapter radioAdapter;
    private List<String> listTxt;


    public void initRecycleRadioView() {

        
        rlv_radio= (RecyclerView) getView().findViewById(R.id.rlv_radio);
        listTxt = new ArrayList<>();

        int i=100;
        for (int i1 = 0; i1 < i; i1++) {
            listTxt.add(""+i);
        }


        radioAdapter = new RadioAdapter(getActivity(), listTxt,
                BaseRecycleViewRadioAdapter.Select_No);

        rlv_radio.setAdapter(radioAdapter);
        rlv_radio.setLayoutManager(new GridLayoutManager(getContext(), 6));

        radioAdapter.setRecycleView(rlv_radio);
        radioAdapter.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
            }
        });

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.e("onHiddenChanged","rlv_radio："+rlv_radio.getWidth());

    }
}
