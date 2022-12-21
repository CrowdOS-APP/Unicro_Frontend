package com.crowdos.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.crowdos.R;


public class fragment_Replies extends Fragment {

    private int mColumnCount = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_comments, container, false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        com.crowdos.databinding.FragmentSettingsBinding binding = null;
    }
}