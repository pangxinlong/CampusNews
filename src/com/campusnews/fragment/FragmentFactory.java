package com.campusnews.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;


/**
 * 创建活动类的所有fragment
 * 
 * @author password
 *
 */

public class FragmentFactory {
  static Fragment fragment;
  
  public static Fragment newInstance(int position) {
//    switch (position) {
//      case 0:
//        fragment = new FragmentNews();
//        break;
//      default:
//        break;
//    }
    fragment = new FragmentNews();
    Bundle data = new Bundle();
    data.putInt("position", position);
    fragment.setArguments(data);
//    Log.i("=======position======",position+"");
    return fragment;

  }
}
