package com.campusnewes.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class AccountBean extends RootPojo {
  @JSONField(name = "results")
  public static AccountData results;


  public static class AccountData {
    /** 用户id */
    @JSONField(name = "user_id")
    public static String user_id;

    /** 密码 */
    @JSONField(name = "password")
    public static String password;

    /** 真实姓名 */
    @JSONField(name = "name")
    public static String name;

    /** 昵称 */
    @JSONField(name = "nickname")
    public static String nickname;

    /** 性别 */
    @JSONField(name = "sex")
    public static String sex;

    /** 专业 */
    @JSONField(name = "professional")
    public static String professional;

    /** 用户类型 */
    @JSONField(name = "type")
    public static String type;

    /** 账号状态 */
    @JSONField(name = "state")
    public static int state;

    /** 头像 */
    @JSONField(name = "icon")
    public static String icon;

    /** 联系方式 */
    @JSONField(name = "contact_information")
    public static String contact_information;
  }
}
