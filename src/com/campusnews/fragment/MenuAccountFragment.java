package com.campusnews.fragment;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusnewes.bean.AccountBean;
import com.campusnews.R;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.LoadingImage;
import com.campusnews.model.UserInfo;
import com.campusnews.util.PhoneUtils;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;

import de.greenrobot.event.EventBus;

public class MenuAccountFragment extends BaseFragment {

  // **************普通用户****************
  /** 普通用户布局 */
  @AndroidView(R.id.rl_putong)
  RelativeLayout rl_putong;

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

  // **************社团用户****************

  /** 社团用户布局 */
  @AndroidView(R.id.rl_shetuan)
  RelativeLayout rl_shetuan;

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


  // 用户类别
  int accountType;

  private String nickName;// 昵称
  private String realName;// 真是姓名
  AccountBean accountData;

  Bitmap pic;// 头像

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_menu_account, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    EventBus.getDefault().register(this);
    requestData();
  }

  private void initView() {
    UserInfo.isNews=1;
    
    accountType = Integer.valueOf(accountData.result.get(0).type);
    nickName = accountData.result.get(0).nickname;
    realName = accountData.result.get(0).name;

    String url = StaticUrl.baseImageUlr + accountData.result.get(0).icon;
    Log.i("=======url========", url);

    // 普通用户
    if (accountType == UserInfo.ORDINARY_TYPE) {
      rl_putong.setVisibility(View.VISIBLE);
      rl_shetuan.setVisibility(View.INVISIBLE);

      tv_account_nickname.setText(nickName);
      // im_account_head.
      tv_account_user_name.setText(realName);
      tv_account_sex.setText(accountData.result.get(0).sex);
      tv_account_professional.setText(accountData.result.get(0).professional);
      tv_account_grade.setText(accountData.result.get(0).grade);

      LoadingImage.loadImage(this.getActivity(), im_account_head, url,UserInfo.isNews);
    }
    // 社团用户
    else {
      rl_putong.setVisibility(View.INVISIBLE);
      rl_shetuan.setVisibility(View.VISIBLE);

      tv_account_oNickname.setText(nickName);
      tv_account_oName.setText(realName);
      tv_account_phone.setText(accountData.result.get(0).contact_information);

      LoadingImage.loadImage(this.getActivity(), im_account_oHead, url,UserInfo.isNews);
    }
  }

  /**
   * 请求数据
   */
  private void requestData() {
    if (!PhoneUtils.isNetAvailable()) {
      ToastUtil.show("请检查您的网络");
      return;
    }
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id", UserInfo.userId);

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this.getActivity(), params, StaticUrl.Query_acount);
    jsonObjectRequestBase.makeSampleHttpRequest(AccountBean.class);
  }

  private boolean isRequest;

  public void onEvent(AccountBean accountData) {
    if (accountData.isSucceed()) {
      this.accountData = accountData;
      UserInfo.userName = accountData.result.get(0).name;
      if (isRequest) {
        initView();
        isRequest = false;
      }
    }
  }



  @Override
  public void onResume() {
    super.onResume();
    isRequest = true;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
  }



  @Override
  public void onDestroy() {
    super.onDestroy();

  }



}
