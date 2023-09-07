package com.crowdos.ui.dashboard;

import static com.crowdos.portals.opInfo.getEmergeEventList;
import static com.crowdos.portals.opInfo.hosts;
import static com.crowdos.portals.opInfo.scheme;
import static com.crowdos.portals.opUser.JSON;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
import com.crowdos.MainActivity;
import com.crowdos.R;
import com.crowdos.databinding.FragmentDynamicEmergeBinding;
import com.crowdos.portals.jsonFiles.emergencyList;
import com.crowdos.ui.Utils;
import com.crowdos.ui.event.EventPageActivity;
import com.crowdos.ui.home.MyLocationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/******************************************************************/
/*************************DYNAMIC**********************************/
/******************************************************************/
public class DashboardFragment extends Fragment {

    private FragmentDynamicEmergeBinding binding;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private List<EmergeEvent> emergeEventList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private TextView receivedComment;
    private TextView myFollows;

    public static List<emergencyList> emergeEventListData = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDynamicEmergeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRecyclerView = root.findViewById(R.id.emerge_list);
        // 构造一些数据
        getEmergeEventList(MyLocationListener.longitude,MyLocationListener.latitude, MainActivity.token);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ignored){
        }
        for (int i = 0; i < emergeEventListData.size(); i++) {
            EmergeEvent emergeEvent = new EmergeEvent();
            Date date = new Date();
            long time = date.getTime();
            emergeEvent.endTime = emergeEventListData.get(i).endtime;
            if(emergeEvent.endTime < time){
                continue;
            }
            emergeEvent.eventName = emergeEventListData.get(i).eventname;
            emergeEvent.description = emergeEventListData.get(i).content;
            emergeEvent.eventId = emergeEventListData.get(i).eventid;
            emergeEvent.eventType = true;
            emergeEvent.isFollowed = emergeEventListData.get(i).isFollow;
            emergeEvent.latitude = emergeEventListData.get(i).latitude;
            emergeEvent.longitude = emergeEventListData.get(i).longitude;
            emergeEvent.startTime = emergeEventListData.get(i).starttime;
            emergeEventList.add(emergeEvent);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        //回复我的
        receivedComment = root.findViewById(R.id.textView16);
        receivedComment.setOnClickListener(v ->
                Toast.makeText(getContext(), "前方的世界下次再来探索吧ヾ(•ω•`)o", Toast.LENGTH_SHORT).show()
        );

        //我的关注
        myFollows = root.findViewById(R.id.textView17);
        myFollows.setOnClickListener(v ->
                Toast.makeText(getContext(), "前方的世界下次再来探索吧ヾ(•ω•`)o", Toast.LENGTH_SHORT).show()
        );
        return root;
    }



    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        public MapView mMapView;
        public BaiduMap mBaiduMap = null;
        private UiSettings mUiSettings;
        private double eventLatitude;
        private double eventLongitude;
        private ArrayList<LatLng> mLatLngs = new ArrayList<>();
        private boolean isSuccess;
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
            holder.mRootView.setOnClickListener(v -> {
                EventPageActivity.eventType = emergeEvent.eventType;
                EventPageActivity.eventId = emergeEvent.eventId;
                EventPageActivity.isJumpFromMainPage = false;
                Intent intent = new Intent(getActivity(), EventPageActivity.class);
                startActivity(intent);
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
            holder.eventFollow.setOnClickListener(v -> {
                if(Utils.isFastClick()){
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
                    //在这个地方需要向后端传入当前的eventId，token，和目前的isFollowed
                    opFollow(MainActivity.token, emergeEvent.eventId, emergeEvent.isFollowed);
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        return;
                    }
                    if(!isSuccess){
                        Toast.makeText(getContext(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "您的手速太快辣w(ﾟДﾟ)w", Toast.LENGTH_SHORT).show();
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

        //关注操作boolean
        public void opFollow(String token,long uid,boolean isFollow){
            HttpUrl url = new HttpUrl.Builder()
                    .scheme(scheme)
                    .host(hosts)
                    .addPathSegment(com.crowdos.portals.url.opFollow)
                    .addQueryParameter("token",token)
                    .addQueryParameter("eventID", String.valueOf(uid))
                    .build();
            JSONObject json = new JSONObject();
            try {
                json.put("isFollow", String.valueOf(isFollow));
            }catch (Exception e){
                e.printStackTrace();
            }
            RequestBody opFollowing = RequestBody.create(JSON,String.valueOf(json));
            final boolean[] isSucceed = {false};
            OkHttpClient gUserInfo = new OkHttpClient();
            Request request = new Request.Builder().url(url)
                    .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "*/*")
                    .addHeader("Host", "$HOST")
                    .addHeader("Connection", "keep-alive")
                    .post(opFollowing)
                    .build();
            gUserInfo.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String data = response.body().string();
                    Log.e("response",data);
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        isSuccess = jsonObject.getBoolean("isSucceed");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }//testify
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