package com.campusnews.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.campusnews.R;
import com.campusnews.util.BaseApplication;

public class LoadingImage {

  
  public static void loadImage(Context context, final ImageView imageView,String url,final int isNews) {

    
    ImageRequest imageRequest =
        new ImageRequest(url,
            new Response.Listener<Bitmap>() {
              @Override
              public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
              }
            }, 0, 0, Config.RGB_565, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                if(isNews==0){
                  imageView.setImageResource(R.drawable.imagedefault);
                }else{
                imageView.setImageResource(R.drawable.default_pic);
                }
              }
            });
    BaseApplication.self.mQueue.add(imageRequest);
  }
  
//
//  if (!PhoneUtils.isNetAvailable()) {
//    ToastUtil.show("请检查您的网络");
//    return;
//  }
//  HashMap<String, String> params = new HashMap<String, String>();
//  params.put("image_name", "1.jpg");
//
//  JsonObjectRequestBase jsonObjectRequestBase =
//      new JsonObjectRequestBase((BaseActivity)context,params,url);
//  jsonObjectRequestBase.makeSampleHttpRequest(ImageFileBean.class);
//  } 
//  
  
//  public void onEvent(ImageFileBean imageData){
//    if(imageData.isSucceed()){
//      String image=imageData.results.image;
//      EventBus.getDefault().unregister(this);
//    }
//  }
}
