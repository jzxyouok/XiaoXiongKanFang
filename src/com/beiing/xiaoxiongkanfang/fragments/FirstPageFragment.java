package com.beiing.xiaoxiongkanfang.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beiing.xiaoxiongkanfang.ChooseCityActivity;
import com.beiing.xiaoxiongkanfang.DiscountActivity;
import com.beiing.xiaoxiongkanfang.InfoDetailActivity;
import com.beiing.xiaoxiongkanfang.KaiPanActivity;
import com.beiing.xiaoxiongkanfang.R;
import com.beiing.xiaoxiongkanfang.XinFangActivity;
import com.beiing.xiaoxiongkanfang.adapters.InfoListAdapter;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask;
import com.beiing.xiaoxiongkanfang.asynctasks.LoadAsyncTask.LoadListener;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.entity.Advertisement;
import com.beiing.xiaoxiongkanfang.entity.Information;
import com.beiing.xiaoxiongkanfang.widget.TopView;
import com.beiing.xiaoxiongkanfang.widget.WaitingDialog;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FirstPageFragment extends Fragment implements OnClickListener {

	TopView topView;

	TextView cityTv;// 城市选择
	TextView searTv;// 搜索
	ImageButton saoIb;// 扫一扫
	TextView xinFangTv;// 新房
	TextView erShouTv;// 二手房
	TextView zuFangTv;// 租房
	TextView ziXunTv;// 资讯
	TextView youHuiTv;// 打折优惠
	TextView kaiPanTv;// 最新开盘
	TextView jiSuanTv;// 房贷计算
	TextView moreTv;// 更多
	TextView qBTv;// 领Q币
	TextView groupTv;// 看房团
	TextView remindeTv;// 还贷提醒
	private Context context;

	private PullToRefreshListView ptrListView;

	private ListView listView;

	private InfoListAdapter adapter;

	private final int FIRST_TIME = 0x001;// 第一次加载时
	private final int LOOK_MORE = 0x002;// 上拉显示更多
	private final int REFRESH = 0X003;// 下拉刷新时

	private String currentId = "1";// 默认是北京的id

	private LinearLayout moreLayout;

	private View head;

	// 小茶壶
	private ImageView waitingImageView;
	// 等待提示布局
	private RelativeLayout waitingRl;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_first_page, null);
		
		initWaitingView(view);
		
		initTop(view);
		ptrListView(view);
		initHead();
		return view;
	}
	
	/**
	 * 初始化等待界面
	 * @param view
	 */
	public  void initWaitingView(View view) {
		waitingRl = (RelativeLayout) view.findViewById(R.id.waiting_rl);
		waitingImageView = (ImageView) view.findViewById(R.id.waiting_iv);
		AnimationDrawable anim = (AnimationDrawable) waitingImageView
				.getBackground();
		anim.start();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadData(FIRST_TIME);
	}

	private void loadData(final int flag) {
		new LoadAsyncTask(context, new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					if (flag == REFRESH)
						adapter.clear();
					adapter.addData((List<Information>) result);
					ptrListView.onRefreshComplete();

					Log.i("--", "waitingRl == null" + (waitingRl == null));
					waitingRl.setVisibility(View.INVISIBLE);
				}
			}
		}).execute(getUrl(flag), LoadAsyncTask.LOAD_INFO_LIST);
	}

	// 初始化listView
	private void ptrListView(View view) {
		ptrListView = (PullToRefreshListView) view
				.findViewById(R.id.ac_main_center_lv);
		// 获取下拉的布局
		ILoadingLayout proxy = ptrListView.getLoadingLayoutProxy(false, true);
		proxy.setPullLabel("上拉显示显示更多");

		head = LayoutInflater.from(context).inflate(
				R.layout.ac_main_header_layout, null);
		listView = ptrListView.getRefreshableView();
		listView.addHeaderView(head);
		adapter = new InfoListAdapter(context);
		ptrListView.setAdapter(adapter);

		ptrListView.setMode(Mode.BOTH);
		ptrListViewEvent();
	}

	// listView的下拉刷新事件、分页事件、item点击事件
	private void ptrListViewEvent() {
		ptrListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase refreshView) {
						// TODO 下拉刷新
						loadData(REFRESH);
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						// TODO 查看更多
						loadData(LOOK_MORE);
					}
				});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("--", "posi:" + position);
				Information info = (Information) adapter.getItem(position - 2);
				InfoDetailActivity.startInfoDetailActivity(context,
						info.getId(), info.getCommentid(), info.getType(),
						info.getImagecount(), info.getCommentcount());
			}
		});

	}

	private void initTop(View view) {
		cityTv = (TextView) view.findViewById(R.id.ac_main_city_tv);
		searTv = (TextView) view.findViewById(R.id.ac_main_search_tv);
		saoIb = (ImageButton) view.findViewById(R.id.ac_main_sao_ib);
		cityTv.setOnClickListener(this);
		searTv.setOnClickListener(this);
		saoIb.setOnClickListener(this);
	}

	private void initHead() {
		// TODO 实例化控件
		topView = (TopView) head.findViewById(R.id.ac_main_ad_pager);
		initTopView();

		moreLayout = (LinearLayout) head.findViewById(R.id.ac_main_more_ll);

		xinFangTv = (TextView) head.findViewById(R.id.ac_main_grid_xinfang);
		erShouTv = (TextView) head.findViewById(R.id.ac_main_grid_ershou);
		zuFangTv = (TextView) head.findViewById(R.id.ac_main_grid_zufang);
		ziXunTv = (TextView) head.findViewById(R.id.ac_main_grid_zixun);
		youHuiTv = (TextView) head.findViewById(R.id.ac_main_grid_youhui);
		kaiPanTv = (TextView) head.findViewById(R.id.ac_main_grid_kaipan);
		jiSuanTv = (TextView) head.findViewById(R.id.ac_main_grid_calculate);
		moreTv = (TextView) head.findViewById(R.id.ac_main_grid_more);
		qBTv = (TextView) head.findViewById(R.id.ac_main_more_qb);
		groupTv = (TextView) head.findViewById(R.id.ac_main_kf_group);
		remindeTv = (TextView) head.findViewById(R.id.ac_main_remind);

		xinFangTv.setOnClickListener(this);
		erShouTv.setOnClickListener(this);
		zuFangTv.setOnClickListener(this);
		ziXunTv.setOnClickListener(this);
		youHuiTv.setOnClickListener(this);
		kaiPanTv.setOnClickListener(this);
		jiSuanTv.setOnClickListener(this);
		moreTv.setOnClickListener(this);
		qBTv.setOnClickListener(this);
		groupTv.setOnClickListener(this);
		remindeTv.setOnClickListener(this);
	}

	/**
	 * 加载广告信息
	 */
	private void initTopView() {
		new LoadAsyncTask(context, new LoadListener() {
			@Override
			public void loadSeccess(Object result) {
				if (result != null) {
					List<Advertisement> ads = (List<Advertisement>) result;
					List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
					Map<String, String> map = null;
					for (int i = 0; i < ads.size(); i++) {
						map = new HashMap<String, String>();
						map.put(TopView.KEY_TITLE, ads.get(i).getTitle());
						map.put(TopView.KEY_URL, ads.get(i).getPicurl());
						datas.add(map);
					}
					topView.setDatas(datas);
				}
			}
		}).execute(String.format(XxKanFUrls.FIRST_PAGE_WEBVIEW, currentId),
				LoadAsyncTask.LOAD_AD);
	}

	/**
	 * 根据不同条件返回不停的url
	 * 
	 * @param flag
	 * @return
	 */
	private String getUrl(int flag) {
		if (flag == FIRST_TIME)
			return String.format(XxKanFUrls.FIRST_PAGE_LISTVIEW, 10, 0, 0,
					currentId);
		else if (flag == LOOK_MORE)
			return String.format(XxKanFUrls.FIRST_PAGE_LISTVIEW, 20, 0, 1,
					currentId);
		else if (flag == REFRESH)
			return String.format(XxKanFUrls.FIRST_PAGE_LISTVIEW, 20, 1, 1,
					currentId);
		return null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_main_search_tv:

			break;
		case R.id.ac_main_sao_ib:

			break;
		case R.id.ac_main_grid_xinfang:
			XinFangActivity.startXinFangActivity(context, currentId, cityTv
					.getText().toString());
			break;
		case R.id.ac_main_grid_ershou:

			break;
		case R.id.ac_main_grid_zufang:

			break;
		case R.id.ac_main_grid_zixun:

			break;
		case R.id.ac_main_grid_youhui:
			DiscountActivity.startDiscountActivity(context, currentId);
			break;
		case R.id.ac_main_grid_kaipan:
			KaiPanActivity.startKaiPanActivity(context, currentId);
			break;
		case R.id.ac_main_grid_calculate:
			break;
		case R.id.ac_main_grid_more:
			if (moreLayout.isShown())
				moreLayout.setVisibility(View.GONE);
			else
				moreLayout.setVisibility(View.VISIBLE);
			break;

		case R.id.ac_main_city_tv:
			Intent chooseIntent = new Intent(context, ChooseCityActivity.class);
			chooseIntent.putExtra(XxKanFKeys.CITY_ID, currentId);
			startActivityForResult(chooseIntent, 9);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 选择另一个城市，更新数据
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
			String cityId = data.getStringExtra(XxKanFKeys.CITY_ID);
			String cityName = data.getStringExtra(XxKanFKeys.CITY_NAME);
			if (!currentId.equals(cityId)) {
				cityTv.setText(cityName);
				initTopView();// 广告信息重置
				currentId = cityId;
				loadData(REFRESH);// 加载另一个城市的数据
			}
		}
	}

}
