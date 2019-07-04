package com.example.biyesdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biyesdemo.helper.MyDBHelper;

import java.util.Date;


/**
 * 修改
 */
public class AltarActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_id, edt_name, edt_phone, edt_mvname, edt_price, edt_number;
    private TextView tv_total;
    private TextView tv_title;
    private TextView tv_click;
    private TextView tv_time;

    private ImageView image_img;
    private MyDBHelper myDBbase;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altar);
        myDBbase = new MyDBHelper(this, "UserStore.db", null, 2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        initView();
        getData(name);
        edt_number.addTextChangedListener(textWatcher);
        edt_price.addTextChangedListener(textWatcher);
    }

    //监听单价和数量，计算和
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

    /**
     * 通过name获取数据库中name==name的字段，并把它取出来
     *
     * @param name
     */
    private void getData(String name) {
        SQLiteDatabase db = myDBbase.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Data where username = '" + name + "'", null);
        //Cursor cursor = db.query("Book", new String[]{"id", "name", "phone", "mvname", "price", "number", "total"}, "name=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {
            edt_name.setText(cursor.getString(cursor.getColumnIndex("username")));
            edt_phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            edt_mvname.setText(cursor.getString(cursor.getColumnIndex("name")));
            edt_price.setText(cursor.getString(cursor.getColumnIndex("price")));
            edt_number.setText(cursor.getString(cursor.getColumnIndex("number")));
            tv_total.setText(cursor.getString(cursor.getColumnIndex("total")));
            tv_time.setText(cursor.getString(cursor.getColumnIndex("time")));
        }
        cursor.close();
    }

    /**
     * 初始化
     */
    private void initView() {
//        edt_id = findViewById(R.id.edt_id);
        edt_name = findViewById(R.id.edt_username);
        edt_phone = findViewById(R.id.edt_phone);
        edt_mvname = findViewById(R.id.edt_mvname);
        edt_price = findViewById(R.id.edt_price);
        edt_number = findViewById(R.id.edt_number);
        tv_total = findViewById(R.id.tv_total);
        tv_title = findViewById(R.id.tv_title);
        image_img = findViewById(R.id.back_img);
        tv_click = findViewById(R.id.tv_click);
        tv_click.setOnClickListener(this);
        image_img.setOnClickListener(this);
        tv_time = findViewById(R.id.tv_time);
        tv_title.setText("数据修改");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tv_click:
                initData();
                break;
        }
    }

    /**
     * 将修改后的值通过name查询到数据并修改
     */
    private void initData() {
        String name = edt_name.getText().toString();
        String phone = edt_phone.getText().toString();
        String mvname = edt_mvname.getText().toString();
        String price = edt_price.getText().toString();
        String number = edt_number.getText().toString();
        String total = tv_total.getText().toString();
        String time = tv_time.getText().toString();

        if (TextUtils.isEmpty(mvname) && TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(price) && TextUtils.isEmpty(number) && TextUtils.isEmpty(total)) {
            Toast.makeText(AltarActivity.this, "请输入完整", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = myDBbase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("phone", phone);
        values.put("name", mvname);
        values.put("number", number);
        values.put("price", price);
        values.put("total", total);
        values.put("time", time);
        db.update("Data", values, "username=?", new String[]{name});
        Intent intent = new Intent(AltarActivity.this, DataActivity.class);
        startActivity(intent);
        finish();
    }

}
