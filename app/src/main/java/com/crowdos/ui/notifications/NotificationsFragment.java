package com.crowdos.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.crowdos.R;
import com.crowdos.databinding.FragmentUserBinding;
/******************************************************************/
/*************************USER*************************************/
/******************************************************************/
public class NotificationsFragment extends Fragment {

    private FragmentUserBinding binding;

    public interface OnButtonClick{
            public void onClick(View view);
    }
    private OnButtonClick onButtonClick;
    private Button HistoryCommentButton;
    private Button YourEventButton;
    public Button UserSettingsButton;
    private Button ChangePasswordButton;
    private Button ExitButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        /*UserSettingButton*/
        Button UserSettingsButton = (Button) view.findViewById(R.id.your_event);
        UserSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onButtonClick != null){
                    onButtonClick.onClick(UserSettingsButton);
                }
            }
        });


        return view;
    }

    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}