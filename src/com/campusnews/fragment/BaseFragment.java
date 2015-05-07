package com.campusnews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.campusnews.annotation.AndroidAutowire;



/**
 * fragment基类
 * 
 * @author pxl
 */
public class BaseFragment extends Fragment {

  protected boolean isInit; // 是否可以开始加载数据



  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    AndroidAutowire.autowire(view, this);
    super.onViewCreated(view, savedInstanceState);
  }


  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    // 每次切换fragment时调用的方法
    Log.i("=========isVisibleToUser=========", isVisibleToUser + "");
    if (isVisibleToUser) {
      isInit = true;
      showData();
    }
  }

  /**
   * 初始化数据
   * 
   * @author yubin
   * @date 2014-1-16
   */
  private void showData() {
    if (isInit) {
      // isInit = false;// 加载数据完成
      // 加载各种数据
      initData();
    }
  }

  protected void initData() {

  }


  @Override
  public void onResume() {
    super.onResume();
    // 判断当前fragment是否显示
    if (getUserVisibleHint()) {
      showData();
    }
  }
}
