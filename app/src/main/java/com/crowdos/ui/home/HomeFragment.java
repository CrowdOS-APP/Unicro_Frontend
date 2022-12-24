package com.crowdos.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.crowdos.R;
import com.crowdos.databinding.FragmentHomeBinding;
/******************************************************************/
/*************************HOME*************************************/
/******************************************************************/
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ImageView Upload;
    @SuppressLint("StaticFieldLeak")
    public static MapView mMapView = null;
    BaiduMap mBaiduMap;
    LocationClient mLocationClient;  //定位客户端
    boolean isFirstLocate = true;

    TextView mapLocation;  //经纬度
    public double longitude;//经度
    public double latitude;//纬度


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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
        return root;
    }

    private void initLocation(View v) {  //初始化
        try {
            mLocationClient = new LocationClient(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLocationClient.registerLocationListener(new MyLocationListener());
        mMapView = v.findViewById(R.id.mapView2);
        mBaiduMap = mMapView.getMap();
        mapLocation = v.findViewById(R.id.location);

        LocationClientOption option = new LocationClientOption();
        //设置扫描时间间隔
        option.setScanSpan(1000);
        //设置定位模式，三选一
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置需要地址信息
        option.setIsNeedAddress(true);
        //保存定位参数
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    //内部类，百度位置监听器
    private class MyLocationListener  implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mapLocation.setText("经度:"+bdLocation.getLongitude()+"  纬度:"+bdLocation.getLatitude());
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation || bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
        }
    }
    private void navigateTo(BDLocation bdLocation) {
        if(isFirstLocate){
            LatLng ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//        if(MainActivity.isShowMap){
//            //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//            mMapView.onDestroy();
//            mLocationClient.stop();
//        }
//    }


}