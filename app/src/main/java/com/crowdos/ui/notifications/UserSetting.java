package com.crowdos.ui.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowdos.R;
import com.crowdos.databinding.FragmentUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSetting extends Fragment {

    private FragmentUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}