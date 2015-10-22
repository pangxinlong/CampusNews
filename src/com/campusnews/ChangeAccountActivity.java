package com.campusnews;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.campusnewes.bean.AccountBean;
import com.campusnewes.bean.RootPojo;
import com.campusnews.model.ImageUploadRequestBase;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.LoadingImage;
import com.campusnews.model.UserInfo;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.PhoneUtils;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.TitlebarUtil;
import com.campusnews.util.ToastUtil;
import com.campusnews.util.EventBusObject.PictureData;
import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.annotation.AndroidView;

import de.greenrobot.event.EventBus;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ChangeAccountActivity extends BaseActivity implements OnClickListener {

  // ======普通用户======
  /** 普通用户 */
  @AndroidView(R.id.ordinary_account)
  View ordinary_account;

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


  // ======社团用户======

  /** 社团用户 */
  @AndroidView(R.id.organizations)
  View organizations;

  /** 昵称 */
  @AndroidView(R.id.et_registration_oNickname)
  EditText etRegistration_oNickname;

  /** 头像-父布局 */
  @AndroidView(R.id.rl_registration_oHead)
  RelativeLayout rl_registration_oHead;

  /** 头像 */
  @AndroidView(R.id.im_registration_oHead)
  ImageView im_registration_oHead;

  /** 用户名 */
  @AndroidView(R.id.et_registration_oUser_id)
  EditText etRegistration_oUser_id;

  /** 密码 */
  @AndroidView(R.id.et_registration_oInput_password)
  EditText et_registration_oInput_password;

  /** 确认密码 */
  @AndroidView(R.id.et_registration_oPassword_again)
  EditText et_registration_oPassword_again;

  /** 真实姓名 */
  @AndroidView(R.id.et_registration_oName)
  EditText et_registration_oName;

  /** 联系方式 */
  @AndroidView(R.id.et_registration_phone)
  EditText et_registration_phone;

  // 自带属性
  /** 完成 */
  @AndroidView(R.id.btn_complete)
  Button btnComplete;


  /** 取消 */
  @AndroidView(R.id.btn_cancle)
  Button btnCancle;

  /**
   * 用户类型
   */
  private int AccounttYpe;

  private String nickName;// 昵称
  private String passwrod;// 密码
  private String sex;// 性别
  private String professional;// 专业
  private String icon_path;// 头像
  private String grade;// 年级
  private String contact_information;// 联系方式

  AccountBean accountData;

  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.activity_change_account);
    EventBus.getDefault().register(this);
    initView();
  }


  private void initView() {
    UserInfo.isNews=1;
    
    String title = "修改用户";
    TitlebarUtil.setTitleBar(this, TitlebarUtil.PAGE_LEVLE_2, title);

    //getAccountData();

    // 判断用户类型
    if (AccounttYpe == UserInfo.ORDINARY_TYPE) {
      ordinary_account.setVisibility(View.VISIBLE);
      organizations.setVisibility(View.INVISIBLE);

      etRegistration_userId.setFocusable(false);
      etRegistration_userId.setEnabled(false);
      etRegistration_userName.setFocusable(false);
      etRegistration_userName.setEnabled(false);

      etRegistration_userId.setText(UserInfo.userId);
      etRegistration_userName.setText(UserInfo.userName);
      etRegistration_nickname.setText(BaseApplication.accountData.nickname);
      etRegistration_inputPassword.setText(BaseApplication.accountData.password);
      etRegistration_professional.setText(BaseApplication.accountData.professional);
      etRegistration_sex.setText(BaseApplication.accountData.sex);
      etRegistration_grade.setText(BaseApplication.accountData.grade);

      
      LoadingImage.loadImage(this, imRegistration_head, StaticUrl.baseImageUlr+BaseApplication.accountData.icon, UserInfo.isNews);
      
      rlRegistration_head.setOnClickListener(this);
    } else {
      ordinary_account.setVisibility(View.INVISIBLE);
      organizations.setVisibility(View.VISIBLE);

      etRegistration_oUser_id.setFocusable(false);
      etRegistration_oUser_id.setEnabled(false);
      et_registration_oName.setFocusable(false);
      et_registration_oName.setEnabled(false);

      etRegistration_oUser_id.setText(UserInfo.userId);
      et_registration_oName.setText(UserInfo.userName);
      etRegistration_oNickname.setText(BaseApplication.accountData.nickname);
      et_registration_oInput_password.setText(BaseApplication.accountData.password);
      et_registration_phone.setText(BaseApplication.accountData.contact_information);
      rl_registration_oHead.setOnClickListener(this);
      
      LoadingImage.loadImage(this, im_registration_oHead, StaticUrl.baseImageUlr+BaseApplication.accountData.icon, UserInfo.isNews);
    }

    btnComplete.setOnClickListener(this);
    btnCancle.setOnClickListener(this);
  }

  /**
   * 获取传过来的账户信息
   */
  private void getAccountData() {
    Intent intent = this.getIntent();
    Bundle bundle = intent.getExtras();

    accountData = (AccountBean) bundle.get("accountData");

    AccounttYpe = accountData.result.get(0).type;
    UserId = UserInfo.userId;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.rl_registration_oHead:
        showDialog();
        break;
      case R.id.rl_registration_head:
        showDialog();
        break;
      case R.id.btn_complete:
        if (chekfromat()) {
          getImageParams(picFile);
        }
        break;
      case R.id.btn_cancle:
        this.finish();
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
   * 发送请求
   */
  private void requestData() {
    if (!PhoneUtils.isNetAvailable()) {
      ToastUtil.show("请检查您的网络");
      return;
    }
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id", UserInfo.userId);
    params.put("pwd", passwrod);
    params.put("nickname", nickName);
    params.put("sex", sex);
    params.put("professional", professional);
    params.put("icon_path", picPath);
    params.put("grade", grade);
    params.put("contact_information", contact_information);

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this, params, StaticUrl.Change_acountUrl);
    jsonObjectRequestBase.makeSampleHttpRequest(RootPojo.class);
  }

  public void onEvent(RootPojo response) {
    if (response.isSucceed()) {
      ToastUtil.show("修改成功!");
      this.finish();
    }
  }


  /**
   * 检查输入格式
   * 
   * @return
   */
  private boolean chekfromat() {
    String pwd, pwdConfirm;

    // 普通用户
    if (AccounttYpe == UserInfo.ORDINARY_TYPE) {
      nickName = etRegistration_nickname.getText().toString();

      pwd = etRegistration_inputPassword.getText().toString();
      pwdConfirm = etRegistration_passwordAgain.getText().toString();
      // 验证确认码
      if (pwd.equals(pwdConfirm)) {
        passwrod = pwd;

      } else {
        ToastUtil.show("确认码与密码不一致！");
        return false;
      }

      sex = etRegistration_sex.getText().toString();
      professional = etRegistration_professional.getText().toString();
      //icon_path=picPath;
      grade = etRegistration_grade.getText().toString();
      contact_information = "";
    } else {
      // 社团用户
      nickName = etRegistration_oNickname.getText().toString();

      // 验证确认码
      pwd = et_registration_oInput_password.getText().toString();
      pwdConfirm = et_registration_oPassword_again.getText().toString();
      if (pwd.equals(pwdConfirm)) {
        passwrod = pwd;
      } else {
        ToastUtil.show("确认码与密码不一致！");
        return false;
      }

      sex = "";
      professional = "";
      grade = "";
     // icon_path=picPath;
      contact_information = et_registration_phone.getText().toString();
    }
    return true;
  }

  public static void intoChangeAccountActivity(Context context, AccountBean accountData) {
    Intent intent = new Intent();
    intent.setClass(context, ChangeAccountActivity.class);

    Bundle bundle = new Bundle();
    bundle.putSerializable("accountData", accountData);
    intent.putExtras(bundle);

    context.startActivity(intent);
  }



  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  // ******************************上传图片********************************
  String picPath = "/Users/password/Myjs/Test/RequireDemo/TestWithCanshu/image";
  private Uri photoUri;
  private final int PIC_FROM_CAMERA = 1; // 从相机拍摄图片
  private final int PIC_FROM＿LOCALPHOTO = 0;// 从相册选择图片
  File picFile;
  String picName;// 图片名称
  String UserId;

  Button btnPhoto;// 相册
  Button btnCamera;// 相机
  Button btnCancel;// 取消
  Dialog dialog;

  /**
   * 获取图片相传的相关参数
   */
  private void getImageParams(File file) {
    if (file.length() != 0) {
      // 获取参数
      final Map<String, String> params = new HashMap<String, String>();
      params.put("userId", "1");

      // 上传图片
      ImageUploadRequestBase imageUpload = new ImageUploadRequestBase(file, this, params);
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
      bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
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
    View view = this.getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
    dialogfindId(view);
    dialogsetOnclick();

    dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
    dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
        LayoutParams.WRAP_CONTENT));
    Window window = dialog.getWindow();
    // 设置显示动画
    window.setWindowAnimations(R.style.main_menu_animstyle);
    WindowManager.LayoutParams wl = window.getAttributes();
    wl.x = 0;
    wl.y = this.getWindowManager().getDefaultDisplay().getHeight();
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

  public void onEvent(PictureData data) {
    picPath = data.picPath;
    requestData();
  }

}
