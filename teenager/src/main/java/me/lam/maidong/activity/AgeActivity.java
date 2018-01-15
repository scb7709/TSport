package me.lam.maidong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.lam.maidong.R;
import me.lam.maidong.entity.RegEntity;
import me.lam.maidong.selfdefine.PickerView;

@ContentView(R.layout.activity_age)
public class AgeActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;

    @ViewInject(R.id.bt_next)
    Button bt_next;

    @ViewInject(R.id.main_wv)
    private PickerView wva;

    static String age;
    public static final String TAG = AgeActivity.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"6","7","8","9","10", "11", "12", "13", "14", "15", "16", "17", "18"};

    RegEntity regEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initialize();

    }
//

    private void initialize() {
        view_publictitle_title.setText("青少年个人信息");
        regEntity = RegEntity.getInstance();
        RegEntity.RegActivityList.add(this);
        regEntity.setAge("12");

        List<String> data = new ArrayList<String>();
        for (int i = 0; i < PLANETS.length; i++) {
            data.add(PLANETS[i]);
        }
        regEntity.setAge(data.get(data.size() / 2));//默认选择集合的中间个
        wva.setData(data);
        wva.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                regEntity.setAge(text);
            }
        });
    }

    @Event(value = {R.id.view_publictitle_back, R.id.bt_next})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.view_publictitle_back:
                finish();
                break;
            case R.id.bt_next:
                Intent i = new Intent(this, HighActivity.class);
                startActivity(i);
                break;
        }
    }

}
