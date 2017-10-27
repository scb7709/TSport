package me.lam.maidong.ClenderUtil;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.lam.maidong.R;


public class TViewCalendar extends TViewBase {
	private ViewPager viewPager;
	private RelativeLayout back;
	private Button recent;
	private TextView tv_date;

	public TViewCalendar(Activity activity) {
		super(activity, true);


	}

	@Override
	public int viewLayoutRes() {
		return R.layout.activity_calendar;
	}

	@Override
	public void initView() {
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		back = (RelativeLayout) view.findViewById(R.id.bt_back);
		recent = (Button) view.findViewById(R.id.bt_recent);
		tv_date = (TextView) view.findViewById(R.id.tv_date);



	}

	@Override
	public void setViewListener() {
		back.setOnClickListener(this);
		recent.setOnClickListener(this);
		/*iv_next.setOnClickListener(this);*/
	}

	@Override
	public void initData() {
	}

	public ViewPager getViewPager(){
		return viewPager;
	}

	public void setDate(String date) {
		tv_date.setText(date);
	}
//日历按钮前后监听
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_recent:


				break;
		}
	}






}