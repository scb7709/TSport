package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.fragment.MaidongFragment;

import me.lam.maidong.entity.msgCallBack;
import me.lam.maidong.fragment.AnalizeFragment;
import me.lam.maidong.fragment.NewsActivityFragment2;
import me.lam.maidong.fragment.SelfActivityFragment;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;
import me.lam.maidong.utils.UpadteApp;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @ViewInject(R.id.activity_main_tabs)
    private RadioGroup activity_main_tabs;
    @ViewInject(R.id.activity_main_maindong)
    private RadioButton activity_main_maindong;
    @ViewInject(R.id.activity_main_bt_data)
    RelativeLayout btData;
    @ViewInject(R.id.activity_main_msg)
    RelativeLayout xiaoxi;

    @ViewInject(R.id.activity_main_title_left)
    public TextView activity_main_title_left;
    @ViewInject(R.id.activity_main_title_center)
    public TextView activity_main_title_center;
    @ViewInject(R.id.activity_main_title_right)
    public TextView activity_main_title_right;


    @ViewInject(R.id.activity_main_title)
    RelativeLayout activity_main_title;
 /*   MaiDongActivityFragment maiDongActivityFragment;
    MessageActivityFragment messageActivityFragment;
   NewsActivityFragment roundActivityFragment;
    DongtaiFragment roundActivityFragment;*/


    AnalizeFragment analizeFragment;
    SelfActivityFragment selfActivityFragment;
    public static FragmentActivity activity;
    Gson gson = new Gson();
    public   FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        activity = this;
        UpadteApp.updateAPP(activity, true);
        initView();

     /*   maiDongActivityFragment = new MaiDongActivityFragment();
        changeFragment(maiDongActivityFragment, "MaiDongActivityFragment");*/
        MaidongFragment maidongFragment = new MaidongFragment();
        changeFragment(maidongFragment, "MaidongFragment");
        activity_main_title_center.setText("迈动");

    }

    public String EduCode;
    public String LastSportDate;


    public  void changeFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment, tag);
        transaction.commit();
    }

    private void initView() {


        EduCode = ShareUitls.getString(MainActivity.this, "EducationCode", "");
        LastSportDate = ShareUitls.getString(MainActivity.this, "LastSportDate", "");
        Log.e("log", EduCode + "主界面的到登陆的id？");
        activity_main_tabs.check(activity_main_maindong.getId());
        activity_main_tabs.setOnCheckedChangeListener(this);
        fragmentManager = getSupportFragmentManager();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_main_maindong:
                xiaoxi.setVisibility(View.VISIBLE);
                btData.setVisibility(View.VISIBLE);
                activity_main_title_left.setVisibility(View.VISIBLE);
                activity_main_title_right.setVisibility(View.VISIBLE);
            /*    maiDongActivityFragment = new MaiDongActivityFragment();
                changeFragment( maiDongActivityFragment, "MaiDongActivityFragment");*/

                MaidongFragment maidongFragment = new MaidongFragment();
                changeFragment(maidongFragment, "MaidongFragment");
                break;
            case R.id.activity_main_analyze:
                xiaoxi.setVisibility(View.GONE);
                btData.setVisibility(View.GONE);
                activity_main_title_left.setVisibility(View.VISIBLE);
                activity_main_title_right.setVisibility(View.VISIBLE);

                analizeFragment = new AnalizeFragment();
                changeFragment(analizeFragment, "AnalizeFragment");
                break;
            case R.id.activity_main_maidongcircle:
                xiaoxi.setVisibility(View.GONE);
                btData.setVisibility(View.GONE);


                activity_main_title_left.setVisibility(View.GONE);
                activity_main_title_right.setVisibility(View.GONE);
                activity_main_title_center.setText("资讯");

               /* roundActivityFragment = new DongtaiFragment();
                changeFragment( roundActivityFragment, "DongtaiFragment");*/


                NewsActivityFragment2 newsActivityFragment2 = new NewsActivityFragment2();
                changeFragment(newsActivityFragment2, "NewsActivityFragment2");
                break;
            case R.id.activity_main_my:
                xiaoxi.setVisibility(View.GONE);
                btData.setVisibility(View.GONE);

                activity_main_title_left.setVisibility(View.GONE);
                activity_main_title_right.setVisibility(View.GONE);
                activity_main_title_center.setText("我");

                selfActivityFragment = new SelfActivityFragment();
                changeFragment(selfActivityFragment, "SelfActivityFragment");
                break;
        }
    }


    @Event(value = {R.id.activity_main_msg, R.id.activity_main_bt_data})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.activity_main_msg:
                getMessage();
                break;
            case R.id.activity_main_bt_data:
                startActivity(new Intent(activity, CalendarActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        // View bt = (View) v;


    }

    public void getMessage() {
        String url = "Message";
        OKHttp.sendRequestRequestParams(MainActivity.this, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                msgCallBack person = gson.fromJson(response, msgCallBack.class);
                if (person != null) {
                    if (person.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, MssageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("msgg", person);
                        intent.putExtras(bundle);
                        MainActivity.this.startActivity(intent);
                        return;
                    } else {
                        Log.e("res", "获取新闻失败" + "正在请求");
                    }

                }
            }

            @Override
            public void onErrorResponse() {

                Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    long temptime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
    {
        // TODO Auto-generated method stub

        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            if (System.currentTimeMillis() - temptime > 2000) // 2s内再次选择back键有效
            {
                System.out.println(Toast.LENGTH_LONG);
                Toast.makeText(this, "请再按一次返回退出", Toast.LENGTH_SHORT).show();
                temptime = System.currentTimeMillis();
            } else {
                finish();
                //凡是非零都表示异常退出!0表示正常退出!
            }

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

}
