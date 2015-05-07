package com.campusnews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusnews.R;
import com.campusnews.annotation.AndroidView;

public class MenuAccountFragment extends BaseFragment {

  /** 普通用户——昵称 */
  @AndroidView(R.id.tv_account_nickname)
  TextView tv_account_nickname;

  /** 普通用户——头像 */
  @AndroidView(R.id.im_account_head)
  ImageView im_account_head;

  /** 普通用户——真实姓名 */
  @AndroidView(R.id.tv_account_user_name)
  TextView tv_account_user_name;

  /** 普通用户——性别 */
  @AndroidView(R.id.tv_account_sex)
  TextView tv_account_sex;

  /** 普通用户——专业 */
  @AndroidView(R.id.tv_account_professional)
  TextView tv_account_professional;

  /** 普通用户——年级 */
  @AndroidView(R.id.tv_account_grade)
  TextView tv_account_grade;



  /** 社团用户——昵称 */
  @AndroidView(R.id.tv_account_oNickname)
  TextView tv_account_oNickname;

  /** 社团用户——头像 */
  @AndroidView(R.id.im_account_oHead)
  ImageView im_account_oHead;

  /** 社团用户——真实姓名 */
  @AndroidView(R.id.tv_account_oName)
  TextView tv_account_oName;

  /** 社团用户——联系电话 */
  @AndroidView(R.id.tv_account_phone)
  TextView tv_account_phone;


  //用户类别
  int accountType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    return inflater.inflate(R.layout.fragment_menu_account, container, false);
  }

}
