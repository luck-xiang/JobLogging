package com.kxiang.job.select;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kxiang.job.R;
import com.kxiang.job.custom.rlv.BaseRecycleViewRadioAdapter;

import java.util.List;

/**
 * 项目名称:Reader
 * 创建人:kexiang
 * 创建时间:2016/9/26 11:39
 */
public class TabAdapter extends BaseRecycleViewRadioAdapter {


    public TabAdapter(Context context, List list, int selectDefault) {
        super(context, list, selectDefault);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TAB) {
            return new RadioHolder(inflater.inflate(R.layout.item_tab_control, parent, false));
        }
        else {
            return new Empty(inflater.inflate(R.layout.item_tab_control_empty, parent, false));
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TAB) {
            CheckTypeBean.CheckBean bean = (CheckTypeBean.CheckBean) list.get(position);
            final RadioHolder item = (RadioHolder) holder;
            if (selectPosition == position) {
                item.ll_all.setSelected(true);
            }
            else {
                item.ll_all.setSelected(false);
            }
            item.tv_music_name.setText(bean.getName());

            item.ll_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position >= 0 && recyclerView != null) {
                        RadioHolder item = (RadioHolder) recyclerView.
                                findViewHolderForLayoutPosition(selectPosition);
                        if (item != null) {//还在屏幕里
                            item.ll_all.setSelected(false);
                        }
                    }
                    item.ll_all.setSelected(true);
                    selectPosition = position;

                    if (onRecycleItemSelectListener != null) {
                        onRecycleItemSelectListener.OnRecycleItemSelect(item.tv_music_name, position);
                    }

                    if (item.ll_all.isSelected()) {
                        if (onSelcectItemLisenter != null) {
                        }
                    }
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private class RadioHolder extends RecyclerView.ViewHolder {

        public TextView tv_music_name;
        public LinearLayout ll_all;


        public RadioHolder(View itemView) {
            super(itemView);
            tv_music_name = (TextView) itemView.findViewById(R.id.tv_music_name);
            ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
        }
    }

    private class Empty extends RecyclerView.ViewHolder {

        public Empty(View itemView) {
            super(itemView);
        }
    }

    private final int TAB = 0;
    private final int EMPTY = 1;


    @Override
    public int getItemViewType(int position) {
        CheckTypeBean.CheckBean bean = (CheckTypeBean.CheckBean) list.get(position);
        if (bean.isCheckSelect()) {
            return TAB;
        }
        else {
            return EMPTY;
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
