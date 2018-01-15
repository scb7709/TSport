package me.lam.maidong.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import me.lam.maidong.activity.LogActivity;
import me.lam.maidong.entity.PublicDataClass;


/**
 * Created by abc on 2017/6/12.
 */
public class OKHttp {
    public interface ResponseListener {
        void onResponse(String response);

        void onErrorResponse();
    }

    public interface ResponseListenerNew {
        void onResponse(String response, PublicDataClass.StatusModel statusModel);

        void onErrorResponse();
    }

    static WaitDialog waitDialog;
  /*public static  ResponseListener responseListener;
    public static  String Response;
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if(Response!=null&&responseListener!=null){
                    responseListener.onResponse(Response);
                }

            } else {
                if(responseListener!=null) {
                    responseListener.onErrorResponse();
                }
            }


        }
    };*/

    public static void sendRequestRequestParams(Activity activity, final String dialogMessage, boolean dialog, String url, final ResponseListener ResponseListener) {
        if (activity == null) {
            Log.i("myblue", "Token空了");
            return;
        }

       /* responseListener=null;
           Response=null;


       responseListener=ResponseListener;*/
        if (InternetUtils.internet(activity)) {
            if (dialog) {
                waitDialog = new WaitDialog(activity);
                if (!dialogMessage.equals("Login")) {
                    waitDialog.setMessage(dialogMessage);
                } else {
                    waitDialog.setMessage("");
                }
                waitDialog.showDailog();
            }
            RequestParams params = new RequestParams(Constant.BASE_URL +url);
            Log.i("myblue", Constant.BASE_URL +url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String Response) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    ResponseListener.onResponse(Response);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    Log.i("onResponse","onFailure");
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    ResponseListener.onErrorResponse();
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
          /*  OkHttpClient mOkHttpClient = new OkHttpClient();
            Request.Builder requestBuilder = new Request.Builder().url(Constant.BASE_URL + url);
            //可以省略，默认是GET请求
            // requestBuilder.method("GET", null);
            Request request = requestBuilder.build();
            okhttp3.Call call = mOkHttpClient.newCall(request);

            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.i("onResponse","onFailure");
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    // Log.i("getAsynHttponFailure", call.request().body().toString());
                    handler.sendEmptyMessage(0);


                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }

                    Response=response.body().string();
                    Log.i("onResponse",""+Response);
                    handler.sendEmptyMessage(1);
                   // responseListener.onResponse(response.body().string());

                }
            });*/


        } else {
            if (dialogMessage.equals("Login")) {
                activity.startActivity(new Intent(activity, LogActivity.class));
                activity.finish();

            }
        }

    }

    public static void sendRequestRequestParamsNew(Activity activity, final String dialogMessage, boolean dialog, String url, final ResponseListenerNew responseListener) {
        if (activity == null) {
            Log.i("myblue", "Token空了");
            return;
        }
        if (InternetUtils.internet(activity)) {
            if (dialog) {
                waitDialog = new WaitDialog(activity);
                if (!dialogMessage.equals("Login")) {
                    waitDialog.setMessage(dialogMessage);
                } else {
                    waitDialog.setMessage("");
                }
                waitDialog.showDailog();
            }
            RequestParams params = new RequestParams(Constant.BASE_URL +url);
            Log.i("myblue", Constant.BASE_URL +url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    String result = s;
                    //  Log.i("myblue", result + "  -------  ");
                   /* Person.Organizations.Type objectType = new TypeToken<PublicDataClass<Data>>() {}.getType();
                     gson.fromJson(json, objectType);*/
                    JSONObject[] jsonObjects = PublicClassGetJsonObject.getPublicClassGetJsonObject(result);
                    PublicDataClass.StatusModel statusModel = new Gson().fromJson(jsonObjects[0].toString(), PublicDataClass.StatusModel.class);
                    responseListener.onResponse(jsonObjects[1].toString(), statusModel);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }

                    responseListener.onErrorResponse();
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });

            /*OkHttpClient mOkHttpClient = new OkHttpClient();
            Request.Builder requestBuilder = new Request.Builder().url(Constant.BASE_URL + url);
            //可以省略，默认是GET请求
            // requestBuilder.method("GET", null);
            Request request = requestBuilder.build();
            okhttp3.Call call = mOkHttpClient.newCall(request);

            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }

                    responseListener.onErrorResponse();

                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    if (waitDialog != null) {
                        waitDialog.dismissDialog();
                    }
                    String result = response.body().string();
                  //  Log.i("myblue", result + "  -------  ");
                   *//* Person.Organizations.Type objectType = new TypeToken<PublicDataClass<Data>>() {}.getType();
                     gson.fromJson(json, objectType);*//*
                    JSONObject[] jsonObjects = PublicClassGetJsonObject.getPublicClassGetJsonObject(result);
                    PublicDataClass.StatusModel statusModel = new Gson().fromJson(jsonObjects[0].toString(), PublicDataClass.StatusModel.class);
                    responseListener.onResponse(jsonObjects[1].toString(), statusModel);

                }
            });

*/
        } else {
            responseListener.onErrorResponse();
            if (dialogMessage.equals("Login")) {
                activity.startActivity(new Intent(activity, LogActivity.class));
                activity.finish();

            }
        }

    }
}
