package com.campusnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusnews.annotation.AndroidView;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.TitlebarUtil;

public class NewDetailActivity extends BaseActivity{

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
  
  
  
  @Override
  protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_new_detail);
        initView();
  }
  
  
  private void initView(){
    String title="活动/消息详情";
    TitlebarUtil.setTitleBar(this, TitlebarUtil.PAGE_LEVLE_2, title);
  }
  
  public static void intoNewDetailActivity(Context context){
    Intent intent=new Intent();
    intent.setClass(context, NewDetailActivity.class);
    context.startActivity(intent);
  }
  
}
