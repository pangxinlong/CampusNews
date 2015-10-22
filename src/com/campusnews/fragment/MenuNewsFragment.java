package com.campusnews.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campusnews.R;
import com.campusnews.adapter.PagerViewPagerAdapter;
import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.annotation.AndroidView;
import com.campusnews.util.BaseApplication;

public class MenuNewsFragment extends BaseFragment {

  @AndroidView(R.id.viewpager)
  ViewPager viewpager;
  

  PagerViewPagerAdapter pagerViewPagerAdapter;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view =
        LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu_news, container, false);
    AndroidAutowire.autowire(view, this);
    String textString = getArguments().getString("text");
    // Toast.makeText(this.getActivity(),textString, Toast.LENGTH_LONG).show();
    return view;
  }
  
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    pagerViewPagerAdapter = new PagerViewPagerAdapter(getChildFragmentManager());
    viewpager.setAdapter(pagerViewPagerAdapter);
    viewpager.setOffscreenPageLimit(3);
    
   
    viewpager.setOnPageChangeListener(new OnPageChangeListener() {
      
      @Override
      public void onPageSelected(int arg0) {
//        Log.i("===========arg0=============",arg0+"");
//          Log.i("===========viewpager.getCurrentItem()=============",viewpager.getCurrentItem()+"");
      }
      
      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        
      }
    });
    Log.i("==========viewpager.getCurrentItem();==========",viewpager.getCurrentItem()+"");
  }


  
}
