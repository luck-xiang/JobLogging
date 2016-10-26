package com.kxiang.job.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:userdefinedview2
 * 创建人:kexiang
 * 创建时间:2016/10/24 10:22
 * 这里是修改了一部分别人的写View
 */

public class CalendarPageView extends View {

    private Surface surface;
    private int downBgIndex = 0; // 按下的格子索引
    public final static int NO_MONTH = -1;
    private int weakSize = 0;

    public CalendarPageView(Context context, int year, int month, int today) {
        super(context);
        init(year, month, today);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // 画框
        canvas.drawPath(surface.boxPath, surface.borderPaint);

        // 星期
       // float weekTextY = surface.weekHeight * 3 / 4f;

        Paint.FontMetricsInt fontMetrics = surface.weekPaint.getFontMetricsInt();

        int baseline= (int) ((surface.weekHeight-fontMetrics.bottom - fontMetrics.top) / 2);

        for (int i = 0; i < surface.weekText.length; i++) {
            float weekTextX = i * surface.cellWidth +
                    (surface.cellWidth - surface.weekPaint.measureText(surface.weekText[i])) / 2f;
            if (i == 0 || i == surface.weekText.length - 1) {
                surface.weekPaint.setColor(surface.weekendColor);
            }
            else {
                surface.weekPaint.setColor(surface.workdayColor);
            }


            canvas.drawText(surface.weekText[i], weekTextX, baseline,
                    surface.weekPaint);
        }
        // 按下状态，选择状态背景色
        if (today == NO_MONTH && downBgIndex == weakSize) {

        }
        else {
            drawCellBg(canvas, downBgIndex, surface.cellSelectedColor);
        }


        for (int i = 0; i < 42; i++) {
            int color = surface.noSelectTextColor;
            if (monthBean.getDay().get(i).isDimBright()) {
                if (monthBean.getDay().get(i).getSolarCalendar().equals(today + "")) {
                    color = surface.todayTextColor;
                }
                else {
                    color = surface.textColor;
                }
            }
            drawCellText(canvas, i, monthBean.getDay().get(i).getSolarCalendar(), color);
        }
        super.onDraw(canvas);
    }


    /**
     * @param canvas
     * @param index
     * @param text
     */
    private void drawCellText(Canvas canvas, int index, String text, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.datePaint.setColor(color);
        float cellY = surface.weekHeight + (y - 1)
                * surface.cellHeight + surface.cellHeight * 3 / 4f;
        float cellX = (surface.cellWidth * (x - 1))
                + (surface.cellWidth - surface.datePaint.measureText(text))
                / 2f;
        canvas.drawText(text, cellX, cellY, surface.datePaint);
    }

    /**
     * @param canvas
     * @param index
     * @param color
     */
    private void drawCellBg(Canvas canvas, int index, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.cellBgPaint.setColor(color);
        float left = surface.cellWidth * (x - 1) + surface.borderWidth;
        float top = surface.weekHeight + (y - 1)
                * surface.cellHeight + surface.borderWidth;
        canvas.drawRect(left, top, left + surface.cellWidth
                - surface.borderWidth, top + surface.cellHeight
                - surface.borderWidth, surface.cellBgPaint);
    }

    private int getXByIndex(int i) {
        return i % 7 + 1; // 1 2 3 4 5 6 7
    }

    private int getYByIndex(int i) {
        return i / 7 + 1; // 1 2 3 4 5 6
    }


    private int downDataIndex;
    private boolean dayBoolean = false;

    private void setSelectedDateByCoor(float x, float y) {

        if (y > surface.weekHeight) {
            dayBoolean = true;
            int m = (int) (Math.floor(x / surface.cellWidth) + 1);
            int n = (int) (Math
                    .floor((y - (surface.weekHeight))
                            / Float.valueOf(surface.cellHeight)) + 1);
            downDataIndex = (n - 1) * 7 + m - 1;
            if (monthBean.getDay().get(downDataIndex).isDimBright()) {
                downBgIndex = downDataIndex;
            }

        }
        else {
            dayBoolean = false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSelectedDateByCoor(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                if (onItemClickListener != null && dayBoolean) {

                    if (monthBean.getDay().get(downDataIndex).isDimBright()) {
                        //响应监听事件
                        onItemClickListener.OnItemClick(monthBean.getDay().get(downDataIndex).getSolarCalendar());
                    }

                    invalidate();
                }
                break;
        }
        return true;
    }

    private OnItemClickListener onItemClickListener;

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //监听接口
    public interface OnItemClickListener {
        void OnItemClick(String date);
    }

    /**
     * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
     */
    private class Surface {
        public float density;
        public int width; // 整个控件的宽度
        public int height; // 整个控件的高度
        private float weekHeight; // 显示星期的高度
        private float cellWidth; // 日期方框宽度
        private float cellHeight; // 日期方框高度
        private float borderWidth;
        private int bgColor = Color.parseColor("#FFFFFF");
        private int textColor = Color.BLACK;
        private int todayTextColor = Color.RED;
        private int noSelectTextColor = Color.parseColor("#CCCCCC");
        private int cellSelectedColor = Color.parseColor("#99CCFF");
        private int workdayColor = Color.parseColor("#00CD66");
        private int weekendColor = Color.RED;
        private Paint borderPaint;
        private Paint weekPaint;
        private Paint datePaint;
        private Paint cellBgPaint;
        private Path boxPath; // 边框路径
        private String[] weekText = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        public void init() {
            float temp = height / 7f;
            weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
            cellHeight = (height - weekHeight) / 6f;
            cellWidth = width / 7f;

            borderPaint = new Paint();
            borderPaint.setColor(noSelectTextColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderWidth = (float) (0.5 * density);
            borderWidth = borderWidth < 1 ? 1 : borderWidth;
            borderPaint.setStrokeWidth(borderWidth);

            weekPaint = new Paint();
            weekPaint.setColor(textColor);
            weekPaint.setAntiAlias(true);
            float weekTextSize = weekHeight * 0.3f;
            weekPaint.setTextSize(weekTextSize);
            weekPaint.setStyle(Paint.Style.FILL);

            datePaint = new Paint();
            datePaint.setColor(textColor);
            datePaint.setAntiAlias(true);

            float cellTextSize = cellHeight * 0.5f;
            datePaint.setTextSize(cellTextSize);
            datePaint.setStyle(Paint.Style.FILL);
            boxPath = new Path();
            boxPath.rLineTo(width, 0);
            boxPath.moveTo(0, weekHeight);
            boxPath.rLineTo(width, 0);
            for (int i = 1; i < 6; i++) {
                boxPath.moveTo(0, weekHeight + i * cellHeight);
                boxPath.rLineTo(width, 0);
                boxPath.moveTo(i * cellWidth, 0);
                boxPath.rLineTo(0, height);
            }
            boxPath.moveTo(6 * cellWidth, 0);
            boxPath.rLineTo(0, height);
            cellBgPaint = new Paint();
            cellBgPaint.setAntiAlias(true);
            cellBgPaint.setStyle(Paint.Style.FILL);
            cellBgPaint.setColor(cellSelectedColor);
        }
    }

    private DataMonthBean monthBean;
    private SpecialCalendar specialCalendar;
    private int today;

    private void init(int year, int month, int today) {
        this.today = today;
        surface = new Surface();
        surface.density = getResources().getDisplayMetrics().density;
        setBackgroundColor(surface.bgColor);
        specialCalendar = new SpecialCalendar();
        monthBean = getCalenderBean(year, month, today);
    }


    public int getWeek(int y, int m, int d) {
        if (m < 3) {
            m += 12;
            --y;
        }
        int w = (d + 1 + 2 * m + 3 * (m + 1) / 5 + y + (y >> 2) - y / 100 + y / 400) % 7;
        return w;
    }

    private DataMonthBean getCalenderBean(int year, int month, int today) {


        int lastMonth = month - 1;

        int firstWeak = weakSize = getWeek(year, month, 1);

        weakSize = downBgIndex = downDataIndex = today + firstWeak - 1;

        int showMonthDay = specialCalendar.getDaysOfMonth(
                specialCalendar.isLeapYear(year)
                , month
        );
        if (specialCalendar.isLeapYear(year) && month == 2) {
            showMonthDay = specialCalendar.getDaysOfMonth(
                    specialCalendar.isLeapYear(year)
                    , month
            );
        }

        int lastMonthDay = specialCalendar.getDaysOfMonth(
                specialCalendar.isLeapYear(year)
                , lastMonth
        ) + 1 - firstWeak;
        if (specialCalendar.isLeapYear(year) && (month - 1) == 2) {
            lastMonthDay = specialCalendar.getDaysOfMonth(
                    specialCalendar.isLeapYear(year)
                    , lastMonth
            ) + 1 - firstWeak;
        }


        DataMonthBean monthBean = new DataMonthBean();
        List<DataMonthBean.DayBean> beanList = new ArrayList<>();
        int next = 1;
        for (int i = 0; i < 42; i++) {

            if (firstWeak == 0) {
                //下个月
                if ((i - firstWeak) >= showMonthDay) {
                    DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                    dayBean.setLunarCalendar("");
//                            lunarCalendar.getLunarDate(lastMonthYear, lastMonth, next, false));
                    dayBean.setSolarCalendar("" + next);
                    dayBean.setDimBright(false);
                    beanList.add(dayBean);
                    next++;
                }
                //当前月
                else {
                    DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                    dayBean.setLunarCalendar("");
//                            lunarCalendar.getLunarDate(year, month, i + 1, false));
                    dayBean.setSolarCalendar("" + (i + 1));
                    dayBean.setDimBright(true);
                    beanList.add(dayBean);
                }
            }
            else {
                //上个月
                if (i < firstWeak) {
                    DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                    dayBean.setLunarCalendar("");
//                            lunarCalendar.getLunarDate(lastMonthYear, lastMonth, lastMonthDay, false));
                    dayBean.setSolarCalendar("" + lastMonthDay);
                    dayBean.setDimBright(false);
                    beanList.add(dayBean);
                    lastMonthDay++;
                }
                else {
                    //下个月
                    if ((i - firstWeak) >= showMonthDay) {
                        DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                        dayBean.setLunarCalendar("");
//                                lunarCalendar.getLunarDate(nextMonthYear, nextMonth, next, false));
                        dayBean.setSolarCalendar("" + next);
                        dayBean.setDimBright(false);
                        beanList.add(dayBean);
                        next++;
                    }
                    //当前月
                    else {
                        DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                        dayBean.setLunarCalendar("");
//                                lunarCalendar.getLunarDate(year, month,
//                                        (i + 1 - firstWeak), false));
                        dayBean.setSolarCalendar("" + (i + 1 - firstWeak));
                        dayBean.setDimBright(true);
                        beanList.add(dayBean);
                    }
                }
            }

        }
        monthBean.setDay(beanList);
        return monthBean;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        surface.width = MeasureSpec.getSize(widthMeasureSpec);
        surface.height = MeasureSpec.getSize(heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(surface.width,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(surface.height,
                MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        if (changed) {
            surface.init();
        }
        super.onLayout(changed, left, top, right, bottom);
    }
}
