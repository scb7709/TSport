package me.lam.maidong.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.entity.callbackRegentity;

import me.lam.maidong.entity.RegEntity;

import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;

@ContentView(R.layout.activity_band_id)
public class BandIdActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;

    @ViewInject(R.id.bt_next)
    Button btNext;


    @ViewInject(R.id.et_pwd)
    EditText etPwd;


    @ViewInject(R.id.why)
    Button why;
    RegEntity regEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initialize();

    }


    private void initialize() {
        view_publictitle_title.setText("绑定教育ID");
        regEntity = RegEntity.getInstance();
        regEntity.RegActivityList.add(this);
    }

    @Event(value = {R.id.view_publictitle_back, R.id.bt_next, R.id.why})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                finish();
                break;
            case R.id.bt_next:
                if (etPwd.getText().toString().length() != 0) {

                    regEntity.setUserId(etPwd.getText().toString());
                    reg();

                } else {

                    Toast.makeText(this, "请输入教育Id", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.why:
                new AlertDialog.Builder(BandIdActivity.this).setTitle("提示")//设置对话框标题
                        .setMessage("学生佩戴迈动腕表后，家长需绑定教育ID方可查看孩子的运动健康数据！")//设置显示的内容

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override

                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件


                            }

                        }).show();
                break;
        }
    }

    private void reg() {
        String url = "User/?UserID=" + regEntity.getPhone() + "&Pwd=" + regEntity.getPwd() + "&Sex=" + regEntity.getSex() + "&Age=" + regEntity.getAge()
                + "&Height=" + regEntity.getHeight() + "&Weight=" + regEntity.getWeight() + "&EducationCode=" + regEntity.getUserId();
        OKHttp.sendRequestRequestParams(BandIdActivity.this, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Message message = Message.obtain();
                    message.obj = response;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onErrorResponse() {
                Toast.makeText(BandIdActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson g = new Gson();
            callbackRegentity logEntity = g.fromJson((String) msg.obj, callbackRegentity.class);
            if (logEntity.getMyStatus().equals("1")) {
                Toast.makeText(BandIdActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                if (regEntity.RegActivityList != null && regEntity.RegActivityList.size() != 0) {
                    for (Activity activity : regEntity.RegActivityList) {
                        if (activity != null) {
                            activity.finish();
                        }

                    }
                }

            } else {

                Toast.makeText(BandIdActivity.this, "注册失败，教育ID不存在", Toast.LENGTH_SHORT).show();


            }

        }
    };
}
