package com.campusnews.model;

import java.io.File;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.campusnewes.bean.ImageBean;
import com.campusnews.BaseActivity;
import com.campusnews.R;
import com.campusnews.network.BitmapCache;
import com.campusnews.network.JsonParser;
import com.campusnews.network.MultipartRequest;
import com.campusnews.util.EventBusObject.PictureData;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;

import de.greenrobot.event.EventBus;

public class ImageUploadRequestBase {
  File file = null;
  RequestQueue mRequestQueue;
  ImageLoader mImageLoader;
  ImageListener listener;
  BaseActivity activity;
  Map<String, String> params;

  String url = StaticUrl.Save_imageUrl;// "http://192.168.0.102:8888/save_image";

  public ImageUploadRequestBase(File file, FragmentActivity fragmentActivity,
      Map<String, String> params) {
    this.file = file;
    this.params = params;
  //  mRequestQueue = Volley.newRequestQueue(fragmentActivity);
  }

  public void upload(ImageView imageView1) {

    listener =
        ImageLoader.getImageListener(imageView1, R.drawable.ic_launcher, R.drawable.ic_launcher);
    mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());

    Request<JSONObject> reuquest = new MultipartRequest(url, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("pxl", "error  " + error.getMessage());
        ToastUtil.show("服务器链接失败");
      }
    }, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        Log.e("pxl", "onResponse:" + response.toString());
        ImageBean imageBean = JsonParser.fromJson(response.toString(), ImageBean.class);
        if (imageBean.isSucceed()) {
          PictureData pictureData = new PictureData();
          pictureData.picPath = imageBean.results.image_path;
          pictureData.state = imageBean.state;
          EventBus.getDefault().post(pictureData);
          Log.i("=====imageBean========", "" + imageBean.results.image_path);
        }
        try {
          Log.i("==================", response.getString("Message"));
        } catch (JSONException e1) {
          e1.printStackTrace();
        }

        // try {
        // mImageLoader.get(response.getString("results.image_path"), listener);
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
      }
    }, file, "file", params);
    BaseApplication.self.mQueue.add(reuquest);
  }

}
