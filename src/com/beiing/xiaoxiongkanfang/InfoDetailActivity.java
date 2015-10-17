package com.beiing.xiaoxiongkanfang;

import java.util.ArrayList;
import java.util.List;

import com.beiing.xiaoxiongkanfang.entity.Information;
import com.beiing.xiaoxiongkanfang.fragments.InfoDetailCommentFragment;
import com.beiing.xiaoxiongkanfang.fragments.InfoDetailContentFragment_type0;
import com.beiing.xiaoxiongkanfang.fragments.InfoDetailContentFragment_type1;
import com.beiing.xiaoxiongkanfang.fragments.InfoDetailContentFragment_type0.OnLoadDataListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class InfoDetailActivity extends FragmentActivity implements
		OnClickListener, OnLoadDataListener {

	private String newsId;
	private String targetId;
	private String type;
	private int imgCount;
	private int commentCount;
	/**
	 * 打开资讯详情页面
	 * 
	 * @param context
	 * @param newsid
	 * @param targetid
	 * @param type
	 *            : 资讯类型，文本多一点还是图片多一点
	 */
	public static void startInfoDetailActivity(Context context, String newsid,
			String targetid, String type, int imgCount, int commentCount) {
		Intent intent = new Intent(context, InfoDetailActivity.class);
		intent.putExtra("newsid", newsid);
		intent.putExtra("targetid", targetid);
		intent.putExtra("type", type);
		intent.putExtra("imgCount", imgCount);
		intent.putExtra("commentCount", commentCount);
		context.startActivity(intent);
	}

	private ViewPager viewPager;

	private VpFmAdapter vpFmAdapter;

	private List<Fragment> fragments;

	private ImageButton backIb;

	private ImageButton shareIb;

	private TextView titleTv;

	private TextView commentTv;

	private Button toggleBtn;

	private InfoDetailContentFragment_type0 contentFragment;
	private InfoDetailContentFragment_type1 contentFragment_type1;
	private InfoDetailCommentFragment commentFragment;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_info_detail);
		Intent intent = getIntent();
		newsId = intent.getStringExtra("newsid");
		targetId = intent.getStringExtra("targetid");
		type = intent.getStringExtra("type");
		imgCount = intent.getIntExtra("imgCount", 0);
		commentCount = intent.getIntExtra("commentCount", 0);
		initViews();
		toggleBtn.setText("评论:" + commentCount);

		initFragments();

		initViewPager();
		
		
	}
	

	private void initFragments() {
		fragments = new ArrayList<Fragment>();
		if (type.equals("0")) {
			contentFragment = InfoDetailContentFragment_type0
					.newInstance(newsId);
			fragments.add(contentFragment);
		} else {
			contentFragment_type1 = InfoDetailContentFragment_type1
					.newInstance(newsId, imgCount);
			fragments.add(contentFragment_type1);
			titleTv.setText("1/" + imgCount);//初始化显示第一张
		}
		commentFragment = InfoDetailCommentFragment.newInstance(targetId);

		fragments.add(commentFragment);
	}

	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.ac_info_detail_vp);
		vpFmAdapter = new VpFmAdapter(getSupportFragmentManager());
		viewPager.setAdapter(vpFmAdapter);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if(position == 0){
					toggleBtn.setText("评论:" + commentCount);
					titleTv.setText("房产资讯");
				}
				else{
					toggleBtn.setText("原文");
					titleTv.setText("评论");
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	private void initViews() {
		backIb = (ImageButton) findViewById(R.id.ac_info_detail_back_ib);
		shareIb = (ImageButton) findViewById(R.id.ac_info_detail_back_share_ib);
		titleTv = (TextView) findViewById(R.id.ac_info_detail_title_tv);
		commentTv = (TextView) findViewById(R.id.ac_info_detail_comment_tv);
		toggleBtn = (Button) findViewById(R.id.ac_info_detail_toggle);

		backIb.setOnClickListener(this);
		shareIb.setOnClickListener(this);
		commentTv.setOnClickListener(this);
		toggleBtn.setOnClickListener(this);
	}

	class VpFmAdapter extends FragmentPagerAdapter {

		public VpFmAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_info_detail_back_ib:
			finish();
			break;
		case R.id.ac_info_detail_back_share_ib:
			Toast.makeText(getApplicationContext(), "分享", 0).show();
			break;
			
		case R.id.ac_info_detail_toggle:
			if(viewPager.getCurrentItem() == 0){
				viewPager.setCurrentItem(1);
			}
				
			else{
				viewPager.setCurrentItem(0);
			}
				
			break;
			
		case R.id.ac_info_detail_comment_tv:
			Toast.makeText(getApplicationContext(), "评论", 0).show();
			break;
		}
	}

	@Override
	public void loadComplete(String title, String sub) {
		commentFragment.setTitleAndSub(title, sub);
	}
}
