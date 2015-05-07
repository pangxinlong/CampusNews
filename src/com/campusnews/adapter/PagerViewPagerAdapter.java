package com.campusnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import com.campusnews.R;
import com.campusnews.fragment.FragmentFactory;
import com.campusnews.util.BaseApplication;

public class PagerViewPagerAdapter extends FragmentPagerAdapter implements BasePagerAdapter {

  private static final String[] mViewpager_title = new String[] {
      BaseApplication.self.getString(R.string.all_news),
      BaseApplication.self.getString(R.string.association_news),
      BaseApplication.self.getString(R.string.personal_news),
      BaseApplication.self.getString(R.string.lost_news),
      BaseApplication.self.getString(R.string.participate_news),
      BaseApplication.self.getString(R.string.news_news)};

  public PagerViewPagerAdapter(FragmentManager fm) {
    super(fm);

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
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
//     Log.i( "========position Destory=========", position+"");
    super.destroyItem(container, position, object);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
//     Log.i("========container.getId()========",container.getId()+"");
//     Log.i("========getItemId(position)========",getItemId(position)+"");
    
    return super.instantiateItem(container, position);
  }



}
