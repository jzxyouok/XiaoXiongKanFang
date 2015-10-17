package com.beiing.xiaoxiongkanfang.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.XinFangDetailActivity;
import com.beiing.xiaoxiongkanfang.adapters.XinFangListAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFConfigs;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.XinFangList;
import com.beiing.xiaoxiongkanfang.entity.XinFangList.XinFang;
import com.beiing.xiaoxiongkanfang.widget.SpinnerButton;
import com.beiing.xiaoxiongkanfang.widget.SpinnerButton.ViewCreatedListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class XinFangListFragment extends Fragment {

	
	public interface OnLoadListener{
		public void onLoadComplete();
	}
	
	OnLoadListener onLoadDataListener;
	
	
	
	public static XinFangListFragment newInStance(String cityid, String baseUrl) {
		XinFangListFragment xflf = new XinFangListFragment();
		Bundle bundle = new Bundle();
		bundle.putString(XxKanFKeys.CITY_ID, cityid);
		bundle.putString(XxKanFKeys.BASE_URL, baseUrl);
		xflf.setArguments(bundle);
		return xflf;
	}

	private String cityid;// ���� ID

	private String baseUrl;// �������·�url�����������¿���url

	private PullToRefreshListView ptrListView;

	private XinFangList xinFangList;// �·�����Դ

	private XinFangListAdapter adapter;

	private ListView listView;

	private TextView totalTv;// ��¥����

	private int curPage = 1;// ��С��1

	SpinnerButton regionSb, kindSb, priceSb, moreSb;

	// С���
	private ImageView waitingImageView;
	// �ȴ���ʾ����
	private RelativeLayout waitingRl;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		onLoadDataListener = (OnLoadListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cityid = getArguments().getString(XxKanFKeys.CITY_ID);
		baseUrl = getArguments().getString(XxKanFKeys.BASE_URL);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_xinfang_list, null);
		ptrListView = (PullToRefreshListView) view
				.findViewById(R.id.fm_xinfang_lv);
		ptrListView.setMode(Mode.PULL_FROM_END);
		listView = ptrListView.getRefreshableView();
		totalTv = (TextView) view.findViewById(R.id.fm_xinfang_total_tv);

		regionSb = (SpinnerButton) view
				.findViewById(R.id.fm_xf_list_search_region);
		kindSb = (SpinnerButton) view.findViewById(R.id.fm_xf_list_search_kind);
		priceSb = (SpinnerButton) view
				.findViewById(R.id.fm_xf_list_search_price);
		moreSb = (SpinnerButton) view.findViewById(R.id.fm_xf_list_search_more);

		createSpinButtonViews();

		ptrListViewEvent();
		return view;
	}

	private void createSpinButtonViews() {
		// TODO ����spinnerbutton�ĵ�������
		regionSb.setResIdAndViewCreatedListener(R.layout.ac_main_top_layout,
				new ViewCreatedListener() {
					@Override
					public void onViewCreated(View v) {

					}
				});

		kindSb.setResIdAndViewCreatedListener(R.layout.fm_xf_search_listview,
				new ViewCreatedListener() {
					@Override
					public void onViewCreated(View v) {
						ListView listView = (ListView) v;
						listView.setAdapter(new ArrayAdapter<String>(
								getActivity(), R.layout.fm_xf_search_item,
								R.id.search_tv, XxKanFConfigs.fang_kinds));
					}
				});

		priceSb.setResIdAndViewCreatedListener(R.layout.fm_xf_search_listview,
				new ViewCreatedListener() {
					@Override
					public void onViewCreated(View v) {
						ListView listView = (ListView) v;
						listView.setAdapter(new ArrayAdapter<String>(
								getActivity(), R.layout.fm_xf_search_item,
								R.id.search_tv, XxKanFConfigs.fang_prices));
					}
				});

		moreSb.setResIdAndViewCreatedListener(R.layout.ac_main_top_layout,
				new ViewCreatedListener() {
					@Override
					public void onViewCreated(View v) {

					}
				});

	}

	private void ptrListViewEvent() {
		// TODO ����listView����¼�
		ptrListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (adapter.getCount() < Integer.parseInt(xinFangList
						.getTotal()))
					LoadData(++curPage);// ������һҳ
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XinFang fang = (XinFang) adapter.getItem(position - 1);
				XinFangDetailActivity.startXinFangDetailActivity(getActivity(),
						fang.getFid(), fang.getFcover());
				Log.i("--", "item ���ʱ��houseId = " + fang.getFid());
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		xinFangList = new XinFangList();
		adapter = new XinFangListAdapter(getActivity());
		ptrListView.setAdapter(adapter);
		LoadData(curPage);// ��һ�μ�������
	}

	private void LoadData(int curPage) {
		new LoadAsyncTask(getActivity(), new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					xinFangList = (XinFangList) result;
					totalTv.setText("����" + xinFangList.getTotal() + "��¥��");
					adapter.addData(xinFangList.getXfList());
				}
				ptrListView.onRefreshComplete();// ������һҳ���
				onLoadDataListener.onLoadComplete();
			}
		}).execute(getUrl(curPage), LoadAsyncTask.LOAD_XINFANG_LIST);
	}

	private String getUrl(int curPage) {
		return String.format(baseUrl, curPage, cityid);
	}

}
