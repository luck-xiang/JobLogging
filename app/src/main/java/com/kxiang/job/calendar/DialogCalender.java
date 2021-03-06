package com.kxiang.job.calendar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kxiang.job.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 项目名称:JobLogging
 * 创建人:kexiang
 * 创建时间:2016/10/25 9:43
 */

public class DialogCalender extends DialogFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.dialog_calendar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVpCalender();
    }


    private ViewPager vp_calender;
    private int year;
    private int month;
    private int day;
    private Calendar calender;

    private final int pagerItem = 960;
    private TextView tv_calendar_year;
    private TextView tv_calendar_month;


    private void initVpCalender() {

        vp_calender = (ViewPager) getView().findViewById(R.id.vp_calender);
        tv_calendar_year = (TextView) getView().findViewById(R.id.tv_calendar_year);
        tv_calendar_month = (TextView) getView().findViewById(R.id.tv_calendar_month);

        getView().findViewById(R.id.btn_last).setOnClickListener(this);
        getView().findViewById(R.id.btn_next).setOnClickListener(this);
        vp_calender.setAdapter(new CalenderPageAdapter());
        vp_calender.setCurrentItem(pagerItem);
        calender = Calendar.getInstance();
        year = calender.get(Calendar.YEAR);
        month = calender.get(Calendar.MONTH) + 1;
        day = calender.get(Calendar.DAY_OF_MONTH);
        tv_calendar_year.setText(year + "年");
        tv_calendar_month.setText(month + "月");
        vp_calender.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                int tempYear = (month + position - pagerItem - 1);
                int yearTemp;
                if (tempYear > 0) {
                    yearTemp = year + tempYear / 12;
                }
                else {
                    yearTemp = year + (tempYear - 12 + 1) / 12;
                }

                int monthTemp = (month + position - 1) % 12 + 1;
                tv_calendar_year.setText(yearTemp + "年");
                tv_calendar_month.setText(monthTemp + "月");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_calendar_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listTxt = new ArrayList<>();
                for (int i = 1970; i <= 2050; i++) {
                    listTxt.add(i + "");
                }
                PopDropDownBox box = new PopDropDownBox(getContext(), tv_calendar_year.getWidth(),
                        tv_calendar_year, listTxt);
                box.showAsDropDown(tv_calendar_year);
                box.setOnItemSelcectListener(new PopDropDownBox.OnItemSelcectListener() {
                    @Override
                    public void OnItemSelcect(int item) {

                        vp_calender.setCurrentItem(vp_calender.getCurrentItem() + item * 12);
                    }
                });
            }
        });
        tv_calendar_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> listTxt = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                    listTxt.add(i + "");
                }
                PopDropDownBox box = new PopDropDownBox(getContext(), tv_calendar_month.getWidth(),
                        tv_calendar_month, listTxt);
                box.showAsDropDown(tv_calendar_month);
                box.setOnItemSelcectListener(new PopDropDownBox.OnItemSelcectListener() {
                    @Override
                    public void OnItemSelcect(int item) {

                        vp_calender.setCurrentItem(vp_calender.getCurrentItem() + item);
                    }
                });
            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_last:

                vp_calender.setCurrentItem(vp_calender.getCurrentItem() - 1);
                break;
            case R.id.btn_next:

                vp_calender.setCurrentItem(vp_calender.getCurrentItem() + 1);
                break;

        }
    }


    public class CalenderPageAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int tempYear = (month + position - pagerItem - 1);
            int yearTemp;
            if (tempYear > 0) {
                yearTemp = year + tempYear / 12;
            }
            else {
                yearTemp = year + (tempYear - 12 + 1) / 12;
            }

            int monthTemp = (month + position - 1) % 12 + 1;
            int dayTemp;
            if (yearTemp == year && monthTemp == month) {
                dayTemp = day;
            }
            else {
                dayTemp = CalendarPageView.NO_MONTH;
            }


            CalendarSingleView view = new CalendarSingleView(getContext(), yearTemp, monthTemp,
                    dayTemp);
            view.setOnItemClickListener(calendarItemClickListener);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 2000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private CalendarItemClickListener calendarItemClickListener = new CalendarItemClickListener();

    class CalendarItemClickListener implements CalendarSingleView.OnItemClickListener {
        @Override
        public void OnItemClick(String date) {

            if (onItemClickListener != null)
                onItemClickListener.OnItemClick(getInt(tv_calendar_year.getText() + ""),
                        getInt(tv_calendar_month.getText() + ""),
                        Integer.parseInt(date));

        }
    }

    private int getInt(String str) {

        return Integer.parseInt(str.substring(0, str.length() - 1));

    }

    private OnItemClickListener onItemClickListener;

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //监听接口
    public interface OnItemClickListener {
        void OnItemClick(int year, int month, int day);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

}
