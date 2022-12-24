package com.crowdos.portals;

import androidx.annotation.NonNull;

import com.crowdos.portals.jsonFiles.eventList;
import com.crowdos.portals.jsonFiles.followedEvents;
import com.crowdos.portals.jsonFiles.getComment;
import com.crowdos.portals.jsonFiles.getEventInfo;
import com.crowdos.portals.jsonFiles.getMyComment;
import com.crowdos.portals.jsonFiles.getUserInfo;
import com.crowdos.portals.jsonFiles.myEventList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    private String scheme = "https";
    private String hosts = "mock.apifox.cn";


    //我完全不想写注释了，你只用知道对于array返回的是List，不是array则返回对应数据结构的变量一个，哥们要猝了
    private boolean isSucceed(String data){
        boolean result = false;
        try {
            JSONArray jsonArray = new JSONArray(data);
            String isSucceed = "false";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                isSucceed = jsonObject.getString("isSucceed");
            }
            if (isSucceed.equals("true")){
                result = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<eventList> gEventList(String data){
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<eventList> eventLists = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            eventList eventList = gson.fromJson(user, eventList.class);
            eventLists.add(eventList);
        }
        return eventLists;
    }

    private List<getComment> getComments(String data){
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<getComment> commentsLists = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            getComment eventList = gson.fromJson(user, getComment.class);
            commentsLists.add(eventList);
        }
        return commentsLists;
    }

    private List<getMyComment> getMyComments(String data){
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<getMyComment> commentsLists = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            getMyComment eventList = gson.fromJson(user, getMyComment.class);
            commentsLists.add(eventList);
        }
        return commentsLists;
    }

    private List<myEventList> getMyEventList(String data){
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<myEventList> eventLists = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            myEventList eventList = gson.fromJson(user, myEventList.class);
            eventLists.add(eventList);
        }
        return eventLists;
    }

    private List<followedEvents> getMyFollow(String data){
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<followedEvents> eventLists = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            followedEvents eventList = gson.fromJson(user, followedEvents.class);
            eventLists.add(eventList);
        }
        return eventLists;
    }


    private getEventInfo getEventInfos(String data){
        getEventInfo result = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.content = jsonObject.getString("content");
                result.eventname = jsonObject.getString("eventname");
                result.endtime = Long.valueOf(jsonObject.getString("endtime"));
                result.starttime = Long.valueOf(jsonObject.getString("starttime"));
                result.latitude = Double.valueOf(jsonObject.getString("latitude"));
                result.longitude = Double.valueOf(jsonObject.getString("longitude"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private getUserInfo getUserInfos(String data){
        getUserInfo result = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                result.signature = jsonObject.getString("signature");
                result.UID = Long.valueOf(jsonObject.getString("UID"));
                result.username = jsonObject.getString("username");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }





    //获取用户个人信息
    public getUserInfo getUserInfo(String token){
        //直接初始化
        final getUserInfo[] userInfo = {null};
        OkHttpClient gUserInfo = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.getUserInfo)
                .addQueryParameter("token",token)//在query加入token
                .build();
        Request request = new Request.Builder().url(url)
                .get()//利用get方法请求
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                userInfo[0] = getUserInfos(data);
            }
        });
        return userInfo[0];
    }

    //修改用户信息
    public boolean updateUserInfo(String username,String sign,String token){
        //直接初始化
        final boolean[] isSuccess = {false};
        //问题同上且UID应该改变不了
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
               isSuccess[0] = isSucceed(data);
            }
        });
        return isSuccess[0];
    }

    //获得我的评论(很奇怪，这里是所有评论的字符串组)
    public List<getMyComment> gMycomment(String token){
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
                result[0] = getMyComments(data);
            }
        });
        return result[0];
    }


    //获得我的事件
    public List<myEventList> gMyEventList(String token){
        final List<myEventList>[] result = new List[]{null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.portals.url.myEventList)
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
                String data = response.body().string();
                result[0] = getMyEventList(data);
            }
        });
        return result[0];//array of eventlists,including name,id,place.
    }


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
    public List<followedEvents> gFollowing(String token){
        final List<followedEvents>[] result = new List[]{null};
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
                result[0] = getMyFollow(data);
            }
        });
        return result[0];//array of integer
    }


    //关注操作(这里的UID是Int，需转)
    public boolean opFollow(String token,long uid,boolean isFollow){
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
                isSucceed[0] = isSucceed(data);
            }
        });
        return isSucceed[0];//array of integer
    }

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
    public List<eventList> gEmergeEventList(double longitude,double latitude,String token){
        final List<eventList>[] result = new List[]{null};
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
        Request request = new Request.Builder().url(url)
                .post(place)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                result[0] = gEventList(data);
            }
        });
        return result[0];//array of eventlists,including name,id,place.
    }


    //getting a single event
    public List<eventList> gEventsNearby(String token,double longitude,double latitude){

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
                result[0] = gEventList(data);
            }
        });
        return result[0];//array of eventlists,including name,id,place.
    }

    //getting event details (listen while clicking for more information)2
    public getEventInfo gEventInfo(int eventID){
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
                result[0] = getEventInfos(data);
            }
        });
        return result[0];//details of a single event
    }


    //upload events
    public boolean upEvent(String token,
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
                isSuccess[0] = isSucceed(data);
            }
        });
        return isSuccess[0];
    }


    //getComment
    public List<getComment> gComment(String token,long eventID){
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
                result[0] = getComments(data);
            }
        });
        return result[0];
    }



    //upload Comments
    public boolean upComment(String token,long eveID,String comment){
        final boolean[] isSuccess = {false};
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
                isSuccess[0] = isSucceed(data);
            }
        });
        return isSuccess[0];
    }

}
