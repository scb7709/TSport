package me.lam.maidong.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by scb on 2016/2/16.
 */
public class InternetUtils {
    // / 没有连接
    public static final int NETWORN_NONE = 0;
    // / wifi连接
    public static final int NETWORN_WIFI = 1;
    // / 手机网络数据连接
    public static final int NETWORN_2G = 2;
    public static final int NETWORN_3G = 3;
    public static final int NETWORN_4G = 4;
    public static final int NETWORN_MOBILE = 5;

    public static boolean internet(final Activity context) {
        //检查当前网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return true;
        } else {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("当前无网络连接，是否前去设置网络？")//设置显示的内容
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
// 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(Settings.ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                            }
                            context.startActivityForResult(intent, 100);


                        }

                    }).show();


        }

        return false;

    }

    /*public static boolean internet2(final Activity context) {
        //检查当前网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            //上传上次力量训练上传失败的数据
            if (!ShareUitls.getString(context, "UUID", "").equals("") && ShareUitls.getString(context, "UUID", "").equals(ShareUitls.getString(context, "UID", ""))) {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("VID", ShareUitls.getString(context, "VID", ""));
                map.put("UID", ShareUitls.getString(context, "UID", ""));
                map.put("SportCal", ShareUitls.getString(context, "SportCal", ""));
                map.put("SportDuration", ShareUitls.getString(context, "SportDuration", ""));
                map.put("SportDate", ShareUitls.getString(context, "SportDate", ""));
                VolleyHttpUtils.getInstance(context).sendRequest(Constant.Request_Method_POST, Constant.BASE_URL + "/MdMobileService.ashx?do=PostPowertrainRequest", map, 0, new VolleyHttpUtils.ResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("rrrrrrrrrrrrr", response.toString());
                                ShareUitls.cleanString(context);
                                Toast.makeText(context, "数据已更新，请重新登录", Toast.LENGTH_LONG).show();
                                context.startActivity(new Intent(context, Login.class));
                                context.finish();
                            }

                            @Override
                            public void onErrorResponse(VolleyError error, int callBackNumber) {
                                Log.i("AAAAAAAAA", "LoginupToken" + callBackNumber);
                                if (callBackNumber == 5) {
                                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                );
            }
            if (!ShareUitls.getString(context, "UUUID", "null").equals("null") && ShareUitls.getString(context, "UUUID", "null").equals(ShareUitls.getString(context, "UID", "null"))) {

                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("UID", ShareUitls.getString(context, "UID", "null"));
                map.put("Data", ShareUitls.getString(context, "Data", "null"));
                map.put("WatchType", ShareUitls.getString(context, "WatchType", "null"));
                map.put("EveryTime", ShareUitls.getString(context, "EveryTime", "null"));
                map.put("EveryVolidTime", ShareUitls.getString(context, "EveryVolidTime", "null"));

                VolleyHttpUtils.getInstance(context).sendRequest(Constant.Request_Method_POST, Constant.BASE_URL + "/MdMobileService.ashx?do=PostSportDataRequest", map, 0, new VolleyHttpUtils.ResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("mmmm", response.toString());
                                ShareUitls.cleanString2(context);
                                Toast.makeText(context, "数据已更新，请重新登录", Toast.LENGTH_LONG).show();
                                context.startActivity(new Intent(context, Login.class));
                                context.finish();
                            }

                            @Override
                            public void onErrorResponse(VolleyError error, int callBackNumber) {
                                Log.i("AAAAAAAAA", "LoginupToken" + callBackNumber);
                                if (callBackNumber == 5) {
                                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                );


            }
            return true;

        }

        else {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("当前无网络连接，是否前去设置网络？")//设置显示的内容
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
// 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(Settings.ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                            }
                            context.startActivityForResult(intent, 100);


                        }

                    }).show();

            return false;
        }
    }*/

    public static boolean internett(final Context context) {
        //检查当前网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return true;
        } else {


            return false;
        }

    }

    public static boolean internetNoWifi(final Context context) {
        //检查当前网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return true;


        }

        else {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("当前无网络连接，是否前去设置网络？")//设置显示的内容
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
// 跳转到系统的网络设置界面
                            Intent intent = null;
                            // 先判断当前系统版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
                                intent = new Intent(Settings.ACTION_SETTINGS);
                            } else {
                                intent = new Intent();
                                intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                            }
                            context.startActivity(intent);


                        }

                    }).show();
            return false;
        }



    }

    public static int getNetworkState(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context

                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == connManager)

            return NETWORN_NONE;

        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();

        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {

            return NETWORN_NONE;

        }

        // Wifi

        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (null != wifiInfo) {

            NetworkInfo.State state = wifiInfo.getState();

            if (null != state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    return NETWORN_WIFI;

                }

        }

        // 网络

        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {

            NetworkInfo.State state = networkInfo.getState();

            String strSubTypeName = networkInfo.getSubtypeName();

            if (null != state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    switch (activeNetInfo.getSubtype()) {

                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g

                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g

                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g

                        case TelephonyManager.NETWORK_TYPE_1xRTT:

                        case TelephonyManager.NETWORK_TYPE_IDEN:

                            return NETWORN_2G;

                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g

                        case TelephonyManager.NETWORK_TYPE_UMTS:

                        case TelephonyManager.NETWORK_TYPE_EVDO_0:

                        case TelephonyManager.NETWORK_TYPE_HSDPA:

                        case TelephonyManager.NETWORK_TYPE_HSUPA:

                        case TelephonyManager.NETWORK_TYPE_HSPA:

                        case TelephonyManager.NETWORK_TYPE_EVDO_B:

                        case TelephonyManager.NETWORK_TYPE_EHRPD:

                        case TelephonyManager.NETWORK_TYPE_HSPAP:

                            return NETWORN_3G;

                        case TelephonyManager.NETWORK_TYPE_LTE:

                            return NETWORN_4G;

                        default://有机型返回16,17

                            //中国移动 联通 电信 三种3G制式

                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {

                                return NETWORN_3G;

                            } else {

                                return NETWORN_MOBILE;

                            }

                    }

                }

        }

        return NETWORN_NONE;

    }

    private void upDate(final String UID, final String Data, final String WatchType, final String EveryTime, final String EveryVolidTime) {


    }
}