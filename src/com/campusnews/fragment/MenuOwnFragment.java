package com.campusnews.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.campusnews.NewDetailActivity;
import com.campusnews.R;
import com.campusnews.adapter.FragmentNewsListViewAdapter;
import com.campusnews.annotation.AndroidView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MenuOwnFragment extends BaseFragment {

  @AndroidView(R.id.pull_refresh_list)
  PullToRefreshListView pull_refresh_list;

  private FragmentNewsListViewAdapter mAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_menu_own, container, false);
  }



  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    setAdapter();
  }

  private void setAdapter() {
    ListView actualListView = pull_refresh_list.getRefreshableView();

    // Need to use the Actual ListView when registering for Context Menu
    registerForContextMenu(actualListView);

    mAdapter = new FragmentNewsListViewAdapter(this.getActivity());
    actualListView.setAdapter(mAdapter);
    actualListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewDetailActivity.intoNewDetailActivity(getActivity());
      }});
  }

  private void initView() {
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
              DateUtils.formatDateTime(MenuOwnFragment.this.getActivity().getApplicationContext(),
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

  }



  // 下拉加载数据
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

  // 上拉加载数据
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



}
