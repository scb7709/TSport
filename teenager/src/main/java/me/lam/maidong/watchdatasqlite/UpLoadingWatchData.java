package me.lam.maidong.watchdatasqlite;

import android.app.Activity;

import org.xutils.http.RequestParams;

import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.Constant;

/**
 * Created by abc on 2017/8/31.
 */
public class UpLoadingWatchData {
    private static volatile UpLoadingWatchData sInstance = null;
    private Activity activity;
    private static String UID, SPID, PlanNameID, Duration;
    private MySQLiteDataDao mySQLiteDataDao;

    private UpLoadingWatchData(Activity activity) {
        this.activity = activity;
        this.mySQLiteDataDao = MySQLiteDataDao.getInstance(activity);
    }

    public static UpLoadingWatchData getInstance(Activity activity) {
        if (sInstance == null) {
            synchronized (MySQLiteDataDao.class) {
                if (sInstance == null) {
                    sInstance = new UpLoadingWatchData(activity);
                }
            }
        }
        return sInstance;
    }


    //腕表数据
    public void uploadingWatchData(final String flag, final String Data,final  int  Starttime) {
        MyToash.Log(flag+"   "+Data);
        if (flag==null||Data == null || Data.length() == 0) {
            return;
        }
        RequestParams params = null;
        switch (flag) {

            case "PostSleepInfoRequest":
                params = new RequestParams(Constant.BASE_URL + "/MdMobileService.ashx?do=PostSleepInfoRequest&version=v2.9.6");
                break;
        }
    }

    public interface GetTemperatureeAndWeathereOrParameterHttp {
        void success(String response);

        // void fail();
    }

}
