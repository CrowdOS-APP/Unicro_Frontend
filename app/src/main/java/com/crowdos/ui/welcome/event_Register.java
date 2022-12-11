package com.crowdos.ui.welcome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.R;

public class event_Register extends AppCompatActivity {

    private TextView button_askForCode;
    private Button button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        button_askForCode = findViewById(R.id.textView) ;
        button_register = findViewById(R.id.button9) ;
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