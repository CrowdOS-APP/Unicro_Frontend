package com.crowdos.ui.welcome;

import static com.crowdos.portals.opUser.userLogin;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class event_Login extends AppCompatActivity {

    private TextView Username;
    private Button button_login;
    private EditText editText;
    private EditText editUsername;
    private EditText editPasswd;
    private String username= null;
    private String passwd = null;
    private ImageView imageView;
    private TextView forget;
    private TextView register;
    private String receivedtoken;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        editUsername = findViewById(R.id.private_change_password_new9);
        editPasswd = findViewById(R.id.private_change_password_new10);

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

        /*************<登陆>******11************/
        Username = (TextView) findViewById(R.id.textView10) ;
        button_login = (Button) findViewById(R.id.button9) ;
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = null;
                passwd = editPasswd.getText().toString();
                username = editUsername.getText().toString();
                token =  userLogin(username,passwd);
                if(!token.equals(null)) {
                    receivedtoken = token ;
                    Toast.makeText(event_Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(event_Login.this, MainActivity.class);
                    MainActivity.isLogin = false;
                    startActivity(intent);
                }
                else{
                    Toast.makeText(event_Login.this, "登录失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*************<登陆>******************/

        saveFiles("用户名", "UserName");
        saveFiles("很酷，不写个签。", "UserSignature");
        saveFiles("1", "UserSculpture");
        saveFiles(receivedtoken,"token");
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