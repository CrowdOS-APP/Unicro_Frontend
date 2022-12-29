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
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*这个文件主要负责进行用户账号操作*/
public class opUser {

    public static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

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
                .scheme("http")
                .host("39.103.146.190")
                .addPathSegment(com.crowdos.portals.url.userRegister)
                .build();
        //新建请求体
        JSONObject json = new JSONObject();
        try {
            json.put("email",username).put("passwd",pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody registerPac = RequestBody.create(JSON,String.valueOf(json));
        //采用异步
        Request request = new Request.Builder()
                .url("http://39.103.146.190/register")
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "39.103.146.190")
                .addHeader("Connection", "keep-alive")
                .post(registerPac)
                .build();
        OkHttpClient loginAct = new OkHttpClient();
        Log.e("post",request.toString());
        loginAct.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                Log.e("response",data);
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
                .scheme("http")
                .host("39.103.146.190")
                .addPathSegment(com.crowdos.portals.url.userLogin)
                .build();
        //新建请求体
        JSONObject json = new JSONObject();
        try {
            json.put("email",username).put("passwd",pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody registerPac = RequestBody.create(JSON,String.valueOf(json));
        //采用异步
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "appliaticon/json")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "39.103.146.190")
                .addHeader("Connection", "keep-alive")
                .post(registerPac)
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
                .scheme("http")
                .host("39.103.146.190")
                .addPathSegment(com.crowdos.portals.url.updatePwd)
                .addQueryParameter("token",token)
                .build();
        JSONObject json = new JSONObject();
        try {
            json.put("oldPasswd",oldPwd).put("newPasswd",pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody updateInfo = RequestBody.create(JSON,String.valueOf(json));
        OkHttpClient gUserInfo = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Host", "39.103.146.190")
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