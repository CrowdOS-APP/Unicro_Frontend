package com.crowdos;

import static com.crowdos.portals.opInfo.getUserInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.crowdos.databinding.ActivityMainBinding;
import com.crowdos.portals.jsonFiles.userInfo;
import com.crowdos.ui.welcome.event_Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//******************************************************************
//*************************MainActivity*****************************
//******************************************************************

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static boolean isLogin;
    private boolean isGotoWelcomePage;
    private TextView intoBt;

    public static boolean isShowMap;
    public static String toNotificationsFragmentUserNameString;
    public static String toNotificationsFragmentUserSignatureString;
    public static String toNotificationsFragmentUserSculpture;
    public static String token;
    public static boolean isExit;
    public static userInfo userInfo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isExit) {
            String string = "";
            saveFiles(string, "token");
            isExit = false;
        }
        token = readData("token");
        if(token == ""){
            isLogin = true;
        }
        else{
            isLogin = false;
        }
        isGotoWelcomePage = isLogin;
        isShowMap = false;
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
            toNotificationsFragmentUserNameString = readData("UserName");
            toNotificationsFragmentUserSignatureString = readData("UserSignature");
            toNotificationsFragmentUserSculpture = readData("UserSculpture");
            getUserInfo(token);
            isShowMap = true;
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



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public String readData(String fname) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput(fname);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();;
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
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