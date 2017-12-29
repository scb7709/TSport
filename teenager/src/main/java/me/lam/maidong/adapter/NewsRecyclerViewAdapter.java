package me.lam.maidong.adapter;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.lam.maidong.R;
import me.lam.maidong.entity.msgCallBack;
import me.lam.maidong.entity.newsEntity;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.Constant;


/**
 * Created by abc on 2017/4/28.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsViewHolder> {


    private List<newsEntity.NewsListEntity> newsListEntities;
    private Activity activity;
    BitmapUtils bitmapUtils;

    Handler handler;

    public NewsRecyclerViewAdapter(List<newsEntity.NewsListEntity> newsListEntities, Activity activity, Handler handler) {
        this.newsListEntities = newsListEntities;
        this.activity = activity;
        this.handler = handler;
        bitmapUtils = new BitmapUtils(activity);

        Log.i("messageListlist", newsListEntities.size() + "   " + newsListEntities.toString());
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_news, parent, false);
        return new NewsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        final newsEntity.NewsListEntity messageListEntity = newsListEntities.get(position);
        //判断是否设置了监听器

        //为ItemView设置监听器
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("mOnItemClickListener", "mOnItemClickListener");
                Message message = Message.obtain();
                message.arg1 = position;
                message.arg2 = 0;
                handler.sendMessage(message);
            }
        });

/*
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("mOnItemClickListener", "mOnItemLongClickListener");
                int position = holder.getLayoutPosition();
                Message message = Message.obtain();
                message.arg1 = position;
                message.arg2 = 1;
                myhandler.sendMessage(message);

                return true;
            }
        });*/

        initializeViews(messageListEntity, holder);
    }

    @Override
    public int getItemCount() {
        return newsListEntities.size();
    }

    private void initializeViews(final newsEntity.NewsListEntity object, NewsViewHolder holder) {
        String url =object.getImgUrl();
        Picasso.with(activity)
                .load((url == null||url.length()==0) ? "11" : url)//图片网址
                .placeholder(R.drawable.logo)//默认图标
                .into(holder.imageView1);//控件

        MyToash.Log("messageListlist   ==" + url);
        // bitmapUtils.display(holder.imageView1, object.getImgUrl());
        //  bitmapUtils.
       /* holder.imageView1.setImageBitmap(drawableToBitamp());*/
        holder.textView1.setText(object.getArticleTitle());
        if (object.getContent().length() < 2) {
            holder.textView3.setText("无");
        } else {
            if (object.getContent().length() > 20) {
                holder.textView3.setText(object.getContent().substring(0, 19) + "...");
            } else {
                holder.textView3.setText(object.getContent());
            }


        }
        holder.bt.setFocusable(false);
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("ffff", object.getArticleCategoryID() + "点击了listview中的按钮");
            }
        });

        if (object.getArticleCategoryID().equals("1")) {

            holder.bt.setText("新闻动态");
        } else if (object.getArticleCategoryID().equals("2")) {
            holder.bt.setText("阳光体育");
        } else if (object.getArticleCategoryID().equals("3")) {
            holder.bt.setText("健身指导");
        } else if (object.getArticleCategoryID().equals("4")) {
            holder.bt.setText("健身新闻");
        } else if (object.getArticleCategoryID().equals("5")) {
            holder.bt.setText("系统通知");
        } else if (object.getArticleCategoryID().equals("7")) {
            holder.bt.setText("线下活动");
        } else if (object.getArticleCategoryID().equals("8")) {
            holder.bt.setText("运动宝典");
        }
    }
}

class NewsViewHolder extends RecyclerView.ViewHolder {

    @ViewInject(R.id.imageView1)
    public ImageView imageView1;
    @ViewInject(R.id.textView1)
    public TextView textView1;
    @ViewInject(R.id.textView3)
    public TextView textView3;
    @ViewInject(R.id.data_fenglei)
    public Button bt;


    public NewsViewHolder(View itemView) {
        super(itemView);
        x.view().inject(this, itemView);
    }


}
