package com.kxiang.job;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MainListAdapter<T> extends CustomBaseAdapter<Map<String, Object>> {
    private TextView tv_main_list_adapter_item_id;
    private Context context;
    /**
     * 构造函数
     */
    public MainListAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            view=View.inflate(context, R.layout.item_main_list_adapter,null);
            tv_main_list_adapter_item_id= (TextView) view.findViewById(R.id.tv_main_list_adapter_item_id);
            view.setTag(tv_main_list_adapter_item_id);
        }


        tv_main_list_adapter_item_id= (TextView) view.getTag();

        tv_main_list_adapter_item_id.setText(list.get(i).get("title")+"");


        return view;
    }
}
