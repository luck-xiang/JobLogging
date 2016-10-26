package com.kxiang.job.select.check;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kxiang.job.R;
import com.kxiang.job.custom.rlv.BaseRecycleViewAdapter;

import java.util.List;

/**
 * 项目名称:Reader
 * 创建人:kexiang
 * 创建时间:2016/9/26 11:39
 */
public class CheckAdapter extends BaseRecycleViewAdapter {


    public CheckAdapter(Context context, List list) {
        super(context, list);
    }

    public void notifyCleanSelect() {

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RadioHolder(inflater.inflate(R.layout.item_check, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RadioHolder item = (RadioHolder) holder;
        final CheckBean bean = (CheckBean) list.get(position);

        item.rl_all.setSelected(bean.isCheck());


        item.tv_name.setText(bean.getName());

        item.rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!item.rl_all.isSelected()) {
                    item.rl_all.setSelected(true);
                    bean.setCheck(true);
                }

            }
        });
    }


    private class RadioHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public RelativeLayout rl_all;


        public RadioHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            rl_all = (RelativeLayout) itemView.findViewById(R.id.rl_all);
        }
    }


}
