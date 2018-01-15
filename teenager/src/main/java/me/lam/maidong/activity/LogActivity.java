package me.lam.maidong.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import me.lam.maidong.R;
import me.lam.maidong.myview.ClearEditText;
import me.lam.maidong.myview.VisibleEditText;
import me.lam.maidong.utils.ShareUitls;

@ContentView(R.layout.activity_log)
public class LogActivity extends OriginalActivity {
    @ViewInject(R.id.bt_log_log)
    Button btLogLog;
    @ViewInject(R.id.bt_log_reg)
    Button btLogReg;
    @ViewInject(R.id.first)
    TextView first;
    @ViewInject(R.id.imageView)
    ImageView imageView;
    @ViewInject(R.id.et_phone)
    ClearEditText etPhone;
    @ViewInject(R.id.et_pwd)
    VisibleEditText etPwd;
    Boolean miandenglu = false;
    Boolean firstLog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

    }

    private void initialize() {
        x.view().inject(this);
        String phone = ShareUitls.getString(getApplicationContext(), "phone", "null");
        String pwd = ShareUitls.getString(getApplicationContext(), "pwd", "null");
        if (!phone.equals("null")) {
            etPhone.setText(phone);
        }
        if (!pwd.equals("null")) {
            etPwd.setText(pwd);
        }

    }


    @Event(value = {R.id.bt_log_log, R.id.bt_log_reg})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.bt_log_log:
                Login();
                break;
            case R.id.bt_log_reg:
                Intent i = new Intent(this, RegActivity.class);
                startActivity(i);
                break;
        }
    }
    private void Login() {
        Boolean phoneWeak = false;
        String phone = etPhone.getText().toString();
        String pwd = etPwd.getText().toString();
        if (phone.length() <= 10 || pwd.length() <= 5) {
            phoneWeak = true;
            Toast.makeText(LogActivity.this, "手机号或密码位数不足", Toast.LENGTH_SHORT).show();
        } else {
            phoneWeak = false;
        }
        if (phone.length() > 11) {
            Toast.makeText(LogActivity.this, "手机号必须为11位", Toast.LENGTH_SHORT).show();
            phoneWeak = true;
        } else if (phone.length() == 11) {
            phoneWeak = false;
        }
        if (!phoneWeak) {
         //   Log.i("LogActivityLogin", phoneWeak + "");
            HomeActivity.Login(LogActivity.this, phone, pwd, "LogActivity");
        }

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
                Toast.makeText(this, "请再按一次返回退出", Toast.LENGTH_SHORT).show();
                temptime = System.currentTimeMillis();
            } else {
                finish();
                //凡是非零都表示异常退出!0表示正常退出!
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
