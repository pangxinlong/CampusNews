package com.campusnews.util;

import android.content.ContentResolver;
import android.net.Uri;

public class EventBusObject {
  public static class PictureData {
    public String picPath;
    public ContentResolver resolver;
    public Uri originalUri;
  }
}
