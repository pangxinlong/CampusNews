package com.campusnews.fragment;

import java.util.HashMap;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campusnewes.bean.AccountBean;
import com.campusnews.ChangeAccountActivity;
import com.campusnews.R;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.UserInfo;
import com.campusnews.util.BaseApplication;
import com.campusnews.util.StaticUrl;

import de.greenrobot.event.EventBus;

public class MenuSettingFragment extends BaseFragment implements OnClickListener {

  /** 用户信息父布局 */
  @AndroidView(R.id.rl_account)
  RelativeLayout rlAccount;

  /** 关于父布局 */
  @AndroidView(R.id.rl_about)
  RelativeLayout rlAbout;

  /** 版本号 */
  @AndroidView(R.id.tv_version)
  TextView tvVersion;

  /** 退出 */
  @AndroidView(R.id.btn_sign_out)
  Button btnSignOut;

  AccountBean accountData;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_menu_setting, container, false);
  }



  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    EventBus.getDefault().register(this);
    initView();
  }



  private void initView() {
    tvVersion.setText("v" + getVersion());

    rlAccount.setOnClickListener(this);
    rlAbout.setOnClickListener(this);
    btnSignOut.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.rl_account:
        requestData();
        ;
        break;
      case R.id.rl_about:
        ;
        break;
      case R.id.btn_sign_out:
        signOut();
        ;
        break;
      default:
        break;
    }

  }

  private void requestData() {

    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id", UserInfo.userId);

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this.getActivity(), params, StaticUrl.Query_acount);
    jsonObjectRequestBase.makeSampleHttpRequest(AccountBean.class);
  }


  public void onEvent(AccountBean accountData) {
    if (accountData.isSucceed()) {
      this.accountData = accountData;
      UserInfo.userName = accountData.result.get(0).name;
      UserInfo.userId=accountData.result.get(0).user_id;
      UserInfo.userType = accountData.result.get(0).type;
      ChangeAccountActivity.intoChangeAccountActivity(this.getActivity(), accountData);
    }
  }

  /**
   * 退出
   */
  private void signOut() {
    BaseApplication baseApplication = (BaseApplication) this.getActivity().getApplicationContext();
    baseApplication.finishAll();
  }

  /**
   * 获取版本号
   * 
   * @return String
   */
  public String getVersion() {
    String version = "0.0.0";

    PackageManager packageManager = this.getActivity().getPackageManager();
    try {
      PackageInfo packageInfo =
          packageManager.getPackageInfo(this.getActivity().getPackageName(), 0);
      version = packageInfo.versionName;
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }

    return version;
  }



  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
  }
  
  
}
