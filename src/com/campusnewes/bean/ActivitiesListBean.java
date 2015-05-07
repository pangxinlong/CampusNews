package com.campusnewes.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ActivitiesListBean extends RootPojo {
  @JSONField(name = "results")
  public static ActivitiesListData results;

  public static class ActivitiesListData {
    
    /** 活动id */
    @JSONField(name = "activity_id")
    public static String activity_id;
    
    /** 用户id */
    @JSONField(name = "user_id")
    public static String user_id;
    
    /** 活动id */
    @JSONField(name = "name")
    public static String name;
    
    /** 活动id */
    @JSONField(name = "activity_type")
    public static int activity_type;
    
    /** 活动id */
    @JSONField(name = "title")
    public static String title;
    
    /** 活动id */
    @JSONField(name = "content")
    public static String content;
    
    /** 活动id */
    @JSONField(name = "date")
    public static long date;
    
    /** 活动id */
    @JSONField(name = "contact_information")
    public static String contact_information;
    
    /** 活动id */
    @JSONField(name = "image_path")
    public static String image_path;
    
    
  }
}
