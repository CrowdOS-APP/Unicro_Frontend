package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class event_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        TextView button_askForCode = (TextView) findViewById(R.id.textView) ;
        Button button_register = (Button) findViewById(R.id.button9) ;
        button_askForCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Register.this,"Requesting for codes",Toast.LENGTH_SHORT).show();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Register.this,"Uploading accounts and codes",Toast.LENGTH_SHORT).show();
            }
        });
        //token needed to judge which activity should jump into
    }
}