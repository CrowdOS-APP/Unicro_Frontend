package com.crowdos.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.MainActivity;
import com.crowdos.R;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class UserSettingsActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        EditText setUserName = findViewById(R.id.private_settings_user);
        EditText setSignature = findViewById(R.id.change_signature);
        TextView setUserNameTextNumber = findViewById(R.id.textView34);
        TextView setSignatureTextNumber = findViewById(R.id.textView35);
        /*************<用户名和个性签名>******************/
        setUserName.addTextChangedListener(new TextWatcher() {
            final int num = 15;
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                setUserNameTextNumber.setText("" + number);
                selectionStart = setUserName.getSelectionStart();
                selectionEnd = setUserName.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    setUserName.setText(s);
                    setUserName.setSelection(tempSelection);//设置光标在最后
                }
            }
        });

        setSignature.addTextChangedListener(new TextWatcher() {
            final int num = 80;
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                setSignatureTextNumber.setText("" + number);
                selectionStart = setSignature.getSelectionStart();
                selectionEnd = setSignature.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    setSignature.setText(s);
                    setSignature.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
        /*************<用户名和个性签名>******************/


        /*************<修改>******************/
        TextView change = findViewById(R.id.password_change2);
        change.setOnClickListener(v -> {
            String userName = setUserName.getText().toString();
            String userSignature = setSignature.getText().toString();
            if(userName.length() != 0) {
                saveData(userName,1);
                Toast.makeText(UserSettingsActivity.this, "用户名已修改", Toast.LENGTH_SHORT).show();
            }
            if(userSignature.length() != 0){
                saveData(userSignature,2);
                Toast.makeText(UserSettingsActivity.this, "个性签名已修改", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);

            startActivity(intent);
        });
        /*************<修改>******************/


    }

    public void saveData(String inputText, int saveType) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        if (inputText != null && !TextUtils.isEmpty(inputText)) {
            try {
                String fileName = null;
                switch (saveType){
                    case 1:
                        fileName = "UserName";
                        break;
                    case 2:
                        fileName = "UserSignature";
                }
                out = openFileOutput(fileName, Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(inputText);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}