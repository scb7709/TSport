package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.lam.maidong.R;
import me.lam.maidong.activity.SelfDetailActivity;

import me.lam.maidong.activity.WebViewActivity;

import me.lam.maidong.entity.SelfDetailCallBack;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.utils.Constant;
import me.lam.maidong.utils.FileSizeUtil;

import me.lam.maidong.activity.LogActivity;
import me.lam.maidong.utils.HttpUtils;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelfActivityFragment extends Fragment {

    @InjectView(R.id.bt_selfDetail)
    Button btSelfDetail;
    @InjectView(R.id.tex)
    TextView tex;
    @InjectView(R.id.imageVi)
    ImageView imageVi;
    @InjectView(R.id.textV)
    TextView textV;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.textVie)
    TextView textVie;
    @InjectView(R.id.bt_clear)
    Button btClear;
    @InjectView(R.id.bt_aboutUs)
    Button btAboutUs;
    @InjectView(R.id.bt_goodSp)
    Button btGoodSp;
    @InjectView(R.id.bt_exit)
    Button btExit;
    @InjectView(R.id.data_cache)
    TextView dataCache;


    SelfDetailCallBack me;
    @InjectView(R.id.img_sex)
    ImageView imgSex;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_id)
    TextView tvId;

    String EduCode;
    boolean isend;

    public SelfActivityFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isend = true;
    }

    @SuppressLint("ValidFragment")
    public SelfActivityFragment(SelfDetailCallBack me) {
        this.me = me;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_self, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EduCode = ShareUitls.getString(getActivity(), "EducationCode", "");
        initialize();
        getmyData();
    }

    private void initialize() {
        tvId.setText("教育ID:" + EduCode);
        long appCache = (long) FileSizeUtil.getFileOrFilesSize("/data/data/me.lam.maidong/shared_prefs/", 3);
        Log.e("cache", appCache + "");
        long Cache = (long) FileSizeUtil.getFileOrFilesSize("/data/data/me.lam.maidong/cache/", 3);
        Log.e("cache", Cache + "");
        dataCache.setText("" + (Cache + appCache) + "MB");


        btSelfDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCurData(EduCode);

                Intent intent = new Intent();
                intent.setClass(getActivity(), SelfDetailActivity.class);
                getActivity().startActivity(intent); //这里用

            }
        });
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllFiles(new File("/data/data/me.lam.maidong/shared_prefs/"));
                deleteAllFiles(new File("/data/data/me.lam.maidong/cache/"));
                dataCache.setText("0mb");
            }
        });
        btAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra("title","关于我们");
                intent.putExtra("URL","http://www.ssp356.com:8080/contract.html");
                startActivity(intent);
            }
        });
        btGoodSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   ShareUitls.putString(getActivity(), "phone", "");
               ShareUitls.putString(getActivity(), "pwd","");*/


                Intent intent = new Intent();
                intent.setClass(getActivity(), LogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("backLog", "backLog");
                intent.putExtras(bundle);
                getActivity().startActivity(intent); //这里用
                getActivity().finish();
            }
        });
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


    SelfDetailCallBack dataRes;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void getmyData() {

        String url = "StudentInfo/?EducationalCode=" + EduCode;
        OKHttp.sendRequestRequestParams(getActivity(), "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                if(!isend) {
                    SelfDetailCallBack person = new Gson().fromJson(response, SelfDetailCallBack.class);
                    if (person.getSex() == 1) {
                        imgSex.setImageResource(R.drawable.btn_male_active);
                        tvName.setText(person.getStudentName());
                    } else {
                        imgSex.setImageResource(R.drawable.female_active);
                    }
                }

            }

            @Override
            public void onErrorResponse() {

                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();


            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SelfDetailCallBack person = new Gson().fromJson((String) msg.obj, SelfDetailCallBack.class);
            if (person.getSex() == 1) {
                imgSex.setImageResource(R.drawable.btn_male_active);
                tvName.setText(person.getStudentName());
            } else {
                imgSex.setImageResource(R.drawable.female_active);
            }
        }
    };
}
