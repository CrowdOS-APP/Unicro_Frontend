package com.crowdos.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentUserBinding;
/******************************************************************/
/*************************USER*************************************/
/******************************************************************/
public class NotificationsFragment extends Fragment {

    private FragmentUserBinding binding;
    private TextView userName;
    private TextView userSignature;
    private ImageView userSculpture;

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

        String userNameString = MainActivity.toNotificationsFragmentUserNameString;
        String userSignatureString = MainActivity.toNotificationsFragmentUserSignatureString;
        String userSculptureOptions = MainActivity.toNotificationsFragmentUserSculpture;
        userName = view.findViewById(R.id.user_name);
        userSignature = view.findViewById(R.id.signature);
        userSculpture = view.findViewById(R.id.imageView);
        userName.setText(userNameString);
        userSignature.setText(userSignatureString);
        switch (userSculptureOptions){
            case "1": userSculpture.setImageResource(R.mipmap.sculpture1);  break;
            case "2": userSculpture.setImageResource(R.mipmap.sculpture2);  break;
            case "3": userSculpture.setImageResource(R.mipmap.sculpture3);  break;
            case "4": userSculpture.setImageResource(R.mipmap.sculpture4);  break;
            case "5": userSculpture.setImageResource(R.mipmap.sculpture5);  break;
            case "6": userSculpture.setImageResource(R.mipmap.sculpture6);  break;
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}