package com.campusnews.adapter;

import android.view.View;
import com.campusnews.annotation.AndroidAutowire;

/**
 * ViewHolder的基类，提供AndroidView自动绑定的功能
 */
public class BaseViewHolder {
  /**
   * 构造函数
   * 
   * @param root root view
   */
  public BaseViewHolder(View root) {
    AndroidAutowire.autowire(root, this);
  }
}
