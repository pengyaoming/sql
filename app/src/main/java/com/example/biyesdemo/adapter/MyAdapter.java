package com.example.biyesdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biyesdemo.DetailsActivity;
import com.example.biyesdemo.R;
import com.example.biyesdemo.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private List<BaseEntity> list = new ArrayList<>();
    private Context mContext;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnremoveListnner {
        void ondelect(int i, String name);
    }

    private OnremoveListnner onremoveListnner;

    public void setOnremoveListnner(OnremoveListnner onremoveListnner) {
        this.onremoveListnner = onremoveListnner;
    }

    public MyAdapter(List<BaseEntity> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_from, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        final BaseEntity base = list.get(i);
        myHolder.tv_title.setText(base.getUsername() + "");
        myHolder.tv_message.setText(base.getPhone() + "");
        myHolder.tv_id.setText(base.getId() + "");
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("name", myHolder.tv_title.getText().toString());
                mContext.startActivity(intent);
            }
        });
        myHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onremoveListnner != null) {
                    onremoveListnner.ondelect(i, base.getUsername() + "");
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_message, tv_id;
        public MyHolder( View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
