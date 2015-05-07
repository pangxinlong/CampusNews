package com.campusnewes.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class RootPojo {

  public static String SUCCEED = "0";

  /**
   * 状态码
   */
  @JSONField(name = "State")
  public String state;

  /**
   * 返回描述或者错误信息
   */
  @JSONField(name = "Message")
  public String message;

  /**
   * 是否请求成功
   * 
   * @return boolean
   */
  public boolean isSucceed() {
    return SUCCEED.equals(state);
  }

}
