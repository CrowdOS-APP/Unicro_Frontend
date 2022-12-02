package com.crowdos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class event_Password_Forgotten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_fogotten_page);
        TextView button_askForCode = (TextView) findViewById(R.id.textView) ;
        Button button_register = (Button) findViewById(R.id.button9) ;
        button_askForCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Password_Forgotten.this,"Requesting for codes",Toast.LENGTH_SHORT).show();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(event_Password_Forgotten.this,"Uploading accounts and codes",Toast.LENGTH_SHORT).show();
            }
        });
        //token needed to judge which activity should jump into
    }
}