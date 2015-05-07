package com.campusnews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.campusnews.R;
import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.annotation.AndroidView;


public class RegistrationOrganizationsFragment extends BaseFragment implements OnClickListener{

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

  /** 注册 */
  @AndroidView(R.id.bt_Oregistration)
  Button bt_Oregistration;



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view =
        LayoutInflater.from(getActivity()).inflate(R.layout.fragment_registration_organizations,
            container, false);
    AndroidAutowire.autowire(view, this);
    return view;
  }



  @Override
  public void onClick(View view) {
    switch(view.getId()){
      case R.id.rl_registration_oHead:;break;
      case R.id.bt_Oregistration:;break;
    }
    
  }
}
