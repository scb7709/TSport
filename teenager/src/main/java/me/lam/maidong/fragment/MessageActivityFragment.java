package me.lam.maidong.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import me.lam.maidong.R;
import me.lam.maidong.activity.MessageDetailActivity;
import me.lam.maidong.adapter.NewsAdapter;
import me.lam.maidong.entity.msgCallBack;

/**
 * A placeholder fragment containing a simple view.
 */
public class MessageActivityFragment extends Fragment {
    private TextView textView;
    private ListView listview;


    public static msgCallBack news;
    public MessageActivityFragment() {
    }
    @SuppressLint("ValidFragment")
    public MessageActivityFragment(msgCallBack news) {
        Log.e("ffff", news.getMessageList().size() + "传递过来的");
        this.news = news;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,null);
    }
    @Override
    public void onStart() {

        textView = (TextView) getActivity().findViewById(R.id.first);
        listview = (ListView) getActivity().findViewById(R.id.listview);
        listview.setAdapter(new NewsAdapter(getActivity(), news.getMessageList()));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                msgCallBack.MessageListEntity  messageListEntity=news.getMessageList().get(position);
                Intent intent = new Intent();
                intent.putExtra("content",messageListEntity.getMessageContent());
                intent.putExtra("title",messageListEntity.getMessageTitle());
                intent.setClass(getActivity(), MessageDetailActivity.class);
                startActivity(intent);
            }
        });
        super.onStart();
    }


}
