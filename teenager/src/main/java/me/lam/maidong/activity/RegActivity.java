package me.lam.maidong.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;

import me.lam.maidong.entity.RegEntity;
import me.lam.maidong.entity.isResged;
import me.lam.maidong.utils.Constant;
import me.lam.maidong.utils.HttpUtils;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;


@ContentView(R.layout.activity_reg)
public class RegActivity extends BaseActivity  {
    @ViewInject(R.id.bt_next)
    Button bt_next;
    int num = 0;
    @ViewInject(R.id.et_pwd)
    EditText etPwd;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_confirm)
    EditText       et_confirm;
    @ViewInject(R.id.bt_log)
    Button btLog;
    @ViewInject(R.id.user_openid)
    TextView openidTextView;
    @ViewInject(R.id.user_nickname)
    TextView nicknameTextView;
    @ViewInject(R.id.bt_comfire)
    Button  bt_comfire;
    RegEntity regEntity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }
    private void initialize() {
        x.view().inject(this);
        regEntity =RegEntity.getInstance();
        bitmapUtils = new BitmapUtils(getApplicationContext());
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("tag", "beforeTextChanged" + ".........." + num);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_phone.getText().length() == 11) {
                    if (isMobile(et_phone.getText().toString())) {
                        //  Toast.makeText(RegActivity.this,"手机格式不正确",Toast.LENGTH_LONG).show();
                        bt_comfire.setTextColor(Color.BLUE);
                        bt_comfire.setClickable(true);
                    } else {
                        Toast.makeText(RegActivity.this, "手机格式不正确", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    BitmapUtils bitmapUtils;

    private void CheckUser(final String phone,final String pwd) {
        String url = "CheckUser/?userID=" + phone;
        OKHttp.sendRequestRequestParams(RegActivity.this, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Log.i("getAsynHttp", response.toString());
                Gson g = new Gson();
                isResged logEntity = g.fromJson(response, isResged.class);
                if (logEntity.getMyStatus() == 1) {
                   // ShareUitls.putString(RegActivity.this, "user", response);
                    regEntity.setPhone(phone);
                    regEntity.setPwd(pwd);
                    Intent i = new Intent(RegActivity.this, SexActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(RegActivity.this, "手机号重复", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse() {

                Toast.makeText(RegActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();


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
                    ShareUitls.putString(RegActivity.this, "user", msg.obj.toString());
                    Intent i = new Intent(RegActivity.this, SexActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(RegActivity.this, "手机号重复", Toast.LENGTH_SHORT).show();
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
        if (et_phone.length() == 0) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
        } else {
            if (et_phone.length() == 11) {
                if (this.isMobile(et_phone.getText().toString())) {

                    if (etPwd.length() >= 6) {
                        if (et_confirm.getText().toString().length() > 0) {
                            if (!Message.equals("fail")) {
                                if (et_confirm.getText().toString().equals(Message)) {
                                    CheckUser(et_phone.getText().toString(),etPwd.getText().toString());
                                } else {
                                    Toast.makeText(this, "验证码验证错误", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(this, "验证码获取失败，请重新获取", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "密码至少为6位", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(this, "手机号错误", Toast.LENGTH_LONG).show();
                }

            } else {

                Toast.makeText(this, "手机号必须为11位", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(RegActivity.this, "手机格式不正确", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    String Message = "fail";
    private void getSMS(String phone) {
        String url ="SMS/?Mobile=" + phone;
        OKHttp.sendRequestRequestParams(RegActivity.this, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Log.i("getSMS", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equals("1")) {
                    Message = jsonObject.getString("Message");
                    } else if (jsonObject.getString("Status").equals("0")) {
                        Toast.makeText(RegActivity.this, "获取获取码失败", Toast.LENGTH_SHORT).show();
                        Message = "fail";
                    }
                    // ShareUitls.putString(getApplicationContext(), "SMSMessage", Message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse() {
                Message = "fail";

                Toast.makeText(RegActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();



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

            Log.e("log2", 3 + "");

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
                Toast.makeText(RegActivity.this, "手机格式不正确", Toast.LENGTH_LONG).show();
            }*/

            Log.e("log2", 2 + "");
            bt_comfire.setClickable(false);
            bt_comfire.setTextColor(Color.GRAY);
            bt_comfire.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}






