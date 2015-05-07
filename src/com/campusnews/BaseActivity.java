package com.campusnews;

import com.campusnews.annotation.AndroidAutowire;
import com.campusnews.util.BaseApplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	    BaseApplication baseApplication = (BaseApplication) this.getApplication();
	    baseApplication.addActivity(this);

	}

	@Override
	public void setContentView(final int layoutResID) {
		super.setContentView(layoutResID);
		// 设置自动绑定ViewId和View功能
		AndroidAutowire.autowire(getWindow().getDecorView(), this);
	};

}
