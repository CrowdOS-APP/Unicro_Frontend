package com.crowdos.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentChangePasswdBinding;


public class ChangePasswordFragment extends Fragment {

    private TextView userName;
    private TextView userSignature;
    private ImageView userSculpture;
    private FragmentChangePasswdBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChangePasswdBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if(view == null)
        {
            view = inflater.inflate(R.layout.fragment_change_passwd,container,false);
        }
        String userNameString = MainActivity.toNotificationsFragmentUserNameString;
        String userSignatureString = MainActivity.toNotificationsFragmentUserSignatureString;
        String userSculptureOptions = MainActivity.toNotificationsFragmentUserSculpture;
        userName = view.findViewById(R.id.user_name3);
        userSignature = view.findViewById(R.id.signature3);
        userSculpture = view.findViewById(R.id.imageView3);
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
        com.crowdos.databinding.FragmentSettingsBinding binding = null;
    }
}