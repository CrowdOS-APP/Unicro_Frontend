package com.crowdos.ui.event;


import static com.crowdos.portals.opInfo.gEventInfo;
import static com.crowdos.portals.opInfo.getComments;
import static com.crowdos.portals.opInfo.uploadComment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.crowdos.portals.jsonFiles.getComment;
import com.crowdos.portals.jsonFiles.getEventInfo;

import java.util.ArrayList;
import java.util.List;

public class EventPageActivity extends AppCompatActivity {

    private TextView eventTitle;
    private TextView eventDescription;
    private TextView eventTime;
    private TextView eventFollowedText;

    private EditText eventComment;

    private ImageButton eventSendComment;
    private ImageButton eventLike;
    private ImageButton eventDislike;
    private ImageButton followEvent;

    private ImageView eventTypeImage;

    private UiSettings mUiSettings;

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private List<EventComment> mEventCommentList = new ArrayList<>();
    private ArrayList<LatLng> mLatLngs = new ArrayList<>();

    private String eventTitleString;
    private String eventDescriptionString;
    private String eventTimeString;

    private double eventLatitude;
    private double eventLongitude;
    private boolean isFollowedEvent;

    public static MapView mMapView = null;
    public static BaiduMap mBaiduMap = null;

    public static boolean eventType;
    public static int eventId;

    public static getEventInfo getEventInfoData = new getEventInfo();
    public static boolean isSuccess;
    public static List<getComment> getCommentData = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        gEventInfo(eventId);
        getComments(MainActivity.token,eventId);

        //简介+标题+时间展示
        eventTitle = findViewById(R.id.textView17);
        eventDescription = findViewById(R.id.textView24);
        eventTime = findViewById(R.id.textView31);
//        eventTitleString = getEventInfo(eventId).eventname;
//        eventDescriptionString = getEventInfo(eventId).content;
//        eventTimeString = getEventInfo(eventId).starttime + "-" + getEventInfo(eventId).endtime;
        eventTitle.setText(eventTitleString);
        eventDescription.setText(eventDescriptionString);
        eventTime.setText(eventTimeString);

        //发布评论
        eventComment = findViewById(R.id.event_comment);
        eventSendComment = findViewById(R.id.imageButton9);
        eventSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventCommentString = eventComment.getText().toString();
                if(eventCommentString != null){
                    EventComment mComment = new EventComment();
                    mComment.userNameString = "名字" + 50;
                    mComment.commentString = eventCommentString;
                    mComment.sculpture = 1;
                    mEventCommentList.add(mComment);
                    uploadComment(MainActivity.token,eventId,eventCommentString);
                    Toast.makeText(EventPageActivity.this, "已发送评论", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EventPageActivity.this, "不可以发空评论哦(●'◡'●)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //点赞+点踩
        eventLike =findViewById(R.id.imageButton2);
        eventDislike =findViewById(R.id.imageButton8);
        eventLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventPageActivity.this, "功能尚未开发，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        eventDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventPageActivity.this, "功能尚未开发，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        //回复
        mRecyclerView = findViewById(R.id.comment_view);
        for (int i = 0; i < 50; i++) {                              //构造一些数据
            EventComment mComment = new EventComment();
            mComment.userNameString = "名字" + i;
            mComment.commentString = "内容" + i;
            mComment.sculpture = 1;
            mEventCommentList.add(mComment);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(EventPageActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        //事件类型
        eventTypeImage = findViewById(R.id.imageView4);
        if(eventType){
            eventTypeImage.setImageResource(R.mipmap.position2);
        }
        else{
            eventTypeImage.setImageResource(R.mipmap.position);
        }

        //获取事件地理位置
        eventLatitude = 39.963175;
        eventLongitude = 116.400244;

        //展示地图
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EventPageActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            initLocation();
        }

        //关注按钮
        followEvent = findViewById(R.id.button6);
        eventFollowedText =findViewById(R.id.textView42);
        //isFollowedEvent = getEventInfo(eventId).;
        followEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFollowedEvent = !isFollowedEvent;
                if(isFollowedEvent){
                    followEvent.setBackground(ContextCompat.getDrawable(EventPageActivity.this,R.drawable.button_type4));
                    eventFollowedText.setText("已关注");
                    Toast.makeText(EventPageActivity.this, "已关注事件", Toast.LENGTH_SHORT).show();
                }
                else{
                    followEvent.setBackground(ContextCompat.getDrawable(EventPageActivity.this,R.drawable.button_type3));
                    eventFollowedText.setText("+关注");
                    Toast.makeText(EventPageActivity.this, "已取消关注", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //展示事件按钮颜色
        if(isFollowedEvent){
            followEvent.setBackground(ContextCompat.getDrawable(EventPageActivity.this,R.drawable.button_type4));
            eventFollowedText.setText("已关注");
        }
        else{
            followEvent.setBackground(ContextCompat.getDrawable(EventPageActivity.this,R.drawable.button_type3));
            eventFollowedText.setText("+关注");
        }
    }

    private void initLocation() {  //初始化
        mMapView = findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mMapView.showZoomControls(false);
        //实例化UiSettings类对象
        mUiSettings = mBaiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings.setCompassEnabled(true);
        //通过设置enable为true或false 选择是否显示比例尺
        mMapView.showScaleControl(true);
        //通过设置enable为true或false 选择是否启用地图旋转功能
        mUiSettings.setRotateGesturesEnabled(true);
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

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(EventPageActivity.this, R.layout.layout_displayer_comment, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            EventComment comment = mEventCommentList.get(position);
            holder.userName.setText(comment.userNameString);
            holder.comment.setText(comment.commentString);
            int sculptureNumber = chooseSculpture(comment.sculpture);
            holder.sculpture.setImageResource(sculptureNumber);
        }

        @Override
        public int getItemCount() {
            return mEventCommentList.size();
        }
    }

    private int chooseSculpture(int number){
        int mSculptureNumber = 0;
        switch (number){
            case 1: mSculptureNumber = R.mipmap.sculpture1; break;
            case 2: mSculptureNumber = R.mipmap.sculpture2; break;
            case 3: mSculptureNumber = R.mipmap.sculpture3; break;
            case 4: mSculptureNumber = R.mipmap.sculpture4; break;
            case 5: mSculptureNumber = R.mipmap.sculpture5; break;
            case 6: mSculptureNumber = R.mipmap.sculpture6; break;
        }
        return mSculptureNumber;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView comment;
        ImageView sculpture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView19);
            comment = itemView.findViewById(R.id.textView20);
            sculpture = itemView.findViewById(R.id.imageView19);
        }
    }
}