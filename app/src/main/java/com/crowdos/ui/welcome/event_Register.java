package com.crowdos.ui.welcome;

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

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.MainActivity;
import com.crowdos.R;

public class event_Register extends AppCompatActivity {

    private TextView button_askForCode;
    private Button button_register;

    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        /*************<显示和隐藏密码>******************/
        editText = findViewById(R.id.private_change_password_new5);
        imageView = findViewById(R.id.hide_password_old6);

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


        button_askForCode = findViewById(R.id.textView) ;
        button_register = findViewById(R.id.button9) ;
        button_askForCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Register.this,"已发送验证码",Toast.LENGTH_SHORT).show();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(event_Register.this, event_Login.class);
                startActivity(intent);
            }
        });
        //token needed to judge which activity should jump into
    }
}