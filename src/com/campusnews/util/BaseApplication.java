package com.campusnews.util;


import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.campusnewes.bean.AccountBean.AccountData;
import com.campusnews.BaseActivity;

public class BaseApplication extends Application{
  
    public static RequestQueue mQueue;
	public static BaseApplication self = null;
	public Handler handlerCommon = new Handler();
    public static AccountData accountData;
	@Override
	public void onCreate() {
		super.onCreate();
		self = this;
		mQueue = Volley.newRequestQueue(BaseApplication.self);
		accountData=new AccountData();
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
