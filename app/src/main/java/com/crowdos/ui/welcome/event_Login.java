package com.crowdos.ui.welcome;

import static com.crowdos.portals.opInfo.gMyEventList;
import static com.crowdos.portals.opInfo.getEmergeEventList;
import static com.crowdos.portals.opInfo.getEventsNearby;
import static com.crowdos.portals.opInfo.getFollowing;
import static com.crowdos.portals.opInfo.getMyComment;
import static com.crowdos.portals.opInfo.getUserInfo;
import static com.crowdos.portals.opInfo.opFollow;
import static com.crowdos.portals.opInfo.updateUserInfo;
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
import com.crowdos.portals.jsonFiles.emergencyList;
import com.crowdos.portals.jsonFiles.eventList;
import com.crowdos.portals.jsonFiles.followedEvents;
import com.crowdos.portals.jsonFiles.getComment;
import com.crowdos.portals.jsonFiles.getEventInfo;
import com.crowdos.portals.jsonFiles.getMyComment;
import com.crowdos.portals.jsonFiles.myEventList;
import com.crowdos.portals.jsonFiles.userInfo;
import com.crowdos.portals.opInfo;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class event_Login extends AppCompatActivity {

    private Button button_login;
    private EditText editPasswd;
    private EditText editEmail;
    private ImageView imageView;
    private TextView forget;
    private TextView register;
    private String receivedToken;
    public static String token = null;
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
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String pwd = editPasswd.getText().toString();
                userLogin(email,pwd);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    return;
                }
                if(token.length()>0) {
                    receivedToken = token ;
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
        //saveFiles(receivedToken,"token");
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