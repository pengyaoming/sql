package com.example.biyesdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biyesdemo.helper.MyDBHelper;

/**
 * 注册
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_phone, edt_paswod, edt_email;
    private Button btn_click;
    private TextView tv_title;
    private ImageView back_img;
    private MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDBHelper = new MyDBHelper(this, "UserStore.db", null, 2);
        initView();
    }

    //初始化数据
    private void initView() {
        edt_phone = findViewById(R.id.edt_phone);
        edt_paswod = findViewById(R.id.edt_paswod);
        edt_email = findViewById(R.id.edt_email);
        btn_click = findViewById(R.id.btn_click);
        tv_title = findViewById(R.id.tv_title);
        back_img = findViewById(R.id.back_img);
        btn_click.setOnClickListener(this);
        back_img.setOnClickListener(this);
        tv_title.setText("注册");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_click:
                initData();
                break;
            case R.id.back_img:
                finish();
                break;
            default:
                break;
        }

    }

    private void initData() {
        String newname = edt_phone.getText().toString();
        String password = edt_paswod.getText().toString();
        if (CheckIsDataAlreadyInDBorNot(newname)) {
            Toast.makeText(this, "该用户名已被注册，注册失败", Toast.LENGTH_SHORT).show();
        } else {

            if (register(newname, password)) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean CheckIsDataAlreadyInDBorNot(String newname) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        String Query = "Select * from userData where nametext =?";
        Cursor cursor = db.rawQuery(Query, new String[]{newname});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    //向数据库插入数据
    public boolean register(String username, String password) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nametext", username);
        values.put("password", password);
        db.insert("userData", null, values);
        db.close();
        return true;
    }
}

