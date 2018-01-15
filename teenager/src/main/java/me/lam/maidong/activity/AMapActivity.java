package me.lam.maidong.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
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

import java.net.Socket;

import me.lam.maidong.R;
import me.lam.maidong.SuperSocket.ICoallBack;
import me.lam.maidong.SuperSocket.IPList;
import me.lam.maidong.SuperSocket.ISocketPacket;
import me.lam.maidong.SuperSocket.SocketClient;
import me.lam.maidong.entity.Location;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.ShareUitls;

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

    private AMap aMap;
    private Activity activity;
    SocketClient client;
    Gson gson;
   String   EducationCode;
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
        EducationCode = ShareUitls.getString(activity, "EducationCode", "");
        view_publictitle_title.setText("孩子的位置");
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图

        MapsInitializer.loadWorldGridMap(true);
        initSuperSocket();
    }

    private void initSuperSocket() {
        client = new SocketClient(IPList._ip, IPList._Port, 6,iCoallBack);
        client.Connection();//连接服务器
//设置接收消息监听
        client.setOnReceiveListener(new ISocketPacket() {
            @Override
            public void SocketPacket(String msg) {
                Location locatio = gson.fromJson(msg, Location.class);
                activity_amap_receivecomment.setText("服务器返回的数据：\n" + msg);
                MyToash.Log("服务器返回msg" + msg);
                //MyToash.Toash(activity, "" + msg);
                // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });


    }
    ICoallBack iCoallBack=new ICoallBack() {
        @Override
        public void OnSuccess(Socket client1) {

            MyToash.Log("已连接");
            MyToash.Toash(activity, "已连接");
            boolean issend= client.SenddData(EducationCode);
            if(issend){
                MyToash.Log("发送成功");
            }else {
                MyToash.Log("发送失败");
                client.closeConnection();
            }
        }

        @Override
        public void OnFailure(Exception e) {

        }
    };
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
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        client.closeConnection();
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

    @Event(value = {R.id.view_publictitle_back, R.id.activity_amap_connect, R.id.activity_amap_close, R.id.activity_amap_send})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                finish();
                break;
            case R.id.activity_amap_connect:
                activity_amap_receivecomment.setText("");
                initSuperSocket();//连接服务器
                break;
            case R.id.activity_amap_close:
                activity_amap_receivecomment.setText("");
                client.closeConnection();
                break;

            case R.id.activity_amap_send:
                boolean issend= client.SenddData(EducationCode);
                if(issend){
                    MyToash.Log("发送成功");
                }else {
                    MyToash.Log("发送失败");
                    client.closeConnection();
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
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.icon_me_active)));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        aMap.addMarker(markerOption);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //markerOption.set;
    }

}
