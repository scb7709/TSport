package me.lam.maidong.myview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by abc on 2017/8/30.
 */
public class MyToash {
    public static void Toash(Context activity, String Message) {
        if (activity != null) {
            Toast.makeText(activity, Message, Toast.LENGTH_LONG).show();
        }
    }
    public static void Toash(Context activity) {
        if (activity != null) {
            Toast.makeText(activity, "网络异常", Toast.LENGTH_LONG).show();
        }
    }
    public static void ToashNoNet(Activity activity) {
        Toash(activity, "网络异常");
    }

    public static void Log(String Message) {
        Log.i("myblue", Message);
    }
}//C:\Users\abc\Desktop\version\adualtqianming\adualt.keystore   02:5A:6A:7B:F1:D3:2A:59:41:B6:DB:1E:FB:2B:72:A8:9D:60:AF:0C
