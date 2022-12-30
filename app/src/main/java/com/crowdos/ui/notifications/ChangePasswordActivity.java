package com.crowdos.ui.notifications;

import static com.crowdos.MainActivity.token;
import static com.crowdos.portals.opUser.updatePasswd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.ui.Utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editTextOld;
    private ImageView imageViewOld;
    private EditText editTextNew;
    private ImageView imageViewNew;
    private EditText editTextNew2;
    private ImageView imageViewNew2;
    private EditText editTextEmail;

    private TextView change;

    public static boolean succeed;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editTextEmail = (EditText) findViewById(R.id.private_change_password_email);
        /*************<显示和隐藏密码>******************/
        editTextOld = findViewById(R.id.private_change_password_old);
        imageViewOld = findViewById(R.id.hide_password_old);
        editTextNew = findViewById(R.id.private_change_password_new);
        imageViewNew = findViewById(R.id.hide_password_new);
        editTextNew2 = findViewById(R.id.private_change_password_new2);
        imageViewNew2 = findViewById(R.id.hide_password_new2);
        final boolean[] isOpen = {false,false,false};

        imageViewOld.setOnClickListener(view -> {
            if(isOpen[0]){
                //如果选中，显示密码
                imageViewOld.setImageResource(R.mipmap.verify_code_2);
                editTextOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isOpen[0] = false;
            }else{
                //否则隐藏密码
                imageViewOld.setImageResource(R.mipmap.verify_code_1);
                editTextOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isOpen[0] = true;
            }

        });

        imageViewNew.setOnClickListener(view -> {
            if(isOpen[1]){
                //如果选中，显示密码
                imageViewNew.setImageResource(R.mipmap.verify_code_2);
                editTextNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isOpen[1] = false;
            }else{
                //否则隐藏密码
                imageViewNew.setImageResource(R.mipmap.verify_code_1);
                editTextNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isOpen[1] = true;
            }

        });

        imageViewNew2.setOnClickListener(view -> {
            if(isOpen[2]){
                //如果选中，显示密码
                imageViewNew2.setImageResource(R.mipmap.verify_code_2);
                editTextNew2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isOpen[2] = false;
            }else{
                //否则隐藏密码
                imageViewNew2.setImageResource(R.mipmap.verify_code_1);
                editTextNew2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isOpen[2] = true;
            }

        });
        /*************<显示和隐藏密码>******************/


        /*************<点击修改按钮>******************/
        change = findViewById(R.id.password_change);

        change.setOnClickListener(v -> {
            if(Utils.isFastClick()){
                String email = editTextEmail.getText().toString();
                String oldPasswd = editTextOld.getText().toString();
                String newPasswd = editTextNew.getText().toString();
                String newPasswd2 = editTextNew2.getText().toString();
                /**（1）判断密码是否6-18位**/
                if(newPasswd.length() < 6 || newPasswd.length() > 18){
                    Toast.makeText(ChangePasswordActivity.this, "密码必须为6-18位,请重新输入", Toast.LENGTH_SHORT).show();
                }
                /**（2）判断两次新密码是否一致**/
                else if(!(newPasswd.equals(newPasswd2))){
                    Toast.makeText(ChangePasswordActivity.this, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
                }
                /**（3）判断邮箱或密码是否为空**/
                else if(email.length() == 0 || oldPasswd.length() == 0){
                    Toast.makeText(ChangePasswordActivity.this, "邮箱或旧密码为空,请重新输入", Toast.LENGTH_SHORT).show();
                }
                else{
                    updatePasswd(token,oldPasswd,newPasswd);
                    try {
                        Thread.sleep(700);
                    }catch (InterruptedException e){
                        return;
                    }
                    if(succeed){
                        Toast.makeText(ChangePasswordActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                        saveFiles(email,"Email");
                        saveFiles(newPasswd,"Password");
                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ChangePasswordActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(ChangePasswordActivity.this, "您的手速太快辣w(ﾟДﾟ)w", Toast.LENGTH_SHORT).show();
            }
        });
        /*************<点击修改按钮>******************/
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}