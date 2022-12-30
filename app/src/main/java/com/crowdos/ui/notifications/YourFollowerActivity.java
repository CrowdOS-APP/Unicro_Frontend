package com.crowdos.ui.notifications;

import static com.crowdos.portals.opInfo.getFollowing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.crowdos.portals.jsonFiles.followedEvents;
import com.crowdos.ui.event.EventPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class YourFollowerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private YourFollowerActivity.MyAdapter mMyAdapter;
    private List<YourFollower> yourFollowerList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    public static List<followedEvents> yourFollowerListData = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_follower);
        mRecyclerView = findViewById(R.id.your_followers_list);
        // 构造一些数据
        getFollowing(MainActivity.token);
        try{
            Thread.sleep(700);
        }catch (InterruptedException e){
            return;
        }
        for (int i = 0; i < yourFollowerListData.size(); i++) {
            YourFollower yourFollower = new YourFollower();
            yourFollower.eventName = yourFollowerListData.get(i).eventname;
            yourFollower.description = yourFollowerListData.get(i).content;
            yourFollower.eventType = yourFollowerListData.get(i).emergency;
            yourFollower.latitude = yourFollowerListData.get(i).latitude;
            yourFollower.eventId = yourFollowerListData.get(i).eventid;
            yourFollower.longitude = yourFollowerListData.get(i).longitude;
            yourFollower.startTime = yourFollowerListData.get(i).starttime;
            yourFollowerList.add(yourFollower);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        layoutManager = new LinearLayoutManager(YourFollowerActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        public MapView mMapView;
        public BaiduMap mBaiduMap = null;
        private UiSettings mUiSettings;
        private double eventLatitude;
        private double eventLongitude;
        private ArrayList<LatLng> mLatLngs = new ArrayList<>();
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(YourFollowerActivity.this, R.layout.layout_displayer_event, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            YourFollower yourFollower = yourFollowerList.get(position);
            holder.eventTitle.setText(yourFollower.eventName);
            holder.eventContent.setText(yourFollower.description);
            if(yourFollower.eventType){
                holder.eventType.setImageResource(R.mipmap.position2);
            }
            else{
                holder.eventType.setImageResource(R.mipmap.position);
            }

            //展示时间
            String startTime;
            startTime = getFormatDate(yourFollower.startTime);
            holder.eventTime.setText("开始时间:" + startTime);
            holder.mRootView.setOnClickListener(v -> {
                EventPageActivity.eventType = yourFollower.eventType;
                EventPageActivity.eventId = yourFollower.eventId;
                EventPageActivity.isJumpFromMainPage = false;
                Intent intent = new Intent(YourFollowerActivity.this, EventPageActivity.class);
                startActivity(intent);
            });

            //设置事件位置
            eventLatitude = yourFollower.latitude;
            eventLongitude = yourFollower.longitude;

            //展示地图
            if (ActivityCompat.checkSelfPermission(YourFollowerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(YourFollowerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                mMapView = holder.mMapView;
                initLocation();
            }
        }

        @Override
        public int getItemCount() {
            return yourFollowerList.size();
        }

        private void initLocation() {  //初始化
            mBaiduMap = mMapView.getMap();
            mMapView.showZoomControls(false);
            //实例化UiSettings类对象
            mUiSettings = mBaiduMap.getUiSettings();
            //禁用所有手势
            mUiSettings.setAllGesturesEnabled(false);
            //通过设置enable为true或false 选择是否显示比例尺
            mMapView.showScaleControl(false);
            //定义Maker坐标点
            LatLng point = new LatLng(eventLatitude, eventLongitude);
            mLatLngs.add(point);
            setBounds(mLatLngs,0);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.position3);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
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
    }

    public String getFormatDate(long times){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = formatter.format(times);
        return format;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView eventContent;
        TextView eventTime;
        ImageView eventType;
        ConstraintLayout mRootView;
        MapView mMapView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.textView19);
            eventContent = itemView.findViewById(R.id.textView20);
            eventTime = itemView.findViewById(R.id.textView28);
            eventType = itemView.findViewById(R.id.imageView21);
            mRootView = itemView.findViewById(R.id.item3);
            mMapView = itemView.findViewById(R.id.mapView5);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}