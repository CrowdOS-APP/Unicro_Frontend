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

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MultiPoint;
import com.baidu.mapapi.map.MultiPointItem;
import com.baidu.mapapi.map.MultiPointOption;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
/******************************************************************/
/*************************HOME*************************************/
/******************************************************************/
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ImageView Upload;
    public static MapView mMapView = null;
    public static BaiduMap mBaiduMap = null;
    private static SharedPreferences homePreferences;
    public LocationClient mLocationClient;
    private UiSettings mUiSettings;
    private MultiPoint mMultiPointA;
    private MultiPoint mMultiPointB;
    private MyLocationListener myLocationListener;

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
             * 海量点展示
             */
            // 海量点icon
            BitmapDescriptor bitmapA = BitmapDescriptorFactory.fromResource(R.mipmap.position);
            BitmapDescriptor bitmapB = BitmapDescriptorFactory.fromResource(R.mipmap.position2);
            //创建点列表
            List<LatLng> locationsNormal = new ArrayList<>();
            List<LatLng> locationsEmerge = new ArrayList<>();
            //本行为测试样例，需要将后端给的周围的事件展示出来
            locationsNormal.add(new LatLng(39.965,116.404));
            locationsNormal.add(new LatLng(39.925,116.454));
            locationsNormal.add(new LatLng(39.955,116.494));
            locationsEmerge.add(new LatLng(39.905,116.554));
            locationsEmerge.add(new LatLng(39.965,116.604));

            ArrayList<MultiPointItem> multiPointItemsNormal = new ArrayList<>();
            for (int i = 0; i < locationsNormal.size(); i++) {
            // 创建覆盖物单个点对象
            MultiPointItem multiPointItem = new MultiPointItem(locationsNormal.get(i));
            multiPointItemsNormal.add(multiPointItem);
            // 设置海量点数据
            MultiPointOption multiPointOption = new MultiPointOption();
            multiPointOption.setMultiPointItems(multiPointItemsNormal);
            multiPointOption.setIcon(bitmapA);
            // 添加海量点覆盖物
                mMultiPointA = (MultiPoint) mBaiduMap.addOverlay(multiPointOption);
            }

            ArrayList<MultiPointItem> multiPointItemsEmerge = new ArrayList<>();
            for (int i = 0; i < locationsEmerge.size(); i++) {
                // 创建覆盖物单个点对象
                MultiPointItem multiPointItem = new MultiPointItem(locationsEmerge.get(i));
                multiPointItemsEmerge.add(multiPointItem);
                // 设置海量点数据
                MultiPointOption multiPointOption = new MultiPointOption();
                multiPointOption.setMultiPointItems(multiPointItemsEmerge);
                multiPointOption.setIcon(bitmapB);
                // 添加海量点覆盖物
                mMultiPointB = (MultiPoint) mBaiduMap.addOverlay(multiPointOption);
            }
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
            mLocationClient.stop();
        }
        binding = null;
    }


}