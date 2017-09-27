package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.adapter.NewsAdapter;
import me.lam.maidong.entity.msgCallBack;



@ContentView(R.layout.fragment_message)
public class MssageActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;


    @ViewInject(R.id.first)
    private TextView textView;
    @ViewInject(R.id.listview)
    private ListView listview;
    public static msgCallBack news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initialize();

    }




    private void initialize() {
        view_publictitle_title.setText("消息");

        Intent intent = this.getIntent();
        news = (msgCallBack) intent.getSerializableExtra("msgg");
        listview.setAdapter(new NewsAdapter(this, news.getMessageList()));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgCallBack.MessageListEntity  messageListEntity=news.getMessageList().get(position);
                Intent intent = new Intent();
                intent.putExtra("content",messageListEntity.getMessageContent());
                intent.putExtra("title",messageListEntity.getMessageTitle());
                intent.setClass(MssageActivity.this, MessageDetailActivity.class);
                startActivity(intent);
            }
        });
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
