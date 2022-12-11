package com.crowdos.ui.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.R;
import com.crowdos.databinding.FragmentSettingsBinding;


public class event_RegisterFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(root == null)
        {
            root = inflater.inflate(R.layout.register_page,container,false);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}