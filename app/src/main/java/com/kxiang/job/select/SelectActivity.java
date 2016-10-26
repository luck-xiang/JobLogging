package com.kxiang.job.select;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kxiang.job.R;
import com.kxiang.job.custom.rlv.OnRecycleItemSelectListener;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initTabs();
    }


    private TabAdapter tabAdapter;
    private RecyclerView rlv_tab;
    private int[] tabImgId;
    private CheckTypeBean checkTypeBean;
    private List<CheckTypeBean.CheckBean> listSingle;
    private String[] name;
    private Fragment[] fragment;


    private void initTabs() {

        checkTypeBean = new CheckTypeBean();
        listSingle = new ArrayList<>();
        fragment = new Fragment[]{
                new RadioFragment(),
                new CheckFragment()
        };
        replaceFragment = fragment[0];
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main, fragment[0]).commit();
        tabImgId = new int[]{
                R.drawable.back_normal,
                R.drawable.back_normal,

        };
        name = new String[]{
                "单选",
                "多选",

        };
        int tempI = name.length;
        for (int i = 0; i < tempI; i++) {

            CheckTypeBean.CheckBean bean = new CheckTypeBean.CheckBean();
            bean.setName(name[i]);
            bean.setCheckSelect(true);
            listSingle.add(bean);

        }
        checkTypeBean.setType("fdfa");
        checkTypeBean.setCheckList(listSingle);
        rlv_tab = (RecyclerView) findViewById(R.id.rlv_tab);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlv_tab.setLayoutManager(manager);
        tabAdapter = new TabAdapter(this, checkTypeBean.getCheckList(), 0);
        rlv_tab.setAdapter(tabAdapter);
        tabAdapter.setRecycleView(rlv_tab);
        tabAdapter.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {

                addOrshowFragment(replaceFragment, fragment[position],
                        getSupportFragmentManager().beginTransaction());


            }
        });
    }

    private Fragment replaceFragment;

    private void addOrshowFragment(Fragment from,
                                   Fragment to,
                                   FragmentTransaction tran) {
        if (from != to) {
            if (to.isAdded()) {
                tran.hide(from).show(to).commit();
            }
            else {
                tran.hide(from).add(R.id.fl_main, to).commit();
            }
            replaceFragment = to;

        }

    }
}
