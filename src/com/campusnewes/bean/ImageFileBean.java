package com.campusnewes.bean;

import java.nio.Buffer;

import com.alibaba.fastjson.annotation.JSONField;

public class ImageFileBean extends RootPojo {
  @JSONField(name = "results")
  public ImageFileData results;

  public class ImageFileData {
    /** 图片 */
    @JSONField(name = "image")
    public String image;
  }
}
