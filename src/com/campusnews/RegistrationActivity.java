package com.campusnews;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.campusnews.adapter.RegistrationViewPagerAdapter;
import com.campusnews.annotation.AndroidView;
import com.campusnews.fragment.RegistrationOrdinaryFragment;
import com.campusnews.util.EventBusObject;
import com.campusnews.util.TitlebarUtil;
import com.campusnews.util.EventBusObject.PictureData;
import com.campusnews.util.SelectImageHelper;

import de.greenrobot.event.EventBus;

public class RegistrationActivity extends BaseActivity implements OnClickListener {

  private static String mpicPath = null;
  
  
  /** 普通用户 */
  @AndroidView(R.id.tv_registration_ordinary)
  TextView tvRegistration_ordinary;

  /** 社团用户 */
  @AndroidView(R.id.tv_registration_organizations)
  TextView tvRegistration_organizations;

  /** 普通用户下划线标示 */
  @AndroidView(R.id.iv_ordinary_line)
  TextView ivOrdinary_line;

  /** 社团用户下划线标示 */
  @AndroidView(R.id.iv_organizations_line)
  TextView ivOrganizations_line;

  /** 头像 */
  ImageView imRegistration_head;


  /** fragment viewpager */
  @AndroidView(R.id.vp_registration_fragment)
  ViewPager vpRegistration_fragment;



  public static final int TYPE_ORDINARY = 0;
  public static final int TYPE_ORGANIZATIONS = 1;

  RegistrationViewPagerAdapter registrationAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.acitivity_registration);
    initView();
    View inflateRootView =
        LayoutInflater.from(this).inflate(R.layout.fragment_registration_ordinary, null);
    imRegistration_head = (ImageView) inflateRootView.findViewById(R.id.im_registration_head);
  }



  public void requestData() {}

  public void initView() {

    String title = "注册";
    TitlebarUtil.setTitleBar(this, TitlebarUtil.PAGE_LEVLE_2, title);

    ivOrdinary_line.setSelected(true);
    ivOrganizations_line.setSelected(false);

    tvRegistration_ordinary.setOnClickListener(this);
    tvRegistration_organizations.setOnClickListener(this);

    registrationAdapter = new RegistrationViewPagerAdapter(getSupportFragmentManager());
    vpRegistration_fragment.setAdapter(registrationAdapter);

    vpRegistration_fragment.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageSelected(int postion) {
        if (postion == TYPE_ORDINARY) {
          ivOrdinary_line.setSelected(true);
          ivOrganizations_line.setSelected(false);
        } else {
          ivOrdinary_line.setSelected(false);
          ivOrganizations_line.setSelected(true);
        }
      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
      }

      @Override
      public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
      }
    });
  }

  @Override
  public void onClick(View view) {

    switch (view.getId()) {
      case R.id.tv_registration_ordinary:
        ivOrdinary_line.setSelected(true);
        ivOrganizations_line.setSelected(false);
        vpRegistration_fragment.setCurrentItem(0);
        break;
      case R.id.tv_registration_organizations:
        ivOrdinary_line.setSelected(false);
        ivOrganizations_line.setSelected(true);
        vpRegistration_fragment.setCurrentItem(1);
        break;
      default:
        break;
    }
  }

  public static void intoRegistrationActivity(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, RegistrationActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
//    mpicPath =
//        SelectImageHelper.getImagePath(this, requestCode, resultCode, data, imRegistration_head);
//
//    // pictureData.picPath = mpicPath;
//    if (mpicPath != null) {
//     registrationAdapter.setData(mpicPath);
//      Log.e("========EventBus=========","pictureData is post");
//      registrationAdapter.notifyDataSetChanged();
//    }
  }
  
  public  String getPicPath(){
    return mpicPath;
  }
}
