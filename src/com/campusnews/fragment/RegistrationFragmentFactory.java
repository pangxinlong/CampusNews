package com.campusnews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class RegistrationFragmentFactory extends BaseFragment {
  public static final int TYPE_ORDINARY = 0;
  public static final int TYPE_ORGANIZATIONS = 1;

  public static Fragment newInstance(int postion, String path) {
    Fragment fragment = null;
    switch (postion) {
      case TYPE_ORDINARY:
        fragment = new RegistrationOrdinaryFragment();
        break;
      case TYPE_ORGANIZATIONS:
        fragment = new RegistrationOrganizationsFragment();
        break;
      default:
        break;
    }

    if (path != null) {
      Bundle bundle = new Bundle();
      bundle.putString("path", path);
      fragment.setArguments(bundle);
    }
    return fragment;

  }
}
