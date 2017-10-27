package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.lam.maidong.R;
import me.lam.maidong.entity.NewLogCallBack;
import me.lam.maidong.entity.PublicDataClass;
import me.lam.maidong.entity.VersionClass;
import me.lam.maidong.utils.Constant;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;
import me.lam.maidong.utils.UpadteApp;
import me.lam.maidong.utils.VersonUtils;
import me.lam.maidong.welcomepage.MyFragmentPagerAdapter;
import me.lam.maidong.welcomepage.fragment3;
import me.lam.maidong.welcomepage.fragment4;
import me.lam.maidong.welcomepage.fragment5;


public class HomeActivity extends OriginalActivity {
    ViewPager mPager;
    ArrayList<Fragment> fragmentsList;
    fragment3 frag1;
    fragment4 frag2;
    fragment5 frag3;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String phone = ShareUitls.getString(HomeActivity.this, "phone", "null");
                String pwd = ShareUitls.getString(HomeActivity.this, "pwd", "null");
                if (!phone.equals("null") && !pwd.equals("null")) {
                    Login(HomeActivity.this, phone, pwd, "HomeActivity");
                } else {
                    startActivity(new Intent(HomeActivity.this, LogActivity.class));
                    finish();
                }
            }
        }
    };
 /*   static Activity TokenActivity;
    static String Phone;
    static Handler token = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (TokenActivity != null && Phone != null) {
              //  updateToken();
            }

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mPager = (ViewPager) this.findViewById(R.id.vPager);
       // ShareUitls.putString(HomeActivity.this, "count", "null");
       // checkVersion(HomeActivity.this);
        // initialize();
        new Timer().schedule(timerTask, 2000);
    }

    private void initialize() {
        boolean flag = !ShareUitls.getString(HomeActivity.this, "first", "yes").equals("yes");
        if (flag) {
            new Timer().schedule(timerTask, 2000);
        } else {
            ShareUitls.putString(HomeActivity.this, "first", "no");
            mPager.setVisibility(View.VISIBLE);
            fragmentsList = new ArrayList<Fragment>();
            frag1 = new fragment3();
            frag2 = new fragment4();
            frag3 = new fragment5();
            fragmentsList.add(frag1);
            fragmentsList.add(frag2);
            fragmentsList.add(frag3);
            mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
            mPager.setOffscreenPageLimit(2);
            mPager.setCurrentItem(0);
        }
    }

    public static void Login(final Activity activity, final String phone, final String pwd, final String ACTIVITY) {
        String url = "user/?userID=" + phone + "&Pwd=" + pwd;
        String flag = "";
        if (!ACTIVITY.equals("LogActivity")) {
            flag = "Login";
        }
        OKHttp.sendRequestRequestParams(activity, flag, true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Log.i("getAsynHttp", response.toString());
                NewLogCallBack logEntity = new Gson().fromJson(response, NewLogCallBack.class);

                Log.i("Login", logEntity.toString() + "");
                if (logEntity.MyStatus.equals("1")) {
                    ShareUitls.putString(activity, "maidongflag", "1");//刷新首页数据
                    ShareUitls.putString(activity, "recent", logEntity.LastSportDay);//2017-08-10 logEntity.LastSportDay
                    ShareUitls.putString(activity, "LastSportDay",logEntity.LastSportDay);
                    ShareUitls.putString(activity, "EducationCode", logEntity.EducationCode);

                    // Log.i("getAsynHttp",logEntity.EducationCode+"  "+logEntity.LastSportDay+"  "+ response.toString());
                    ShareUitls.putString(activity, "phone", phone);
                    ShareUitls.putString(activity, "pwd", pwd);
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    updateToken(activity, phone);
                } else {
                    if (!ACTIVITY.equals("LogActivity")) {
                        activity.startActivity(new Intent(activity, LogActivity.class));
                        activity.finish();
                    }
                    Toast.makeText(activity, logEntity.StatusMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse() {
                if (!ACTIVITY.equals("LogActivity")) {
                    activity.startActivity(new Intent(activity, LogActivity.class));
                    activity.finish();
                }
            }
        });
    }

    public static void updateToken(final Activity TokenActivity, String Phone) {
        final String DeviceToken = ShareUitls.getString(TokenActivity, "token", "");
        Log.i("updateToken", "ff   " + DeviceToken + "   " + Phone);
        if (DeviceToken.length() != 0) {
            Log.i("updateToken", "OK");
            String url = "User?Mobile=" + Phone + "&DeviceToken=" + DeviceToken + "&DeviceType=1";
            //  String url=" http://192.168.1.250:8085/api/User?Mobile=18611347385&DeviceToken=AhqeuMoLPFLLXCE8T4QMHNvXsnxRTFIIpIh-8ZoyLEAS&DeviceType=1";
            OKHttp.sendRequestRequestParams(TokenActivity, "TokenActivity", false, url, new OKHttp.ResponseListener() {
                @Override
                public void onResponse(String response) {
                    Log.i("updateToken", response);
                   /* try {
                        JSONObject jsonObject=new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    if(TokenActivity!=null) {
                        TokenActivity.finish();
                    }

                }

                @Override
                public void onErrorResponse() {

                    if(TokenActivity!=null) {
                        TokenActivity.finish();
                    }
                }
            });
        }
        //
    }

    Handler checkVersionhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                VersionClass versionClass = new Gson().fromJson(checkVersion, VersionClass.class);
                ShareUitls.putVersion(HomeActivity.this, versionClass);
                initialize();
              /*  if (versionClass.VersionCode > VersonUtils.getVerisonCode(HomeActivity.this)) {
                    UpadteApp upadteApp = new UpadteApp(HomeActivity.this, versionClass, false, new UpadteApp.UpdateResult() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            initialize();
                        }
                    });
                } else {
                    initialize();
                }*/
            } else {
                initialize();
            }
        }
    };
    String checkVersion;

    public void checkVersion(final Activity activity) {
        String url = "Version";
        OKHttp.sendRequestRequestParamsNew(activity, "", false, url, new OKHttp.ResponseListenerNew() {
            @Override
            public void onResponse(String response, PublicDataClass.StatusModel statusModel) {
                Log.i("myblue", response+"  -------  "+statusModel.toString());
                if (statusModel.StatusCode == 1) {

                    checkVersion = response;
                    if (checkVersion != null) {
                        checkVersionhandler.sendEmptyMessage(1);
                    }


                } else {

                    checkVersionhandler.sendEmptyMessage(2);
                }
            }

            @Override
            public void onErrorResponse() {

                checkVersionhandler.sendEmptyMessage(2);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
    {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
