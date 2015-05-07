package com.campusnews.fragment;


import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.campusnews.NewDetailActivity;
import com.campusnews.R;
import com.campusnews.adapter.FragmentNewsHeaderAdapter;
import com.campusnews.adapter.FragmentNewsListViewAdapter;
import com.campusnews.annotation.AndroidView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FragmentNews extends BaseFragment {

  @AndroidView(R.id.pull_refresh_list)
  PullToRefreshListView pull_refresh_list;

  private FragmentNewsListViewAdapter mAdapter;
  int y;

  int location = 100;

  ViewGroup headerView;
  ViewPager viewPager;
  RadioGroup radioGroup;

  public boolean isLoop = true;

  // 图片下面的小圆点
  private int[] listbutton = {R.id.imageView1, R.id.imageView2, R.id.imageView3};
  List<Integer> list;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view =
        LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, container, false);
    return view;
  }



  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Bundle data = this.getArguments();
    location = data.getInt("position");
    // if(id==1){
    // setUserVisibleHint(true);
    // }
    initData();
    initView();
    setAdapter();

    // mAdapter.notifyDataSetChanged();
  }

  @Override
  protected void initData() {
    //图片数据
    list = new ArrayList<Integer>();
    list.add(R.drawable.img1);
    list.add(R.drawable.img2);
    list.add(R.drawable.img3);

    automaticSwitch();
  }

  // 实现header自动切换功能
  private void automaticSwitch() {
    new Thread(new Runnable() {

      @Override
      public void run() {
        int position = 0;
        while (isLoop) {
          Message message = new Message();
          message.obj = position++;
          message.what = location;
          handler.sendMessage(message);
          Log.i("=============message==========", "" + location);
          if (position >= list.size()) {
            position = 0;
          }
          SystemClock.sleep(4000);
        }
      }
    }).start();
  }

  private Handler handler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == location) {
        viewPager.setCurrentItem((Integer) msg.obj);
      }
    }
  };

  private void initView() {
    //设置header
    headerView =
        (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(
            R.layout.fragment_news_list_header, null);
    viewPager = (ViewPager) headerView.findViewById(R.id.view_pager);

    FragmentNewsHeaderAdapter fragmentNewsHeaderAdapter =
        new FragmentNewsHeaderAdapter(this.getActivity(), list);
    viewPager.setAdapter(fragmentNewsHeaderAdapter);
    radioGroup = (RadioGroup) headerView.findViewById(R.id.layout_cotainer);

    viewPager.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageSelected(int postion) {
        if (radioGroup.getCheckedRadioButtonId() != listbutton[postion]) {// %listbutton.length
          ((RadioButton) (headerView.findViewById(listbutton[postion]))).setChecked(true);// %listbutton.length
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


    // 设置下拉刷新和上拉加载更多
    pull_refresh_list.setPullToRefreshOverScrollEnabled(true);
    pull_refresh_list.setMode(Mode.BOTH);

    // Set a listener to be invoked when the list should be refreshed.(设置一个监听器被调用时应刷新列表。)
    pull_refresh_list.setOnRefreshListener(new OnRefreshListener<ListView>() {
      @Override
      public void onRefresh(PullToRefreshBase<ListView> refreshView) {

        if (refreshView.isHeaderShown()) {
          Log.i("", "下拉刷新... ");
          String label =
              DateUtils.formatDateTime(FragmentNews.this.getActivity().getApplicationContext(),
                  System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                      | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

          // Update the LastUpdatedLabel
          refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

          // Do work to refresh the list here.
          new GetDataTask().execute();
        } else {
          Log.i("", "上拉加载更多... ");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上拉加载");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("放开以加载");
          // y = mListItems.size();

          new GetHeaderDataTask().execute();

        }
      }
    });
    
    //添加list点击响应
    pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewDetailActivity.intoNewDetailActivity(FragmentNews.this.getActivity());
        
      }});
  }

  private void setAdapter() {
    ListView actualListView = pull_refresh_list.getRefreshableView();

    // Need to use the Actual ListView when registering for Context Menu
    registerForContextMenu(actualListView);

    // mListItems = new LinkedList<String>();
    // mListItems.addAll(Arrays.asList(mStrings));
    actualListView.addHeaderView(headerView);
    mAdapter = new FragmentNewsListViewAdapter(this.getActivity());
    actualListView.setAdapter(mAdapter);
  }



  // 下拉加载数据
  private class GetDataTask extends AsyncTask<Void, Void, String[]> {

    @Override
    protected String[] doInBackground(Void... params) {
      // Simulates a background job.
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {}
      return mStrings;
    }

    @Override
    protected void onPostExecute(String[] result) {
      mAdapter.notifyDataSetChanged();

      // Call onRefreshComplete when the list has been refreshed.
      pull_refresh_list.onRefreshComplete();

      super.onPostExecute(result);
    }
  }

  // 上拉加载数据
  private class GetHeaderDataTask extends AsyncTask<Void, Void, String[]> {

    @Override
    protected String[] doInBackground(Void... params) {
      // Simulates a background job.
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {}
      return mStrings;
    }

    @Override
    protected void onPostExecute(String[] result) {
      mAdapter.notifyDataSetChanged();
      // 停止刷新
      pull_refresh_list.onRefreshComplete();
      // // 设置滚动条的位置 -- 加载更多之后，滚动条的位置应该在上一次划到的位置
      // lvShow.setSelectionFromTop(y, TRIM_MEMORY_BACKGROUNP);
      super.onPostExecute(result);
    }
  }

  // 假数据
  private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
      "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag",
      "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc",
      "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost",
      "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
      "Allgauer Emmentaler"};

  @Override
  public void onStop() {
    super.onStop();
    handler.removeMessages(0);
    isLoop = false;
  }

}
