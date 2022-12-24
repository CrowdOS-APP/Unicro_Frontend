package com.crowdos.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.BaiduMap;
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
    public static BaiduMap mBaiduMap = null;
    private static SharedPreferences homePreferences;

    TextView mapLocation;  //经纬度
    public double longitude;//经度
    public double latitude;//纬度

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View root, Bundle savedInstanceState){
        super.onViewCreated(root, savedInstanceState);
        Upload = root.findViewById(R.id.Upload);
        Upload.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), event_Upload.class);
            startActivity(intent);
        });
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            initLocation(root);
        }
    }

    private void initLocation(View v) {  //初始化
        mMapView = v.findViewById(R.id.mapView2);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mapLocation = v.findViewById(R.id.location);
    }

    @Override
    public void onStart(){
        super.onStart();
        if(MainActivity.isShowMap) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(MainActivity.isShowMap) {
            mMapView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.isShowMap){
            mMapView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(MainActivity.isShowMap){
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        }
        binding = null;
    }


}