package com.campusnews;

import java.lang.ref.WeakReference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.campusnews.fragment.MenuFragmentFactory;
import com.campusnews.fragment.MenuNewsFragment;
import com.campusnews.util.BaseApplication;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;


@SuppressWarnings("deprecation")
public class MainActivity extends BaseActivity implements
		OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] menuLists;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String titleString;
	private int iSelect = -1;
	private View heandrView;
	
	  private MsgReceiver updateListViewReceiver;
	  private Context context;
	  Message m = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/* get the application title获得标题 */
		titleString = (String) getTitle();

		initView();

		Bundle args = new Bundle();
		args.putString("text", menuLists[0]);
		Fragment fragment = new MenuNewsFragment();
		fragment.setArguments(args);
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, fragment).commit();
		
		
		//注册推送服务
		   context = this;
		    XGPushConfig.enableDebug(this, true);
		 // 0.注册数据更新监听器
		    updateListViewReceiver = new MsgReceiver();
		    IntentFilter intentFilter = new IntentFilter();
		    intentFilter.addAction("com.xgtest.UPDATE_LISTVIEW");
		    registerReceiver(updateListViewReceiver, intentFilter);
		    
		 // 1.获取设备Token
		    Handler handler = new HandlerExtension(MainActivity.this);
		    m = handler.obtainMessage();
		    
		    
		 // 注册接口
		    XGPushManager.registerPush(getApplicationContext(),
		            new XGIOperateCallback() {
		                @Override
		                public void onSuccess(Object data, int flag) {
		                    Log.w(Constants.LogTag,
		                            "+++ register push sucess. token:" + data);
		                    m.obj = "+++ register push sucess. token:" + data;
		                    m.sendToTarget();
		                }

		                @Override
		                public void onFail(Object data, int errCode, String msg) {
		                    Log.w(Constants.LogTag,
		                            "+++ register push fail. token:" + data
		                                    + ", errCode:" + errCode + ",msg:"
		                                    + msg);

		                    m.obj = "+++ register push fail. token:" + data
		                            + ", errCode:" + errCode + ",msg:" + msg;
		                    m.sendToTarget();
		                }
		            });
	}

	// 初始化
	public void initView() {
		heandrView = LayoutInflater.from(this).inflate(
				R.layout.home_menu_list_header, null);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		mDrawerList.addHeaderView(heandrView);
		menuLists = getResources().getStringArray(R.array.menu_contetn);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuLists);
		mDrawerList.setAdapter(adapter);
		// 给mDrawerList设置监听
		mDrawerList.setOnItemClickListener(this);

		/*
		 * set the shadow for drawer at start(left) or
		 * end(right)设置drawerLayout侧滑方式（左向右或右向左）
		 */
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		/* show the home icon */
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/* make sure the home icon enable click */
		getActionBar().setHomeButtonEnabled(true);

		/*
		 * set the application ActionBar title changes
		 * 监听DrawerLayout打开关闭的状态，用来设置标题
		 */
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(R.string.please);

			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (-1 == iSelect) {
					getActionBar().setTitle(titleString);
				} else if(iSelect==0){
					getActionBar().setTitle("个人信息");
				}else{
					getActionBar().setTitle(menuLists[iSelect - 1]);
				}
			}

		};
		/* 给DrawerLayout设置监听 set the DrawerLayout Listener */
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* make sure the home icon enable click and display the DrawerLayout */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View container, int point,
			long arg3) {
		creatFragment(point);
	}

	// 创建菜单栏的fragment
	public void creatFragment(int point) {
		Toast.makeText(this, "菜单第" + point + "个item", Toast.LENGTH_SHORT)
				.show();
		Fragment fragment = MenuFragmentFactory.newInstance(point);
		Bundle args = new Bundle();
		iSelect = point;
		Log.i("iselect============",iSelect+"");
		if (0 == iSelect) {
			args.putString("text", "我的账户");
			//iSelect = 1;
		} else {
			args.putString("text", menuLists[iSelect-1]);
		}
		fragment.setArguments(args);

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, fragment).commit();
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private static class HandlerExtension extends Handler {
	    WeakReference<MainActivity> mActivity;

	    HandlerExtension(MainActivity activity) {
	        mActivity = new WeakReference<MainActivity>(activity);
	    }

	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        MainActivity theActivity = mActivity.get();
	        if (theActivity == null) {
	            theActivity = new MainActivity();
	        }
//	        if (msg != null) {
//	            Log.w(Constants.LogTag, msg.obj.toString());
//	            TextView textView = (TextView) theActivity
//	                   .findViewById(R.id.deviceToken);
//	            textView.setText(XGPushConfig.getToken(theActivity));
//	        }
	      //   XGPushManager.registerCustomNotification(theActivity,
	      //   "BACKSTREET", "BOYS", System.currentTimeMillis() + 5000, 0);
	    }
	}
	
	
	
	public class MsgReceiver extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        // TODO Auto-generated method stub
	      Log.i("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝","接收到推送消息");
	      //  allRecorders = notificationService.getCount();
	      //  getNotificationswithouthint(id);
	    }
	}
	
	public static void intoMainActivity(Context context){
	  Intent intent=new Intent(context,MainActivity.class);
	  context.startActivity(intent);
	}

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(updateListViewReceiver);
  }
	
	
}
