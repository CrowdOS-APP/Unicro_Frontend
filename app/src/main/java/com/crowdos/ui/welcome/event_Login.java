package com.crowdos.ui.welcome;

import static com.crowdos.portals.opUser.userLogin;

import android.annotation.SuppressLint;
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

import com.crowdos.MainActivity;
import com.crowdos.R;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class event_Login extends AppCompatActivity {

    private Button button_login;
    private EditText editPasswd;
    private EditText editEmail;
    private ImageView imageView;
    private TextView forget;
    private TextView register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_login);


        editEmail = findViewById(R.id.private_change_password_new9);


        /*************<显示和隐藏密码>******************/
        editPasswd = findViewById(R.id.private_change_password_new10);
        imageView = findViewById(R.id.hide_password_old3);
        final boolean[] isOpen = {false};
        imageView.setOnClickListener(view -> {
            if(isOpen[0]){
                //如果选中，显示密码
                imageView.setImageResource(R.mipmap.verify_code_2);
                editPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isOpen[0] = false;
            }else{
                //否则隐藏密码
                imageView.setImageResource(R.mipmap.verify_code_1);
                editPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        button_login = (Button) findViewById(R.id.button9) ;
        button_login.setOnClickListener(view -> {
            String email = editEmail.getText().toString();
            String pwd = editPasswd.getText().toString();
            userLogin(email,pwd);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                return;
            }
            if(MainActivity.token.length() > 0) {
                Toast.makeText(event_Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(event_Login.this, MainActivity.class);
                MainActivity.isLogin = false;
                saveFiles(MainActivity.token,"token");
                saveFiles(email,"Email");
                saveFiles(pwd,"Password");
                startActivity(intent);
            }
            else{
                Toast.makeText(event_Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
        /*************<登陆>******************/
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