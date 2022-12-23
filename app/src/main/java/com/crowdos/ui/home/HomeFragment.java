package com.crowdos.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.MyLocationStyle;
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
        //获取地图控件引用
        mMapView = root.findViewById(R.id.mapView2);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        //初始化地图控制器对象
        AMap aMap = null;
        if (aMap == null) {
            aMap = mMapView.getMap();
            //对地图上的控件的管理
            UiSettings settings =  aMap.getUiSettings();

            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(true);
            //添加指南针
            settings.setCompassEnabled(true);

            //管理缩放控件
            settings.setZoomControlsEnabled(true);
            //设置logo位置，左下，底部居中，右下（隐藏logo：settings.setLogoLeftMargin(9000)）
            settings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
            //设置显示地图的默认比例尺
            settings.setScaleControlsEnabled(true);
            settings.setAllGesturesEnabled(true);//所有手势的开关
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        }
        MyLocationStyle myLocationStyle; //初始化
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        /*
         初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
          连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）
          如果不设置myLocationType，默认也会执行此种模式。
         */
        myLocationStyle.interval(1000);//设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}