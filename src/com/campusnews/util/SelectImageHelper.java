package com.campusnews.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.campusnews.BaseActivity;
import com.campusnews.util.EventBusObject.PictureData;



/**
 * 上传图片以及过去图片路径
 * 
 * @author password
 *
 */
public class SelectImageHelper {

  static String picPath = "";// 图片路径

  /**
   * 选择图片
   * 
   * @param fragmentActivity
   */
  public static void select(BaseActivity fragmentActivity) {
    /***
     * 这个是调用android内置的intent，来过滤图片文件 ，同时也可以过滤其他的
     */
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    fragmentActivity.startActivityForResult(intent, 0);
  }

  /**
   * 获取图片路径
   * 
   * @param fragmentActivity （当前activity）
   * @param requestCode
   * @param resultCode （返回结果code）
   * @param data （返回得数据）
   * @param imageView （图片显示位置）
   * @return
   */
  public static String getImagePath(FragmentActivity fragmentActivity, int resultCode,
      Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      /**
       * 当选择的图片不为空的话，在获取到图片的途径
       */
//      Uri uri = data.getData();
//      Log.e("!!!!", "uri = " + uri);

      try {
        Uri originalUri = data.getData(); // 获得图片的uri
        // 显得到bitmap图片
        String[] pojo = {MediaStore.Images.Media.DATA};

        Cursor cursor = fragmentActivity.managedQuery(originalUri, pojo, null, null, null);
        Log.i("cursor==============", cursor + "");
        if (cursor != null) {
          int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
          cursor.moveToFirst();
          String path = cursor.getString(colunm_index);
          Log.i("path==============", path + "");
          /***
           * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了， 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
           */
          if (path.endsWith("jpg") || path.endsWith("png")) {
            /**
             * 方式一
             */
//            Bitmap bm = null;
//            ContentResolver resolver = fragmentActivity.getContentResolver();
//            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//            imageView.setImageBitmap(bm);
            picPath = path;
            
//            pictureData=new PictureData();
//            pictureData.picPath=picPath;
//            pictureData.resolver=resolver;
//            pictureData.originalUri=originalUri;
            /**
             * 方式二
             */
            /*
             * Log.i("picPath==============", picPath + ""); ContentResolver cr =
             * getContentResolver(); Bitmap bitmap = BitmapFactory.decodeStream(cr
             * .openInputStream(uri)); imageView.setImageBitmap(bitmap);
             * 
             * picPath = path;
             */
          } else {
            alert(fragmentActivity);
          }
        } else {
          alert(fragmentActivity);
        }

      } catch (Exception e) {
        Log.e("error:", e.toString());
      }
    }
    return picPath;

  }

  private static void alert(FragmentActivity activity) {
    Dialog dialog =
        new AlertDialog.Builder(activity).setTitle("提示").setMessage("您选择的不是有效的图片")
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                picPath = "";
              }
            }).create();
    dialog.show();
  }
}
