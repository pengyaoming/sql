package com.example.biyesdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biyesdemo.helper.MyDBHelper;

/**
 * 登录
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_title;
    private ImageView back_img;
    private EditText edt_phone, edt_paswod;
    private Button btn_login, btn_query;

    private MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBHelper = new MyDBHelper(this, "UserStore.db", null, 2);
        initView();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        back_img = findViewById(R.id.back_img);
        edt_phone = findViewById(R.id.edt_phone);
        edt_paswod = findViewById(R.id.edt_paswod);
        btn_login = findViewById(R.id.btn_login);
        btn_query = findViewById(R.id.btn_query);
        btn_login.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        back_img.setOnClickListener(this);
        back_img.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.back_img:
                finish();
                break;
            case R.id.btn_query:
                initData();
                break;
            default:
                break;
        }

    }

    private void initData() {
        String userName = edt_phone.getText().toString();
        String passWord = edt_paswod.getText().toString();
        if (login(userName, passWord)) {
            Toast.makeText(MainActivity.this, "登陆成功（ZY，111）", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DataActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean login(String userName, String passWord) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        String sql = "select * from userData where nametext=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName, passWord});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;

    }
}

