package com.crowdos;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.crowdos.databinding.ActivityMainBinding;
import com.crowdos.ui.home.HomeFragment;
import com.crowdos.ui.welcome.event_Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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

        super.onCreate(savedInstanceState);
        if (isGotoWelcomePage) {
            //调用event_welcome类
            setContentView(R.layout.activity_event_welcome);

            intoBt = findViewById(R.id.startVoyage);
            intoBt.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, event_Login.class);
                startActivity(intent);
            });

        } else {

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

            String userNameString = readData("UserName");
            String userSignatureString = readData("UserSignature");
            Log.e(TAG, "onCreate: "+userNameString);
            Log.e(TAG, "onCreate: "+userSignatureString);
            TextView userName = findViewById(R.id.user_name);
            TextView userSignature = findViewById(R.id.signature);
//            userName.setText(userNameString);
//            userSignature.setText(userSignatureString);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        HomeFragment.mMapView.onDestroy();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        mMapView.onResume();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        HomeFragment.mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        HomeFragment.mMapView.onSaveInstanceState(outState);
    }

    public String readData(String outputText) {
        FileInputStream in = null;
        BufferedReader reader = null;
        try {
            in = openFileInput(outputText);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String len = "";
            while ((len = reader.readLine()) != null) {
                buffer.append(len);
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}