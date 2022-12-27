package com.crowdos.portals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.crowdos.MainActivity;
import com.crowdos.ui.notifications.ChangePasswordActivity;
import com.crowdos.ui.welcome.event_Register;

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
            JSONObject jsonObject = new JSONObject(data);
            result = jsonObject.getBoolean("isSucceed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*以下是登录函数2*/
    public static void userRegister(String username,String pwd){
        //结果变量()
        boolean[] judge = {false};
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("mock.apifox.cn")
                .addPathSegment(com.crowdos.portals.url.userRegister)
                .build();
        //新建请求体
        RequestBody registerPac = new FormBody.Builder().add("email",username).add("passwd",pwd).build();
        //采用异步
        Request request = new Request.Builder()
                .url("https://mock.apifox.cn/m1/1900041-0-default/register")
                .post(registerPac)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "mock.apifox.cn")
                .addHeader("Connection", "keep-alive")
                .build();
        OkHttpClient loginAct = new OkHttpClient();
        loginAct.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                if(data.equals("true")){
                    judge[0] = true;
                }
                event_Register.isSuccess = judge[0];
            }
        });

    }

    public static void userLogin(String username,String pwd){
        //结果变量()
        boolean[] judge = {false};
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("mock.apifox.cn")
                .addPathSegment(com.crowdos.portals.url.userRegister)
                .build();
        //新建请求体
        RequestBody registerPac = new FormBody.Builder().add("email",username).add("passwd",pwd).build();
        //采用异步
        Request request = new Request.Builder()
                .url("https://mock.apifox.cn/m1/1900041-0-default/login")
                .post(registerPac)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "mock.apifox.cn")
                .addHeader("Connection", "keep-alive")
                .build();
        OkHttpClient loginAct = new OkHttpClient();
        loginAct.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                if(data != null){
                    MainActivity.token = data;
                }
            }
        });

    }


    /*注册函数*/

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
        Request request = new Request.Builder().url("https://mock.apifox.cn/m1/1900041-0-default/updatePasswd?token="+token)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "mock.apifox.cn")
                .addHeader("Connection", "keep-alive")
                .post(updateInfo)
                .build();
        gUserInfo.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                Log.e("onResponse: ",data );
                ChangePasswordActivity.succeed = isSucceed(data);
                Log.e("onResponse: ",""+ChangePasswordActivity.succeed );
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