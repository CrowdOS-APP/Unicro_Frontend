package com.crowdos.ui.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.R;
import com.crowdos.databinding.FragmentSettingsBinding;


public class ChangePasswordFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private View root;
    private View v_password;
    private EditText et_password;
    private ImageView ivEye;

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


        /*if(v_password == null){
            et_password = (EditText) v_password.findViewById(R.id.private_change_password_old);
            ivEye = (ImageView) v_password.findViewById(R.id.hide_password_old);
        }*/

        final boolean[] isOpenEye = {false};
        //密码是否可见
        /*
        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpenEye[0]) {
                    ivEye.setSelected(true);
                    isOpenEye[0] = true;
                    //密码可见
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    ivEye.setSelected(false);
                    isOpenEye[0] = false;
                    //密码不可见
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}