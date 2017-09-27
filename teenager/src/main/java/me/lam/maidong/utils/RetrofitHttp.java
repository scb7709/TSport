package me.lam.maidong.utils;

import android.app.Activity;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.Log;


import com.google.gson.JsonObject;

import org.json.JSONObject;


import java.util.Map;



/*
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;*/

/**
 * Created by abc on 2017/6/12.
 */
public class RetrofitHttp {
    public interface ResponseListener {
        void onResponse(String response);

        void onErrorResponse();
    }


    private static RetrofitHttp retrofitHttp;
    private static Activity context;
    //  private static com.headlth.management.clenderutil.WaitDialog waitDialog;


    public RetrofitHttp() {

    }

    public static RetrofitHttp getInstance(Activity activity) {
        context = activity;
        if (retrofitHttp == null) {
            synchronized (RetrofitHttp.class) {
                if (retrofitHttp == null) {
                    retrofitHttp = new RetrofitHttp();
                }

            }
        }
        return retrofitHttp;
    }

/*
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();


    public static void sendRequestRequestParams(Activity activity, final String dialogMessage, final Map<String, String> params, boolean dialog, final ResponseListener responseListener) {
        WaitDialog waitDialog = new WaitDialog(activity);
        if (dialog) {
            waitDialog.setCancleable(true);
            waitDialog.setMessage(dialogMessage);
            waitDialog.showDailog();
        }

        if (InternetUtils.internet(activity)) {
            RetrofitApi repo = retrofit.create(RetrofitApi.class);

            Call<String> call = repo.getMaindongData(params);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
//response.body().toString() + " " + response.message() +
                            Log.i("senstParams", response.toString() + " " + "");

                  //  responseListener.onResponse(response.body().toString());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    responseListener.onErrorResponse();

                    Log.i("senstParamsf",retrofit.baseUrl()+" "+ t.getMessage()+" ");

                }
            });
            ;

        } else {

        }
        ;

    }*/
}
