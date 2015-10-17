package com.beiing.xiaoxiongkanfang.fragments;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.adapters.XFDetailCommentAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.XinFangDetailComment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class XFDetailCommentsFragment extends Fragment {

	public interface FreshListener{
		public void onFreshComplete(int commentCount);
	}
	
	FreshListener freshListener;
	
	public static XFDetailCommentsFragment newInstance(String houseId) {
		XFDetailCommentsFragment xfcf = new XFDetailCommentsFragment();
		Bundle bundle = new Bundle();
		bundle.putString(XxKanFKeys.HOUSE_ID, houseId);
		xfcf.setArguments(bundle);
		return xfcf;
	}

	ListView ptrListView;

	XFDetailCommentAdapter commentAdapter;

	String houseId;

	int curPage = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		freshListener =  (FreshListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		houseId = getArguments().getString(XxKanFKeys.HOUSE_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_xf_detail_comments, null);
		ptrListView = (ListView) view
				.findViewById(R.id.fm_xf_detail_comments_lv);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		commentAdapter = new XFDetailCommentAdapter(getActivity());
		ptrListView.setAdapter(commentAdapter);


		loadData();
	}

	public void loadData() {
		new LoadAsyncTask(getActivity(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					List<XinFangDetailComment> newData = (List<XinFangDetailComment>) result;
					commentAdapter.addData(newData);
					freshListener.onFreshComplete(commentAdapter.getCount());
				}
				
			}
		}).execute(getUrl(), LoadAsyncTask.LOAD_XINFANG_DETAIL_COMMENT);
	}

	private String getUrl() {
		return String.format(XxKanFUrls.NEW_HOUSE_COMMENT,
				Integer.parseInt(houseId), ++curPage);
	}
}
