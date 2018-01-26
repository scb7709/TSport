package me.lam.maidong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.entity.SelfDetailCallBack;
import me.lam.maidong.fragment.SelfActivityFragment;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.ShareUitls;

@ContentView(R.layout.activity_self_message)
public class SelfDetailActivity extends BaseActivity {

    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;
    @ViewInject(R.id.data_name)
    TextView dataName;
    @ViewInject(R.id.data_sex)
    TextView dataSex;
    @ViewInject(R.id.data_age)
    TextView dataAge;
    @ViewInject(R.id.data_high)
    TextView dataHigh;
    @ViewInject(R.id.data_weight)
    TextView dataWeight;
    SelfDetailCallBack dataRes;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        activity=this;
        initialize();

    }


    private void initialize() {
        view_publictitle_title.setText("青少年个人信息");
        getData();
    }

    @Event(value = {R.id.view_publictitle_back})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                finish();
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    public void getData() {
        SelfActivityFragment.getmyData(activity, new SelfActivityFragment.MyUserData() {
            @Override
            public void onResponse(String response) {
                dataRes = new Gson().fromJson(response, SelfDetailCallBack.class);
                if (dataRes != null) {
                    dataName.setText(dataRes.getStudentName() + "");
                    if (dataRes.getSex() == 1) {
                        dataSex.setText("男");
                    } else {
                        dataSex.setText("女");
                    }
                    dataAge.setText(dataRes.getAge() + "岁");
                    dataHigh.setText(dataRes.getHeight() + "cm");
                    dataWeight.setText(dataRes.getWeight() + "kg");
                }else {
                    MyToash.ToashNoNet(SelfDetailActivity.this);
                    ShareUitls.putString(SelfDetailActivity.this, "mydata", "");
                }
            }

            @Override
            public void onErrorResponse() {
                MyToash.ToashNoNet(SelfDetailActivity.this);
            }
        });
    }


}





