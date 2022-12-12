package com.crowdos.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdos.MainActivity;
import com.crowdos.R;

public class event_Login extends AppCompatActivity {

    private TextView Username;
    private Button button_login;
    private EditText editText;
    private ImageView imageView;
    private TextView forget;
    private TextView register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_login);
        /*************<显示和隐藏密码>******************/
        editText = findViewById(R.id.private_change_password_new10);
        imageView = findViewById(R.id.hide_password_old3);

        final boolean[] isOpen = {false};

        imageView.setOnClickListener(view -> {
            if(isOpen[0]){
                //如果选中，显示密码
                imageView.setImageResource(R.mipmap.verify_code_2);
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isOpen[0] = false;
            }else{
                //否则隐藏密码
                imageView.setImageResource(R.mipmap.verify_code_1);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isOpen[0] = true;
            }

        });
        /*************<显示和隐藏密码>******************/


        /*************<忘记密码>******************/
        forget = findViewById(R.id.textView21);
        forget.setOnClickListener(view -> {
            Intent intent = new Intent(event_Login.this, event_Password_Forgotten.class);
            startActivity(intent);
        });
        /*************<忘记密码>******************/


        /*************<注册>******************/
        register = findViewById(R.id.textView22);
        register.setOnClickListener(view -> {
            Intent intent = new Intent(event_Login.this, event_Register.class);
            startActivity(intent);
        });

        /*************<登陆>******************/
        Username = (TextView) findViewById(R.id.textView10) ;
        button_login = (Button) findViewById(R.id.button9) ;
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(event_Login.this, MainActivity.class);
                MainActivity.isLogin = false;
                startActivity(intent);

            }
        });
        /*************<登陆>******************/
    }
}