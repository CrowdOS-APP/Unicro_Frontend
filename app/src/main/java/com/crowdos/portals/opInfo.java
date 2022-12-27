package com.crowdos.portals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.crowdos.portals.jsonFiles.emergencyList;
import com.crowdos.portals.jsonFiles.eventList;
import com.crowdos.portals.jsonFiles.followedEvents;
import com.crowdos.portals.jsonFiles.getComment;
import com.crowdos.portals.jsonFiles.getEventInfo;
import com.crowdos.portals.jsonFiles.getMyComment;
import com.crowdos.portals.jsonFiles.userInfo;
import com.crowdos.portals.jsonFiles.myEventList;
import com.crowdos.ui.welcome.event_Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class opInfo {

    //

    private static String scheme = "https";
    private static String hosts = "mock.apifox.cn";


    //我完全不想写注释了，你只用知道对于array返回的是List，不是array则返回对应数据结构的变量一个，哥们要猝了
    private static boolean isSucceed(String data){
        boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            result = jsonObject.getBoolean("isSucceed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static List<eventList> gEventList(String data){
        List<eventList> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                eventList container = new eventList();
                container.eventid = jsonObject.getLong("eventid");
                container.eventname = jsonObject.getString("eventname");
                container.latitude = jsonObject.getDouble("latitude");
                container.longitude = jsonObject.getDouble("longitude");
                container.emergency = jsonObject.getBoolean("emergency");
                result.add(container);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private static List<getComment> getComments(String data){
        List<getComment> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                getComment container = new getComment();
                container.content = jsonObject.getString("content");
                container.commentID = jsonObject.getLong("commentid");
                container.username = jsonObject.getString("username");
                result.add(container);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private static List<getMyComment> getMyComments(String data){
        List<getMyComment> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                getMyComment container = new getMyComment();
                container.content = jsonObject.getString("content");
                container.commentID = jsonObject.getLong("commentid");
                container.eventid = jsonObject.getLong("eventid");
                container.UID = jsonObject.getLong("UID");
                container.username = jsonObject.getString("username");
                result.add(container);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private static List<myEventList> getMyEventList(String data){
        List<myEventList> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                myEventList container = new myEventList();
                container.content = jsonObject.getString("content");
                container.emergency = jsonObject.getBoolean("emergency");
                container.eventid = jsonObject.getLong("eventid");
                container.eventname = jsonObject.getString("eventname");
                container.latitude = jsonObject.getDouble("latitude");
                container.longitude = jsonObject.getDouble("longitude");
                container.starttime = jsonObject.getLong("starttime");
                container.endtime = jsonObject.getLong("endtime");
                container.uid = jsonObject.getLong("uid");
                result.add(container);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private static List<followedEvents> getMyFollow(String data){
        List<followedEvents> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                followedEvents container = new followedEvents();
                container.eventid = jsonObject.getLong("eventid");
                container.content = jsonObject.getString("content");
                container.eventname = jsonObject.getString("eventname");
                container.latitude = jsonObject.getDouble("latitude");
                container.longitude = jsonObject.getDouble("longitude");
                result.add(container);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private static List<emergencyList> getEmergencyList(String data){
        List<emergencyList> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                emergencyList container = new emergencyList();
                container.eventid = jsonObject.getLong("eventid");
                container.content = jsonObject.getString("content");
                container.eventname = jsonObject.getString("eventname");
                container.latitude = jsonObject.getDouble("latitude");
                container.longitude = jsonObject.getDouble("longitude");
                container.isFollow = jsonObject.getBoolean("isFollowed");
                result.add(container);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private static getEventInfo getEventInfos(String data){
        getEventInfo result = new getEventInfo();
        try {
            JSONObject jsonObj = new JSONObject(data);
            result.content = jsonObj.getString("content");
            result.eventname = jsonObj.getString("eventName");
            result.endtime = jsonObj.getLong("endTime");
            result.starttime = jsonObj.getLong("startTime");
            result.latitude = jsonObj.getDouble("latitude");
            result.longitude = jsonObj.getDouble("longitude");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static userInfo getUserInfos(String data){
        userInfo result = new userInfo();
        try {
            JSONObject jsonObject = new JSONObject(data);
            result.signature = jsonObject.getString("signature");
            result.UID = jsonObject.getLong("UID");
            result.username = jsonObject.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**分割
     *
     *
     *
     */

    //获取用户个人信息
    public static void getUserInfo(String token){
        //直接初始化
        OkHttpClient gUserInfo = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.getUserInfo)
                .addQueryParameter("token",token)//在query加入token
                .build();
        Request request = new Request.Builder()
                .url("https://mock.apifox.cn/m1/1900041-0-default/getUserInfo?token=")
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "mock.apifox.cn")
                .addHeader("Connection", "keep-alive")
                .get()//利用get方法请求
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
            }
        });
    }//Tested

    //修改用户信息1
    public static void updateUserInfo(String username,String sign,String token){
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.updateUserInfo)
                .addQueryParameter("token",token)//在query加入token
                .build();
        RequestBody updateInfo = new FormBody.Builder()
                .add("username",username)
                .add("signature",sign)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .post(updateInfo)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               String data = response.body().string();
               event_Login.isSuccess = isSucceed(data);
            }
        });
    }//tested

    //获得我的评论(很奇怪，这里是所有评论的字符串组)
    public static void getMyComment(String token){
        final List<getMyComment>[] result = new List[]{null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.myComment)
                .addQueryParameter("token",token)//在query加入token
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
            }
        });
    }//tested

    //获得我的事件
    public static void gMyEventList(String token){
        final List<myEventList>[] result = new List[]{null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.myEventList)
                .addQueryParameter("token",token)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://mock.apifox.cn/m1/1900041-0-default/myEventList?token="+token)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "mock.apifox.cn")
                .addHeader("Connection", "keep-alive")
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();

            }
        });
    }//testify

    /*获得别人的事件
    public String gOtherEventList(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.otherEventList)
                .addQueryParameter("token",token)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                result[0] = response.body().string();
            }
        });
        return result[0];//array of eventlists,including name,id,place.
    }*/

    //获得关注列表
    public static void getFollowing(String token){
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.following)
                .addQueryParameter("token",token)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();

            }
        });
    }//testify


    //关注操作boolean
    public static void opFollow(String token,long uid,boolean isFollow){
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.opFollow)
                .addQueryParameter("token",token)
                .addQueryParameter("UID", String.valueOf(uid))
                .build();
        RequestBody opFollowing = new FormBody.Builder()
                .add("isFollow", String.valueOf(isFollow))
                .build();
        final boolean[] isSucceed = {false};
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .post(opFollowing)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();

            }
        });
    }//testify

    //搜索操作
    /*coming soon!*/
    /*public String opSearch(String key){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.otherEventList)
                .addQueryParameter("token",key)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m2/1900041-0-default/49791362")
                .post(RequestBody.create(MediaType.parse("key"),key))
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                result[0] = response.body().string();
            }
        });
        return result[0];//array of a list,including name,id,place,userlist etc.
    }*/


    //紧急事件列表
    public static void getEmergeEventList(double longitude,double latitude,String token){
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.gemergList)
                .addQueryParameter("token",token)
                .build();
        RequestBody place = new FormBody.Builder()
                .add("longitude", String.valueOf(longitude))
                .add("latitude", String.valueOf(latitude))
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getEmergencyList?token="+token)
                .post(place)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                Log.e("onResponse: ",data );
            }
        });
    }//not tested(apifox failed)


    //getting a single event
    public static void getEventsNearby(String token,double longitude,double latitude){

        final List<eventList>[] result = new List[]{null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.gEventList)
                .addQueryParameter("token",token)
                .addQueryParameter("longitude", String.valueOf(longitude))
                .addQueryParameter("latitude", String.valueOf(latitude))
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();

            }
        });
    }//testify

    //getting event details (listen while clicking for more information)2
    public static void getEventInfo(int eventID){
        final getEventInfo[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.gEventInfo)
                .addQueryParameter("eventID", String.valueOf(eventID))
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
            }
        });
    }//testify


    //upload events
    public static void upEvent(String token,
                          String title,String content,
                          double longitude,double latitude,
                          long startTime,long endTime){
        final boolean[] isSuccess = {false};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.upEventInfo)
                .addQueryParameter("token",token)
                .build();
        //eventID应该是名称
        RequestBody updateInfo = new FormBody.Builder()
                .add("title",title)
                .add("content",content)
                .add("startTime",String.valueOf(startTime))
                .add("endTime", String.valueOf(endTime))
                .add("longitude", String.valueOf(longitude))
                .add("latitude", String.valueOf(latitude))
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .post(updateInfo)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                Log.e("Onresponse", data);
                event_Login.isSuccess = isSucceed(data);
                Log.e("responding", ""+event_Login.isSuccess);
            }
        });
    }//tested failed(apifox failed)


    //getComment
    public static void getComment(String token, long eventID){
        final List<getComment>[] result = new List[]{null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.gComment)
                .addQueryParameter("token",token)
                .addQueryParameter("eventID", String.valueOf(eventID))
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                Log.e("data",data);
                event_Login.getCommen = getComments(data);
                Log.e("fuck",event_Login.getCommen.get(0).content);
            }
        });
    }//testify



    //upload Comments
    public void upComment(String token,long eveID,String comment){
        //eventID应该是名称
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.upComment)
                .addQueryParameter("token",token)
                .build();
        RequestBody updateInfo = new FormBody.Builder()
                .add("eventID", String.valueOf(eveID))
                .add("comment",comment)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .post(updateInfo)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
            }
        });
    }//testify

}
