package me.lam.maidong.utils;

import android.content.Context;
import android.content.SharedPreferences;

import me.lam.maidong.entity.VersionClass;

/**
 * Created by Administrator on 2015/8/18.
 */
public class ShareUitls {

    public static void  putString(Context c,String key,String msg){
        SharedPreferences sp = c.getSharedPreferences("aa.xml",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(key,msg);
        e.commit();
    }
    public static  String getString(Context c,String key,String d){
        SharedPreferences sp = c.getSharedPreferences("aa.xml",Context.MODE_PRIVATE);
        return sp.getString(key,d);
    }
    public static void putVersion(Context c, VersionClass version) {
        SharedPreferences sp = c.getSharedPreferences("version.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        if (version != null) {
            e.putString("VersionName", version.VersionName);
            e.putInt("VersionCode", version.VersionCode);
            e.putString("Description", version.Description);
            e.putString("DownloadUrl", version.DownloadUrl);
        }
        e.commit();
    }

    public static VersionClass getVersion(Context c) {
        SharedPreferences sp = c.getSharedPreferences("version.xml", Context.MODE_PRIVATE);
        int VersionCode = sp.getInt("VersionCode", 0);
        if (VersionCode != 0) {
            VersionClass version = new VersionClass();
            version.VersionCode = VersionCode;
            version.VersionName = sp.getString("VersionName", "");
            version.Description = sp.getString("Description", "");
            version.DownloadUrl = sp.getString("DownloadUrl", "");
            return version;
        }
        return null;
    }


}
