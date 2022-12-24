package com.crowdos.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.MapView;
import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentHomeBinding;
/******************************************************************/
/*************************HOME*************************************/
/******************************************************************/
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ImageView Upload;
    public static MapView mMapView = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Upload = root.findViewById(R.id.Upload);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), event_Upload.class);
                startActivity(intent);
            }
        });
        mMapView = root.findViewById(R.id.mapView2);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if(MainActivity.isShowMap){
            //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
            mMapView.onDestroy();
        }
    }


}