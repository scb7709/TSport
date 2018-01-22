package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.lam.maidong.R;
import me.lam.maidong.entity.NewLogCallBack;
import me.lam.maidong.entity.PublicDataClass;
import me.lam.maidong.entity.VersionClass;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;



public class HomeActivity extends Activity {
    ViewPager mPager;
    ArrayList<View> GuidePageList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) this.findViewById(R.id.vPager);
        checkVersion(this);
       // ShareUitls.putString(HomeActivity.this, "count", "null");
       // checkVersion(HomeActivity.this);
        // initialize();
       // new Timer().schedule(timerTask, 2000);
    }

    private void initialize() {
        boolean flag = !ShareUitls.getString(HomeActivity.this, "first", "yes").equals("yes");
        if (flag) {
            new Timer().schedule(timerTask, 1000);
        } else {
            ShareUitls.putString(HomeActivity.this, "first", "no");
            mPager.setVisibility(View.VISIBLE);
            GuidePageList = new ArrayList<View>();
            LayoutInflater layoutInflater=LayoutInflater.from(HomeActivity.this);
            for(int i=0;i<3;i++){
                View view=layoutInflater.inflate(R.layout.layout_guide_page,null);
                RelativeLayout relativeLayout=(RelativeLayout)view.findViewById(R.id.layout_guide_page_layout);
                LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.layout_guide_page_go);
                switch (i){
                    case  0:
                        relativeLayout.setBackgroundResource(R.mipmap.guide_page_1);
                        break;
                    case 1:
                        relativeLayout.setBackgroundResource(R.mipmap.guide_page_3);
                        break;
                    case 2:
                        relativeLayout.setBackgroundResource(R.mipmap.guide_page_2);
                        linearLayout.setVisibility(View.VISIBLE);
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(HomeActivity.this,LogActivity.class));
                                finish();
                            }
                        });
                        break;
                }
                GuidePageList.add(view);
            }
            PagerAdapter pagerAdapter=new PagerAdapter() {
                // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
                @Override
                public int getCount() {
                    return 3;
                }

                // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
                @Override
                public boolean isViewFromObject(View arg0, Object arg1) {
                    return arg0 == arg1;
                }

                // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
                @Override
                public void destroyItem(ViewGroup view, int position, Object object) {
                    view.removeView(GuidePageList.get(position));
                }

                // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
                @Override
                public Object instantiateItem(ViewGroup view, int position) {
                    view.addView(GuidePageList.get(position));
                    return GuidePageList.get(position);
                }
            };
            mPager.setAdapter(pagerAdapter);
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
               MyToash.Log(response.toString());
                NewLogCallBack logEntity = new Gson().fromJson(response, NewLogCallBack.class);

              //  Log.i("Login", logEntity.toString() + "");
                if (logEntity.MyStatus.equals("1")) {
                    ShareUitls.putString(activity,"mydata","");// 我界面的缓存去掉
                    ShareUitls.putString(activity, "maidongflag", "1");//刷新首页数据
                    ShareUitls.putString(activity, "recent", logEntity.LastSportDay);//2017-08-10 logEntity.LastSportDay
                    ShareUitls.putString(activity, "LastSportDay",logEntity.LastSportDay);
                    ShareUitls.putString(activity, "CLICKDADE", logEntity.LastSportDay);
                    ShareUitls.putString(activity, "EducationCode", logEntity.EducationCode);
                  ShareUitls.putString(activity, "TOKEN", logEntity.Token);

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
       // Log.i("updateToken", "ff   " + DeviceToken + "   " + Phone);
        if (DeviceToken.length() != 0) {
          //  Log.i("updateToken", "OK");
            String url = "User?Mobile=" + Phone + "&DeviceToken=" + DeviceToken + "&DeviceType=1";
            OKHttp.sendRequestRequestParams(TokenActivity, "TokenActivity", false, url, new OKHttp.ResponseListener() {
                @Override
                public void onResponse(String response) {
                 //   Log.i("updateToken", response);
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
String str="{\"VersionCode\":\"11\",\"VersionName\":\"3.0.0\",\"Description\":\"1,aaa;2,bbb;3,cccc\",\"DownloadUrl\":\"https://www.ssp365.com/upload/app/teenager2.3.1.apk\"}";



    public void checkVersion(final Activity activity) {
        String url = "Version";
        OKHttp.sendRequestRequestParamsNew(activity, "", false, url, new OKHttp.ResponseListenerNew() {
            @Override
            public void onResponse(String response, PublicDataClass.StatusModel statusModel) {
                if (statusModel.StatusCode == 1) {
                    if (response != null) {
                        VersionClass versionClass = new Gson().fromJson(response, VersionClass.class);
                        ShareUitls.putVersion(HomeActivity.this, versionClass);
                    }
                }
                initialize();
            }

            @Override
            public void onErrorResponse() {
                initialize();

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
