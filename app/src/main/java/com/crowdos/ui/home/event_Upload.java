package com.crowdos.ui.home;

import static com.crowdos.ui.home.event_UploadFragment.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crowdos.MainActivity;
import com.crowdos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class event_Upload extends AppCompatActivity {

    private boolean isChooseEventType = false;
    private boolean isSetStartTime = false;
    private boolean isSetEndTime = false;
    //boolean isUploadPicture = false;
    private AlertDialog alertDialog2;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private static final String TAG = "tzbc";
    private String[] getTime = {"00:00","00:00"};
    private String[] getDate = {"2000-01-01","2000-01-01"};
    private TextView startDate,endDate, startTime, endTime;


    public boolean EventType;//true代表紧急事件，false代表普通事件
    public long unixStartTime;
    public long unixEndTime;
    public String description;
    public String title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_upload);

        startDate = findViewById(R.id.textView32);
        endDate = findViewById(R.id.textView33);
        startTime = findViewById(R.id.textView7);
        endTime = findViewById(R.id.textView8);

        Button upload_Pictures = findViewById(R.id.add_pic);
        TextView upload_Description_num = findViewById(R.id.textView30);
        EditText upload_Description = findViewById(R.id.upload_description);

        TextView upload_Title_num = findViewById(R.id.textView29);
        EditText upload_Title = findViewById(R.id.upload_title);

        ImageButton upload_event = findViewById(R.id.imageButton5);
        ImageButton upload_StartTime = findViewById(R.id.imageButton6);
        ImageButton upload_EndTime = findViewById(R.id.imageButton7);
        ImageButton upload_locate = findViewById(R.id.imageButton4);

        /*************<时间显示>******************/
        //日历初始化
        calendar = Calendar.getInstance();
        upload_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSetStartTime = onClickTime(v);
            }
        });
        upload_EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSetEndTime = onClickTime(v);
            }
        });
        /*************<时间显示>******************/

        /*************<相册>******************/
        //coming soon
        upload_Pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(event_Upload.this, "功能尚未开放，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        /*************<相册>******************/


        /*************<标题和简介>******************/
        upload_Title.addTextChangedListener(new TextWatcher() {
            final int num = 40;
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum= s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                upload_Title_num.setText("" + number);
                selectionStart=upload_Title.getSelectionStart();
                selectionEnd = upload_Title.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    upload_Title.setText(s);
                    upload_Title.setSelection(tempSelection);//设置光标在最后
                }
            }

        });
        

        upload_Description.addTextChangedListener(new TextWatcher() {
            final int num = 500;
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum= s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                upload_Description_num.setText("" + number);
                selectionStart=upload_Description.getSelectionStart();
                selectionEnd = upload_Description.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    upload_Description.setText(s);
                    upload_Description.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
        /*************<标题和简介>******************/




        /*************<地图>******************/
        upload_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        /*************<地图>******************/


        /*************<上传>******************/
        upload_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = upload_Description.getText().toString();
                title = upload_Title.getText().toString();
                timeChangeUnix();
                if(isChooseEventType && isSetStartTime && isSetEndTime && unixEndTime >= unixStartTime) {
                    //此处打包信息上传至服务器
                    Toast.makeText(event_Upload.this, "事件已上传", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(event_Upload.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(!isChooseEventType)
                {
                    Toast.makeText(event_Upload.this, "未选择事件类型", Toast.LENGTH_SHORT).show();
                }
                else if(!isSetStartTime){
                    Toast.makeText(event_Upload.this, "未设置起始时间", Toast.LENGTH_SHORT).show();
                }
                else if(!isSetEndTime){
                    Toast.makeText(event_Upload.this, "未设置终止时间", Toast.LENGTH_SHORT).show();
                }
                else if(unixEndTime < unixStartTime){
                    Toast.makeText(event_Upload.this, "终止时间需晚于起始时间", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*************<上传>******************/
    }

    /*************<类型>******************/
    public void showSingleAlertDialog(View view){
        final String[] items = {"紧急事件", "普通事件"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("事件类型");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0)
                    EventType = true;
                else{
                    EventType = false;
                }
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isChooseEventType = true;
                alertDialog2.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }
    /*************<类型>******************/


    /*************<时间>******************/
    @SuppressLint("NonConstantResourceId")
    public boolean onClickTime(View v) {
        switch (v.getId()) {
            case R.id.imageButton6:
                showTimeDialog(false);
                showCalenderDialog(false);
                break;
            case R.id.imageButton7:
                showTimeDialog(true);
                showCalenderDialog(true);
                break;
        }
        return true;
    }

    private void showTimeDialog(boolean chooseTimeType) {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time ;
                if(hourOfDay < 10 && minute < 10){
                    time = "0" + hourOfDay + ":0" + minute;
                }
                else if(hourOfDay < 10){
                    time = "0" + hourOfDay + ":" + minute;
                }
                else if(minute < 10){
                    time = hourOfDay + ":0" + minute;
                }
                else{
                    time = hourOfDay + ":" + minute;
                }

                if(chooseTimeType){
                    getTime[1] = time;
                    endTime.setText("终止时间"+ getTime[1]);
                }
                else{
                    getTime[0] = time;
                    startTime.setText("起始时间"+ getTime[0]);
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();

    }

    private void showCalenderDialog(boolean chooseTimeType) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String calender;
                if(month < 10 && dayOfMonth <10){
                    calender = year + "-0" + (month + 1) + "-0" + dayOfMonth;
                }
                else if(dayOfMonth < 10){
                    calender = year + "-" + (month + 1) + "-0" + dayOfMonth;
                }
                else if(month < 10) {
                    calender = year + "-0" + (month + 1) + "-" + dayOfMonth;
                }
                else{
                    calender = year + "-" + (month + 1) + "-" + dayOfMonth;
                }

                if(chooseTimeType){
                    getDate[1] = calender;
                    endDate.setText(getDate[1]);
                }
                else{
                    getDate[0] = calender;
                    startDate.setText(getDate[0]);
                }


            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //时间转换
    private void timeChangeUnix() {
        String startTimeUnixString = getDate[0] + " " + getTime[0];
        String endTimeUnixString = getDate[1] + " " + getTime[1];
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateStart = null;
        {
            try {
                dateStart = ft1.parse(startTimeUnixString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Date dateEnd = null;
        {
            try {
                dateEnd = ft2.parse(endTimeUnixString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        unixStartTime = dateStart.getTime();
        unixEndTime = dateEnd.getTime();
    }
    /*************<时间>******************/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

}