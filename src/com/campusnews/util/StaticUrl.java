package com.campusnews.util;

public class StaticUrl {

  public static String IP = "110";

  /** 基本url */
  public static String baseUlr = "http://192.168.0." + IP + ":8888/";

  /** 图片服务器url */
  public static String baseImageUlr = "http://192.168.0." + IP + ":8889";

  /** 接口——登录 */
  public static String LoginUrl = baseUlr + "login";

  /** 接口——注册 */
  public static String RegistrationUrl = baseUlr + "registration";

  /** 接口——查看个人资料 */
  public static String Query_acount = baseUlr + "query_acount";

  /** 接口——修改个人资料 */
  public static String Change_acountUrl = baseUlr + "change_acount";

  /** 接口——查询活动 */
  public static String Query_activityUrl = baseUlr + "query_activity";

  /** 接口——发布活动 */
  public static String Create_activityUrl = baseUlr + "create_activity";

  /** 接口——活动详情 */
  public static String Activity_detailUrl = baseUlr + "activity_detail";

  /** 接口——删除活动 */
  public static String Activity_deleteUrl = baseUlr + "activity_delete";

  /** 接口——上传图片 */
  public static String Save_imageUrl = baseUlr + "save_image";



}
