package me.lam.maidong.selfdefine;

import android.app.Activity;

import android.util.Log;

import com.google.gson.Gson;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;
import java.util.Set;

import me.lam.maidong.utils.InternetUtils;
import me.lam.maidong.utils.VersonUtils;
import me.lam.maidong.utils.WaitDialog;

/**
 * Created by abc on 2016/7/18.
 */
public class HttpUtils {
    private static HttpUtils httpUtils;
    static HttpManager httpManager;
    private static Activity context;
    private static WaitDialog waitDialog;

    public interface ResponseListener {
        void onResponse(String response);

        void onErrorResponse(Throwable ex);
    }

    public HttpUtils(Activity activity) {
        context = activity;
        httpManager = x.http();
        httpUtils = new HttpUtils();
    }

    public HttpUtils() {

    }

    public static HttpUtils getInstance(Activity activity) {
        context = activity;
        httpManager = x.http();
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

    public void sendRequest(final String dialogMessage, final String url, final Map<String, String> map, int point, final ResponseListener responseListener) {
        final WaitDialog waitDialog = new WaitDialog(context);
        waitDialog.setCancleable(true);
        if (point != 10) {
            waitDialog.setMessage(dialogMessage);
            waitDialog.showDailog();
        }

        if (InternetUtils.internet(context)) {
            RequestParams params = new RequestParams(url);
            //params.addBodyParameter(entry.getKey().toString(), entry.getValue().toString());
            Set entries = map.entrySet();
            if (entries != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    Log.i("AAAAAAAAUUU", entry.getKey() + "   " + entry.getValue());
                    params.addQueryStringParameter(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            httpManager.post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    responseListener.onResponse(result);
                    Log.i("AAAAAAAAAA", result + "'");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    Log.i("AAAAACCCAAAA", ex.toString() + "'" + isOnCallback);
                    responseListener.onErrorResponse(ex);

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
                    //responseListener.onErrorResponse(new VolleyError());
                }
            });

        } else {
            if (waitDialog != null) {
                waitDialog.dismissDialog();
            }
        }
        ;

    }

    public void sendRequestRequestParams(final String dialogMessage, final RequestParams params, boolean dialog, final ResponseListener responseListener) {
        final WaitDialog waitDialog = new WaitDialog(context);
        if (dialog) {
            waitDialog.setCancleable(true);
            waitDialog.setMessage(dialogMessage);
            waitDialog.showDailog();
        }
        if (InternetUtils.internet(context)) {
            params.addQueryStringParameter("VersionNum", VersonUtils.getVersionName(context));
            ;

            Log.i("VersionNum", VersonUtils.getVersionName(context));
            httpManager.post(params, new Callback.CommonCallback<String>() {
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
        }
        ;

    }

    Gson g = new Gson();



    public void sendGetRequest(final String url, final ResponseListener responseListener) {


        if (InternetUtils.internet(context)) {
            RequestParams params = new RequestParams(url);
            httpManager.get(params, new Callback.CommonCallback<String>() {
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
                    //   responseListener.onErrorResponse(new VolleyError());
                }
            });

        }
    }
}


