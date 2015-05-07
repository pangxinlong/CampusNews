package com.campusnews.util;

import com.campusnews.R;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast工具类
 * @author pxl
 */
public class ToastUtil {
  static Toast toast;
  static TextView tvToastText;

  public static void hide() {
    if (toast != null) {
      toast.cancel();
    }
  }

  public static void show(String text) {
    showCustomToast(text, Toast.LENGTH_SHORT);
  }


  public static void show(String text, int time) {
    showCustomToast(text, time);
  }

  // 自定义Toast 居中显示
  @SuppressLint("InflateParams")
private static void showCustomToast(final String msg, final int time) {
    if (toast == null) {
      toast = new Toast(BaseApplication.self);
      View view =
          LayoutInflater.from(BaseApplication.self).inflate(R.layout.toast_show, null);
      tvToastText = (TextView) view.findViewById(R.id.toast);
      toast.setView(view);
    }
    BaseApplication.self.handlerCommon.post(new Runnable() {

      @Override
      public void run() {
        tvToastText.setText(msg);
        toast.setDuration(time);
        toast.show();
      }
    });
  }
}
