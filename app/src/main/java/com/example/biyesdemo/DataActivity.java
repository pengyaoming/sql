package com.example.biyesdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biyesdemo.RecyclerView.RecycleViewDivider;
import com.example.biyesdemo.adapter.MyAdapter;
import com.example.biyesdemo.entity.BaseEntity;
import com.example.biyesdemo.helper.MyDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主Activity
 */
public class DataActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView ry;
    private MyDBHelper myDBHelper;
    private MyAdapter adapter;
    private TextView tv_inse, tv_title, tv_chaxun, tv_id, tv_message, tv_name;
    private ImageView back_img;
    private EditText edt_search;
    private LinearLayout liner;

    private List<BaseEntity> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        myDBHelper = new MyDBHelper(this, "UserStore.db", null, 2);
        initView();
        getData();
        ry.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(list, this);
        ry.setAdapter(adapter);
        adapter.setOnremoveListnner(new MyAdapter.OnremoveListnner() {
            @Override
            public void ondelect(int i, String name) {
                getDialog(i, name);
            }
        });
        ry.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.textcolor)));

    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        back_img = findViewById(R.id.back_img);
        back_img.setVisibility(View.GONE);
        tv_title.setText("毕设");
        ry = findViewById(R.id.ry);
        tv_inse = findViewById(R.id.tv_inse);
        tv_inse.setOnClickListener(this);
        edt_search = findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(textWatcher);
        tv_chaxun = findViewById(R.id.tv_chaxun);
        tv_chaxun.setOnClickListener(this);
        tv_id = findViewById(R.id.tv_id);
        tv_name = findViewById(R.id.tv_name);
        tv_message = findViewById(R.id.tv_message);
        liner = findViewById(R.id.liner);
        liner.setOnClickListener(this);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s.toString())) {
                ry.setVisibility(View.VISIBLE);
                liner.setVisibility(View.GONE);
            }
        }
    };

    private void getDialog(final int i, final String name) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除").setMessage("需要删除这条数据吗？").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(i);
                adapter.notifyDataSetChanged();
                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                db.delete("Data", "username=?", new String[]{name});
                Toast.makeText(DataActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void getData() {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.query("Data", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                String prive = cursor.getString(cursor.getColumnIndex("price"));
                String total = cursor.getString(cursor.getColumnIndex("total"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                BaseEntity base = new BaseEntity(username, phone, prive, number, name, total, id, time);
                list.add(base);
            } while (cursor.moveToNext());

        }
        cursor.close();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_inse:
                Intent intent = new Intent(DataActivity.this, IncreaseActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_chaxun:
                getchaxun();
                break;
            case R.id.liner:
                Intent mIntent = new Intent(DataActivity.this, DetailsActivity.class);
                mIntent.putExtra("name", tv_name.getText().toString());
                startActivity(mIntent);
        }
    }

    private void getchaxun() {
        if (TextUtils.isEmpty(edt_search.getText().toString())) {
            Toast.makeText(DataActivity.this, "请输入正确的编号", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(edt_search.getText().toString());
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Data where id = '" + id + "'", null);
        if (cursor.moveToNext()) {
            tv_id.setText(cursor.getString(cursor.getColumnIndex("id")));
            tv_name.setText(cursor.getString(cursor.getColumnIndex("username")));
            tv_message.setText(cursor.getString(cursor.getColumnIndex("phone")));
        }
        cursor.close();
        ry.setVisibility(View.GONE);
        liner.setVisibility(View.VISIBLE);

    }
}
