package me.lam.maidong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.entity.msgCallBack;


@ContentView(R.layout.msg_detail)
public class MessageDetailActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;

    @ViewInject(R.id.msg_title)
    private TextView msgTitle;
    @ViewInject(R.id.tv_msg)
    private TextView tvMsg;
    public static msgCallBack news;
    public static int positon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initialize();

    }


    private void initialize() {
        view_publictitle_title.setText("消息详情");
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");
        msgTitle.setText(title);
        tvMsg.setText(content);
    }

    @Event(value = {R.id.view_publictitle_back})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                finish();
                break;
        }
    }


}
