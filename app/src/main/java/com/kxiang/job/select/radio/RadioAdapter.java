package com.kxiang.job.select.radio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kxiang.job.R;
import com.kxiang.job.custom.rlv.BaseRecycleViewRadioAdapter;

import java.util.List;

/**
 * 项目名称:Reader
 * 创建人:kexiang
 * 创建时间:2016/9/26 11:39
 */
public class RadioAdapter extends BaseRecycleViewRadioAdapter {

    public RadioAdapter(Context context, List list, int selectDefault) {
        super(context, list, selectDefault);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RadioHolder(inflater.inflate(R.layout.item_radio, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RadioHolder item = (RadioHolder) holder;


        if (selectPosition == position) {
            item.rl_all.setSelected(true);
        }
        else {
            item.rl_all.setSelected(false);
        }
        item.tv_music_name.setText(list.get(position) + "");

        item.rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中后才能执行该操作
                if (item.rl_all.isSelected()) {
                    if (onSelcectItemLisenter != null) {
                        onSelcectItemLisenter.OnSelcectItem(position, 1);
                    }
                }
                if (position >= 0 && recyclerView != null) {
                    RadioHolder item = (RadioHolder) recyclerView.findViewHolderForLayoutPosition(selectPosition);
                    if (item != null) {//还在屏幕里
                        item.rl_all.setSelected(false);
                    }
                }
                item.rl_all.setSelected(true);
                selectPosition = position;
                if (onRecycleItemSelectListener != null) {
                    onRecycleItemSelectListener.OnRecycleItemSelect(item.tv_music_name, position);
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private class RadioHolder extends RecyclerView.ViewHolder {

        public TextView tv_music_name;
        public RelativeLayout rl_all;


        public RadioHolder(View itemView) {
            super(itemView);
            tv_music_name = (TextView) itemView.findViewById(R.id.tv_music_name);
            rl_all = (RelativeLayout) itemView.findViewById(R.id.rl_all);
        }
    }

    public interface OnSelcectItemLisenter {
        void OnSelcectItem(int position, int type);
    }

    private OnSelcectItemLisenter onSelcectItemLisenter;

    public void setOnSelcectRadioHolderLisenter(OnSelcectItemLisenter onSelcectItemLisenter) {
        this.onSelcectItemLisenter = onSelcectItemLisenter;
    }
}
