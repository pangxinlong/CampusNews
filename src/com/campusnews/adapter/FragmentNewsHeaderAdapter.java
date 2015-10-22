package com.campusnews.adapter;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class FragmentNewsHeaderAdapter extends PagerAdapter implements BasePagerAdapter {

  Context context;
  List<Integer> list;
  Random random;
  int sum =-1;

  public FragmentNewsHeaderAdapter(Context context, List<Integer> list) {
    this.list = list;
    random = new Random();

  }

  @Override
  public int getCount() {
    return 3;// list.size();//Integer.MAX_VALUE;
  }

  @Override
  public int getIconResId(int index) {
    return 0;
  }

  @Override
  public boolean isViewFromObject(View arg0, Object arg1) {
    return arg0 == arg1;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {

    int max = 10;
    int min = 0;
    int s = random.nextInt(max) % (max - min + 1) + min;
    while(sum==s){
      s = random.nextInt(max) % (max - min + 1) + min;
    }
    sum=s;

    ImageView imageView = new ImageView(container.getContext());
    imageView.setImageResource(list.get(sum));
    imageView.setScaleType(ScaleType.FIT_XY);
    container.addView(imageView);
    return imageView;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {}
}
