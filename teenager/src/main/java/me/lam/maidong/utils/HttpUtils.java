package me.lam.maidong.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;



import me.lam.maidong.activity.LogActivity;

/**
 * Created by abc on 2016/7/18.
 */
public class HttpUtils {
    private static HttpUtils httpUtils;
    private static Activity context;
    private static WaitDialog waitDialog;

    public interface ResponseListener {
        void onResponse(String response);

        void onErrorResponse(Throwable ex);
    }

   /* public HttpUtils() {
        httpUtils = new HttpUtils();
    }*/
    public HttpUtils() {

    }

    public static HttpUtils getInstance(Activity activity) {
        context = activity;
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }

            }
        }
        return httpUtils;
    }


    private void initDialog() {
        waitDialog = new WaitDialog(context);
        waitDialog.setCancleable(true);

    }

    public void sendRequestRequestParams(final String dialogMessage, final RequestParams params, boolean dialog, final ResponseListener responseListener) {
        final WaitDialog waitDialog = new WaitDialog(context);
        if (dialog) {
            waitDialog.setCancleable(true);
            if(!dialogMessage.equals("Login")){
                waitDialog.setMessage(dialogMessage);
            }else {
                waitDialog.setMessage("");
            }
            waitDialog.showDailog();
        }
        if (InternetUtils.internet(context)) {
           // params.addQueryStringParameter("VersionNum", VersonUtils.getVersionName(context));
           // Log.i("VersionNum", VersonUtils.getVersionName(context));
           // Log.i("params", params.getUri()+" "+params.getQueryStringParams().get(0)+"  "+params.getQueryStringParams().get(1));
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    responseListener.onResponse(result);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    Log.i("AAAAACCCAAAA", ex.toString() + "'" + isOnCallback);
                    try {
                        responseListener.onErrorResponse(ex);
                    } catch (Exception E) {
                    }


                }

                @Override
                public void onCancelled(CancelledException cex) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    // responseListener.onErrorResponse(new VolleyError());
                }

                @Override
                public void onFinished() {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                }
            });

        } else {
            if (waitDialog != null) {
                waitDialog.dismissDialog();
            }

            if(dialogMessage.equals("Login")){

                context.startActivity(new Intent(context, LogActivity.class));
                context.finish();

            }
        }
        ;

    }

    Gson g = new Gson();



    public void sendGetRequest(RequestParams params , final ResponseListener responseListener) {


        if (InternetUtils.internet(context)) {
          //  RequestParams params = new RequestParams(url);
            final WaitDialog waitDialog = new WaitDialog(context);
            waitDialog.setMessage("");
            waitDialog.showDailog();

            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {

                    responseListener.onResponse(result);
                    Log.i("AAAAAAAAAA", result + "'");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    responseListener.onErrorResponse(ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    waitDialog.dismissDialog();

                }

                @Override
                public void onFinished() {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    //   responseListener.onErrorResponse(new VolleyError());
                }
            });

        }
    }
}


