package com.campusnews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.Toast;

import com.campusnews.fragment.MenuFragmentFactory;
import com.campusnews.fragment.MenuNewsFragment;
import com.campusnews.util.BaseApplication;

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

	
	public static void intoMainActivity(Context context){
	  Intent intent=new Intent(context,MainActivity.class);
	  context.startActivity(intent);
	}
}
