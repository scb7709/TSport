package me.lam.maidong.ClenderUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CalendarView extends View {
    private static final int TOTAL_COL = 7;
    private static final int TOTAL_ROW = 6;
    private Paint mCirclePaint;
    private Paint mCirclePaint2;
    private Paint mTextPaint;
    private int mViewWidth;
    private int mViewHight;
    private int mCellSpace;
    private Row rows[] = new Row[TOTAL_ROW];
    private static CustomDate mShowDate;//自定义的日期  包括year month day
    private CallBack mCallBack;//回调
    private int touchSlop;
    private List<Integer> SSSportDay;
    private int SSSportDaySize;
    private Calendar calendar;
    public interface CallBack {

        void clickDate(CustomDate date);//回调点击的日期

        void initHandler();

        void changeDate(CustomDate date);//回调滑动viewPager改变的日期
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    private WaitDialog waitDialog;

    private void showDialog(boolean isShow) {
        if (isShow) {
            waitDialog.showDailog();
        } else {
            if (waitDialog != null) {
                waitDialog.dismissDialog();
            }
        }
    }

    private void initDialog() {
        waitDialog = new WaitDialog(getContext());
        waitDialog.setMessage("请稍候...");
        waitDialog.setCancleable(false);

    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public CalendarView(Context context, CallBack mCallBack) {
        super(context);
        this.mCallBack = mCallBack;
        init(context);
    }
    public CalendarView(Context context, Calendar calendar , List<Integer> SSSportDay, CallBack mCallBack) {
        super(context);
        this.mCallBack = mCallBack;
        this.SSSportDay = SSSportDay;
        SSSportDaySize = SSSportDay.size();
        this.calendar = calendar;
        init(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null)
                rows[i].drawCells(canvas);
        }
    }

    private void init(Context context) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


       /* mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);*/
        mCirclePaint = new Paint((int) 1);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.parseColor("#42c3f7"));
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        initDate();

    }

    private void initDate() {
        mShowDate = new CustomDate(calendar);
        fillDate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHight = h;
        mCellSpace = Math.min(mViewHight / TOTAL_ROW, mViewWidth / TOTAL_COL);
        mTextPaint.setTextSize(mCellSpace / 3);
    }

    private float mDownX;
    private float mDownY;

    /*
     *
     * 触摸事件为了确定点击的位置日期
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                Log.e("zuobiao", mDownX + "-----坐标-----" + mDownY);
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - mDownX;
                float disY = event.getY() - mDownY;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                    int col = (int) (mDownX / mCellSpace);

                    int row = (int) (mDownY / mCellSpace);
                    Log.e("zuobiao", col + "----具体到那一个------" + row);
                    measureClickCell(col, row);
                }
                break;
        }
        return true;
    }

    private void measureClickCell(int col, int row) {
        if (col >= TOTAL_COL || row >= TOTAL_ROW)
            return;
        if (rows[row] != null) {
            CustomDate date = rows[row].cells[col].date;
            date.week = col;
            mCallBack.clickDate(date);
            invalidate();
        }
    }

    // 组
    class Row {
        public int j;

        Row(int j) {
            this.j = j;
        }

        public Cell[] cells = new Cell[TOTAL_COL];

        public void drawCells(Canvas canvas) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null)
                    cells[i].drawSelf(canvas);
            }

        }
    }

    // 单元格
    class Cell {
        public CustomDate date;
        public State state;
        public int i;
        public int j;

        public Cell(CustomDate date, State state, int i, int j) {
            super();
            this.date = date;
            this.state = state;
            this.i = i;
            this.j = j;
        }


        // 绘制一个单元格 如果颜色需要自定义可以修改
        Boolean noData = true;

        public void drawSelf(Canvas canvas) {
            switch (state) {
                case CURRENT_MONTH_DAY:
                    //当前正常状态
                    //字体颜色
                    mTextPaint.setColor(Color.parseColor("#c7c7c7"));
                    String content = date.day + "";


                    //内环圆
                  /*  canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), (float) (mCellSpace / 3-10),
                            mCirclePaint);
                    Log.e("r", (float) (mCellSpace / 3) + "-----1");
                    Log.e("r", (float) (mCellSpace / 3 - 1) + "-----1");*/


                    //画内环圆字体
                    canvas.drawText(content,
                            (float) ((i + 0.5) * mCellSpace - mTextPaint.measureText(content) / 2),
                            (float) ((j + 0.7) * mCellSpace - mTextPaint.measureText(
                                    content, 0, 1) / 2), mTextPaint);


                    //画外环圆
                    mCirclePaint2 = new Paint((int) 2);
                    mCirclePaint2.setStyle(Paint.Style.STROKE);
                    mCirclePaint2.setAntiAlias(true);
                    mCirclePaint2.setColor(Color.parseColor("#c7c7c7"));
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint2);
                    Log.e("r", (float) (mCellSpace / 3) + "-----2");
                    Log.e("r", (float) (mCellSpace / 3 - 1) + "-----2");
                    break;
                case NEXT_MONTH_DAY:
                case PAST_MONTH_DAY:
                    //隐藏数据日期颜色
                    mTextPaint.setColor(Color.parseColor("#ffff00"));
                    break;
                case SPORT_DAY:

                    mTextPaint.setColor(Color.parseColor("#00ffff"));
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint);
                    String content2 = date.day + "";
                    canvas.drawText(content2,
                            (float) ((i + 0.5) * mCellSpace - mTextPaint.measureText(content2) / 2),
                            (float) ((j + 0.7) * mCellSpace - mTextPaint.measureText(
                                    content2, 0, 1) / 2), mTextPaint);
                    break;
            }
         /* // 绘制文字
            String content = date.day + "";
            canvas.drawText(content,
                    (float) ((i + 0.5) * mCellSpace - mTextPaint.measureText(content) / 2),
                    (float) ((j + 0.7) * mCellSpace - mTextPaint.measureText(
                            content, 0, 1) / 2), mTextPaint);*/
        }
    }

    /**
     * @author huang
     *         cell的state
     *         当前月日期，过去的月的日期，下个月的日期，今天，点击的日期
     */
    enum State {
        CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, SPORT_DAY;
    }

    /**
     * 填充日期的数据
     */
    private void fillDate() {
        fillMonthDate();
        mCallBack.changeDate(mShowDate);
    }

    /**
     * 填充月份模式下数据 通过getWeekDayFromDate得到一个月第一天是星期几就可以算出所有的日期的位置 然后依次填充
     * 这里最好重构一下
     */
 /*   public static Back back;
    public  void setOnShow(Back onShow){
        this.back = onShow;
    }

    public interface Back{
        void Back(int first);
    }*/

  /*  int first=1;*/
    public void fillMonthDate() {
        Log.e("DDD", "fillMonthDate1");
         /*   if(back!=null){
                back.Back( first);
            }*/
        int monthDay = DateUtil.getCurrentMonthDay();
        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month - 1);
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month);
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year, mShowDate.month);

        //   Log.e("llll", "fillMonthDate" + copy.getInstance().SSSportDay.toString() + "----" + currentMonthDays);
        boolean isCurrentMonth = false;
        if (DateUtil.isCurrentMonth(mShowDate)) {
            isCurrentMonth = true;
        }
        int day = 0;

        for (int j = 0; j < TOTAL_ROW; j++) {
            rows[j] = new Row(j);
            for (int i = 0; i < TOTAL_COL; i++) {
                int postion = i + j * TOTAL_COL;
                if (postion >= firstDayWeek && postion < firstDayWeek + currentMonthDays) {
                    day++;
                    if (day > SSSportDaySize) {
                        return;
                    }
                    CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);

                    int temp=0;
                    try {
                        temp =SSSportDay .get(day - 1);
                    }catch (Exception E){


                    }
                    if (temp!=0){

                        date.week = i;
                        rows[j].cells[i] = new Cell(date, State.SPORT_DAY, i, j);

                        continue;
                    }


                    rows[j].cells[i] = new Cell(date, State.CURRENT_MONTH_DAY, i, j);

                } else if (postion < firstDayWeek) {
                    rows[j].cells[i] = new Cell(new CustomDate(mShowDate.year, mShowDate.month - 1, lastMonthDays - (firstDayWeek - postion - 1)), State.PAST_MONTH_DAY, i, j);
                } else if (postion >= firstDayWeek + currentMonthDays) {
                    rows[j].cells[i] = new Cell((new CustomDate(mShowDate.year, mShowDate.month + 1, postion - firstDayWeek - currentMonthDays + 1)), State.NEXT_MONTH_DAY, i, j);

                }
            }
        }
    }

    private String getNum(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }

    public void update() {
        fillDate();
        invalidate();
    }

    public void backToday() {
        initDate();
        invalidate();
    }

   /* if ( new Dir().getDir()) {
        Log.e("ooo","左减");
                       *//* copy.getInstance().SSSportDay=copy1.getInstance().SSSportDay;*//*
        if (copy.getInstance().SSSportDay.get(day - 1) != 0) {

            date.week = i;
            rows[j].cells[i] = new Cell(date, State.SPORT_DAY, i, j);
                     *//*   try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*//*
            continue;
        }
    }
    if(!new Dir().getDir()){
        Log.e("ooo","右加");
                    *//*    copy2.getInstance().SSSportDay=copy1.getInstance().SSSportDay;*//*
        if (copy2.getInstance().SSSportDay.get(day - 1) != 0) {

            date.week = i;
            rows[j].cells[i] = new Cell(date, State.SPORT_DAY, i, j);

            continue;
        }
    }*/

    //向右滑动


    private Map<String, Object> mapSport;

  /*  public void updateSportDate(Map<String, Object> mapSport) {
        this.mapSport = mapSport;
        fillMonthDate();
        invalidate();
    }*/
}
