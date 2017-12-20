package me.lam.maidong.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;



import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Date;

import me.lam.maidong.R;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.VersonUtils;

/**

 */
public class UpdateService extends Service {

    private String apkUrl;
    private String filePath;
    private NotificationManager notificationManager;
    private Notification notification;
    private HttpManager httpManager;
    private RemoteViews views;
    public boolean DownLoading;
    private File file;
    public static boolean DOWNLOADING;//zhengzai xiaz
    @Override
    public void onCreate() {

        if (!DOWNLOADING) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //  filePath = Environment.getExternalStorageDirectory()+"/AppUpdate/czhappy.apk";
            File tempfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/maidong/apk/" + new Date().getTime() + "Version");
            if (!tempfile.exists()) {
                tempfile.mkdirs();
            }
            filePath = tempfile.getPath().toString();
        } else {
            MyToash.Toash(UpdateService.this, "新版本正在下载");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("tag", "UpdateService onStartCommand()");
        if (!DOWNLOADING) {
            if (intent == null) {
                notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed), 0);

                stopSelf();
                return 0;
            }
            apkUrl = intent.getStringExtra("apkUrl");
            notifyUser(getString(R.string.update_download_start), getString(R.string.update_download_start), 0);
            startDownload();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        DownLoading = true;
        RequestParams requestParams = new RequestParams(apkUrl);
        requestParams.setSaveFilePath(filePath);
        httpManager = x.http();
        httpManager.get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int percent = (int) (current * 100 / total);
                notifyUser(getString(R.string.update_download_progressing), getString(R.string.update_download_progressing), percent);
            }

            @Override
            public void onSuccess(File result) {
                VersonUtils.installApk(result, getApplicationContext());// 安装apk
                file = result;
                //notifyUser(getString(R.string.update_download_finish), getString(R.string.update_download_finish), 100);
                // stopSelf();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed), 0);
                stopSelf();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed), 0);
                stopSelf();
            }

            @Override
            public void onFinished() {
                Log.e("tag", "onFinished()");

            }
        });
    }

    /**
     * 更新notification
     *
     * @param result
     * @param msg
     * @param progress
     */
    private void notifyUser(String result, String msg, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo_big)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_big))
                .setContentTitle("迈动健康版本更新");
        if (progress > 0 && progress <= 100) {

            builder.setProgress(100, progress, false);

        } else {
            builder.setProgress(0, 0, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(result);
        builder.setContentIntent(progress >= 100 ? getContentIntent() :
                PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        notification = builder.build();
        notificationManager.notify(0, notification);


    }

    @Override
    public void onDestroy() {

        DownLoading = false;
    }



    /**
     * 进入apk安装程序
     *
     * @return
     */
    private PendingIntent getContentIntent() {
       /* Log.e("tag", "getContentIntent()");
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()), "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startActivity(intent);*/
        return PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
