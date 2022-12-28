package com.crowdos.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.R;


public class EventPageFragment extends Fragment {

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(root == null)
        {
            root = inflater.inflate(R.layout.event_page,container,false);
        }
        return root;
    }
}