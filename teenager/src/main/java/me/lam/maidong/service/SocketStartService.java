package me.lam.maidong.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.net.Socket;

import me.lam.maidong.SuperSocket.ICoallBack;
import me.lam.maidong.SuperSocket.IPList;
import me.lam.maidong.SuperSocket.ISocketPacket;
import me.lam.maidong.SuperSocket.SocketClient;
import me.lam.maidong.entity.Location;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.InternetUtils;
import me.lam.maidong.utils.ShareUitls;
import me.lam.maidong.watchdatasqlite.MySQLiteDataDao;

/**
 * Created by abc on 2018/1/16.
 */
public class SocketStartService extends Service {
    public static String CONNECT_STATUS = "";
    public static String SEND_STATUS = "";
    public static String RECEIVE_MESSAGE = "";
    private SocketClient client;
    String TOKEN;
    Intent ServiceToActivityIntent;
    // Thread thread = new Thread();
    Socket socket;
    Gson gson;
    Location location;
    private MySQLiteDataDao mySQLiteDataDao;
    boolean IS_OVER;
    //用来测试和服务端 是链接状态的 心跳包 数据  状态密码 80809
    byte[] CHECK_CONNECT = {1, 0, 0, 0, 0, 0, 49, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 0, 1, 59, -87, 8, 0, 0, 0, 32, 0, 0, 0, 4, 100, 97, 116, 97, 35, 35, 95, 42, 42};
    Service service;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service=SocketStartService.this;
        this.mySQLiteDataDao = MySQLiteDataDao.getInstance(service);
        ServiceToActivityIntent = new Intent();
        ServiceToActivityIntent.setAction("ServiceToActivityReceiver");
        initSuperSocket();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    private void initSuperSocket() {
        ShareUitls.putString(service, "templocation", "");
        gson = new Gson();
        TOKEN = ShareUitls.getString(this, "TOKEN", "");
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
                if(msg.endsWith("}")&&msg.startsWith("{")){
                    hanlderData(msg);
                }else if(msg.endsWith("]")&&msg.startsWith("[")){
                    hanlderData(msg);
                    ShareUitls.putString(service, "templocation", "");
                }else if(msg.startsWith("[")){
                   // String temp=ShareUitls.getString(service, "templocation", "");
                    ShareUitls.putString(service, "templocation", msg);
                }else  if(msg.endsWith("]")){
                    String temp=ShareUitls.getString(service, "templocation", "");
                    hanlderData(temp+msg);
                    ShareUitls.putString(service, "templocation", "");
                }else if(!msg.endsWith("**")){
                     String temp=ShareUitls.getString(service, "templocation", "");
                    ShareUitls.putString(service, "templocation",temp+ msg);
                }

            }

        }
    };

    private void hanlderData(String msg) {
        try {
            //JSONArray jsonArray = new JSONArray(msg);
            //if (jsonArray != null && jsonArray.length() > 0) {
               // MyToash.Log("youshuju"+jsonArray.length());
               // JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                location = gson.fromJson(msg, Location.class);
                ShareUitls.putString(service, "location", location.latitude + "_" + location.longitude);
                MyToash.Log(msg);
                ServiceToActivityIntent.putExtra("FLAG", "SERIVCE_MSG");
                ServiceToActivityIntent.putExtra("DATA", location.latitude + "_" + location.longitude);
                sendBroadcast(ServiceToActivityIntent);
                mySQLiteDataDao.Insert(msg);
         //   }

        } catch (Exception e) {
            MyToash.Log("解析异常youshuju");
        }
    }

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
        send(true);
        //stopForeground(true);
        try {
            handler.removeMessages(0);
        } catch (Exception e) {
        }
        //MyToash.Log("onDestroy " + SEND_STATUS);

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
                    } else {
                        SEND_STATUS = "关闭失败";
                    }
                    MyToash.Log("OnSuccess" + SEND_STATUS);
                    client.closeConnection();
                } else {
                    if (issend) {
                        SEND_STATUS = "发送成功";
                        handler.sendEmptyMessageDelayed(0, 30000);//每隔30秒检查一次
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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new Thread(new Runnable() {
                @Override
                public void run() {//8144771b856c46c0adbb5d14a09311c6

                    if (InternetUtils.internett(service)) {
                        boolean issend = client.SenddDataCheck(CHECK_CONNECT);
                        if (issend) {
                            MyToash.Log("正常连接");
                            handler.sendEmptyMessageDelayed(0, 30000);//每隔30秒检查一次
                        } else {
                            MyToash.Log("连接断开");
                            ConnectSocket();//重连一次
                        }
                    } else {
                        handler.sendEmptyMessageDelayed(0, 10000);//每隔10秒检查一次
                        ServiceToActivityIntent.putExtra("FLAG", "NONET");
                        ServiceToActivityIntent.putExtra("DATA", "");
                        sendBroadcast(ServiceToActivityIntent);
                    }
                }
            }).start();
        }
    };
}
