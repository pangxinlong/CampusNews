package com.campusnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.campusnews.R;
import com.campusnews.fragment.FragmentFactory;
import com.campusnews.util.BaseApplication;

public class PagerViewPagerAdapter extends FragmentStatePagerAdapter implements BasePagerAdapter {

  private static final String[] mViewpager_title = new String[] {
      BaseApplication.self.getString(R.string.all_news),
      BaseApplication.self.getString(R.string.association_news),
      BaseApplication.self.getString(R.string.personal_news),
      BaseApplication.self.getString(R.string.lost_news),
      BaseApplication.self.getString(R.string.participate_news),
      BaseApplication.self.getString(R.string.news_news)};

  FragmentManager fm;


  public PagerViewPagerAdapter(FragmentManager fm) {
    super(fm);
    this.fm = fm;

  }

  @Override
  public CharSequence getPageTitle(int position) {
    return PagerViewPagerAdapter.mViewpager_title[position];
  }

  @Override
  public Fragment getItem(int position) {
    return FragmentFactory.newInstance(position);
  }

  @Override
  public int getCount() {
    return mViewpager_title.length;
  }

  @Override
  public int getIconResId(int index) {
    return index;
  }



}
