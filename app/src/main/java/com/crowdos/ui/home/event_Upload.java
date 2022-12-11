package com.crowdos.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crowdos.R;

import java.io.ByteArrayOutputStream;

public class event_Upload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_page);
        Button upload_Pictures = (Button) findViewById(R.id.floatingActionButton2);
        EditText upload_Description = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText upload_Title = (EditText) findViewById(R.id.editTextTextPersonName2);
        ImageButton upload = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton destroy = (ImageButton) findViewById(R.id.imageButton7);
        upload_Pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**public void photo(){//调用系统相册选择图片
                    Intent intent = new Intent();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                    } else {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                    }
                    intent.setType("image/*");
                    startActivityForResult(intent, 1000);//打开相册
                }
                 don't know if its fine
                 */
            }
        });
        upload_Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        upload_Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此处打包信息上传至服务器
            }
        });
        destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
            }
        });
    }


}