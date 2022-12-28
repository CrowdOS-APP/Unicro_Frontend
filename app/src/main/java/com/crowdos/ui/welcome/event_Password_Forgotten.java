package com.crowdos.ui.welcome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.R;

public class event_Password_Forgotten extends AppCompatActivity {

    private TextView button_askForCode;
    private Button button_change;
    private EditText editText;
    private ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_password_forgotten);

        /*************<显示和隐藏密码>******************/
        editText = findViewById(R.id.private_change_password_new8);
        imageView = findViewById(R.id.hide_password_old4);

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
        button_askForCode = findViewById(R.id.textView);
        button_change = findViewById(R.id.button9);
        button_askForCode.setOnClickListener(view ->
                Toast.makeText(event_Password_Forgotten.this,"已发送验证码",Toast.LENGTH_SHORT).show()
        );
        button_change.setOnClickListener(view -> {
            Toast.makeText(event_Password_Forgotten.this,"已更新密码，请重新登陆",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(event_Password_Forgotten.this, event_Login.class);
            startActivity(intent);
        });
        //token needed to judge which activity should jump into
    }
}