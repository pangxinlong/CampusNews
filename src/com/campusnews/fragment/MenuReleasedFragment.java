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
import android.widget.TextView;

import com.campusnews.R;
import com.campusnews.annotation.AndroidView;

public class MenuReleasedFragment extends BaseFragment implements OnClickListener {

  /** 活动标题 */
  @AndroidView(R.id.et_activity_title)
  EditText etActivityTitle;

  /** 活动发布人 */
  @AndroidView(R.id.et_release_people)
  TextView etReleasePeople;

  /** 活动内容 */
  @AndroidView(R.id.et_activity_content)
  EditText etActivityContent;

  /** 活动图片父布局 */
  @AndroidView(R.id.rl_activity_image)
  RelativeLayout rl_activity_image;

  /** 活动图片 */
  @AndroidView(R.id.im_activity_image)
  ImageView im_activity_image;

  /** 发布按钮 */
  @AndroidView(R.id.btn_release)
  Button btnRelease;



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
    btnRelease.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_release:
        ;
        break;
      default:
        break;
    }

  }

}
