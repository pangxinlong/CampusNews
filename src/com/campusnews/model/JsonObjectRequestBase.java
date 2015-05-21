package com.campusnews.model;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusnewes.bean.RootPojo;
import com.campusnews.BaseActivity;
import com.campusnews.network.JsonParser;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;

import de.greenrobot.event.EventBus;

public class JsonObjectRequestBase {
  RequestQueue mRequestQueue;// 声明requestQueue对象
  String url; // 请求url
  HashMap<String, String> params;
  BaseActivity context;


  public JsonObjectRequestBase(BaseActivity context, HashMap<String, String> params, String url) {
    this.context = context;
  //  mRequestQueue = Volley.newRequestQueue(this.context);
    this.params = params;
    this.url = url;

  }

  public JsonObjectRequestBase(FragmentActivity context, HashMap<String, String> params, String url) {
    this.context = (BaseActivity) context;
  //  mRequestQueue = Volley.newRequestQueue(this.context);
    this.params = params;
    this.url = url;
  }

  // 发送请求
  public <T extends RootPojo> void makeSampleHttpRequest(final Class<T> cls) {

    StringRequest jr =
        new StringRequest(Request.Method.POST, getUrl(), new Response.Listener<String>() {// 请求成功
              @Override
              public void onResponse(String response) {
                // 处理返回信息
                Log.e("======response=======", response);
                T info = JsonParser.fromJson(response, cls);
                if (info instanceof RootPojo) {
                  EventBus.getDefault().post(info);
                }
              }
            }, new Response.ErrorListener() {// 请求失败

              @Override
              public void onErrorResponse(VolleyError error) {
                Log.i("error response", error + "");
                ToastUtil.show("网络连接失败");
              }
            }) {

          @Override
          protected Response<String> parseNetworkResponse(NetworkResponse response) {
            try {
              String jsonString = new String(response.data, "UTF-8");
              return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
              return Response.error(new ParseError(e));
            } catch (Exception je) {
              return Response.error(new ParseError(je));
            }
          }

          @Override
          protected Map<String, String> getParams() throws AuthFailureError {

            return params;
          }

        };
        BaseApplication.self.mQueue.add(jr);
  }

  // 获取url
  private String getUrl() {
    return url;
  }

  // 取消所有请求
  protected void cancelRequest() {
    BaseApplication.self.mQueue.cancelAll(this.context);
  }

}
