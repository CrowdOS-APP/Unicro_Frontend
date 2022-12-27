package com.crowdos.ui.home;

import static com.crowdos.portals.opInfo.getEventsNearby;

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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MultiPoint;
import com.baidu.mapapi.map.MultiPointItem;
import com.baidu.mapapi.map.MultiPointOption;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentHomeBinding;
import com.crowdos.portals.jsonFiles.eventList;
import com.crowdos.ui.event.EventPageActivity;

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
    private ArrayList<LatLng> mLatLngs = new ArrayList<>();
    private ArrayList<EventNearby> eventNearbyList = new ArrayList<>();

    TextView mapLocation;  //经纬度
    public static double longitude;//经度
    public static double latitude;//纬度
    public static List<eventList> getEventListData = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getEventsNearby(MainActivity.token, longitude, latitude);
        double a = 160;
        double b = 40;
        boolean c = false;
        //构造一些数据
        for (int i = 0; i < 10; i++){
            EventNearby eventNearby = new EventNearby();
            eventNearby.eventId = i;
            eventNearby.longitude = a++;
            eventNearby.latitude = b++;
            eventNearby.EventType = c;
            c= !c;
            eventNearbyList.add(eventNearby);
        }
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

            //定义Maker坐标点
            LatLng point = new LatLng(MyLocationListener.latitude, MyLocationListener.longitude);
            mLatLngs.add(point);
            setBounds(mLatLngs,0);

            /**
             * 海量点展示
             */
            // 海量点icon
            BitmapDescriptor bitmapA = BitmapDescriptorFactory.fromResource(R.mipmap.position);
            BitmapDescriptor bitmapB = BitmapDescriptorFactory.fromResource(R.mipmap.position2);
            //创建点列表
            List<EventPack> locationsNormal = new ArrayList<>();
            List<EventPack> locationsEmerge = new ArrayList<>();

            //本行为测试样例，需要将后端给的周围的事件展示出来
            for(int i = 0; i < eventNearbyList.size(); i++){
                double latitude = eventNearbyList.get(i).latitude;
                double longitude = eventNearbyList.get(i).longitude;
                LatLng latLng = new LatLng(latitude, longitude);
                EventPack eventPack = new EventPack();
                eventPack.latLng = latLng;
                //创建传递参数
                Bundle mBundle = new Bundle();
                mBundle.putInt("eventId",eventNearbyList.get(i).eventId);
                eventPack.eventId = mBundle;
                if(eventNearbyList.get(i).EventType){
                    locationsEmerge.add(eventPack);
                }
                else{
                    locationsNormal.add(eventPack);
                }
            }

            /**普通事件**/
            ArrayList<MultiPointItem> multiPointItemsNormal = new ArrayList<>();
            for (int i = 0; i < locationsNormal.size(); i++) {
                // 创建覆盖物单个点对象
                MultiPointItem multiPointItem = new MultiPointItem(locationsNormal.get(i).latLng);
                multiPointItemsNormal.add(multiPointItem);
                // 设置海量点数据
                MultiPointOption multiPointOptionA = new MultiPointOption();
                multiPointOptionA.setMultiPointItems(multiPointItemsNormal);
                multiPointOptionA.setIcon(bitmapA);
                // 添加海量点覆盖物
                mMultiPointA = (MultiPoint) mBaiduMap.addOverlay(multiPointOptionA);
                //传参
                mMultiPointA.setExtraInfo(locationsNormal.get(i).eventId);
            }

            /**紧急事件**/
            ArrayList<MultiPointItem> multiPointItemsEmerge = new ArrayList<>();
            for (int i = 0; i < locationsEmerge.size(); i++) {
                // 创建覆盖物单个点对象
                MultiPointItem multiPointItem = new MultiPointItem(locationsEmerge.get(i).latLng);
                multiPointItemsEmerge.add(multiPointItem);
                // 设置海量点数据
                MultiPointOption multiPointOptionB = new MultiPointOption();
                multiPointOptionB.setMultiPointItems(multiPointItemsEmerge);
                multiPointOptionB.setIcon(bitmapB);
                // 添加海量点覆盖物
                mMultiPointB = (MultiPoint) mBaiduMap.addOverlay(multiPointOptionB);
                //传参
                mMultiPointB.setExtraInfo(locationsEmerge.get(i).eventId);
            }

            //点击地图事件
            mBaiduMap.setOnMultiPointClickListener(new BaiduMap.OnMultiPointClickListener() {
                @Override
                public boolean onMultiPointClick(MultiPoint multiPoint, MultiPointItem multiPointItem) {
                    Bundle bundle = multiPoint.getExtraInfo();
                    int eventId = bundle.getInt("eventId");
                    EventPageActivity.eventId = eventId;
                    Intent intent = new Intent(getContext(), EventPageActivity.class);
                    startActivity(intent);
                    return true;
                }
            });

        }
    }

    private class EventPack{
        LatLng latLng;
        Bundle eventId;
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
        if(MainActivity.isShowMap){
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
            mLocationClient.stop();
        }
        binding = null;
    }


}