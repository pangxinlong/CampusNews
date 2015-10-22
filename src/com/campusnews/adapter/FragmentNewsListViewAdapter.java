package com.campusnews.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusnewes.bean.ActivitiesListBean;
import com.campusnewes.bean.ActivitiesListBean.ActivitiesListData;
import com.campusnews.R;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.LoadingImage;
import com.campusnews.model.UserInfo;
import com.campusnews.util.StaticUrl;

public class FragmentNewsListViewAdapter extends BaseAdapter implements BasePagerAdapter {

  Context context;
  List<ActivitiesListData> listData;

  public FragmentNewsListViewAdapter(Context context) {
    this.context = context;
    listData= new ArrayList<ActivitiesListData>();
  }

  public void setData(List<ActivitiesListData> listData) {
    this.listData.clear();
    this.listData.addAll(listData);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    if (listData == null) {
      return 0;
    }
    return listData.size();
  }

  @Override
  public Object getItem(int position) {
    if (listData == null) {
      return position;
    }
    return listData.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
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
    viewHolder.bind(postion);
    return convertView;
  }


  @Override
  public int getIconResId(int index) {
    return index;
  }

  public class ViewHolder extends BaseViewHolder {

    public ViewHolder(View root) {
      super(root);
    }

    public void bind(int postion) {
      ActivitiesListData data = listData.get(postion);
      tv_news_activity_title.setText(data.title);
      tv_news_activity_content.setText(data.content);

      LoadingImage.loadImage(context, im_news_activity_picture, StaticUrl.baseImageUlr
          + data.image_path,UserInfo.isNews);
    }

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
