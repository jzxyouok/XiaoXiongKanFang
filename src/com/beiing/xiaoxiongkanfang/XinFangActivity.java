package com.beiing.xiaoxiongkanfang;

import com.baidu.mapapi.SDKInitializer;
import com.beiing.xiaoxiongkanfang.configs.XxKanFKeys;
import com.beiing.xiaoxiongkanfang.configs.XxKanFUrls;
import com.beiing.xiaoxiongkanfang.fragments.XinFangListFragment;
import com.beiing.xiaoxiongkanfang.fragments.XinFangListFragment.OnLoadListener;
import com.beiing.xiaoxiongkanfang.fragments.XinFangMapFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XinFangActivity extends Activity implements OnLoadListener{

	public static void startXinFangActivity(Context context, String cityid,
			String cityName) {
		Intent intent = new Intent(context, XinFangActivity.class);
		intent.putExtra(XxKanFKeys.CITY_ID, cityid);
		intent.putExtra(XxKanFKeys.CITY_NAME, cityName);
		context.startActivity(intent);
	}

	private Context context;
	private int containerId = R.id.ac_xinfang_fm;

	private ImageButton mapIb;
	private TextView titleTv;

	private FragmentManager fManager;

	private XinFangListFragment fangListFragment;
	private XinFangMapFragment fangMapFragment;

	private final int XF_LIST = 0;// 显示新房详情列表界面
	private final int XF_MAP = 1;// 显示地图界面
	private int shownPage = XF_LIST;

	// 小茶壶
	private ImageView waitingImageView;
	// 等待提示布局
	private RelativeLayout waitingRl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// SDKInitializer.initialize(getApplicationContext());// 初始化地图
		setContentView(R.layout.activity_xinfang);
		context = this;
		mapIb = (ImageButton) findViewById(R.id.ac_xinfang_map_ib);
		titleTv = (TextView) findViewById(R.id.ac_xinfang_title_tv);
		fManager = getFragmentManager();

		String cityid = getIntent().getStringExtra(XxKanFKeys.CITY_ID);
		String cityName = getIntent().getStringExtra(XxKanFKeys.CITY_NAME);
		fangListFragment = XinFangListFragment.newInStance(cityid,
				XxKanFUrls.LOOKING_NEWHOUSE);
		fangMapFragment = XinFangMapFragment.newInStance(cityid, cityName);
		fManager.beginTransaction().add(containerId, fangListFragment)
				.add(containerId, fangMapFragment).hide(fangMapFragment)
				.commit();

		initWaitingView();
	}

	
	/**
	 * 初始化等待界面
	 * @param view
	 */
	public void initWaitingView() {
		waitingRl = (RelativeLayout) findViewById(R.id.waiting_rl);
		waitingImageView = (ImageView) findViewById(R.id.waiting_iv);
		AnimationDrawable anim = (AnimationDrawable) waitingImageView
				.getBackground();
		anim.start();
	}

	public void onBtnClick(View v) {
		switch (v.getId()) {
		case R.id.ac_xinfang_back_ib:
			finish();
			break;
		case R.id.ac_xinfang_map_ib:
			if (shownPage == XF_LIST) {
				// 显示地图
				mapIb.setBackground(getResources().getDrawable(
						R.drawable.btn_map_list_selector));
				shownPage = XF_MAP;
				fManager.beginTransaction().hide(fangListFragment)
						.show(fangMapFragment).commit();
			} else {
				// 显示列表
				mapIb.setBackground(getResources().getDrawable(
						R.drawable.btn_map_selector));
				shownPage = XF_LIST;
				fManager.beginTransaction().hide(fangMapFragment)
						.show(fangListFragment).commit();
			}
			break;

		case R.id.ac_xinfang_search_ib:
			Toast.makeText(context, "搜索", 0).show();

			break;

		default:
			break;
		}
	}


	@Override
	public void onLoadComplete() {
		waitingRl.setVisibility(View.INVISIBLE);
	}

}
