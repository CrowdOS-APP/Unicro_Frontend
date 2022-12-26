package com.crowdos.ui.home;

import static com.crowdos.ui.home.HomeFragment.mBaiduMap;
import static com.crowdos.ui.home.HomeFragment.mMapView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;

public class MyLocationListener extends BDAbstractLocationListener {
    public static double latitude;
    public static double longitude;
    public static String address;
    @Override
    public void onReceiveLocation(BDLocation location) {
        //mapView 销毁后不在处理新接收的位置
        if (location == null || mMapView == null){
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        address = location.getAddrStr();
    }
}
