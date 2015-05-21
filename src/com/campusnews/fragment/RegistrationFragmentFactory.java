package com.campusnews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class RegistrationFragmentFactory extends BaseFragment {
  public static final int TYPE_ORDINARY = 0;
  public static final int TYPE_ORGANIZATIONS = 1;
  private static int userType;
  
  public static Fragment newInstance(int postion) {
    Fragment fragment = null;
    switch (postion) {
      case TYPE_ORDINARY:
        fragment = new RegistrationOrdinaryFragment();
        userType=TYPE_ORDINARY;
        break;
      case TYPE_ORGANIZATIONS:
        fragment = new RegistrationOrganizationsFragment();
        userType=TYPE_ORGANIZATIONS;
        break;
      default:
        break;
    }

      Bundle bundle = new Bundle();
      bundle.putInt("userType", userType);
      fragment.setArguments(bundle);
    
    return fragment;

  }
}
