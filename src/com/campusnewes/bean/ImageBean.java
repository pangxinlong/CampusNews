package com.campusnewes.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ImageBean extends RootPojo {
  @JSONField(name = "results")
  public ImageData results;

  public static class ImageData {
    @JSONField(name = "image_path")
    public String image_path;
  }
}
