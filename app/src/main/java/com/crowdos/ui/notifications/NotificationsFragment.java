package com.crowdos.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentUserBinding;

import java.io.File;
import java.io.FileInputStream;
/******************************************************************/
/*************************USER*************************************/
/******************************************************************/
public class NotificationsFragment extends Fragment {

    private FragmentUserBinding binding;
    private TextView userName;
    private TextView userSignature;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        /*UserSettingButton*/
        Button UserSettingsButton = (Button) view.findViewById(R.id.private_button);
        UserSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserSettingsActivity.class);
                startActivity(intent);
            }
        });

        /*HistoryCommentButton*/
        Button HistoryCommentButton = (Button) view.findViewById(R.id.history);
        HistoryCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryCommentActivity.class);
                startActivity(intent);
            }
        });

        /*YourFollowerButton*/
        Button YourFollowerButton = (Button) view.findViewById(R.id.follows);
        YourFollowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YourFollowerActivity.class);
                startActivity(intent);
            }
        });

        /*YourEventButton*/
        Button YourEventButton = (Button) view.findViewById(R.id.your_event);
        YourEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YourEventActivity.class);
                startActivity(intent);
            }
        });

        /*ChangePasswordButton*/
        Button ChangePasswordButton = (Button) view.findViewById(R.id.change_password);
        ChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        /*ExitButton*/
        Button ExitButton = (Button) view.findViewById(R.id.private_button3);
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                MainActivity.isLogin = true;
                startActivity(intent);
            }
        });

        String userNameString = readData("UserName");
        String userSignatureString = readData("UserSignature");
        userName = view.findViewById(R.id.user_name);
        userSignature = view.findViewById(R.id.signature);
        userName.setText(userNameString);
        userSignature.setText(userSignatureString);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static String readData(String fname){
        String filePath =Environment.getExternalStorageDirectory().toString() + File.separator + fname +".txt";
        String result=null;
        try {
            File f=new File(filePath);
            int length=(int)f.length();
            byte[] buff=new byte[length];
            FileInputStream fin=new FileInputStream(f);
            fin.read(buff);
            fin.close();
            result=new String(buff,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}