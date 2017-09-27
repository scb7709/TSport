package me.lam.maidong.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.List;

import me.lam.maidong.utils.ShareUitls;

/**
 * Created by abc on 2017/6/13.
 */
public class MyAPP extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // Bugly.init(getApplicationContext(), "06096a0224", true);
//Xutils3 初始化
        x.Ext.init(this);
        x.Ext.setDebug(false);


        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(false);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                ShareUitls.putString(getApplicationContext(), "token", deviceToken);
                Log.i("myblue", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("myblue", s + "  " + s1);
            }
        });
        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    UmengMessageHandler messageHandler = new UmengMessageHandler() {

        @Override
        public Notification getNotification(Context context, UMessage msg) {
        /*    //广播 跟新首页未读通知条数
            Intent sendBroadcast = new Intent();
            sendBroadcast.setAction("main_listCount");
            context.sendBroadcast(sendBroadcast);
            Log.i("getNotification", "" + msg.custom);*/
            switch (msg.builder_id) {

                default:
                    //默认为0，若填写的builder_id并不存在，也使用默认。
                    return super.getNotification(context, msg);

            }
        }
    };

    /**
     * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
     * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
     * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
     */
    UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {


        @Override
        public void dismissNotification(Context context, UMessage uMessage) {
            super.dismissNotification(context, uMessage);
        }

        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {

            try {
                JSONObject jsonObject = new JSONObject(msg.custom);
                if (jsonObject.getString("MsgtypeId").equals("2")) {

                }
            } catch (JSONException e) {
                Log.i("ASDFGJSONException", "ASDFGMsgtypeId");

                e.printStackTrace();
            }


        }
    };

}
