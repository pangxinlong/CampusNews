package com.campusnews.adapter;

import com.campusnews.fragment.RegistrationFragmentFactory;
import com.campusnews.util.ToastUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RegistrationViewPagerAdapter extends FragmentPagerAdapter implements BasePagerAdapter {

  
  public RegistrationViewPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void setData(String path){
  }
  
  
  @Override
  public int getIconResId(int index) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Fragment getItem(int postion) {
    ToastUtil.show(postion+"");
    return RegistrationFragmentFactory.newInstance(postion);
  }

  @Override
  public int getCount() {
    return 2;
  }

}
