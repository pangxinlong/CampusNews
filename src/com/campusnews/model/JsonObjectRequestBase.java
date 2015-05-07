package com.campusnews.model;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusnewes.bean.RootPojo;
import com.campusnews.BaseActivity;
import com.campusnews.network.JsonParser;
import com.campusnews.util.StaticUrl;

import de.greenrobot.event.EventBus;

public class JsonObjectRequestBase {
  RequestQueue mRequestQueue;// 声明requestQueue对象
  String url = StaticUrl.LoginUrl; // 请求url
  HashMap<String, String> params;
  BaseActivity context;


  public JsonObjectRequestBase(BaseActivity context, HashMap<String, String> params) {
    this.context = context;
    mRequestQueue = Volley.newRequestQueue(this.context);
    this.params = params;

  }

  // 发送请求
  public <T extends RootPojo> void makeSampleHttpRequest(final Class<T> cls) {

    StringRequest jr =
        new StringRequest(Request.Method.POST, getUrl(), new Response.Listener<String>() {// 请求成功
              @Override
              public void onResponse(String response) {
                // 处理返回信息
                Log.i("response", response);
                T info = JsonParser.fromJson(response, cls);
                if (info instanceof RootPojo) {
                  EventBus.getDefault().post(info);
                }
              }
            }, new Response.ErrorListener() {// 请求失败

              @Override
              public void onErrorResponse(VolleyError error) {
                Log.i("error response", error + "");
              }
            }) {

          @Override
          protected Map<String, String> getParams() throws AuthFailureError {

            return params;
          }

        };
    mRequestQueue.add(jr);
  }

  // 获取url
  private String getUrl() {
    return url;
  }

  // 取消所有请求
  protected void cancelRequest() {
    mRequestQueue.cancelAll(this.context);
  }

}
