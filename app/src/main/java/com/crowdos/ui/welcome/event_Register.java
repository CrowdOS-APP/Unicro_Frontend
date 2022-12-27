package com.crowdos.ui.welcome;

import static com.crowdos.portals.opUser.userRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.R;

public class event_Register extends AppCompatActivity {

    private TextView button_askForCode;
    private Button button_register;

    private EditText editText;
    private EditText editPasswd;
    private EditText editEmail;
    private ImageView imageView;
    private String email;
    private String passwd;
    public static boolean isSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        editEmail = findViewById(R.id.private_change_password_new3);

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


        /*************<发送验证码>******************/
        button_askForCode = findViewById(R.id.textView);
        button_askForCode.setOnClickListener(view ->
            Toast.makeText(event_Register.this,"已发送验证码",Toast.LENGTH_SHORT).show()
        );
        /*************<发送验证码>******************/


        /*************<注册>******************/
        button_register = findViewById(R.id.button9);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editEmail.getText().toString();
                passwd = editText.getText().toString();
                userRegister(email,passwd);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                Log.e("is",""+isSuccess);
                if(isSuccess) {
                    Toast.makeText(event_Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(event_Register.this, event_Login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(event_Register.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*************<注册>******************/
    }
}