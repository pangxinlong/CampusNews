package com.campusnews.util;


import java.util.ArrayList;
import java.util.List;

import com.campusnews.BaseActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

public class BaseApplication extends Application{
	public static BaseApplication self = null;
	public Handler handlerCommon = new Handler();
	@Override
	public void onCreate() {
		super.onCreate();
		self = this;
	}
	
	private List<BaseActivity> mainActivity = new ArrayList<BaseActivity>();  
    public List<BaseActivity> MainActivity() {  
        return mainActivity;  
    }  
    public void addActivity(BaseActivity act) {  
        mainActivity.add(act);  
    }  
    public void finishAll() {  
        for (BaseActivity act : mainActivity) {  
            if (!act.isFinishing()) {  
                act.finish();  
            }  
        }  
        mainActivity = null;  
    } 
}
