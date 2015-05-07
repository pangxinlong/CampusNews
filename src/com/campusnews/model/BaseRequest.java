package com.campusnews.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class BaseRequest {
	private  String TAG = "http";
	private  List<NameValuePair> params;
	private  String url;
	private String result;
	public  String postData(final List<NameValuePair> params,final String url){
		this.params=params;
		this.url=url;
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							params, HTTP.UTF_8);
					// URL使用基本URL即可，其中不需要加参数
					HttpPost httpPost = new HttpPost(url);
					// 将请求体内容加入请求中
					httpPost.setEntity(requestHttpEntity);
					// 需要客户端对象来发送请求
					HttpClient httpClient = new DefaultHttpClient();

					// 发送请求
					HttpResponse response = httpClient.execute(httpPost);
					Log.i(TAG, "====================response========"
							+ response.getStatusLine().getStatusCode());
					System.out.println("response" + response);
					// 显示响应
					// showResponseResult(response);
					if (response.getStatusLine().getStatusCode() == 200) {
						// 第三步，使用getEntity方法活得返回结果
						String result = EntityUtils.toString(response
								.getEntity());
						System.out.println("result:" + result);
						Log.i(TAG, "====================strResult========"
								+ result);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

		return result;
	}
}
