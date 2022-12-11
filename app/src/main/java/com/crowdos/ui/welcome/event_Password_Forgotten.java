package com.crowdos.ui.welcome;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.R;

public class event_Password_Forgotten extends AppCompatActivity {

    private TextView button_askForCode;
    private Button button_change;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_password_forgotten);


        button_askForCode = findViewById(R.id.textView);
        button_change = findViewById(R.id.button9);
        button_askForCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Password_Forgotten.this,"已发送验证码",Toast.LENGTH_SHORT).show();
            }
        });
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Password_Forgotten.this,"已更新密码",Toast.LENGTH_SHORT).show();
            }
        });
        //token needed to judge which activity should jump into
    }
}