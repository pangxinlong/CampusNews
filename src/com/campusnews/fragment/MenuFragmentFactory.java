package com.campusnews.fragment;

import android.support.v4.app.Fragment;

/**
 * 创建menu对应的fragment
 * 
 * @author password
 *
 */
public class MenuFragmentFactory extends BaseFragment {

  private static final int MENU_ACCOUNT_FRAGMENT = 0;
  private static final int MENU_NEWS_FRAGMENT = 1;
  private static final int MENU_OWN_FRAGMENT = 2;
  private static final int MENU_RELEASED_FRAGMENT = 3;
  private static final int MENU_SETTING_FRAGMENT = 4;

  public static Fragment newInstance(int point) {
    Fragment fragment = null;
    switch (point) {
      case MENU_ACCOUNT_FRAGMENT:
        fragment = new MenuAccountFragment();
        break;
      case MENU_NEWS_FRAGMENT:
        fragment = new MenuNewsFragment();
        break;
      case MENU_OWN_FRAGMENT:
        fragment = new MenuOwnFragment();
        break;
      case MENU_RELEASED_FRAGMENT:
        fragment = new MenuReleasedFragment();
        break;
      case MENU_SETTING_FRAGMENT:
        fragment = new MenuSettingFragment();

      default:
        break;
    }
    return fragment;
  }
}
