package me.lam.maidong.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import me.lam.maidong.R;
import me.lam.maidong.entity.RegEntity;
import me.lam.maidong.selfdefine.PickerView;
import me.lam.maidong.selfdefine.WheelView;

@ContentView(R.layout.activity_weight)
public class WeightActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;

    @ViewInject(R.id.bt_next)
    Button bt_next;

    @ViewInject(R.id.main_wv)
    private PickerView wva;
    @ViewInject(R.id.main_wv)
    private PickerView wva2;

    public static final String TAG = AgeActivity.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100"};
    private static final String[] PLANETS2 = new String[]{".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"};
    RegEntity regEntity;
    String planets1 = "", planets2 = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        x.view().inject(this);
        initialize();

    }


    private void initialize() {
        view_publictitle_title.setText("青少年个人信息");
        regEntity = RegEntity.getInstance();
        regEntity.RegActivityList.add(this);
        regEntity.setWeight("45");

        setData();
    }

    private void setData() {
        wva = (PickerView) this.findViewById(R.id.main_wv);
        wva2 = (PickerView) this.findViewById(R.id.main_wv2);
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < PLANETS.length; i++) {
            data.add(PLANETS[i]);
        }
        List<String> data2 = new ArrayList<String>();
        for (int i = 0; i < PLANETS2.length; i++) {
            data2.add(PLANETS2[i]);
        }
        planets1 = data.get(data.size() / 2);
        planets2 = data2.get(data2.size() / 2);
        regEntity.setWeight(planets1 + planets2);
        wva.setData(data);
        wva.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                planets1 = text;
                regEntity.setWeight(planets1 + planets2);
            }
        });
        wva2.setData(data2);
        wva2.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                planets2 = text;
                regEntity.setWeight(planets1 + planets2);
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
                Intent i = new Intent(this, BandIdActivity.class);
                startActivity(i);
                break;
        }
    }


}
