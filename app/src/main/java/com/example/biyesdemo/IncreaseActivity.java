package com.example.biyesdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biyesdemo.helper.MyDBHelper;

import java.util.Date;

/**
 * 添加
 */
public class IncreaseActivity extends AppCompatActivity implements View.OnClickListener {
    private MyDBHelper myDBHelper;
    private EditText edt_uername, edt_phone, edt_name, edt_number, edt_price;
    private TextView tv_total, tv_click, tv_title;
    private ImageView back_img;
    private TextView tv_time;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase);
        myDBHelper = new MyDBHelper(this, "UserStore.db", null, 2);
        initView();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int as = 0;
                int ab = 0;
                if (TextUtils.isEmpty(edt_price.getText().toString())) {
                    as = 0;
                    tv_total.setText(0 + "");
                } else if (TextUtils.isEmpty(edt_number.getText().toString())) {
                    ab = 0;
                    tv_total.setText(0 + "");
                } else {
                    as = Integer.parseInt(edt_price.getText().toString());
                    ab = Integer.parseInt(edt_number.getText().toString());
                    if (as == 0 || ab == 0) {
                        tv_total.setText("0");
                    } else {
                        int ad = as * ab;
                        tv_total.setText(ad + " ");
                    }
                }
            }
        };
        edt_number.addTextChangedListener(textWatcher);
        edt_price.addTextChangedListener(textWatcher);
    }

    private void initView() {
        edt_uername = findViewById(R.id.edt_uername);
        edt_phone = findViewById(R.id.edt_phone);
        edt_name = findViewById(R.id.edt_name);
        edt_number = findViewById(R.id.edt_number);
        edt_price = findViewById(R.id.edt_price);
        tv_total = findViewById(R.id.tv_total);
        tv_click = findViewById(R.id.tv_click);
        tv_title = findViewById(R.id.tv_title);
        back_img = findViewById(R.id.back_img);
        tv_click.setOnClickListener(this);
        back_img.setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_title.setText("添加");
        date = new Date();
        String ad = date.toLocaleString();
        tv_time.setText(ad);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_click:
                getData();
                break;
            case R.id.back_img:
                finish();
                break;
        }

    }

    private void getData() {
        String username = edt_uername.getText().toString();
        String phone = edt_phone.getText().toString();
        String name = edt_name.getText().toString();
        String number = edt_number.getText().toString();
        String price = edt_price.getText().toString();
        String total = tv_total.getText().toString();
        String time = tv_time.getText().toString();
        Log.d("name", username);
        if (TextUtils.isEmpty(time) && TextUtils.isEmpty(username) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(name) && TextUtils.isEmpty(number) && TextUtils.isEmpty(price) && TextUtils.isEmpty(total)) {
            Toast.makeText(IncreaseActivity.this, "请输入完整", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("phone", phone);
        values.put("name", name);
        values.put("number", number);
        values.put("price", price);
        values.put("total", total);
        values.put("time", time);
        db.insert("Data", null, values);
        Intent intent = new Intent(IncreaseActivity.this, DataActivity.class);
        startActivity(intent);
        values.clear();
    }

}
