package com.crowdos.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crowdos.R;
import com.crowdos.ui.event.EventPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class YourFollowerActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    YourFollowerActivity.MyAdapter mMyAdapter;
    List<YourFollower> yourFollowerList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_follower);
        mRecyclerView = findViewById(R.id.your_followers_list);
        // 构造一些数据
        for (int i = 0; i < 50; i++) {
            YourFollower yourFollower = new YourFollower();
            yourFollower.eventName = "标题" + i;
            yourFollower.description = "内容" + i;
            yourFollower.eventType = true;
            yourFollowerList.add(yourFollower);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        layoutManager = new LinearLayoutManager(YourFollowerActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(YourFollowerActivity.this, R.layout.layout_displayer_event, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            YourFollower yourFollower = yourFollowerList.get(position);
            holder.eventTitle.setText(yourFollower.eventName);
            holder.eventContent.setText(yourFollower.description);
            if(yourFollower.eventType){
                holder.eventType.setImageResource(R.mipmap.position2);
            }
            else{
                holder.eventType.setImageResource(R.mipmap.position);
            }

            //展示时间
            String startTime;
            startTime = getFormatDate(yourFollower.startTime);
            holder.eventTime.setText("开始时间:"+startTime);
            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventPageActivity.eventType = yourFollower.eventType;
                    Intent intent = new Intent(YourFollowerActivity.this, EventPageActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return yourFollowerList.size();
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
        ImageView eventType;
        ConstraintLayout mRootView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.textView19);
            eventContent = itemView.findViewById(R.id.textView20);
            eventTime = itemView.findViewById(R.id.textView28);
            eventType = itemView.findViewById(R.id.imageView21);
            mRootView = itemView.findViewById(R.id.item3);
        }
    }
}