package me.lam.maidong.fragment;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class LocationFragment extends Fragment {
   /* @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;*/

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
    Socket socket;
    boolean IS_OVER;
    String TOKEN;
    public String CONNECT_STATUS = "";
    public String SEND_STATUS = "";
    public String RECEIVE_MESSAGE = "";
    public SocketService.ServiceBinder mBinderService;

    public LocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return org.xutils.x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        initialize();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    private void initialize() {
        gson = new Gson();
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
        TOKEN = "8144771b856c46c0adbb5d14a09311c6";
        client = new SocketClient(IPList._ip, IPList._Port, 6, iCoallBack, iSocketPacket);
        ConnectSocket();
    }


    @Event(value = {R.id.activity_amap_connect, R.id.activity_amap_connectstatus, R.id.activity_amap_send})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.activity_amap_connect:
                activity_amap_connectstatus.setText("");
                activity_amap_sendstatus.setText("");
                try {
                    mBinderService.AgainConnect();
                } catch (Exception e) {
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
            /*    try {
                    mBinderService.UpMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                send(false);
                break;
        }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        initMarker();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
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
            activity_amap_connectstatus.setText(CONNECT_STATUS);

            socket = client.getSocket();
            send(false);

        }

        @Override
        public void OnFailure(Exception e) {
            CONNECT_STATUS = "未连接";
            MyToash.Log("OnFailure");

            activity_amap_connectstatus.setText(CONNECT_STATUS);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        IS_OVER = true;
        mMapView.onDestroy();
        send(true);

        //client.closeConnection();
    }

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
                    handler.sendEmptyMessage(0);

                    MyToash.Log("OnSuccess" + SEND_STATUS);
                }

            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activity_amap_sendstatus.setText(SEND_STATUS);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
