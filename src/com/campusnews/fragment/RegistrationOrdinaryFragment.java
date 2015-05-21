package com.campusnews.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.campusnewes.bean.RegistrationBean;
import com.campusnewes.bean.RootPojo;
import com.campusnews.BaseActivity;
import com.campusnews.LoginActivity;
import com.campusnews.R;
import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.ImageUploadRequestBase;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.UserInfo;
import com.campusnews.util.EventBusObject.PictureData;
import com.campusnews.util.PhoneUtils;
import com.campusnews.util.SelectImageHelper;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;

import de.greenrobot.event.EventBus;

public class RegistrationOrdinaryFragment extends BaseFragment implements OnClickListener {

  /** 昵称 */
  @AndroidView(R.id.et_registration_nickname)
  EditText etRegistration_nickname;


  /** 头像-父布局 */
  @AndroidView(R.id.rl_registration_head)
  RelativeLayout rlRegistration_head;

  /** 头像 */
  @AndroidView(R.id.im_registration_head)
  ImageView imRegistration_head;

  /** 用户名 */
  @AndroidView(R.id.et_registration_user_id)
  EditText etRegistration_userId;


  /** 密码 */
  @AndroidView(R.id.et_registration_input_password)
  EditText etRegistration_inputPassword;

  /** 确认密码 */
  @AndroidView(R.id.et_registration_password_again)
  EditText etRegistration_passwordAgain;

  /** 真实姓名 */
  @AndroidView(R.id.et_registration_user_name)
  EditText etRegistration_userName;

  /** 专业 */
  @AndroidView(R.id.et_registration_professional)
  EditText etRegistration_professional;

  /** 性别 */
  @AndroidView(R.id.et_registration_sex)
  EditText etRegistration_sex;

  /** 年级 */
  @AndroidView(R.id.et_registration_grade)
  EditText etRegistration_grade;

  /** 注册 */
  @AndroidView(R.id.bt_registration)
  Button btRegistration;

  String nickname;// 昵称
  String userId;// 用户名
  String password;// 密码
  String passwordAgain;// 确认密码
  String userName;// 真实姓名
  String professional;// 专业
  String sex;// 性别
  String grade; // 年级
  int type; // 用户类型
  int state; // 用户账号状态
  String picPath = "/Users/password/Myjs/Test/RequireDemo/TestWithCanshu/image";// 图片路径

  Button btnPhoto;// 相册
  Button btnCamera;// 相机
  Button btnCancel;// 取消
  Dialog dialog;


  private Uri photoUri;
  private final int PIC_FROM_CAMERA = 1; // 从相机拍摄图片
  private final int PIC_FROM＿LOCALPHOTO = 0;// 从相册选择图片
  File picFile;
  String UserId = "pang";
  String picName;// 图片名称

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // EventBus.getDefault().register(this);
    View inflateRootView =
        LayoutInflater.from(getActivity()).inflate(R.layout.fragment_registration_ordinary, null);
    AndroidAutowire.autowire(inflateRootView, this);
    return inflateRootView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();

  }

  public void initView() {
    EventBus.getDefault().register(this);

    Bundle bundle = this.getArguments();
    state = bundle.getInt("userType");
    rlRegistration_head.setOnClickListener(this);
    btRegistration.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.rl_registration_head:
        showDialog();
        break;
      case R.id.bt_registration:
        // Log.i("======checkFormat()========",checkFormat()+"");

        if (!PhoneUtils.isNetAvailable()) {
          ToastUtil.show("请检查您的网络");
          return;
        }
        if (checkFormat()) {
          getImageParams(picFile);
        }
        break;
      case R.id.btn_photo:
        doHandlerPhoto(PIC_FROM＿LOCALPHOTO);
        dialog.cancel();
        break;
      case R.id.btn_camera:
        doHandlerPhoto(PIC_FROM_CAMERA);
        dialog.cancel();
        break;
      case R.id.btn_cancel:
        dialog.cancel();
        break;
      default:
        break;
    }
  }

  /**
   * 获取图片相传的相关参数
   */
  private void getImageParams(File file) {
    if (file.length() != 0) {
      // 获取参数
      final Map<String, String> params = new HashMap<String, String>();
      params.put("userId", "1");

      // 上传图片
      ImageUploadRequestBase imageUpload =
          new ImageUploadRequestBase(file, this.getActivity(), params);
      imageUpload.upload(imRegistration_head);
    } else {
      ToastUtil.show("请重新选取图片");
    }
  }

 


  /**
   * 根据不同方式选择图片设置ImageView
   * 
   * @param type 0-本地相册选择，非0为拍照
   */
  private void doHandlerPhoto(int type) {
    try {
      // 保存裁剪后的图片文件
      File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
      if (!pictureFileDir.exists()) {
        pictureFileDir.mkdirs();
      }
      // 为图片命名
      picName = UserId + String.valueOf(PhoneUtils.getCurrentData()) + ".jpeg";// getCurrentData()+"upload";
      Log.i("=========picName==============", "" + picName);
      picFile = new File(pictureFileDir, picName);
      if (!picFile.exists()) {
        picFile.createNewFile();
      }
      photoUri = Uri.fromFile(picFile);
      if (type == PIC_FROM＿LOCALPHOTO) {
        Intent intent = getCropImageIntent();
        startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
      } else {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
      }

    } catch (Exception e) {
      Log.i("HandlerPicError", "处理图片出现错误");
    }
  }

  /**
   * 调用图片剪辑程序
   */
  public Intent getCropImageIntent() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
    intent.setType("image/*");
    setIntentParams(intent);
    return intent;
  }

  /**
   * 启动裁剪
   */
  private void cropImageUriByTakePhoto() {
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(photoUri, "image/*");
    setIntentParams(intent);
    startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
  }

  /**
   * 设置公用参数
   */
  private void setIntentParams(Intent intent) {
    intent.putExtra("crop", "true");
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);
    intent.putExtra("outputX", 600);
    intent.putExtra("outputY", 600);
    intent.putExtra("noFaceDetection", true); // no face detection
    intent.putExtra("scale", true);
    intent.putExtra("return-data", false);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case PIC_FROM_CAMERA: // 拍照
        try {
          cropImageUriByTakePhoto();
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      case PIC_FROM＿LOCALPHOTO:
        if (data == null) {
          picName = null;
        }
        try {
          if (photoUri != null && picName != null) {
            Bitmap bitmap = decodeUriAsBitmap(photoUri);
            imRegistration_head.setImageBitmap(bitmap);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
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

  /**
   * 检查注册填写信息
   * 
   * @return boolean
   */
  private boolean checkFormat() {
    nickname = etRegistration_nickname.getText().toString();
    userId = etRegistration_userId.getText().toString();
    password = etRegistration_inputPassword.getText().toString();
    passwordAgain = etRegistration_passwordAgain.getText().toString();
    userName = etRegistration_userName.getText().toString();
    professional = etRegistration_professional.getText().toString();
    sex = etRegistration_sex.getText().toString();
    grade = etRegistration_grade.getText().toString();

    if (nickname.isEmpty()) {
      ToastUtil.show("请输入昵称");
      return false;
    } else if (userId.isEmpty()) {
      ToastUtil.show("请输入用户名（学号）");
      return false;
    } else if (password.isEmpty()) {
      ToastUtil.show("请输入密码");
      return false;
    } else if (passwordAgain.isEmpty()) {
      ToastUtil.show("请输入确认密码");
      return false;
    } else if (userName.isEmpty()) {
      ToastUtil.show("请输入真实姓名");
      return false;
    } else if (professional.isEmpty()) {
      ToastUtil.show("请输入专业");
      return false;
    } else if (sex.isEmpty()) {
      ToastUtil.show("请输入性别");
      return false;
    } else if (grade.isEmpty()) {
      ToastUtil.show("请输入年级");
      return false;
    } else if (!password.equals(passwordAgain)) {
      ToastUtil.show("确认密码与密码不匹配");
      return false;
    }
    return true;
  }

  /**
   * 发送注册信息
   */
  private void requestData() {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id", userId);
    params.put("pwd", password);
    params.put("nickname", nickname);
    params.put("name", userName);
    params.put("sex", sex);
    params.put("professional", professional);
    params.put("type", String.valueOf(state));
    params.put("state", "0");
    params.put("icon_path", picPath);
    params.put("contact_information", "");
    params.put("grade", grade);

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this.getActivity(), params,StaticUrl.RegistrationUrl);
    jsonObjectRequestBase.makeSampleHttpRequest(RegistrationBean.class);
  }

  public void onEvent(PictureData data) {
    picPath = data.picPath;
   // if (data.state.equals("0")) {
      requestData();
   // }
  }

  public void onEvent(RegistrationBean data) {
    if (data.isSucceed()) {
      ToastUtil.show("注册成功！");
      UserInfo.userName=userName;
      LoginActivity.intoLoginActivity(this.getActivity());
    }
  }

  @Override
  public void onDestroyView() {
    EventBus.getDefault().unregister(this);
    super.onDestroyView();
  }
}
