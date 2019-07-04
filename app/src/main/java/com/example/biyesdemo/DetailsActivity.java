package com.example.biyesdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biyesdemo.helper.MyDBHelper;

import java.util.Date;

/**
 * 详情
 */
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private MyDBHelper myDBHelper;
    private TextView tv_id, tv_name, tv_phone, tv_mvname, tv_price, tv_number, tv_total, tv_title, tv_click;
    private ImageView image_img;
    private Date date;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myDBHelper = new MyDBHelper(this, "UserStore.db", null, 2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        initView();
        getData(name);
    }

    private void initView() {
//        tv_id = findViewById(R.id.tv_id);
        tv_name = findViewById(R.id.tv_username);
        tv_phone = findViewById(R.id.tv_phone);
        tv_mvname = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        tv_number = findViewById(R.id.tv_number);
        tv_total = findViewById(R.id.tv_total);
        tv_title = findViewById(R.id.tv_title);
        tv_click = findViewById(R.id.tv_click);
        tv_time = findViewById(R.id.tv_time);
        image_img = findViewById(R.id.back_img);
        image_img.setOnClickListener(this);
        tv_title.setText("详情");
        tv_click.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tv_click:
                Intent intent = new Intent(DetailsActivity.this, AltarActivity.class);
                String name = tv_name.getText().toString();
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
                break;


        }
    }

    private void getData(String name) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Data where username = '" + name + "'", null);
        //Cursor cursor = db.query("Book", new String[]{"id", "name", "phone", "mvname", "price", "number", "total"}, "name=?", new String[]{name}, null, null, null);
        if (cursor.moveToNext()) {

            tv_name.setText(cursor.getString(cursor.getColumnIndex("username")));
            tv_phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            tv_mvname.setText(cursor.getString(cursor.getColumnIndex("name")));
            tv_price.setText(cursor.getString(cursor.getColumnIndex("price")));
            tv_number.setText(cursor.getString(cursor.getColumnIndex("number")));
            tv_time.setText(cursor.getString(cursor.getColumnIndex("time")));
            tv_total.setText(cursor.getString(cursor.getColumnIndex("total")));

        }
        cursor.close();
    }
}
