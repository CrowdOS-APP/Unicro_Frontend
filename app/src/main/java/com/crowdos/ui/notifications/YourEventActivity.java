package com.crowdos.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crowdos.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class YourEventActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    List<YourEvent> yourEventList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_event);
        mRecyclerView = findViewById(R.id.your_event_list);
        // 构造一些数据
        for (int i = 0; i < 50; i++) {
            YourEvent yourEvent = new YourEvent();
            yourEvent.eventName = "标题" + i;
            yourEvent.description = "内容" + i;
            yourEventList.add(yourEvent);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        layoutManager = new LinearLayoutManager(YourEventActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(YourEventActivity.this, R.layout.layout_displayer_event, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            YourEvent yourEvent = yourEventList.get(position);
            holder.eventTitle.setText(yourEvent.eventName);
            holder.eventContent.setText(yourEvent.description);

            //展示时间
            String startTime;
            startTime = getFormatDate(yourEvent.startTime);
            holder.eventTime.setText("开始时间:"+startTime);
        }

        @Override
        public int getItemCount() {
            return yourEventList.size();
        }
    }

    public String getFormatDate(long times){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = formatter.format(times);
        return format;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView eventContent;
        TextView eventTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.textView19);
            eventContent = itemView.findViewById(R.id.textView20);
            eventTime = itemView.findViewById(R.id.textView28);
        }
    }

}