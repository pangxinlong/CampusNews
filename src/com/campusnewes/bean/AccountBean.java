package com.campusnewes.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class AccountBean extends RootPojo implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 8301355971989594774L;
  @JSONField(name = "result")
  public List<AccountData> result;


  public static class AccountData implements Serializable{
    /** 用户id */
    @JSONField(name = "user_id")
    public String user_id;

    /** 密码 */
    @JSONField(name = "password")
    public String password;

    /** 真实姓名 */
    @JSONField(name = "name")
    public String name;

    /** 昵称 */
    @JSONField(name = "nickname")
    public String nickname;

    /** 性别 */
    @JSONField(name = "sex")
    public String sex;

    /** 专业 */
    @JSONField(name = "professional")
    public String professional;

    /** 年级 */
    @JSONField(name = "grade")
    public String grade;

    /** 用户类型 */
    @JSONField(name = "type")
    public int type;

    /** 账号状态 */
    @JSONField(name = "state")
    public int state;

    /** 头像 */
    @JSONField(name = "icon")
    public String icon;

    /** 联系方式 */
    @JSONField(name = "contact_information")
    public String contact_information;


  }
}
