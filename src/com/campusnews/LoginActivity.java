package com.campusnews;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusnewes.bean.AccountBean;
import com.campusnewes.bean.LoginBean;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.UserInfo;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.PhoneUtils;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity implements OnClickListener {

  HashMap<String, String> params;
  String user_id;
  String pwd;

  /** 登录logo */
  @AndroidView(R.id.iv_logo)
  ImageView ivLogo;

  /** 用户名 */
  @AndroidView(R.id.et_login_name)
  EditText etLogin_name;

  /** 密码 */
  @AndroidView(R.id.et_login_password)
  EditText etLogin_password;

  /** 注册 */
  @AndroidView(R.id.bt_register)
  Button bt_register;

  /** 登录 */
  @AndroidView(R.id.bt_login)
  Button btLogin;

  /** 忘记密码 */
  @AndroidView(R.id.tv_forget_password)
  TextView tvForget_password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    EventBus.getDefault().register(this);

    initView();
  }


  public void loadData() {

    user_id = etLogin_name.getText().toString();
    pwd = etLogin_password.getText().toString();
    if (user_id.isEmpty()) {
      ToastUtil.show("用户名不能为空");
    } else if (pwd.isEmpty()) {
      ToastUtil.show("密码不能为空");
    } else {
      params = new HashMap<String, String>();
      params.put("user_id", user_id);
      params.put("pwd", pwd);

      requestData();
    }


  }

  public void initView() {
    btLogin.setOnClickListener(this);
    bt_register.setOnClickListener(this);
    tvForget_password.setOnClickListener(this);
  }

  public void requestData() {
    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this, params, StaticUrl.LoginUrl);
    jsonObjectRequestBase.makeSampleHttpRequest(LoginBean.class);
  }

  public void requestAccountData(String userId) {

    if (!PhoneUtils.isNetAvailable()) {
      ToastUtil.show("请检查您的网络");
      return;
    }
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id", userId);

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this, params, StaticUrl.Query_acount);
    jsonObjectRequestBase.makeSampleHttpRequest(AccountBean.class);

  }

  public void onEvent(LoginBean response) {

    if (response.isSucceed()) {
      ToastUtil.show("登录成功", Toast.LENGTH_SHORT);
      requestAccountData(user_id);
      MainActivity.intoMainActivity(this);
    } else {
      ToastUtil.show("登录失败", Toast.LENGTH_SHORT);
      Log.e("==========LOGIN.message======================", response.message);
    }
  }

  public void onEvent(AccountBean response) {
    if (response.isSucceed()) {
      BaseApplication.accountData = response.result.get(0);
      UserInfo.userId = BaseApplication.accountData.user_id;
      UserInfo.loginState = 0;// 记录登录状态

    }
  }


  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.bt_login:
        // MainActivity.intoMainActivity(this);
        loadData();
        break;
      case R.id.bt_register:
        RegistrationActivity.intoRegistrationActivity(this);
        ;
        break;
      case R.id.tv_forget_password:
        ;
        break;
      default:
        break;
    }

  }


  public static void intoLoginActivity(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, LoginActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

}
