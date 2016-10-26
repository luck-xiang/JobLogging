package com.kxiang.job.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kxiang.job.R;
import com.kxiang.job.select.SelectActivity;

import static com.kxiang.job.R.id.btn_calendar;

public class CalendarActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        initCalendar();
        Log.e("tv_calendar", "onCreate:" + tv_calendar.getWidth());
        tv_calendar.post(new Runnable() {
            @Override
            public void run() {
                Log.e("tv_calendar", "post:" + tv_calendar.getWidth());
            }
        });
        Log.e("activity", "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("activity", "onDestroy");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e("tv_calendar", "onPostCreate:" + tv_calendar.getWidth());
        Log.e("activity", "onPostCreate");
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("tv_calendar", "onWindowFocusChanged:" + tv_calendar.getWidth());
        Log.e("activity", "onWindowFocusChanged");
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.e("tv_calendar", "" + tv_calendar.getWidth());
    }

    private TextView tv_calendar;

    private void initCalendar() {
        tv_calendar = (TextView) findViewById(R.id.tv_calendar);
        initOnClick(btn_calendar);
    }


    private void initOnClick(int id) {
        findViewById(id).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_calendar:
                DialogCalender calender = new DialogCalender();
                calender.show(getSupportFragmentManager(), "dialog_calender");
                calender.setOnItemClickListener(new DialogCalender.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int year, int month, int day) {
                        tv_calendar.setText(year + "年" + month + "月" + day + "日");
                        Log.e("tv_calendar", "" + tv_calendar.getWidth());
                    }
                });
                startActivity(new Intent(this, SelectActivity.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("activity", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("activity", "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("activity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("activity", "onPause");
    }
}
