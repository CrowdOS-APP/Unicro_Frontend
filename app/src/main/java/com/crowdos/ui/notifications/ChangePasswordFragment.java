package com.crowdos.ui.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.R;


public class ChangePasswordFragment extends Fragment {

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
            root = inflater.inflate(R.layout.fragment_change_passwd,container,false);
        }
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        com.crowdos.databinding.FragmentSettingsBinding binding = null;
    }
}