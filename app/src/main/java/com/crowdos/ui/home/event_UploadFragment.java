package com.crowdos.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.crowdos.MainActivity;
import com.crowdos.R;

import java.util.ArrayList;


public class event_UploadFragment extends Fragment {

    private View root;
    public static MapView mMapView = null;
    public static BaiduMap mBaiduMap = null;
    public LocationClient mLocationClient;
    private UiSettings mUiSettings;
    private ArrayList<LatLng> mLatLngs = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(root == null)
        {
            root = inflater.inflate(R.layout.upload_page,container,false);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            initLocation(root);
        }
        return root;
    }

    private void initLocation(View v) {
        //初始化
        mMapView = v.findViewById(R.id.mapView4);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        //实例化UiSettings类对象
        mUiSettings = mBaiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings.setCompassEnabled(true);
        //通过设置enable为true或false 选择是否显示比例尺
        mMapView.showScaleControl(true);
        //通过设置enable为true或false 选择是否启用地图旋转功能
        mUiSettings.setRotateGesturesEnabled(true);

    }

    @Override
    public void onStart(){
        super.onStart();
        if(MainActivity.isShowMap) {
            mBaiduMap.setTrafficEnabled(true);
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

            LocationClient.setAgreePrivacy(true);
            try {
                mLocationClient = new LocationClient(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //通过LocationClientOption设置LocationClient相关参数
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);

            //设置locationClientOption
            mLocationClient.setLocOption(option);

            //注册LocationListener监听器
            MyLocationListener myLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myLocationListener);
            //开启地图定位图层
            mLocationClient.start();

            /**
             * 制作定位点
             */
            //定义Maker坐标点
            LatLng point = new LatLng(MyLocationListener.latitude, MyLocationListener.longitude);
            mLatLngs.add(point);
            setBounds(mLatLngs,0);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.position3);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions optionMarker = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(optionMarker);
        }
    }

    /**
     * 最佳视野内显示所有点标记
     */
    private void setBounds(ArrayList<LatLng> LatLngs , int paddingBottom ) {
        int padding = 80;
        // 构造地理范围对象
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        // 让该地理范围包含一组地理位置坐标
        builder.include(LatLngs);
        // 设置显示在指定相对于MapView的padding中的地图地理范围
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngBounds(builder.build(), padding, padding,
                padding, paddingBottom);
        // 更新地图
        mBaiduMap.setMapStatus(mapStatusUpdate);
        // 设置地图上控件与地图边界的距离，包含比例尺、缩放控件、logo、指南针的位置
        mBaiduMap.setViewPadding(0,0,0,paddingBottom);
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
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mLocationClient.stop();
    }
}