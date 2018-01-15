package me.lam.maidong.activity;

/**
 * Created by 1 on 2015/11/13.
 */


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.entity.RegEntity;
import me.lam.maidong.myview.MyToash;

@ContentView(R.layout.activity_sex)
public class SexActivity extends BaseActivity {
    @ViewInject(R.id.img_female)
    Button imgFeMale;
    @ViewInject(R.id.bt_next)
    Button bt_next;
    @ViewInject(R.id.img_male)
    Button imgMale;


    RegEntity regEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        x.view().inject(this);

        initialize();

    }

    private void initialize() {
        regEntity =RegEntity.getInstance();
        RegEntity.RegActivityList.add(this);

    }
    long temptime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
    {
        // TODO Auto-generated method stub

        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            if (System.currentTimeMillis() - temptime > 2000) // 2s内再次选择back键有效
            {
                System.out.println(Toast.LENGTH_LONG);
                MyToash.Toash(SexActivity.this,"请再按一次返回退出");

                temptime = System.currentTimeMillis();
            } else {
                finish();
                 //凡是非零都表示异常退出!0表示正常退出!
            }

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    Boolean CHOOSEWHO=false;//false 男  true女

    @Event(value = {R.id.img_female,R.id.img_male, R.id.bt_next})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.img_female:
                CHOOSEWHO=true;
                imgFeMale.setBackground(getResources().getDrawable(R.drawable.female_active));
                imgMale.setBackground(getResources().getDrawable(R.drawable.btn_male_negative));
                break;
            case R.id.img_male:
                CHOOSEWHO=false;
                imgMale.setBackground(getResources().getDrawable(R.drawable.btn_male_active));
                imgFeMale.setBackground(getResources().getDrawable(R.drawable.female_negative));

                break;
            case R.id.bt_next:
                if(CHOOSEWHO){
                    regEntity.setSex("1");
                }else{
                    regEntity.setSex("0");
                }// Toast.makeText(SexActivity.this,"请选择一种性别",Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,AgeActivity.class);
                startActivity(i);
                break;
        }
    }


}
