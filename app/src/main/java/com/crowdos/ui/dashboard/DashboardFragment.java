package com.crowdos.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.crowdos.R;
import com.crowdos.databinding.FragmentDynamicEmergeBinding;
import com.crowdos.portals.jsonFiles.emergencyList;
import com.crowdos.ui.event.EventPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/******************************************************************/
/*************************DYNAMIC**********************************/
/******************************************************************/
public class DashboardFragment extends Fragment {

    private FragmentDynamicEmergeBinding binding;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private List<EmergeEvent> emergeEventList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    public List<emergencyList> emergeEventListData = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDynamicEmergeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRecyclerView = root.findViewById(R.id.emerge_list);
        // 构造一些数据
        for (int i = 0; i < 50; i++) {
            EmergeEvent emergeEvent = new EmergeEvent();
            emergeEvent.eventName = "标题" + i;
            emergeEvent.description = "内容" + i;
            emergeEvent.eventType = true;
            emergeEvent.isFollowed = true;
            emergeEvent.latitude = 39.963175;
            emergeEvent.longitude = 116.400244;
            emergeEventList.add(emergeEvent);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        return root;
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
            View view = View.inflate(getContext(), R.layout.layout_displayer_event_emerge, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            EmergeEvent emergeEvent = emergeEventList.get(position);
            holder.eventTitle.setText(emergeEvent.eventName);
            holder.eventContent.setText(emergeEvent.description);
            if(emergeEvent.eventType){
                holder.eventType.setImageResource(R.mipmap.position2);
            }
            else{
                holder.eventType.setImageResource(R.mipmap.position);
            }

            //展示时间
            String startTime;
            startTime = getFormatDate(emergeEvent.startTime);
            holder.eventTime.setText("开始时间:"+startTime);

            //点击事件
            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventPageActivity.eventType = emergeEvent.eventType;
                    EventPageActivity.eventId = emergeEvent.eventId;
                    Intent intent = new Intent(getActivity(), EventPageActivity.class);
                    startActivity(intent);
                }
            });

            //设置事件位置
            eventLatitude = emergeEvent.latitude;
            eventLongitude = emergeEvent.longitude;

            //展示地图
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                mMapView = holder.mMapView;
                initLocation();
            }

            //点击关注按钮
            holder.eventFollow.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    emergeEvent.isFollowed = !emergeEvent.isFollowed;
                    if(emergeEvent.isFollowed) {
                        holder.eventFollow.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_type4));
                        holder.eventFollowText.setText("已关注");
                        Toast.makeText(getContext(), "已关注" + emergeEvent.eventName, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        holder.eventFollow.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_type3));
                        holder.eventFollowText.setText("+关注");
                        Toast.makeText(getContext(), "已取消关注" + emergeEvent.eventName, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //展示事件按钮颜色
            if(emergeEvent.isFollowed) {
                holder.eventFollow.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_type4));
                holder.eventFollowText.setText("已关注");
            }
            else{
                holder.eventFollow.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.button_type3));
                holder.eventFollowText.setText("+关注");
            }
        }

        @Override
        public int getItemCount() {
            return emergeEventList.size();
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
        TextView eventFollowText;
        ImageView eventType;
        ConstraintLayout mRootView;
        MapView mMapView;
        ImageButton eventFollow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.textView19);
            eventContent = itemView.findViewById(R.id.textView20);
            eventTime = itemView.findViewById(R.id.textView28);
            eventType = itemView.findViewById(R.id.imageView24);
            mRootView = itemView.findViewById(R.id.item4);
            mMapView = itemView.findViewById(R.id.mapView6);
            eventFollow = itemView.findViewById(R.id.button2);
            eventFollowText = itemView.findViewById(R.id.textView38);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}