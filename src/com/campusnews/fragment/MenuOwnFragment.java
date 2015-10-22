package com.campusnews.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.campusnewes.bean.ActivitiesListBean;
import com.campusnewes.bean.ActivitiesListBean.ActivitiesListData;
import com.campusnews.NewDetailActivity;
import com.campusnews.R;
import com.campusnews.adapter.FragmentNewsListViewAdapter;
import com.campusnews.annotation.AndroidView;
import com.campusnews.model.JsonObjectRequestBase;
import com.campusnews.model.UserInfo;
import com.campusnews.util.StaticUrl;
import com.campusnews.util.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import de.greenrobot.event.EventBus;

public class MenuOwnFragment extends BaseFragment {

  @AndroidView(R.id.pull_refresh_list)
  PullToRefreshListView pull_refresh_list;

  private FragmentNewsListViewAdapter mAdapter;
  private List<ActivitiesListData> listData; 

  private int onRefreshLoad=1;
  private int onMoreLoad=2;
  private int refreshType=-1;
  
  private int mPageStart=1;
  public  int mPage=UserInfo.mPage;//消息条数

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_menu_own, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    EventBus.getDefault().register(this);
    initView();
    setAdapter();
    requestData();
  }

  /**
   * 设置adapter
   */
  private void setAdapter() {
    ListView actualListView = pull_refresh_list.getRefreshableView();

    // Need to use the Actual ListView when registering for Context Menu
    registerForContextMenu(actualListView);

    mAdapter = new FragmentNewsListViewAdapter(this.getActivity());
    actualListView.setAdapter(mAdapter);
    actualListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listData!=null){
          ToastUtil.show(position+"");
          NewDetailActivity.intoNewDetailActivity(MenuOwnFragment.this.getActivity(),listData.get(position-1));
          }
      }
    });
  }

  /**
   * 初始化数据
   */
  private void initView() {
    listData= new ArrayList<>();
    
    // 设置下拉刷新和上拉加载更多
    pull_refresh_list.setPullToRefreshOverScrollEnabled(true);
    pull_refresh_list.setMode(Mode.BOTH);

    // Set a listener to be invoked when the list should be refreshed.(设置一个监听器被调用时应刷新列表。)
    pull_refresh_list.setOnRefreshListener(new OnRefreshListener<ListView>() {
      @Override
      public void onRefresh(PullToRefreshBase<ListView> refreshView) {

        if (refreshView.isHeaderShown()) {
          refreshType=onRefreshLoad;
          mPageStart=1;
          Log.i("", "下拉刷新... ");
          String label =
              DateUtils.formatDateTime(MenuOwnFragment.this.getActivity().getApplicationContext(),
                  System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                      | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

          // Update the LastUpdatedLabel
          refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

          // Do work to refresh the list here.
          new GetDataTask().execute();
          requestData();
        } else {
          refreshType=onMoreLoad;
          mPageStart=mPageStart+5;
          mPage=mPage+5;
          Log.i("", "上拉加载更多... ");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上拉加载");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setPullLabel("");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
          pull_refresh_list.getLoadingLayoutProxy(false, true).setReleaseLabel("放开以加载");
          // y = mListItems.size();

          new GetHeaderDataTask().execute();
          requestData();
        }
      }
    });
    
    


  }



  /**
   * 下拉加载数据
   * 
   * @author password
   *
   */
  private class GetDataTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
      // Simulates a background job.
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {}
      return "1";
    }

    @Override
    protected void onPostExecute(String result) {
      mAdapter.notifyDataSetChanged();

      // Call onRefreshComplete when the list has been refreshed.
      pull_refresh_list.onRefreshComplete();

      super.onPostExecute(result);
    }
  }

  /**
   * 上拉加载数据
   * 
   * @author password
   *
   */
  private class GetHeaderDataTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
      // Simulates a background job.
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {}
      return "1";
    }

    @Override
    protected void onPostExecute(String result) {
      mAdapter.notifyDataSetChanged();
      // 停止刷新
      pull_refresh_list.onRefreshComplete();
      // // 设置滚动条的位置 -- 加载更多之后，滚动条的位置应该在上一次划到的位置
      // lvShow.setSelectionFromTop(y, TRIM_MEMORY_BACKGROUNP);
      super.onPostExecute(result);
    }
  }

  /**
   * 请求数据
   */
  private void requestData() {

    HashMap<String, String> params = new HashMap<String, String>();
    params.put("user_id",UserInfo.userId);
    params.put("pagestart", String.valueOf(mPageStart) );
    params.put("pageposition",String.valueOf(mPage) );

    JsonObjectRequestBase jsonObjectRequestBase =
        new JsonObjectRequestBase(this.getActivity(), params, StaticUrl.Query_activityUrl);
    jsonObjectRequestBase.makeSampleHttpRequest(ActivitiesListBean.class);
  }
  
  
  public void onEvent(ActivitiesListBean listData){
//    if (listData.isSucceed()) {
//      this.listData=listData;
//      mAdapter.setData(listData.result);
//    }
    if (listData.isSucceed()) {
      if(refreshType==onRefreshLoad){//下拉刷新
        this.listData.clear();
        this.listData.addAll(listData.result); 
        mAdapter.setData(this.listData);
      }else{//上拉加载
        if(listData.result.size()==0){
          Toast.makeText(this.getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
        }else{
          this.listData.addAll(listData.result); 
          mAdapter.setData(this.listData);
        }
      }
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
