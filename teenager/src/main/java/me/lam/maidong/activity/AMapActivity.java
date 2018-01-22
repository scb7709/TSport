package me.lam.maidong.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import me.lam.maidong.R;
import me.lam.maidong.SuperSocket.ICoallBack;
import me.lam.maidong.SuperSocket.IPList;
import me.lam.maidong.SuperSocket.ISocketPacket;
import me.lam.maidong.SuperSocket.SocketClient;
import me.lam.maidong.entity.Location;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.service.SocketService;

@ContentView(R.layout.activity_amap)
public class AMapActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;

    @ViewInject(R.id.activity_map)
    MapView mMapView;
    @ViewInject(R.id.activity_amap_receivecomment)
    TextView activity_amap_receivecomment;

    @ViewInject(R.id.activity_amap_connectstatus)
    Button activity_amap_connectstatus;

    @ViewInject(R.id.activity_amap_send)
    Button activity_amap_sendstatus;

    private AMap aMap;
    private Activity activity;
    SocketClient client;
    Gson gson;
    private Messenger iMessenger;
    Socket  socket;
    boolean IS_OVER;
    String TOKEN;
    public  String CONNECT_STATUS = "";
    public  String SEND_STATUS = "";
    public  String RECEIVE_MESSAGE = "";
    public SocketService.ServiceBinder mBinderService;

    private BroadcastReceiver ServiceToActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String DATA = intent.getStringExtra("DATA");
                switch (intent.getStringExtra("FLAG")) {
                    case "SERIVCE_MSG":
                        if (DATA != null && DATA.length() > 0) {
                            MyToash.Log("服务器返回msg" + DATA);
                            try {
                                String strlocatio = DATA.replace("[", "").replace("]", "");
                                Location locatio = gson.fromJson(strlocatio, Location.class);
                                MyToash.Log("服务器返回msg  =" + locatio.toString());
                            } catch (Exception e) {
                            }
                            activity_amap_receivecomment.setText("服务器返回的数据：\n" + DATA);
                        }
                        break;
                    case "CONNECY_STATUS":
                        activity_amap_connectstatus.setText(DATA);
                       // activity_amap_sendstatus.setText(DATA);
                        socket=mBinderService.getSokket();
                        break;
                    case "SEND_STATUS":
                        activity_amap_sendstatus.setText(DATA);
                        break;
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        activity = this;
        mMapView.onCreate(savedInstanceState);
        initialize();


    }


    private void initialize() {
        gson = new Gson();
        view_publictitle_title.setText("孩子的位置");
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图

        MapsInitializer.loadWorldGridMap(true);
        initSuperSocket();
       // registServiceToActivityReceiver();
       // bindService(new Intent(activity, SocketService.class), mConnection, BIND_AUTO_CREATE);
       // new Thread(runnable).start();
    }


    private void initSuperSocket() {
        TOKEN = "8087182dfada405d885f107b200efe89";
        client = new SocketClient(IPList._ip, IPList._Port, 6, iCoallBack, iSocketPacket);
        ConnectSocket();
    }
    /**
     * 对地图添加onMapIsAbroadListener
     */
    private void setUpMap() {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                // MyToash.Toash(activity,"当前地图中心位置是否在国外");
            }

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        initMarker();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Event(value = {R.id.view_publictitle_back, R.id.activity_amap_connect, R.id.activity_amap_connectstatus, R.id.activity_amap_send})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
                mMapView.onDestroy();
                IS_OVER=true;
              //  unbindService(mConnection);
                finish();
                break;
            case R.id.activity_amap_connect:
                activity_amap_connectstatus.setText("");
                activity_amap_sendstatus.setText("");
                try {
                    mBinderService.AgainConnect();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity_amap_receivecomment.setText("");
                // initSuperSocket();//连接服务器
                break;
            case R.id.activity_amap_connectstatus:
                activity_amap_receivecomment.setText("");
                // client.closeConnection();
                break;

            case R.id.activity_amap_send:
                try {
                    mBinderService.UpMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void initMarker() {
        aMap.clear();
        MarkerOptions markerOption = new MarkerOptions();
        LatLng latLng = new LatLng(39.9329110, 116.444177);
        markerOption.position(latLng);//38°39′6.48″ 东经E104°04′35.11
        markerOption.title("孩子的位置").snippet("39.9329110, 116.444177");
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_me_active)));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        aMap.addMarker(markerOption);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //markerOption.set;
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            MyToash.Log("----onServiceDisconnected----");
        }

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder service) {
            mBinderService = (SocketService.ServiceBinder) service;

        }
    };


    private void registServiceToActivityReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("ServiceToActivityReceiver");
        registerReceiver(ServiceToActivityReceiver, filter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
    {
        // TODO Auto-generated method stub

        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            mMapView.onDestroy();
            IS_OVER=true;
          //  unbindService(mConnection);
            finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onStop() {
        super.onStop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean issend = client.SenddData(TOKEN, false);
                if (issend) {
                    SEND_STATUS = "关闭成功";
                } else {
                    SEND_STATUS = "关闭失败";
                }
                client.closeConnection();
            }
        });
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
                if (msg != null && msg.length() > 0) {
                    MyToash.Log("服务器返回msg" + msg);
                    try {
                        String strlocatio = msg.replace("[", "").replace("]", "");
                        Location locatio = gson.fromJson(strlocatio, Location.class);
                        MyToash.Log("服务器返回msg  =" + locatio.toString());
                    } catch (Exception e) {
                    }
                    activity_amap_receivecomment.setText("服务器返回的数据：\n" + msg);
                }
            }

        }
    };

    ICoallBack iCoallBack = new ICoallBack() {
        @Override
        public void OnSuccess(Socket client1) {
            MyToash.Log("OnSuccess");
            CONNECT_STATUS = "已连接";
            socket = client.getSocket();

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       boolean issend = client.SenddData(TOKEN, true);
                       if (issend) {
                           SEND_STATUS = "发送成功";
                       } else {
                           SEND_STATUS = "发送失败";
                       }
                       MyToash.Log("OnSuccess"+SEND_STATUS);
                   }
               }) .start();

        }

        @Override
        public void OnFailure(Exception e) {
            MyToash.Log("OnFailure");

        }
    };


    String server_msg="";
    Runnable  runnable=new Runnable() {
        @Override
        public void run() {
            while (!IS_OVER){
                if(socket!=null){
                    MyToash.Log("收到服务端消息        一直在 ");
                    try {
                        InputStream in = socket.getInputStream();//获取服务端的输入流，为了获取服务端输入的数据
                        DataInputStream dataInputStream = new DataInputStream(in);
                        byte[] buffer;
                        buffer = new byte[dataInputStream.available()];
                        if (buffer.length != 0) {
                            dataInputStream.read(buffer);
                             server_msg = new String(buffer);
                            // String b1 = new String(three.getBytes("iso-8859-1","utf-8"));
                            MyToash.Log("收到服务端消息11         " + server_msg);
                            if (server_msg != null && server_msg.length() > 0) {
                                MyToash.Log(server_msg);
                                try {
                                    String strlocatio = server_msg.replace("[", "").replace("]", "");
                                    Location locatio = gson.fromJson(strlocatio, Location.class);
                                    MyToash.Log("服务器返回msg  =" + locatio.toString());
                                } catch (Exception e) {
                                }
                                if(server_msg.length()!=0){
                                    handler.sendEmptyMessage(1);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activity_amap_receivecomment.setText("服务器返回的数据：\n" + server_msg);

        }
    };
}
