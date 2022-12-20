package com.crowdos.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.MainActivity;
import com.crowdos.R;

public class event_Upload extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_upload);


        Button upload_Pictures = findViewById(R.id.add_pic);
        EditText upload_Description = findViewById(R.id.upload_description);
        EditText upload_Title = findViewById(R.id.upload_title);
        ImageButton upload_event = findViewById(R.id.imageButton5);
        ImageButton upload_type = findViewById(R.id.imageButton3);
        ImageButton upload_StartTime = findViewById(R.id.imageButton6);
        ImageButton upload_EndTime = findViewById(R.id.imageButton7);
        ImageButton upload_locate = findViewById(R.id.imageButton4);

        /*************<相册>******************/

        /*************<相册>******************/


        /*************<标题和简介>******************/
        upload_Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        upload_Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        /*************<标题和简介>******************/


        /*************<类型>******************/
        upload_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        /*************<类型>******************/


        /*************<时间>******************/
        upload_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        upload_EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        /*************<时间>******************/


        /*************<地图>******************/
        upload_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        /*************<地图>******************/


        /*************<上传>******************/
        upload_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处打包信息上传至服务器
                Toast.makeText(event_Upload.this,"事件已上传",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(event_Upload.this, MainActivity.class);
                startActivity(intent);
            }
        });
        /*************<上传>******************/
    }


}