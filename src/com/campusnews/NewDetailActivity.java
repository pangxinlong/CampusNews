package com.campusnews;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusnewes.bean.ActivitiesListBean.ActivitiesListData;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.LoadingImage;
import com.campusnews.model.UserInfo;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.TitlebarUtil;

public class NewDetailActivity extends BaseActivity {

  /**
   * 活动标题
   */
  @AndroidView(R.id.tv_newsDetail_title)
  TextView tvNewsDetailTitle;

  /**
   * 活动发布人
   */
  @AndroidView(R.id.tv_publish_people)
  TextView tvPublishPeople;

  /**
   * 活动发布时间
   */
  @AndroidView(R.id.tv_publish_time)
  TextView tvPublishTime;

  /**
   * 活动图片
   */
  @AndroidView(R.id.im_newsdetail)
  ImageView im_newsdetail;

  /**
   * 活动内容
   */
  @AndroidView(R.id.tv_content)
  TextView tvContent;

  ActivitiesListData newsData;

  
  
  /** Called when the activity is first created. */  
  Bitmap bp=null;  
  ImageView imageview;  
  float scaleWidth;  
  float scaleHeight;  
    
 int h;  
  boolean num=false; 
  
  
  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.activity_new_detail);
    initView();
  }


  private void initView() {
    UserInfo.isNews=0;
    
    String title = "活动/消息详情";
    TitlebarUtil.setTitleBar(this, TitlebarUtil.PAGE_LEVLE_2, title);

    Intent intent = this.getIntent();
    Bundle bundle = intent.getExtras();
    newsData = (ActivitiesListData) bundle.getSerializable("newsData");

    tvNewsDetailTitle.setText(newsData.title);
    tvPublishPeople.setText(newsData.name);
    tvContent.setText(newsData.content);

    if (newsData.date != null) {
      long ldate = Long.parseLong(newsData.date);
      Date date = new Date(ldate);
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
      String strDate = df.format(date);
      tvPublishTime.setText(strDate);
    }

    LoadingImage.loadImage(this, im_newsdetail, StaticUrl.baseImageUlr + newsData.image_path,UserInfo.isNews);
    
  }

  public static void intoNewDetailActivity(Context context, ActivitiesListData newsData) {
    Intent intent = new Intent();
    intent.setClass(context, NewDetailActivity.class);

    Bundle bundle = new Bundle();
    bundle.putSerializable("newsData", newsData);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

}
