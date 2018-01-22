package me.lam.maidong.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.net.Socket;

import me.lam.maidong.SuperSocket.ICoallBack;
import me.lam.maidong.SuperSocket.IPList;
import me.lam.maidong.SuperSocket.ISocketPacket;
import me.lam.maidong.SuperSocket.SocketClient;
import me.lam.maidong.myview.MyToash;

/**
 * Created by abc on 2018/1/16.
 */
public class SocketService extends Service {
    public static String CONNECT_STATUS = "";
    public static String SEND_STATUS = "";
    public static String RECEIVE_MESSAGE = "";
    private SocketClient client;
    String TOKEN;
    Intent ServiceToActivityIntent;
   // Thread thread = new Thread();
    Socket socket;
    public enum STATUS {
        SEND_STATUS, CONNECY_STATUS, SERIVCE_MSG
    }

    boolean IS_OVER;
    public ServiceBinder mBinder = new ServiceBinder(); /* 数据通信的桥梁 */

    /* 第一种模式通信：Binder */
    public class ServiceBinder extends Binder {

        public void AgainConnect() throws InterruptedException {
            ConnectSocket();
        }

        public void UpMessage() throws InterruptedException {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean issend = client.SenddData(TOKEN, true);
                    if (issend) {
                        SEND_STATUS = "发送成功";
                    } else {
                        SEND_STATUS = "发送失败";

                    }
                    ServiceToActivityIntent.putExtra("FLAG", "SEND_STATUS");
                    ServiceToActivityIntent.putExtra("DATA", SEND_STATUS);
                    sendBroadcast(ServiceToActivityIntent);

                    MyToash.Log("OnSuccess " + SEND_STATUS);
                }
            }).start();
        }
        public  Socket getSokket(){
            return  socket;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceToActivityIntent = new Intent();
        ServiceToActivityIntent.setAction("ServiceToActivityReceiver");
        initSuperSocket();
    }

    private void initSuperSocket() {
        TOKEN = "8144771b856c46c0adbb5d14a09311c6";
        client = new SocketClient(IPList._ip, IPList._Port, 6, iCoallBack, iSocketPacket);
        ConnectSocket();
    }

    private void ConnectSocket() {
        IS_OVER = false;
        client.Connection();//连接服务器
    }

    ISocketPacket iSocketPacket = new ISocketPacket() {
        @Override
        public void SocketPacket(String msg) {
            if (msg != null && msg.length() > 0) {
                MyToash.Log(msg);
                RECEIVE_MESSAGE = msg;
                ServiceToActivityIntent.putExtra("FLAG", "SERIVCE_MSG");
                ServiceToActivityIntent.putExtra("DATA", RECEIVE_MESSAGE);
                sendBroadcast(ServiceToActivityIntent);
            }

        }
    };

    ICoallBack iCoallBack = new ICoallBack() {
        @Override
        public void OnSuccess(Socket client1) {
            MyToash.Log("OnSuccess");
            CONNECT_STATUS = "已连接";
            socket = client.getSocket();
            RECEIVE_HANDLE.sendEmptyMessageDelayed(100, 1);
            ServiceToActivityIntent.putExtra("FLAG", "CONNECY_STATUS");
            ServiceToActivityIntent.putExtra("DATA", CONNECT_STATUS);
            sendBroadcast(ServiceToActivityIntent);
            send(false);
        }

        @Override
        public void OnFailure(Exception e) {
            MyToash.Log("OnFailure");
            CONNECT_STATUS = "连接失败";
            ServiceToActivityIntent.putExtra("FLAG", "CONNECY_STATUS");
            ServiceToActivityIntent.putExtra("DATA", CONNECT_STATUS);
            sendBroadcast(ServiceToActivityIntent);
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        MyToash.Log("onUnbind");
        IS_OVER = true;
        send(true);
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        MyToash.Log("onDestroy " + SEND_STATUS);

    }

    Handler RECEIVE_HANDLE = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private void send(final boolean isover) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean issend = client.SenddData(TOKEN, isover);
                if (isover) {
                    if (issend) {
                        SEND_STATUS = "关闭成功";
                        MyToash.Log("OnSuccess" + SEND_STATUS);
                        client.closeConnection();
                    } else {
                        SEND_STATUS = "关闭失败";
                        MyToash.Log("OnSuccess" + SEND_STATUS);
                        client.closeConnection();
                    }
                } else {
                    if (issend) {
                        SEND_STATUS = "发送成功";

                    } else {
                        SEND_STATUS = "发送失败";

                    }
                    ServiceToActivityIntent.putExtra("FLAG", "SEND_STATUS");
                    ServiceToActivityIntent.putExtra("DATA", SEND_STATUS);
                    sendBroadcast(ServiceToActivityIntent);

                    MyToash.Log("OnSuccess" + SEND_STATUS);
                }

            }
        }).start();
    }
}
