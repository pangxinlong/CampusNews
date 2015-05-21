package com.campusnewes.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ActivitiesListBean extends RootPojo {
  @JSONField(name = "result")
  public  List<ActivitiesListData> result;

  public static class ActivitiesListData implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -219718936834087885L;

    /** 活动id */
    @JSONField(name = "activity_id")
    public String activity_id;

    /** 用户id */
    @JSONField(name = "user_id")
    public String user_id;

    /** 活动发布人 */
    @JSONField(name = "name")
    public String name;

    /** 活动类型 */
    @JSONField(name = "activity_type")
    public int activity_type;

    /** 活动标题 */
    @JSONField(name = "title")
    public String title;

    /** 活动内容 */
    @JSONField(name = "content")
    public String content;

    /** 活动发布时间 */
    @JSONField(name = "date")
    public String date;

    /** 联系方式 */
    @JSONField(name = "contact_information")
    public String contact_information;

    /** 活动图片 */
    @JSONField(name = "image_path")
    public String image_path;


  }
}
