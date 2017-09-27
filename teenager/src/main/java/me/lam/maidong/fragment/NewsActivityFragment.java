package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import java.io.IOException;

import me.lam.maidong.R;
import me.lam.maidong.activity.WebViewActivity;


import me.lam.maidong.adapter.ListviewItemAdapter;
import me.lam.maidong.entity.NewsList;
import me.lam.maidong.entity.newsEntity;
import me.lam.maidong.utils.Constant;

/**
 * A placeholder fragment containing a simple view.
 */

public class NewsActivityFragment extends Fragment {
    /*private TextView textView;
    private PullToRefreshListView listview;
    public  newsEntity news;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("huadong", "onPageSelected？+第一次执行顺序是？？？");
     *//*   pd = new ProgressDialog(getActivity());
        pd.setTitle("登录中...");
        pd.setMessage("请稍等...");
//        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);*//*
        return inflater.inflate(R.layout.fragment_news, null);
    }




    @SuppressLint("ValidFragment")
    public NewsActivityFragment(newsEntity news) {


        this.news = news;
    }
 *//*   下拉更新：
    GET http://182.92.215.55:8088/api/GetNews*//*


    *//*   上拉更新：
       GET http://182.92.215.55:8088/api/GetNews?Date={Date}*//*
    public NewsActivityFragment() {

    }

    static String backLast;
    static String lastTime;
    static int i = 0;
    ListviewItemAdapter adp;
    int mesgfirstAsk=0;

  @Override
    public void onStart() {
        super.onStart();
        Log.e("huadong", "onStart");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("title","咨询详情");
                intent.putExtra("URL",Constant.BASE+"ArticleView.aspx?id="+ news.getNewsList().get(position - 1).getID());
                startActivity(intent);

            }
        });

        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                lastTime = news.getNewsList().get(news.getNewsList().size() - 1).getArticleModified();
                Log.e("res", "dsfsd" + lastTime);
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("res", "dsfsd" + "上推正在请求" + lastTime);
                Log.e("res", "dsfsd" + "上推正在请求最后一次时间" + news.getNewsList().get(news.getNewsList().size() - 1).getArticleModified());
                Log.e("res", "dsfsd" + "上推正在请求" + news.getNewsList().get(news.getNewsList().size() - 1).getArticleModified());
                if (i == 0) {
                    Log.e("res", "dsfsd" + "走为0这里了");

                    lastTime = news.getNewsList().get(news.getNewsList().size() - 1).getArticleModified();
                    getData(lastTime);
                    i++;
                } else {

                    Log.e("res", "dsfsd" + "走不为0了" + lastTime);
                    getData(lastTime);
                }
            }
        });

        listview.setMode(PullToRefreshBase.Mode.BOTH);
    }






    public void getData() {
        String url = Constant.BASE_URL+"/api/GetNews";
        HttpUtils httpUtils = new HttpUtils();
        RequestParams rp = new RequestParams();
        Gson gson = new Gson();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                listview.onRefreshComplete();
                String r = responseInfo.result;
                Gson g = new Gson();
                newsEntity flt = g.fromJson(r, newsEntity.class);
                if (flt.getStatus() == 1) {
                    listview.onRefreshComplete();
                    Log.e("res", flt + "下拉成功：：：状态" + flt.getStatus() + "集合" + flt.getNewsList().size());

                    adp = new ListviewItemAdapter(getActivity(), flt.getNewsList());
                    backLast = flt.getNewsList().get(flt.getNewsList().size() - 1).getArticleModified();
                    Log.e("res", flt + "下拉成功：：：backLast" + backLast);
                    i = 0;
                    listview.setAdapter(adp);

                     *//*   dataResualtCallBack call=dataResualtCallBack.getInstance();
                        Log.e("res",call+"测试获取最近一次是否成功：：："+call.getStatus());*//*
                  *//*  Intent intent = new Intent();
                    intent.setClass(LogActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataRes", dataRes);
                    bundle.putString("EduCode", EduCode);
                    intent.putExtras(bundle);
                    LogActivity.this.startActivity(intent);*//*
                    return;
                } else {
                    Log.e("res", "下拉失败" + flt.getStatus());
                       *//* try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*//*
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("res", "下拉失败");
            }
        });
    }

    public void getData(final String LastSportDate) {
        String url = Constant.BASE_URL+"/api/GetNews?Date=" + LastSportDate;
        HttpUtils httpUtils = new HttpUtils();
        RequestParams rp = new RequestParams();
        Log.e("res", "onSuccess" + "上推正在请求" + url);
        Gson gson = new Gson();
        httpUtils.send(HttpRequest.HttpMethod.GET, url.replaceAll(" ", "%20"), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("res", "onSuccess" + "上推正在请求" + news.getNewsList());
                listview.onRefreshComplete();
                String r = responseInfo.result;
                Gson g = new Gson();
                newsEntity flt = g.fromJson(r, newsEntity.class);
                if (flt.getStatus() == 0) {
                    Log.e("res", "上推失败lallala" + lastTime);

                    lastTime = news.getNewsList().get(news.getNewsList().size() - 1).getArticleModified();
                    return;
                } else {
                    if (i != 0) {
                        adp.add(flt.getNewsList());
                        adp.notifyDataSetChanged();
                        lastTime = adp.getObjects().get(adp.getObjects().size() - 1).getArticleModified();
//                        filelistListview.stopLoadMore();
//                        if(flt.getCount()==flt.getOffset()+flt.getData().size()) {
//                            filelistListview.mFooterView.txt_progresstext.setText("加载完成");
//                        }
                    } else {
                        Log.e("res", "上推第一次");
//                        if(filelistListview.mHeaderView.getst)
//                        filelistListview.stopRefresh();
                   *//*     String lastTime=flt.getNewsList().get(flt.getNewsList().size()-1).getArticleModified();*//*
                        adp.add(flt.getNewsList());
                        lastTime = adp.getObjects().get(adp.getObjects().size() - 1).getArticleModified();
                        Log.e("res", "上推第一次" + lastTime);
                        listview.setAdapter(adp);
                        // adp.notifyDataSetChanged();
                    }


//
                    return;

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
//                filelistListview.stopLoadMore();
//                filelistListview.stopRefresh();
                Log.e("res", "onFailure" + "上推正在请求" + news.getNewsList());
                listview.onRefreshComplete();
            }
        });

    }
    Boolean msgshibai = false;
    public Handler hh = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 9) {
           *//*     pd.dismiss();*//*
                if (msg.arg1 == 2) {
                    mesgfirstAsk = 0;
                }
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 7) {
                adp = new ListviewItemAdapter(getActivity(), news.getNewsList());
                listview.setAdapter(adp);








            }


        }
    };*/

}