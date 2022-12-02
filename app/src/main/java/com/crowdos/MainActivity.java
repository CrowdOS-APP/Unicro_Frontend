package com.crowdos;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.view.View;

import com.crowdos.databinding.ActivityMainBinding;

//******************************************************************
//*************************MainActivity*****************************
//******************************************************************

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button WelcomePageButtonForLogin;

    private boolean isLogin;
    private boolean isGotoWelcomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(isLogin)
            isGotoWelcomePage = true;
        else
            isGotoWelcomePage = false;

        if(isGotoWelcomePage)
        {
            super.onCreate(savedInstanceState);
            WelcomePageButtonForLogin = (Button) findViewById(R.id.button);
            WelcomePageButtonForLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else{
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