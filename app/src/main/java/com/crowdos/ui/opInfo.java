package com.crowdos.ui;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class opInfo {

    private String scheme = "https";
    private String hosts = "mock.apifox.cn";

    //获取用户个人信息(此处存疑，get方法需要调用.addHeader方法来进行头部验证，我不清楚是不是把token放在这里，不过不难改)
    public String getUserInfo(String token){
        //直接初始化
        final String[] userInfo = {null};
        OkHttpClient gUserInfo = new OkHttpClient();
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.getUserInfo)
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
                userInfo[0] = response.body().string();
            }
        });
        return userInfo[0];
    }

    //修改用户信息
    public String updateUserInfo(String username,String sign,String token){
        //直接初始化
        final String[] isSuccess = {null};
        //问题同上且UID应该改变不了
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.updateUserInfo)
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
               isSuccess[0] = response.body().string();
            }
        });
        return isSuccess[0];
    }



    //获得我的评论(很奇怪，这里是所有评论的字符串组)
    public String gMycomment(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.myComment)
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
                result[0] = response.body().string();
            }
        });
        return result[0];
    }


    //获得我的事件
    public String gMyEventList(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.myEventList)
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
    }


    //获得别人的事件
    public String gOtherEventList(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.otherEventList)
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
    }


    //获得关注列表
    public String gFollowing(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.following)
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
                result[0] = response.body().string();
            }
        });
        return result[0];//array of integer
    }


    //关注操作(这里的UID是Int，需转)
    public String opFollow(String token,String uid,boolean isFollow){
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.opFollow)
                .addQueryParameter("token",token)
                .addQueryParameter("UID",uid)
                .build();
        RequestBody opFollowing = new FormBody.Builder()
                .add("isFollow", String.valueOf(isFollow))
                .build();
        final String[] isSucceed = {null};
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
                isSucceed[0] = response.body().string();
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
                .addPathSegment(com.crowdos.ui.url.otherEventList)
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
    public String gEmergEventList(long longitude,long latitude){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.gemergList)
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
                result[0] = response.body().string();
            }
        });
        return result[0];//array of eventlists,including name,id,place.
    }


    //getting a single event
    public String gEventList(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.gEventList)
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
    }

    //getting event details (listen while clicking for more information)
    public String gEventInfo(int eventID){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.otherEventList)
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
                result[0] = response.body().string();
            }
        });
        return result[0];//details of a single event
    }


    //upload events
    public String upEvent(String token,long eveID,String content,double longitude,double latitude,
                          long startTime,long endTime){
        final String[] isSuccess = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.upEventInfo)
                .addQueryParameter("token",token)
                .addQueryParameter("eventID", String.valueOf(eveID))
                .build();
        //eventID应该是名称
        RequestBody updateInfo = new FormBody.Builder()
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
                isSuccess[0] = response.body().string();
            }
        });
        return isSuccess[0];
    }


    //getComment
    public String gComment(String token){
        final String[] result = {null};
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.gComment)
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
        return result[0];
    }

    //upload Comments
    public String upComment(String token,String eveID,String comment){
        final String[] isSuccess = {null};
        //eventID应该是名称
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(hosts)
                .addPathSegment(com.crowdos.ui.url.upComment)
                .addQueryParameter("token",token)
                .build();
        RequestBody updateInfo = new FormBody.Builder()
                .add("eventID",eveID)
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
                isSuccess[0] = response.body().string();
            }
        });
        return isSuccess[0];
    }

}
