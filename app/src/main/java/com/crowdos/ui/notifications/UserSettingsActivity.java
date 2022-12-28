package com.crowdos.ui.notifications;

import static com.crowdos.portals.opInfo.updateUserInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crowdos.MainActivity;
import com.crowdos.R;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class UserSettingsActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private List<HeadSculpture> sculptureRecyclerViewList = new ArrayList<>();
    private int sculptureId;

    public static boolean isSuccess;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        EditText setUserName = findViewById(R.id.private_settings_user);
        EditText setSignature = findViewById(R.id.change_signature);
        TextView setUserNameTextNumber = findViewById(R.id.textView34);
        TextView setSignatureTextNumber = findViewById(R.id.textView35);
        /*************<用户名和个性签名>******************/
        setUserName.addTextChangedListener(new TextWatcher() {
            final int num = 15;
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                setUserNameTextNumber.setText("" + number);
                selectionStart = setUserName.getSelectionStart();
                selectionEnd = setUserName.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    setUserName.setText(s);
                    setUserName.setSelection(tempSelection);//设置光标在最后
                }
            }
        });

        setSignature.addTextChangedListener(new TextWatcher() {
            final int num = 80;
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                setSignatureTextNumber.setText("" + number);
                selectionStart = setSignature.getSelectionStart();
                selectionEnd = setSignature.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    setSignature.setText(s);
                    setSignature.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
        /*************<用户名和个性签名>******************/


        /*************<修改>******************/
        TextView change = findViewById(R.id.password_change2);
        change.setOnClickListener(v -> {
            String userName = setUserName.getText().toString();
            String userSignature = setSignature.getText().toString();
            if(userName.length() != 0 || userSignature.length() != 0 || sculptureId != 0) {
                if(userName.length() != 0){
                    saveUserFiles(userName,"UserName");
                }
                if(userSignature.length() != 0){
                    saveUserFiles(userSignature,"UserSignature");
                }
                if(sculptureId != 0){
                    saveUserFiles(""+sculptureId,"UserSculpture");
                }
                updateUserInfo(userName, userSignature, MainActivity.token);
                if(isSuccess){
                    Toast.makeText(UserSettingsActivity.this, "已修改", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserSettingsActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        /*************<修改>******************/


        /*************<头像>******************/
        //构造数据
        RecyclerView sculptureRecyclerView = findViewById(R.id.sculpture);

        for(int i = 1; i <= 6; i++){
            HeadSculpture headSculpture = new HeadSculpture();
            headSculpture.sculptureContent = "头像" + i;
            headSculpture.number = i;
            switch (i){
                case 1: headSculpture.sculpture = R.mipmap.sculpture1; break;
                case 2: headSculpture.sculpture = R.mipmap.sculpture2; break;
                case 3: headSculpture.sculpture = R.mipmap.sculpture3; break;
                case 4: headSculpture.sculpture = R.mipmap.sculpture4; break;
                case 5: headSculpture.sculpture = R.mipmap.sculpture5; break;
                case 6: headSculpture.sculpture = R.mipmap.sculpture6; break;
            }
            sculptureRecyclerViewList.add(headSculpture);
        }
        myAdapter = new MyAdapter();
        sculptureRecyclerView.setAdapter(myAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(UserSettingsActivity.this,3);
        sculptureRecyclerView.setLayoutManager(layoutManager);
        /*************<头像>******************/


    }

    public void saveUserFiles(String setString, String fileName) {

        String data = setString;
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(""+fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<showHeadSculpture>{
        @NonNull
        @Override
        public showHeadSculpture onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View view = View.inflate(UserSettingsActivity.this, R.layout.item_sculpture, null);
            showHeadSculpture myViewHoder = new showHeadSculpture(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull showHeadSculpture holder, int position) {
            HeadSculpture news = sculptureRecyclerViewList.get(position);
            holder.mySculpture.setImageResource(news.sculpture);
            holder.mySculptureText.setText(news.sculptureContent);
            holder.number = news.number;
            sculptureId = 0;
            holder.mRootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sculptureId = holder.number;
                    Toast.makeText(UserSettingsActivity.this,"已选择" + news.sculptureContent, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return sculptureRecyclerViewList.size();
        }

    }

    class showHeadSculpture extends RecyclerView.ViewHolder{
        ImageView mySculpture;
        TextView mySculptureText;
        ConstraintLayout mRootview;
        int number;
        public showHeadSculpture(@NonNull View item){
            super(item);
            mySculpture = item.findViewById(R.id.imageView9);
            mySculptureText = item.findViewById(R.id.textView36);
            mRootview = item.findViewById(R.id.item1);
        }
    }


}