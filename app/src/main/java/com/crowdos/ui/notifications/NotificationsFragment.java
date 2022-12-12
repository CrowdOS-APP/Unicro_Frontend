package com.crowdos.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}