package me.lam.maidong.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import me.lam.maidong.R;
import me.lam.maidong.activity.AMapActivity;
import me.lam.maidong.activity.LogActivity;
import me.lam.maidong.activity.SelfDetailActivity;
import me.lam.maidong.activity.WebViewActivity;
import me.lam.maidong.entity.SelfDetailCallBack;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.service.SocketStartService;
import me.lam.maidong.utils.FileSizeUtil;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;

/**
 * A placeholder fragment containing a simple view.
 */

@ContentView(R.layout.fragment_self)
public class SelfActivityFragment extends Fragment {
    @ViewInject(R.id.fragment_my_data_cache)
    TextView dataCache;
    @ViewInject(R.id.fragment_my_sex)
    ImageView imgSex;
    @ViewInject(R.id.fragment_my_name)
    TextView tvName;
    @ViewInject(R.id.fragment_my_id)
    TextView tvId;
    String EducationalCode;
    private Activity activity;
    //private int RequestCount;
    //  boolean isend;

    public SelfActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        initialize();
    }
    private void setData() {
        getmyData(activity, new MyUserData() {
            @Override
            public void onResponse(String response) {
                SelfDetailCallBack person = new Gson().fromJson(response, SelfDetailCallBack.class);
                if (person != null) {
                    if (person.getSex() == 1) {
                        imgSex.setImageResource(R.drawable.btn_male_active);
                    } else {
                        imgSex.setImageResource(R.drawable.female_active);
                    }
                    tvName.setText(person.getStudentName());
                }else {
                    MyToash.ToashNoNet(activity);
                    ShareUitls.putString(activity, "mydata", "");
                }
            }

            @Override
            public void onErrorResponse() {
                MyToash.ToashNoNet(getActivity());
            }
        });
    }

    private void initialize() {
        tvId.setText("教育ID:" +  ShareUitls.getString(activity, "EducationCode", ""));
        long appCache = (long) FileSizeUtil.getFileOrFilesSize("/data/data/me.lam.maidong/shared_prefs/", 3);
        long Cache = (long) FileSizeUtil.getFileOrFilesSize("/data/data/me.lam.maidong/cache/", 3);
        dataCache.setText("" + (Cache + appCache) + "MB");
        setData();
    }


    @Event(value = {R.id.fragment_my_checkdetials, R.id.fragment_my_clear, R.id.fragment_my_aboutus, R.id.fragment_my_exit, R.id.fragment_my_location})
    private void getEvent(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.fragment_my_checkdetials:
                intent.setClass(getActivity(), SelfDetailActivity.class);
                getActivity().startActivity(intent); //这里用
                break;
            case R.id.fragment_my_clear:
                deleteAllFiles(new File("/data/data/me.lam.maidong/shared_prefs/"));
                deleteAllFiles(new File("/data/data/me.lam.maidong/cache/"));
                dataCache.setText("0mb");
                break;
            case R.id.fragment_my_aboutus:

                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("title", "关于我们");
                intent.putExtra("URL", "http://www.ssp356.com:8080/contract.html");
                startActivity(intent);
                break;
            case R.id.fragment_my_exit:
                try {
                    activity.stopService(new Intent(activity, SocketStartService.class));
                } catch (Exception e) {
                }
                intent.setClass(activity, LogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("backLog", "backLog");
                intent.putExtras(bundle);
                getActivity().startActivity(intent); //这里用
                getActivity().finish();
                break;
            case R.id.fragment_my_location:
                intent.setClass(getActivity(), AMapActivity.class);
                startActivity(intent);
                break;

        }
    }


    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                   /* deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }*/
                } else {
                    if (f.exists()) { // 判断是否存在
                        if (f.getName().equals("aa.xml.xml")) {
                            f.delete();
                        }
                        if (f.getName().equals("com.android.opengl.shaders_cache")) {
                            f.delete();
                        }


                    }
                }
            }
    }

    public interface MyUserData {
        void onResponse(String response);

        void onErrorResponse();
    }

    public static void getmyData(final  Activity activity,final  MyUserData myUserData) {
        String mydata = ShareUitls.getString(activity, "mydata", "");
        if (mydata.length() == 0) {
            String  EducationalCode = ShareUitls.getString(activity, "EducationCode", "");
            String url = "StudentInfo/?EducationalCode=" + EducationalCode;
            OKHttp.sendRequestRequestParams(activity, "", true, url, new OKHttp.ResponseListener() {
                @Override
                public void onResponse(String response) {
                    ShareUitls.putString(activity, "mydata", response);
                    myUserData.onResponse(response);
                }

                @Override
                public void onErrorResponse() {
                    myUserData.onErrorResponse();
                }
            });
        }else {
            myUserData.onResponse(mydata);
        }
    }
}
