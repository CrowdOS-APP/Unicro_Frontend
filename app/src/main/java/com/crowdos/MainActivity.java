package com.crowdos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crowdos.ui.welcome.event_Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.crowdos.databinding.ActivityMainBinding;

//******************************************************************
//*************************MainActivity*****************************
//******************************************************************

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static boolean isLogin = true;
    private boolean isGotoWelcomePage;
    private TextView intoBt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isLogin)
            isGotoWelcomePage = true;
        else
            isGotoWelcomePage = false;

        if (isGotoWelcomePage) {
            //调用event_welcome类
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_welcome);

            intoBt = findViewById(R.id.startVoyage);
            intoBt.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, event_Login.class);
                startActivity(intent);
            });

        } else {
            super.onCreate(savedInstanceState);

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);

        }
    }



}