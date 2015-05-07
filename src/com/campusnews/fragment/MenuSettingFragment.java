package com.campusnews.fragment;

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

import com.campusnews.ChangeAccountActivity;
import com.campusnews.R;
import com.campusnews.annotation.AndroidView;
import com.campusnews.util.BaseApplication;

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



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_menu_setting, container, false);
  }



  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
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
      case R.id.rl_account:ChangeAccountActivity.intoChangeAccountActivity(this.getActivity());
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
}
