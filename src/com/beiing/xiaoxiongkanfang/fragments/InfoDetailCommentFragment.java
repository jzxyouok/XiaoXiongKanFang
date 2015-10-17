package com.beiing.xiaoxiongkanfang.fragments;

import java.util.List;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.adapters.InfoCommentAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.InfoComment;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class InfoDetailCommentFragment extends Fragment {
	
	public static InfoDetailCommentFragment newInstance(String targetId) {
		InfoDetailCommentFragment commentFragment = new InfoDetailCommentFragment();
		Bundle bundle = new Bundle();
		bundle.putString("targetid", targetId);
		commentFragment.setArguments(bundle);
		return commentFragment;
	}
	

	private PullToRefreshListView ptrListView;
	
	private ListView listView;
	
	private List<InfoComment> list;
	
	private InfoCommentAdapter adapter;

	private TextView titleTv;
	private TextView subTv;
	
	private String targetId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		targetId = getArguments().getString("targetid");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info_detail_comments,
				null);
		ptrListView = (PullToRefreshListView) view
				.findViewById(R.id.ac_main_center_lv);
		View headView = inflater.inflate(R.layout.ac_info_detail_header, null);
		titleTv = (TextView) headView.findViewById(R.id.fm_info_detail_title);
		subTv = (TextView) headView.findViewById(R.id.fm_info_detail_sub);
		
		ptrListView.setMode(Mode.PULL_FROM_END);
		listView = ptrListView.getRefreshableView();
		listView.addHeaderView(headView);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new LoadAsyncTask(getActivity(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if(result != null){
					list = (List<InfoComment>) result;
					adapter = new InfoCommentAdapter(getActivity(), list);
					ptrListView.setAdapter(adapter);
				}
			}
		}).execute(String.format(XxKanFUrls.NEWS_COMMENT, targetId), LoadAsyncTask.LOAD_INFO_COMMENT);
	}
	
	/**
	 * 共有方法
	 * @param title
	 * @param sub
	 */
	public void setTitleAndSub(String title, String sub){
		if(titleTv != null)
			titleTv.setText(title);
		if(subTv != null)
			subTv.setText(sub);
	}
	
	

}





