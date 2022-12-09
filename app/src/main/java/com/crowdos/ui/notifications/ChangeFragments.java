package com.crowdos.ui.notifications;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class ChangeFragments extends AppCompatActivity {
    private NotificationsFragment FirstFragment = new NotificationsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirstFragment.setOnButtonClick(new NotificationsFragment.OnButtonClick(){

            @Override
            public void onClick(View view) {
                //FragmentManager fm = getActivity().getFragmentManager();
                //fm.beginTransaction().replace().commit();
            }

        });
    }
}