package com.campusnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusnews.R;
import com.campusnews.annotation.AndroidView;

public class FragmentNewsListViewAdapter extends BaseAdapter implements BasePagerAdapter {

  Context context;

  public FragmentNewsListViewAdapter(Context context){
    this.context=context;
  }
  
  @Override
  public int getCount() {
    // TODO Auto-generated method stub
    return 10;
  }

  @Override
  public Object getItem(int arg0) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getItemId(int arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public View getView(int postion, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    if (convertView == null) {
      convertView =
          LayoutInflater.from(context).inflate(R.layout.fragment_news_list_item, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    }
    viewHolder = (ViewHolder) convertView.getTag();
    viewHolder.bind();
    return convertView;
  }


  @Override
  public int getIconResId(int index) {
    // TODO Auto-generated method stub
    return 0;
  }

  public class ViewHolder extends BaseViewHolder {

    public ViewHolder(View root) {
      super(root);
      // TODO Auto-generated constructor stub
    }

    public void bind() {}

    /** 活动列表－－图片 */
    @AndroidView(R.id.im_news_activity_picture)
    ImageView im_news_activity_picture;

    /** 活动列表－－标题 */
    @AndroidView(R.id.tv_news_activity_title)
    TextView tv_news_activity_title;

    /** 活动列表－－内容 */
    @AndroidView(R.id.tv_news_activity_content)
    TextView tv_news_activity_content;
  }


}
