package com.campusnews.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.campusnewes.bean.RegistrationBean;
import com.campusnewes.bean.RootPojo;
import com.campusnews.LoginActivity;
import com.campusnews.R;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.ImageUploadRequestBase;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.UserInfo;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.PhoneUtils;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;
import com.campusnews.util.EventBusObject.PictureData;

import de.greenrobot.event.EventBus;

public class MenuReleasedFragment extends BaseFragment implements OnClickListener {

  /** 活动标题 */
  @AndroidView(R.id.et_activity_title)
  EditText etActivityTitle;

  /** 活动发布人 */
  @AndroidView(R.id.tv_release_people)
  TextView tvReleasePeople;

  /** 活动内容 */
  @AndroidView(R.id.et_activity_content)
  EditText etActivityContent;

  /** 活动图片父布局 */
  @AndroidView(R.id.rl_activity_image)
  RelativeLayout rlActivityImage;

  /** 活动图片 */
  @AndroidView(R.id.im_activity_image)
  ImageView imActivityImage;

  /** 联系方式 */
  @AndroidView(R.id.et_contact_information)
  EditText etContactInformation;

  /** 活动类型 */
  @AndroidView(R.id.sr_activity_type)
  Spinner srActivityType;


  /** 发布按钮 */
  @AndroidView(R.id.btn_release)
  Button btnRelease;

  String activity_id; // 活动id
  String user_id; // 用户
  String name; // 发布人（用户姓名）
  int activity_type; // 活动类型
  String title; // 活动标题
  String content; // 活动内容
  long date; // 发布时间
  String contact_information; // 联系方式
  String image_path = "/Users/password/Myjs/Test/RequireDemo/TestWithCanshu/image/"; // 图片路径


  Button btnPhoto;// 相册
  Button btnCamera;// 相机
  Button btnCancel;// 取消
  Dialog dialog;

  Uri mOutPutFileUri;
  private int activityType=0;
  private final int PIC_FROM_CAMERA = 1; // 从相机拍摄图片
  private final int PIC_FROM＿LOCALPHOTO = 0;// 从相册选择图片
  String path1;
  File picFile;

  private static final String[] activit_type = {"消息", "参与", "失物认领"};

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_memu_released, container, false);
  }

  private void initView() {
    EventBus.getDefault().register(this);

    tvReleasePeople.setText("发布人 : " + BaseApplication.accountData.name);

    etContactInformation.setVisibility(View.INVISIBLE);
    // 将可选内容与ArrayAdapter连接起来
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,
            activit_type);
    // 设置下拉列表的风格
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // 将adapter 添加到spinner中
    srActivityType.setAdapter(adapter);
    // 添加事件Spinner事件监听
    srActivityType.setOnItemSelectedListener(new OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        activityType = position;
        if (activityType != 0) {
          etContactInformation.setVisibility(View.VISIBLE);
        } else {
          etContactInformation.setVisibility(View.INVISIBLE);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        activityType = 0;
      }
    });
    // 设置默认值
    srActivityType.setVisibility(View.VISIBLE);
    rlActivityImage.setOnClickListener(this);
    btnRelease.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_release:
        if (!PhoneUtils.isNetAvailable()) {
          ToastUtil.show("请检查您的网络");
          return;
        }
        if (checkFormat()) {
          getImageParams(picFile);
        }
        break;
      case R.id.rl_activity_image:
        showDialog();
        break;
      case R.id.btn_camera:
        selectPic(PIC_FROM_CAMERA);
        dialog.cancel();
        break;
      case R.id.btn_photo:
        selectPic(PIC_FROM＿LOCALPHOTO);
        dialog.cancel();
      case R.id.btn_cancel:
        dialog.cancel();
        break;
      default:
        break;
    }
  }

  /**
   * 发布消息请求
   */
  private void requestData() {
    Log.i("====pxl====", "发送请求信息");
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id", UserInfo.userId);
    params.put("name", name);
    params.put("activity_type", String.valueOf(activityType));
    params.put("title", title);
    params.put("content", content);
    params.put("date", String.valueOf(PhoneUtils.getCurrentData()));
    params.put("image_path", image_path);
    params.put("contact_information", contact_information);

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this.getActivity(), params, StaticUrl.Create_activityUrl);
    jsonObjectRequestBase.makeSampleHttpRequest(RootPojo.class);
  }

  /**
   * 获取图片相传的相关参数
   */
  private void getImageParams(File file) {
    if (file == null) {
      file = new File("/Users/password/Myjs/Test/RequireDemo/TestWithCanshu/image/imagedefault.jpg");
    }
    // 获取参数
    final Map<String, String> params = new HashMap<String, String>();
    params.put("userId", "1");

    // 上传图片
    ImageUploadRequestBase imageUpload =
        new ImageUploadRequestBase(file, this.getActivity(), params);
    imageUpload.upload(imActivityImage);
  }


  /**
   * 选择图片
   * 
   * @param type
   */
  private void selectPic(int type) {
    Intent intent;
    // 文件夹upload
    String path = Environment.getExternalStorageDirectory().toString() + "/upload";
    File path1 = new File(path);
    if (!path1.exists()) {
      path1.mkdirs();
    }
    picFile = new File(path1, String.valueOf(PhoneUtils.getCurrentData()) + ".jpeg");
    if (!picFile.exists()) {
      try {
        picFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Log.i("========图片路径============", picFile.toString());
    mOutPutFileUri = Uri.fromFile(picFile);

    if (type == PIC_FROM＿LOCALPHOTO) {
      intent = new Intent(Intent.ACTION_GET_CONTENT,null);
      intent.setType("image/*");
      startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
    } else {
      intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
      startActivityForResult(intent, 1);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (data != null) { //拍照
        if (data.hasExtra("data")) {
          Bitmap thunbnail = data.getParcelableExtra("data");
          imActivityImage.setImageBitmap(thunbnail);
          // 处理缩略图
        }
      } else {
        // 处理mOutPutFileUri中的完整图像
        Bitmap bitmap = decodeUriAsBitmap(mOutPutFileUri);
        imActivityImage.setImageBitmap(bitmap);
      }

    }
    if (requestCode == 0) {
      if (data != null) {
        try {
          Uri uri = data.getData();
          // 显得到bitmap图片
          String[] pojo = {MediaStore.Images.Media.DATA};
          Cursor cursor = this.getActivity().managedQuery(uri, pojo, null, null, null);
          if (cursor != null) {
            int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path1 = cursor.getString(colunm_index);
            ContentResolver resolver = this.getActivity().getContentResolver();
            Bitmap bm = null;
            try {
              bm = MediaStore.Images.Media.getBitmap(resolver, uri);
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
            imActivityImage.setImageBitmap(bm);
          }
        } catch (Exception e) {}
      } else {
        ToastUtil.show("请重新添加图片");
      }
    }
  }

  private Bitmap decodeUriAsBitmap(Uri uri) {
    Bitmap bitmap = null;
    try {
      bitmap =
          BitmapFactory.decodeStream(this.getActivity().getContentResolver().openInputStream(uri));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
    return bitmap;
  }

  /**
   * 检查输入格式
   * 
   * @return
   */
  private boolean checkFormat() {
    activity_id = UserInfo.userId + PhoneUtils.getCurrentData();
    user_id = UserInfo.userId;
    name = BaseApplication.accountData.name;// UserInfo.userName;
    activity_type = activityType;
    title = etActivityTitle.getText().toString();
    content = etActivityContent.getText().toString();
    date = PhoneUtils.getCurrentData();
    contact_information = etContactInformation.getText().toString();
    image_path = path1;

    if (user_id.isEmpty()) {
      LoginActivity.intoLoginActivity(this.getActivity());
      return false;
    } else if (title.isEmpty()) {
      ToastUtil.show("请输入（活动/消息）标题");
      return false;
    } else if (content.isEmpty()) {
      ToastUtil.show("请输入内容");
      return false;
    } else if (contact_information.isEmpty()) {
      if (activity_type == 0) {
        contact_information = "***";
        return true;
      } else {
        ToastUtil.show("请输入联系方式");
        return false;
      }
    } else if (image_path.isEmpty()) {
      image_path = "/image/imagedefault";
    }
    return true;

  }


  /**
   * 选择上传头像方式dialog
   */
  private void showDialog() {
    View view = this.getActivity().getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
    dialogfindId(view);
    dialogsetOnclick();

    dialog = new Dialog(this.getActivity(), R.style.transparentFrameWindowStyle);
    dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
        LayoutParams.WRAP_CONTENT));
    Window window = dialog.getWindow();
    // 设置显示动画
    window.setWindowAnimations(R.style.main_menu_animstyle);
    WindowManager.LayoutParams wl = window.getAttributes();
    wl.x = 0;
    wl.y = this.getActivity().getWindowManager().getDefaultDisplay().getHeight();
    // 以下这两句是为了保证按钮可以水平满屏
    wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
    wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

    // 设置显示位置
    dialog.onWindowAttributesChanged(wl);
    // 设置点击外围解散
    dialog.setCanceledOnTouchOutside(true);
    dialog.show();
  }

  /**
   * 接受返回的图片路径并发送请求
   * 
   * @param data
   */
  public void onEvent(PictureData data) {
    image_path = data.picPath;
    // if (data.state.equals("0")) {
    requestData();
    // }
  }
  
  
  /**
   * 接受发布信息的结果
   * 
   * @param data
   */
  public void onEvent(RootPojo data) {
      if(data.isSucceed()){
        ToastUtil.show("发布成功");
      }else{
        ToastUtil.show("发布失败");
      }
      
  }

  /**
   * 绑定dialog控件
   * 
   * @param view
   */
  private void dialogfindId(View view) {
    btnPhoto = (Button) view.findViewById(R.id.btn_photo);
    btnCamera = (Button) view.findViewById(R.id.btn_camera);
    btnCancel = (Button) view.findViewById(R.id.btn_cancel);
  }

  /**
   * 注册dialog中控件监听器
   */
  private void dialogsetOnclick() {
    btnPhoto.setOnClickListener(this);
    btnCamera.setOnClickListener(this);
    btnCancel.setOnClickListener(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
  }



}
