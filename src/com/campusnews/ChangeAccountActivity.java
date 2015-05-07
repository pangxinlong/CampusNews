package com.campusnews;

import com.campusnews.util.TitlebarUtil;
import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.annotation.AndroidView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ChangeAccountActivity extends BaseActivity implements OnClickListener {

  // ======普通用户======

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

  /** 年级 */
  @AndroidView(R.id.bt_registration)
  Button btRegistration;


  // ======社团用户======

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

  // 在带属性
  /** 完成 */
  @AndroidView(R.id.btn_complete)
  Button btnComplete;


  /** 取消 */
  @AndroidView(R.id.btn_cancle)
  Button btnCancle;

  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);
    setContentView(R.layout.activity_change_account);
    initView();
  }


  private void initView() {
    String title = "修改用户";
    TitlebarUtil.setTitleBar(this, TitlebarUtil.PAGE_LEVLE_2, title);

    btnComplete.setOnClickListener(this);
    btnCancle.setOnClickListener(this);
  }

  public static void intoChangeAccountActivity(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, ChangeAccountActivity.class);
    context.startActivity(intent);
  }


  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_complete:
        ;
        break;
      case R.id.btn_cancle:
        this.finish();
        ;
        break;
      default:
        break;
    }

  }

}
