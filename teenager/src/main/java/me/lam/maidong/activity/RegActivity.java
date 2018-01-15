package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import me.lam.maidong.R;
import me.lam.maidong.entity.RegEntity;
import me.lam.maidong.entity.isResged;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.OKHttp;


@ContentView(R.layout.activity_reg)
public class RegActivity extends BaseActivity {
    @ViewInject(R.id.bt_next)
    Button bt_next;
    int num = 0;
    @ViewInject(R.id.et_pwd)
    EditText etPwd;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_confirm)
    EditText et_confirm;
    @ViewInject(R.id.bt_log)
    Button btLog;
    @ViewInject(R.id.user_openid)
    TextView openidTextView;
    @ViewInject(R.id.user_nickname)
    TextView nicknameTextView;
    @ViewInject(R.id.bt_comfire)
    Button bt_comfire;
    RegEntity regEntity;

    Activity activity;
    Map<String, String> phoneAndverify_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        initialize();
    }

    private void initialize() {
        x.view().inject(this);
        phoneAndverify_code = new HashMap<>();
        regEntity = RegEntity.getInstance();
        bitmapUtils = new BitmapUtils(getApplicationContext());
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Log.e("tag", "beforeTextChanged" + ".........." + num);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_phone.getText().length() == 11) {
                    if (isMobile(et_phone.getText().toString())) {
                        bt_comfire.setTextColor(Color.BLUE);
                        bt_comfire.setClickable(true);
                    } else {
                        MyToash.Toash(activity, "手机格式不正确");

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    BitmapUtils bitmapUtils;

    private void CheckUser(final String phone, final String pwd) {
        String url = "CheckUser/?userID=" + phone;
        OKHttp.sendRequestRequestParams(activity, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Gson g = new Gson();
                isResged logEntity = g.fromJson(response, isResged.class);
                if (logEntity.getMyStatus() == 1) {
                    regEntity.setPhone(phone);
                    regEntity.setPwd(pwd);
                    Intent i = new Intent(activity, SexActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    MyToash.Toash(activity, "手机号重复");
                }
            }

            @Override
            public void onErrorResponse() {
                MyToash.ToashNoNet(activity);
            }
        });
    }

    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           /* if (msg.what == 1) {
                Gson g = new Gson();
                Log.e("json", msg.obj.toString() + "");
                isResged logEntity = g.fromJson(msg.obj.toString(), isResged.class);
                if (logEntity.getMyStatus() == 1) {
                    ShareUitls.putString(activity, "user", msg.obj.toString());
                    Intent i = new Intent(activity, SexActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(activity, "手机号重复", Toast.LENGTH_SHORT).show();
                }
            }*/

            if (msg.what == 2) {
                bt_comfire.setTextColor(Color.BLUE);
                bt_comfire.setText("重新获取");
                bt_comfire.setClickable(true);
            }
            // pb.dismiss();
        }

    };
    //判断手机号是否重复

    isResged isRes;

    private void checkPhone() {
        String et_phonee=et_phone.getText().toString();
        if (et_phonee.length() == 0) {
            MyToash.Toash(activity, "手机号不能为空");

        } else {
            if (et_phonee.length() == 11) {
                if (isMobile(et_phonee)) {
                    if (phoneAndverify_code.get(et_phonee)==null){
                        MyToash.Toash(activity, "该手机号还未获取验证码");
                        return;
                    }
                    if (etPwd.length() >= 6) {
                        if (et_confirm.getText().toString().length() > 0) {
                            if (myMessage.length() == 0) {
                                MyToash.Toash(activity, "请先点击获取验证码");
                            } else {
                                if (!myMessage.equals("fail")) {
                                    if (et_confirm.getText().toString().equals(myMessage)) {
                                        CheckUser(et_phone.getText().toString(), etPwd.getText().toString());
                                    } else {
                                        MyToash.Toash(activity, "验证码验证错误");
                                    }
                                } else {
                                    MyToash.Toash(activity, "验证码获取失败，请重新获取");
                                }
                            }
                        } else {
                            MyToash.Toash(activity, "请输入验证码");
                        }
                    } else {
                        MyToash.Toash(activity, "密码至少为6位");
                    }


                } else {
                    MyToash.Toash(activity, "请输入正确的手机号");
                }

            } else {
                MyToash.Toash(activity, "手机号必须为11位");
            }
        }
    }

    @Event(value = {R.id.bt_log, R.id.bt_next, R.id.bt_comfire})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.bt_log:
                finish();
                break;
            case R.id.bt_next:
                checkPhone();
                break;
            case R.id.bt_comfire:
                if (isMobile(et_phone.getText().toString())) {
                    getSMS(et_phone.getText().toString());
                    time.start();
                } else {
                    MyToash.Toash(activity, "手机格式不正确");
                }
                break;

        }
    }


    String myMessage = "";

    private void getSMS(final String phone) {
        String url = "SMS/?Mobile=" + phone;
        OKHttp.sendRequestRequestParams(activity, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                // Log.i("getSMS", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equals("1")) {
                        myMessage = jsonObject.getString("Message");
                        phoneAndverify_code.put(phone,myMessage);
                    } else if (jsonObject.getString("Status").equals("0")) {
                        MyToash.Toash(activity, "获取获取码失败");
                        myMessage = "fail";
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse() {
                myMessage = "fail";
                MyToash.Toash(activity, "获取获取码失败");
            }
        });


    }

    //判断手机号
    public static boolean isMobile(String mobiles) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    //验证计时
    TimeCount time = new TimeCount(60000, 1000);
    int count = 0;
    Boolean isPhone = true;


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发

            //Log.e("log2", 3 + "");

            if (et_phone.getText().length() == 11) {
                Message msg = new Message();
                msg.what = 2;
                h.sendMessage(msg);
            }
          /*  bt_comfire.setText("获取验证码");
            bt_comfire.setClickable(true);
            Log.e("log2", 4 + "");*/
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
          /*  if (!new RegActivity().isMobile(et_phone.getText().toString())) {
                Log.e("log2", "错" + "");
                Toast.makeText(activity, "手机格式不正确", Toast.LENGTH_LONG).show();
            }*/

            //   Log.e("log2", 2 + "");
            bt_comfire.setClickable(false);
            bt_comfire.setTextColor(Color.GRAY);
            bt_comfire.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}






