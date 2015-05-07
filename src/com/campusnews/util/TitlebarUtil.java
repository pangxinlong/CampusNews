package com.campusnews.util;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.campusnews.BaseActivity;
import com.campusnews.R;
import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.annotation.AndroidView;



/**
 * 标题栏工具
 */
public class TitlebarUtil {
  /** 返回按钮 */
  @AndroidView(R.id.commont_title_bar_back_normal)
  private TextView btnBack;
  /** Home按钮 */
  @AndroidView(R.id.commont_title_bar_left_normal)
  private TextView btnHome;

  /** 当前的页面类型 */
  private int pageLevel = 1;

  /** 一级页面 */
  public static final int PAGE_LEVLE_1 = 1;
  /** 二级页面 */
  public static final int PAGE_LEVLE_2 = 2;

  public TitlebarUtil(final int type) {
    this.pageLevel = type;
  }

  /**
   * 设置标题栏
   * 
   * @param activity BaseActivity
   * @param pageLevel int
   * @param title String
   */
  public static void setTitleBar(final BaseActivity activity, final int pageLevel,
      final String title) {
    setTitleBar(activity, pageLevel, title, null);
  }

  /**
   * 设置标题栏
   * 
   * @param activity Activity
   * @param pageLevel int
   * @param title String
   * @param buttonListener is need to deal event,can be null;
   */
  public static void setTitleBar(final Activity activity, final int pageLevel, final String title,
      final OnClickListener buttonListener) {
    setTitleBar(activity, activity.findViewById(R.id.title_bar_util), pageLevel, title,
        buttonListener);
  }

  /**
   * 设置标题栏
   * 
   * @param activity Activity
   * @param view is included view;
   * @param pageLevel int
   * @param title String
   * @param buttonListener is need to deal event,can be null;
   */
  public static void setTitleBar(final Activity activity, final View view, final int pageLevel,
      final String title, final OnClickListener buttonListener) {
    TitlebarUtil titlebarUtil = new TitlebarUtil(pageLevel);
    titlebarUtil.setTitlebar(activity, view, title, buttonListener);
  }

  /**
   * 
   * @param view is included view;
   * @param title is need to show txt;
   * @param buttonListener is need to deal event,can be null;
   */
  private void setTitlebar(final Activity activity, final View view, final String title,
      OnClickListener buttonListener) {
    AndroidAutowire.autowire(view, this);
    TextView button;
    if (this.pageLevel == PAGE_LEVLE_1) {// 一级页面
      this.btnHome.setVisibility(View.VISIBLE);
      this.btnBack.setVisibility(View.INVISIBLE);
      button = this.btnHome;
    } else {// 二级页面
      this.btnHome.setVisibility(View.INVISIBLE);
      this.btnBack.setVisibility(View.VISIBLE);
      button = this.btnBack;
    }

    button.setText(title);
    if (buttonListener == null) {
      // 设置默认按钮监听器
      buttonListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
          activity.finish();
        }
      };
    }
    button.setOnClickListener(buttonListener);
  }
}
