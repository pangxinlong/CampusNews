package com.campusnews.model;

public class UserInfo {
  /**
   * 未激活状态
   */
  public static final int ACTIVE_STATE = 0;
  /**
   * 激活状态
   */
  public static final int INACTIVE_STATE = 1;

  /**
   * 普通用户
   */
  public static final int ORDINARY_TYPE = 0;
  /**
   * 社团用户
   */
  public static final int ORGANIZATION_TYPE = 1;

  /**
   * 判断默认图片是哪个
   */
  public static int isNews;//0为默认消息图片，1为默认头像图片
  
  public static int loginState = -1;
  public static String userId;
  public static String userName;
  public static int userType;
  
  
  public static int mPage=5;
}
