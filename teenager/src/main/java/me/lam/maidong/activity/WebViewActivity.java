package me.lam.maidong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.utils.Constant;


@ContentView(R.layout.layout_shanshi)
public class WebViewActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;
    @ViewInject(R.id.webView)
    private WebView webView;

    @ViewInject(R.id.myProgressBar)
    private ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initialize();


    }

    private void initialize() {
        Intent intent = this.getIntent();
        String title = intent.getStringExtra("title");
        String URL = intent.getStringExtra("URL");
        Log.i("myblue",URL);
        view_publictitle_title.setText(title);//URL
//支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new webViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        webView.loadUrl(URL);
        //
    }


    @Event(value = {R.id.view_publictitle_back})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                finish();
                break;
        }
    }

    private class webViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
          //  Log.i("myblue", "" + url);
            //  view.loadUrl("javascript:function setTop(){document.getElementsByClassName('adpic')[0].style.display = 'none';}setTop();");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           // Log.i("myblue", "shouldOv   " + url);
/*//这里进行url拦截
            if (url != null && url.contains("http://www.ssp365.com/AccountManage/Login.aspx")) {
                // Intent intent = new Intent(PublicWebView.this, RegisterByPhone.class);
                //  PublicWebView.this.startActivity(intent);

               // view.loadUrl(url);
                ;
                return true;
            }


            return super.shouldOverrideUrlLoading(view, url);*/
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
           // Log.i("myblue", "shouldInt" + url);
            return null;
        }

    }

    private class webChromeClient extends WebChromeClient {
        // 可以通过拦截JS中的如下3个提示方法，也就是几种样式的对话框（在JS中有三个常用的对话框方法alert、comfirm、prompt），
        // 得到他们的消息内容，然后解析即可。这样可达到修改弹出框样式与app风格统一，或者与本地代码进行交互的目的，
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {


            return super.

                    onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

}
