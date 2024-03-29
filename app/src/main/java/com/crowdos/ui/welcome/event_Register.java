package com.crowdos.ui.welcome;

import static com.crowdos.portals.opUser.userRegister;

import android.content.Context;
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
import com.crowdos.ui.Utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class event_Register extends AppCompatActivity {

    private TextView button_askForCode;
    private Button button_register;
    private EditText editText;
    private EditText editEmail;
    private ImageView imageView;
    private String email;
    private String passwd;
    public static boolean isSuccess;
    public static String userName;
    public static String userSignature;
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
        button_register.setOnClickListener(view -> {
            if(Utils.isFastClick()){
                email = editEmail.getText().toString();
                passwd = editText.getText().toString();
                if(passwd.length() < 6 || passwd.length() >18){
                    Toast.makeText(event_Register.this, "密码必须为6-18位,请重新输入", Toast.LENGTH_SHORT).show();
                }
                else{
                    userRegister(email,passwd);
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        return;
                    }
                    if(isSuccess) {
                        Toast.makeText(event_Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                        saveFiles(userName, "UserName");
                        saveFiles(userSignature, "UserSignature");
                        saveFiles("1", "UserSculpture");
                        Intent intent = new Intent(event_Register.this, event_Login.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(event_Register.this,"注册失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(event_Register.this, "您的手速太快辣w(ﾟДﾟ)w", Toast.LENGTH_SHORT).show();
            }
        });
        /*************<注册>******************/
    }

    public void saveFiles(String setString, String fileName) {

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
}