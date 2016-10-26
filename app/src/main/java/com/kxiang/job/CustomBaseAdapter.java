package com.kxiang.job;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义基本适配器
 *
 * @param <T>
 */
public abstract class CustomBaseAdapter<T> extends BaseAdapter {

    public static SparseArray<View> viewHolder;
    protected int id;
    protected Context context;
    protected List<T> list = new ArrayList<T>();
    private int slectid = -1;

    public int getSlectid() {
        return slectid;
    }

    public void setSlectid(int slectid) {
        this.slectid = slectid;
    }

    /**
     * 构造函数
     *
     * @param context
     */
    public CustomBaseAdapter(Context context) {
        this.context = context;
        viewHolder = new SparseArray<View>();
    }




    /**
     * 设置list 尾部添加一条数据
     */

    public void addListBottom(T t) {

        this.list.add(t);

        this.notifyDataSetChanged();
    }


    /**
     * 设置list 头部添加一条数据
     */

    public void addListTop(T t) {

        this.list.add(0,t);

        this.notifyDataSetChanged();
    }




    /**
     * 设置list 更新数据源中的所有数据
     *
     * @param list
     */

    public void setChangeListAll(List<T> list) {


        this.list = list;

        this.notifyDataSetChanged();
    }

    /**
     * 设置list 添加所有数据数据源中的所有数据
     *
     * @param list
     */

    public void setListAll(List<T> list) {

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.list.add(iterator.next());
        }
        this.notifyDataSetChanged();
    }



    /**
     * 设置list 尾部添加数据
     *
     * @param list
     */

    public void setListBottom(List<T> list) {

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.list.add(iterator.next());
        }
        this.notifyDataSetChanged();
    }




    /**
     * 设置list 头部添加数据
     *
     * @param list
     */
    public void setListTop(List<T> list) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.list.add(0, iterator.next());
        }
        this.notifyDataSetChanged();

    }

    /**
     * 返回list
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    @Override
    public int getCount() {

        if (list != null && list.size() > 0) {
            return list.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    public static class ViewHolder {


        public static void setViewByID(View view, int id) {
            View childView = view.findViewById(id);
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }

        public static void setTag(View view) {
            view.setTag(viewHolder);
        }

        @SuppressWarnings("unchecked")
        public static void getTag(View view) {
            viewHolder = (SparseArray<View>) view.getTag();
        }

        /**
         * adapter优化
         *
         * @param view 适配器自带view
         * @param id   对应控件的id
         * @return
         */

        @SuppressWarnings({"unchecked"})
        public static <T extends View> T getViewByID(View view, int id) {
            View childView = viewHolder.get(id);
            return (T) childView;
        }

    }

    /**
     * 设置adapter 图片宽比高
     *
     * @param width
     * @param height
     * @return
     */
    protected LayoutParams getLayoutParams(int width, int height) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.height = height;
        return params;
    }

}
