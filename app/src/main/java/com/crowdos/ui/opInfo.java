package com.crowdos.ui;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class opInfo {

    //获取用户个人信息(此处存疑，get方法需要调用.addHeader方法来进行头部验证，我不清楚是不是把token放在这里，不过不难改)
    public String getUserInfo(String token){
        //直接初始化
        final String[] userInfo = {null};
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getUserInfo")
                .post(RequestBody.create(MediaType.parse("String"),token))//新建一个token
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
    public String updateUserInfo(String token,String avtUrl,String sign,String place){
        //直接初始化
        final String[] isSuccess = {null};
        //问题同上且UID应该改变不了
        RequestBody updateInfo = new FormBody.Builder()
                .add("token",token)
                .add("avatarUrl",avtUrl)
                .add("signature",sign)
                .add("place",place)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getUserInfo")
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
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/myEventList?apifoxApiId=49789888")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/myEventList?apifoxApiId=49790847")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/myEventList?apifoxApiId=49790847")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/following")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
        RequestBody opFollowing = new FormBody.Builder()
                .add("token",token)
                .add("UID",uid)
                .build();
        final String[] isSucceed = {null};
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/following")
                .post(opFollowing)
                .post(null)//boolean型似乎无法传入，只能使用String
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
    public String opSearch(String key){
        final String[] result = {null};
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
    }


    //紧急事件列表
    public String gEmergEventList(String token){
        final String[] result = {null};
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getEmergencyList")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getEventList")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
    public String gEventInfo(String eventID){
        final String[] result = {null};
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getEvenInfo")
                .post(RequestBody.create(MediaType.parse("id"),eventID))
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
    public String upEvent(String token,String eveID,String content,String place,String time){
        final String[] isSuccess = {null};
        //eventID应该是名称
        RequestBody updateInfo = new FormBody.Builder()
                .add("token",token)
                .add("eventID",eveID)
                .add("time",time)
                .add("place",place)
                .add("content",content)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/uploadEvenInfo")
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
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/getComment")
                .post(RequestBody.create(MediaType.parse("token"),token))
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
        RequestBody updateInfo = new FormBody.Builder()
                .add("token",token)
                .add("eventID",eveID)
                .add("comment",comment)
                .build();
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder().url("https://mock.apifox.cn/m2/1900041-0-default/49790085")
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
