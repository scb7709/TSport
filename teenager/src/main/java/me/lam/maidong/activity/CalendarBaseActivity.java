package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;


import me.lam.maidong.ClenderUtil.ACache;
import me.lam.maidong.ClenderUtil.TViewOnClickListener;
import me.lam.maidong.ClenderUtil.preferences;
import me.lam.maidong.R;



public abstract class CalendarBaseActivity extends BaseActivity implements TViewOnClickListener {
	/** 登录文件存储的SharedPreferences */
	public SharedPreferences mPreferencesLogin;
	/** 登录文件存储的Editor */
	public SharedPreferences.Editor spEditorLogin;
	/** 缓存文件框架 */
	public ACache aCache;
	/** 打开当前页面的意图 */
	public Intent mIntent;
	public LinearLayout ll_main;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mPreferencesLogin = getSharedPreferences(preferences.PREFS_NAME, MODE_PRIVATE);
		this.spEditorLogin = this.mPreferencesLogin.edit();
	/*	this.aCache = ACache.get(this);*/
		this.mIntent = getIntent();

		setContentView(R.layout.activity_main2);
		ll_main = (LinearLayout) findViewById(R.id.ll_main);
		//每个activity都会集成普通头部
	/*	tViewHeader = new TViewNormalHeader(this);*/
	}

	public void onPause() {
		super.onPause();
	//	MobclickAgent.onPause(this);
	}

	public void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);
	}

	public void onStop() {
		super.onStop();
	}
//复写返回键的点击事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() != KeyEvent.ACTION_UP) {
			back();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * 退出当前页面，从左向右滑动
	 */
	public void back() {
		finish();
	}
}
