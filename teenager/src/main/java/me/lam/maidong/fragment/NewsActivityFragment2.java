package me.lam.maidong.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.support.annotation.Nullable;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.activity.WebViewActivity;
import me.lam.maidong.adapter.NewsRecyclerViewAdapter;
import me.lam.maidong.entity.NewsList;
import me.lam.maidong.entity.newsEntity;
import me.lam.maidong.myview.EndLessOnScrollListener;
import me.lam.maidong.utils.Constant;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;

/**
 * A placeholder fragment containing a simple view.
 */


@ContentView(R.layout.fragment_news)
public class NewsActivityFragment2 extends LazyFragment {

    @ViewInject(R.id.frgment_news_SwipeRefreshLayout)
    private SwipeRefreshLayout frgment_news_SwipeRefreshLayout;
    @ViewInject(R.id.frgment_news_recyclerView)
    private RecyclerView frgment_news_recyclerView;
    NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    public newsEntity news;
    //  List<newsEntity.NewsListEntity> newsListEntities;
    View view;
    Gson gson = new Gson();
    LinearLayoutManager linearLayoutManager;

    private String flag;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                int possition = msg.arg1;
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("title", "资讯详情");//
                //http://www.ssp365.com/ArticleView.aspx?id=123
                intent.putExtra("URL", Constant.WEBVIEW + "ArticleView.aspx?id=" + NewsList.getInstance().newsListEntities.get(possition).getID());
                startActivity(intent);
            } catch (IndexOutOfBoundsException e) {
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        //   lazyLoad();
    }

    static String lastTime;
    static int i = 0;
    boolean isPrepared;

    @Override
    protected void lazyLoad() {

       /* //Fragment可见之后才进行网络加载
        if (!isPrepared || !isVisible) {
            Log.e("huadong", "lazyLoadreturn");
            return;
        }
        if (news == null) {
            getNews("first");
        }*/

    }

    Boolean msgshibai = false;

    void initialize() {
        // newsListEntities =  NewsList.getInstance().newsListEntities;
        linearLayoutManager = new LinearLayoutManager(getActivity());

        frgment_news_recyclerView.setLayoutManager(linearLayoutManager);


        frgment_news_SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                frgment_news_SwipeRefreshLayout.setRefreshing(true);
                NewsList.getInstance().newsListEntities.clear();
                flag = "Refreshing";
                getNews();
            }
        });


        frgment_news_recyclerView.addOnScrollListener(
                new EndLessOnScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int currentPage) {
                        if (NewsList.getInstance().newsListEntities.size() != 0) {
                            flag = "loadmore";
                            getNews();
                        }
                        ;
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        LinearLayoutManager layoutManager = (LinearLayoutManager) frgment_news_recyclerView.getLayoutManager();
                        //获取可视的第一个view
                        View topView = layoutManager.getChildAt(0);
                        if (topView != null) {
                            //获取与该view的顶部的偏移量
                            int lastOffset = topView.getTop();
                            //得到该View的数组位置
                            int lastPosition = layoutManager.getPosition(topView);
                            ShareUitls.putString(getActivity(), "newslastOffset", lastOffset + "");
                            ShareUitls.putString(getActivity(), "newslastPosition", lastPosition + "");
                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }


                }

        );

        if (NewsList.getInstance().newsListEntities.size() != 0) {
            newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(NewsList.getInstance().newsListEntities, getActivity(), handler);
            frgment_news_recyclerView.setAdapter(newsRecyclerViewAdapter);
            int lastOffset = Integer.parseInt(ShareUitls.getString(getActivity(), "newslastOffset", "0"));
            int lastPosition = Integer.parseInt(ShareUitls.getString(getActivity(), "newslastPosition", "0"));
            if (frgment_news_recyclerView.getLayoutManager() != null && lastPosition >= 0) {
                ((LinearLayoutManager) frgment_news_recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
            }
        } else {
            flag = "first";
            getNews();
        }
    }

    private void getNews() {
        String url = "GetNews";
        Log.i("myblue", flag + "  " + NewsList.getInstance().newsListEntities.size() + "  ");

        if (flag.equals("loadmore")) {
            url = url + "/?Date=" + NewsList.getInstance().newsListEntities.get(NewsList.getInstance().newsListEntities.size() - 1).getArticleModified();
            Log.i("myblue", flag + "  " + NewsList.getInstance().newsListEntities.size() + "  " + NewsList.getInstance().newsListEntities.get(NewsList.getInstance().newsListEntities.size() - 1).getArticleModified());
        }
        OKHttp.sendRequestRequestParams(getActivity(), "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Log.i("getAsynHttp", response.toString());
                if (flag.equals("Refreshing")) {
                    frgment_news_SwipeRefreshLayout.setRefreshing(false);
                }
                newsEntity news = gson.fromJson(response, newsEntity.class);

                Log.i("getAsynHttp", news.toString());


                if (news != null) {
                    if (news.getStatus() == 1 && news.getNewsList().size() != 0) {
                        if (NewsList.getInstance().newsListEntities.size() == 0) {
                            NewsList.getInstance().newsListEntities.addAll(news.getNewsList());
                            // newsListEntities = news.getNewsList();
                            newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(NewsList.getInstance().newsListEntities, getActivity(), handler);
                            frgment_news_recyclerView.setAdapter(newsRecyclerViewAdapter);
                        } else {
                            NewsList.getInstance().newsListEntities.addAll(NewsList.getInstance().newsListEntities.size(), news.getNewsList());
                            newsRecyclerViewAdapter.notifyItemRangeInserted(NewsList.getInstance().newsListEntities.size(), news.getNewsList().size());
                        }


                    } else {
                        Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse() {

                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
