package com.crowdos.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdos.R;

public class event_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_login);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView Username = (TextView) findViewById(R.id.textView10) ;
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button_login = (Button) findViewById(R.id.button9) ;
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Login.this,"正在尝试登录,需回传账号密码数据密码",Toast.LENGTH_SHORT).show();

            }
        });
    //token needed to judge which activity should jump into,also the destroy
    }
}