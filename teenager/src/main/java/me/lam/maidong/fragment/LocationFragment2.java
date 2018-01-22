package me.lam.maidong.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import me.lam.maidong.R;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.service.SocketService;
import me.lam.maidong.service.SocketStartService;
import me.lam.maidong.utils.ShareUitls;

@ContentView(R.layout.activity_amap)
public class LocationFragment2 extends Fragment {
    @ViewInject(R.id.activity_map)
    MapView mMapView;
    @ViewInject(R.id.activity_amap_receivecomment)
    TextView activity_amap_receivecomment;

    @ViewInject(R.id.activity_amap_connectstatus)
    Button activity_amap_connectstatus;
    @ViewInject(R.id.activity_amap_send)
    Button activity_amap_sendstatus;
    private AMap aMap;
    Gson gson;
    boolean IS_OVER;
    public SocketService.ServiceBinder mBinderService;
    private Activity activity;
    BitmapDescriptor bitmapDescriptor;
    CameraUpdate cameraUpdate;
    public LocationFragment2() {
    }

    private BroadcastReceiver ServiceToActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String DATA = intent.getStringExtra("DATA");
                switch (intent.getStringExtra("FLAG")) {
                    case "SERIVCE_MSG":
                        if (DATA != null && DATA.length() > 0 && DATA.contains("_")) {
                            String[] str = DATA.split("_");
                           try {
                               initMarker(Double.parseDouble(str[0]), Double.parseDouble(str[1]));
                               activity_amap_receivecomment.setText("服务器返回的数据：\n" + Double.parseDouble(str[0])+"   "+Double.parseDouble(str[1]));
                           }catch (Exception e){}
                        }
                        break;
                    case "CONNECY_STATUS":
                        activity_amap_connectstatus.setText(DATA);
                        // socket=mBinderService.getSokket();
                        break;
                    case "SEND_STATUS":
                        activity_amap_sendstatus.setText(DATA);
                        break;

                    case "NONET":
                        MyToash.Toash(activity, "请检查网路连接");
                        break;

                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return org.xutils.x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
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
       // bitmapDescriptor=BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_me_active));
        cameraUpdate=CameraUpdateFactory.zoomTo(18);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        MapsInitializer.loadWorldGridMap(true);
        registServiceToActivityReceiver();
        activity.startService(new Intent(activity, SocketStartService.class));
    }

    private void registServiceToActivityReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("ServiceToActivityReceiver");
        activity.registerReceiver(ServiceToActivityReceiver, filter);
    }

    @Event(value = {R.id.activity_amap_connect, R.id.activity_amap_connectstatus, R.id.activity_amap_send})
    private void getEvent(View view) {
        switch (view.getId()) {
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
        String DATA = ShareUitls.getString(activity, "location", "");
    // initMarker(39.9329110, 116.444177);
        if (DATA != null && DATA.length() > 0 && DATA.contains("_")) {
            String[] str = DATA.split("_");
            try {
                initMarker(Double.parseDouble(str[0]), Double.parseDouble(str[1]));
            }catch (Exception e){}
        }else {
           // initMarker(39.9329110, 116.444177);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    private void initMarker(double latitude, double longitude) {
        aMap.clear();//39.9329110, 116.444177
        MarkerOptions markerOption = new MarkerOptions();
        LatLng latLng = new LatLng(latitude, longitude);
        markerOption.position(latLng);//38°39′6.48″ 东经E104°04′35.11
        markerOption.title("孩子的位置").snippet("东经"+latitude+"北纬"+ longitude);
        markerOption.draggable(false);//设置Marker可拖动 bitmapDescriptor  BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.fragment_location_man))
      //  markerOption.icon(bitmapDescriptor);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(cameraUpdate);
        aMap.addMarker(markerOption);
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        //markerOption.set;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        IS_OVER = true;
        mMapView.onDestroy();
        activity.unregisterReceiver(ServiceToActivityReceiver);
        //activity.unbindService(mConnection);
    }

}
