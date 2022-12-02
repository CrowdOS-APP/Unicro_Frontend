package com.crowdos;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class event_Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        TextView intoBt = (TextView) findViewById(R.id.Welcome);
        intoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true)//需要判定token==1
                {
                    //此处进入主页面
                    //Activity_main 处换做主界面act
                    // Intent intent = new Intent(event_Welcome.this,activity_main.class)
                }
                else{
                    //此处进入注册登录页
                    Intent intent = new Intent(event_Welcome.this,event_Register.class);
                    startActivity(intent);
                }
            }
        });
    }
}