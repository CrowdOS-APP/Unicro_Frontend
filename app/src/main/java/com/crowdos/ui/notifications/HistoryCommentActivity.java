package com.crowdos.ui.notifications;

import static com.crowdos.portals.opInfo.gMyComment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.portals.jsonFiles.getMyComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HistoryCommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private List<HistoryComment> mHistoryCommentList = new ArrayList<>();

    public static List<getMyComment> mHistoryCommentListData = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_comment);
        mRecyclerView = findViewById(R.id.your_history_list);

        // 构造一些数据
        gMyComment(MainActivity.token);
        try{
            Thread.sleep(700);
        }catch (InterruptedException e){
            return;
        }
        for (int i = 0; i < mHistoryCommentListData.size(); i++) {
            HistoryComment historyComment = new HistoryComment();
            Random random = new Random();
            int temp = random.nextInt(7);
            historyComment.eventNameString = mHistoryCommentListData.get(i).eventname;
            historyComment.userNameString = mHistoryCommentListData.get(i).username;
            historyComment.commentString = mHistoryCommentListData.get(i).content;
            if(temp <= 0){
                historyComment.sculpture = 1;
            }
            else{
                historyComment.sculpture = temp;
            }
            mHistoryCommentList.add(historyComment);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryCommentActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(HistoryCommentActivity.this, R.layout.layout_displayer_comment, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            HistoryComment comment = mHistoryCommentList.get(position);
            holder.userName.setText(comment.userNameString);
            holder.comment.setText("回复" + comment.eventNameString + "：" + comment.commentString);
            int sculptureNumber = chooseSculpture(comment.sculpture);
            holder.sculpture.setImageResource(sculptureNumber);
        }

        @Override
        public int getItemCount() {
            return mHistoryCommentList.size();
        }
    }
    
    private int chooseSculpture(int number){
        int mSculptureNumber = 0;
        switch (number){
            case 1: mSculptureNumber = R.mipmap.sculpture1; break;
            case 2: mSculptureNumber = R.mipmap.sculpture2; break;
            case 3: mSculptureNumber = R.mipmap.sculpture3; break;
            case 4: mSculptureNumber = R.mipmap.sculpture4; break;
            case 5: mSculptureNumber = R.mipmap.sculpture5; break;
            case 6: mSculptureNumber = R.mipmap.sculpture6; break;
        }
        return mSculptureNumber;
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView comment;
        ImageView sculpture;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView19);
            comment = itemView.findViewById(R.id.textView20);
            sculpture = itemView.findViewById(R.id.imageView19);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}