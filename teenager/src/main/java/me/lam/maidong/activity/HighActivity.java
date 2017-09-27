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

@ContentView(R.layout.activity_high)
public class HighActivity extends BaseActivity {
    @ViewInject(R.id.view_publictitle_title)
    private TextView view_publictitle_title;
    @ViewInject(R.id.view_publictitle_back)
    private RelativeLayout view_publictitle_back;

    @ViewInject(R.id.bt_next)
    Button bt_next;

    @ViewInject(R.id.main_wv)
    private PickerView wva;
    public static final String TAG = AgeActivity.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160","161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180","181","182","183","184","185","186","187","188","189","190"};

    RegEntity regEntity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    x.view().inject(this);
    initialize();

}


    private void initialize() {
        view_publictitle_title.setText("青少年个人信息");
        regEntity =RegEntity.getInstance();
        regEntity.RegActivityList.add(this);
        regEntity.setHeight("145");
        List<String> data = new ArrayList<String>();
        for(int i=0;i<PLANETS.length;i++){
            data.add(PLANETS[i]);

        }
        regEntity.setHeight(data.get(data.size()/2));//默认选择集合的中间个
        wva.setData(data);
        wva.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                regEntity.setHeight(text);
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
                Intent i = new Intent(this, WeightActivity.class);
                startActivity(i);
                break;
        }
    }



}
