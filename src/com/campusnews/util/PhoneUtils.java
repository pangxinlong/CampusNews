package com.campusnews.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class PhoneUtils {
  /**
   * 判断网络连接是否可用
   * 
   * @return
   */

  public static boolean isNetAvailable() {
    boolean isAvailable = false;

    NetworkInfo mNetworkInfo = PhoneUtils.getActiveNetworkInfo();
    if (mNetworkInfo != null) {
      return mNetworkInfo.isAvailable();
    }
    return isAvailable;
  }

  public static NetworkInfo getActiveNetworkInfo() {
    try {
      ConnectivityManager mConnectivityManager =
          (ConnectivityManager) BaseApplication.self.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      return mNetworkInfo;
    } catch (NullPointerException e) {
      // 注意在部分手机rom中会出现如下异常：
      // java.lang.NullPointerException
      // at android.os.Parcel.readException(Parcel.java:1431)
      // at android.os.Parcel.readException(Parcel.java:1379)
      // at
      // android.net.IConnectivityManager$Stub$Proxy.getActiveNetworkInfo(IConnectivityManager.java:688)
      // at
      // android.net.ConnectivityManager.getActiveNetworkInfo(ConnectivityManager.java:460)
      return null;
    }
  }
}
