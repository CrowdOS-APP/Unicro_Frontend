package com.crowdos.portals;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*这个文件主要负责进行用户账号操作*/
public class opUser {
    private static boolean isSucceed(String data){
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
    private static String receivedToken(String data){
        String token = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                token = jsonObject.getString("token");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;

    }
    /*以下是登录函数2*/
    public static String userLogin(String username,String pwd){
        //结果变量(token)
        final String[] token = {null};
        //建立url
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("mock.apifox.cn")
                .port(8888)
                .addPathSegment(com.crowdos.portals.url.userLogin)
                .build();
        //新建请求体
        RequestBody loginPac = new FormBody.Builder().add("email",username).add("passwd",pwd).build();
        //采用异步
        OkHttpClient loginAct = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(loginPac).build();
        loginAct.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                token[0] = receivedToken(data);
            }
        });
        return token[0];
    }


    /*注册函数*/
    public static boolean userRegister(String username,String pwd){
        //结果变量()
        final boolean[] judge = {false};
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("mock.apifox.cn")
                .addPathSegment(com.crowdos.portals.url.userRegister)
                .build();
        //新建请求体
        RequestBody registerPac = new FormBody.Builder().add("email",username).add("passwd",pwd).build();
        //采用异步
        OkHttpClient loginAct = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(registerPac).build();
        loginAct.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                judge[0] = isSucceed(data);

            }
        });
        return judge[0];
    }

    //修改密码
    public static boolean updatePasswd(String token, String oldPwd, String pwd){
        final boolean[] isSucceed = {false};
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("mock.apifox.cn")
                .addPathSegment(com.crowdos.portals.url.updatePwd)
                .addQueryParameter("token",token)
                .build();
        RequestBody updateInfo = new FormBody.Builder()
                .add("oldPasswd",oldPwd)
                .add("newPasswd",pwd)
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
                isSucceed(data);
            }


        });
        return isSucceed[0];
    }
    /*coming sonn
    //请求验证码（注册段）1
    public boolean requestVerifyCode(String email){
        final boolean[] isSucceed = {false};
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("mock.apifox.cn")
                .addPathSegment(com.crowdos.portals.url.getVerifyCode)
                .build();
        RequestBody requestPac = new FormBody.Builder()
                .add("email",email)
                .build();
        OkHttpClient requestForVerifyCode = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestPac)
                .build();
        requestForVerifyCode.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
        return isSucceed[0];
    }*/

}